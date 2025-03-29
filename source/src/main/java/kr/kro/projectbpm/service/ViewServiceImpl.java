
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

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {
    private final ViewRepository viewRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public void createView(BoardDto boardDto) {
        Board board = boardRepository.findByBoardNum(boardDto.getBoardNum());
        board.read();
        View view = new View(board);
        viewRepository.save(view);
    }

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
