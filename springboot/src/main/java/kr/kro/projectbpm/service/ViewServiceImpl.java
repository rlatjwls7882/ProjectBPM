
package kr.kro.projectbpm.service;

import kr.kro.projectbpm.domain.Board;
import kr.kro.projectbpm.domain.User;
import kr.kro.projectbpm.domain.View;
import kr.kro.projectbpm.dto.BoardDto;
import kr.kro.projectbpm.dto.UserDto;
import kr.kro.projectbpm.repository.BoardRepository;
import kr.kro.projectbpm.repository.UserRepository;
import kr.kro.projectbpm.repository.ViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 게시글 조회 서비스 구현 클래스입니다.
 * 이 클래스는 게시글 조회수 관리 기능을 제공합니다.
 * @see ViewRepository
 * @see BoardRepository
 * @see UserRepository
 */
@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {
    private final ViewRepository viewRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /**
     * 게시글을 생성합니다.
     * @param boardDto 게시글 정보
     */
    @Override
    public void createView(BoardDto boardDto) {
        Board board = boardRepository.findByBoardNum(boardDto.getBoardNum());
        board.read();
        View view = new View(board);
        viewRepository.save(view);
    }

    /**
     * 게시글 조회수를 반환합니다.
     * @param userDto 사용자 정보
     * @param type 조회 기간 (today, week, month)
     * @return 조회수
     */
    @Override
    public long getViewCnt(UserDto userDto, String type) {
        User user = userRepository.findUserById(userDto.getId());
        LocalDateTime start = switch (type) {
            case "today" -> LocalDateTime.now().minusDays(1);
            case "week" -> LocalDateTime.now().minusWeeks(1);
            default -> LocalDateTime.now().minusMonths(1);
        };
        return viewRepository.findViewCountByUserId(user.getId(), start);
    }
}
