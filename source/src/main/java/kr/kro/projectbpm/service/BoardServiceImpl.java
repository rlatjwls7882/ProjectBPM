package kr.kro.projectbpm.service;

import kr.kro.projectbpm.domain.Board;
import kr.kro.projectbpm.dto.BoardDto;
import kr.kro.projectbpm.dto.UserDto;
import kr.kro.projectbpm.repository.BoardRepository;
import kr.kro.projectbpm.repository.CategoryRepository;
import kr.kro.projectbpm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private static final int PAGE_SIZE = 10;
    private static final Sort latest = Sort.by(Sort.Order.desc("boardNum"));

    private LocalDateTime getStartTime(String sortType) {
        switch (sortType) {
            case "popular_today" -> {
                return LocalDateTime.now().minusDays(1);
            }
            case "popular_week" -> {
                return LocalDateTime.now().minusWeeks(1);
            }
            default -> {
                return LocalDateTime.now().minusMonths(1);
            }
        }
    }

    @Override
    public List<BoardDto> getBoards() {
        return ((List<Board>) boardRepository.findAll()).stream().map(BoardDto::new).toList();
    }

    @Override
    public long getBoardCnt(UserDto userDto) {
        return boardRepository.countBoardsByUserId(userDto.getId());
    }

    @Override
    public Page<BoardDto> getLists() {
        return getLists("latest", "", 0);
    }

    @Override
    public Page<BoardDto> getLists(String sortType, String query, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, latest);
        if(sortType.equals("latest")) {
            return boardRepository.findByTitleContainingIgnoreCase(query, pageable).map(BoardDto::new);
        } else {
            LocalDateTime start = getStartTime(sortType);
            return boardRepository.findBoardsSortedByViews(start, query, pageable).map(BoardDto::new);
        }
    }

    @Override
    public Page<BoardDto> getLists(String sortType, String query, String id, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, latest);
        if(sortType.equals("latest")) {
            return boardRepository.findByUserIdAndTitleContainingIgnoreCase(id, query, pageable).map(BoardDto::new);
        } else {
            LocalDateTime start = getStartTime(sortType);
            return boardRepository.findBoardsByUserIdSortedByViews(id, start, query, pageable).map(BoardDto::new);
        }
    }

    @Override
    public Page<BoardDto> getLists(long categoryNum, String sortType, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, latest);
        if(sortType.equals("latest")) {
            return boardRepository.findBoardsByCategoryNum(categoryNum, pageable).map(BoardDto::new);
        } else {
            LocalDateTime start = getStartTime(sortType);
            return boardRepository.findBoardsByCategoryNumSortedByViews(categoryNum, start, pageable).map(BoardDto::new);
        }
    }

    @Override
    public long createBoard(String title, String content, String userId, long categoryNum) {
        Board board = new Board(title, content, userRepository.findUserById(userId));
        if(categoryNum==0) {
            board.setCategory(null);
        } else {
            board.setCategory(categoryRepository.findCategoryByNum(categoryNum));
        }
        boardRepository.save(board);
        return board.getBoardNum();
    }

    @Override
    public BoardDto getBoard(long boardNum) {
        return new BoardDto(boardRepository.findByBoardNum(boardNum));
    }

    @Override
    public long countByCategoryNum(long categoryNum) {
        return boardRepository.countByCategoryNum(categoryNum);
    }


    @Override
    public void deleteBoard(long boardNum) {
        boardRepository.deleteById(boardNum);
    }

    @Override
    @Transactional
    public void editBoard(long boardNum, String title, String content) {
        Board board = boardRepository.findByBoardNum(boardNum);
        board.edit(title, content);
    }

    @Override
    public boolean checkBoard(long boardNum, Object id) {
        return !boardRepository.findByBoardNum(boardNum).getUser().getId().equals(id);
    }
}
