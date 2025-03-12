package kr.kro.projectbpm.service;

import kr.kro.projectbpm.domain.Board;
import kr.kro.projectbpm.domain.User;
import kr.kro.projectbpm.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public List<Board> getLists() {
        return getLists("latest", "");
    }

    @Override
    public List<Board> getLists(String sortType) {
        return getLists(sortType, "");
    }

    @Override
    public List<Board> getLists(String sortType, String query) {
        if(sortType.equals("latest")) {
            return boardRepository.findByTitleContainingIgnoreCase(Sort.by(Sort.Order.desc("boardNum")), query);
        }
        LocalDateTime start = switch (sortType) {
            case "popular_today" -> LocalDateTime.now().minusDays(1);
            case "popular_week" -> LocalDateTime.now().minusWeeks(1);
            default -> LocalDateTime.now().minusMonths(1);
        };
        return boardRepository.findBoardsSortedByViews(start, query);
    }

    @Override
    public void createBoard(String title, String content, User user) {
        Board board = new Board(title, content, user);
        boardRepository.save(board);
    }

    @Override
    public Board getBoard(long boardNum) {
        return boardRepository.findByBoardNum(boardNum);
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
