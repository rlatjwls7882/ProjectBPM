package kr.kro.projectbpm.service;

import kr.kro.projectbpm.dto.BoardDto;
import kr.kro.projectbpm.dto.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardService {
    long getBoardCnt(UserDto userDto);
    List<BoardDto> getBoards();
    Page<BoardDto> getLists();
    Page<BoardDto> getLists(String sortType, String query, int pageNumber);
    Page<BoardDto> getLists(String sortType, String query, String id, int pageNumber);
    Page<BoardDto> getLists(long categoryNum, String sortType, int pageNumber);
    long createBoard(String title, String content, String userId, long categoryNum);
    BoardDto getBoard(long boardNum);
    void deleteBoard(long boardNum);
    void editBoard(long boardNum, String title, String content);
    void checkBoard(long boardNum, Object id);
    long countByCategoryNum(long categoryNum);
}
