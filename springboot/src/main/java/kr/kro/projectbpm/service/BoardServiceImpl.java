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

/**
 * 게시판 서비스 구현 클래스입니다.
 * 이 클래스는 게시글의 생성, 조회, 수정, 삭제 등의 기능을 제공합니다.
 * @see BoardRepository
 * @see UserRepository
 * @see CategoryRepository
 */
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private static final int PAGE_SIZE = 10;
    private static final Sort latest = Sort.by(Sort.Order.desc("boardNum"));

    /**
     * 게시판의 시작 시간을 반환합니다.
     * @param sortType 정렬 타입에 따라 시작 시간을 결정합니다.
     * @return 시작 시간
     */
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

    /**
     * 게시판의 모든 게시글을 가져옵니다.
     * @return 게시글 목록
     */
    @Override
    public List<BoardDto> getBoards() {
        return ((List<Board>) boardRepository.findAll()).stream().map(BoardDto::new).toList();
    }

    /**
     * 특정 사용자의 게시글 개수를 반환합니다.
     * @param userDto 사용자 정보
     * @return 게시글 개수
     */
    @Override
    public long getBoardCnt(UserDto userDto) {
        return boardRepository.countBoardsByUserId(userDto.getId());
    }

    /**
     * 게시글 목록을 최신순으로 가져옵니다.
     * @return 게시글 목록 페이지
     */
    @Override
    public Page<BoardDto> getLists() {
        return getLists("latest", "", 0);
    }

    /**
     * 게시글 목록을 정렬 타입과 검색어에 따라 가져옵니다.
     * @param sortType 정렬 타입 (latest, popular_today, popular_week, popular_month)
     * @param query 검색어
     * @param pageNumber 페이지 번호
     * @return 게시글 목록 페이지
     */
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

    /**
     * 특정 사용자의 게시글 목록을 정렬 타입과 검색어에 따라 가져옵니다.
     * @param sortType 정렬 타입 (latest, popular_today, popular_week, popular_month)
     * @param query 검색어
     * @param id 사용자 ID
     * @param pageNumber 페이지 번호
     * @return 게시글 목록 페이지
     */
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

    /**
     * 특정 카테고리의 게시글 목록을 정렬 타입에 따라 가져옵니다.
     * @param categoryNum 카테고리 번호
     * @param sortType 정렬 타입 (latest, popular_today, popular_week, popular_month)
     * @param pageNumber 페이지 번호
     * @return 게시글 목록 페이지
     */
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

    /**
     * 게시글을 생성합니다.
     * @param title 게시글 제목
     * @param content 게시글 내용
     * @param userId 게시글 작성자 ID
     * @param categoryNum 카테고리 번호 (0이면 카테고리 없음)
     * @return 생성된 게시글 번호
     */
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

    /**
     * 게시글 번호로 게시글을 가져옵니다.
     * @param boardNum 게시글 번호
     * @return 게시글 DTO
     */
    @Override
    public BoardDto getBoard(long boardNum) {
        return new BoardDto(boardRepository.findByBoardNum(boardNum));
    }

    /**
     * 특정 카테고리에 속한 게시글 개수를 반환합니다.
     * @param categoryNum 카테고리 번호
     * @return 해당 카테고리에 속한 게시글 개수
     */
    @Override
    public long countByCategoryNum(long categoryNum) {
        return boardRepository.countByCategoryNum(categoryNum);
    }

    /**
     * 게시글을 삭제합니다.
     * @param boardNum 게시글 번호
     */
    @Override
    public void deleteBoard(long boardNum) {
        boardRepository.deleteById(boardNum);
    }

    /**
     * 게시글을 수정합니다.
     * @param boardNum 게시글 번호
     * @param title 새 제목
     * @param content 새 내용
     */
    @Override
    @Transactional
    public void editBoard(long boardNum, String title, String content) {
        Board board = boardRepository.findByBoardNum(boardNum);
        board.edit(title, content);
    }

    /**
     * 게시글의 작성자가 아닌지 확인합니다.
     * @param boardNum 게시글 번호
     * @param id 사용자 ID
     */
    @Override
    public void checkBoard(long boardNum, Object id) {
        if(!boardRepository.findByBoardNum(boardNum).getUser().getId().equals(id)) {
            throw new IllegalStateException("id 불일치");
        }
    }
}
