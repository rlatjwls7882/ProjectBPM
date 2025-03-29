package kr.kro.projectbpm.service;

import kr.kro.projectbpm.dto.BoardDto;

import java.util.List;

public interface BoardService {
    List<BoardDto> getLists();
    List<BoardDto> getLists(String sortType);
    List<BoardDto> getLists(String sortType, String query);
    List<BoardDto> getLists(String sortType, String query, String id);
    List<BoardDto> getLists(long categoryNum, String sortType);
    long createBoard(String title, String content, String userId, long categoryNum);
    BoardDto getBoard(long boardNum);
    void deleteBoard(long boardNum);
    void editBoard(long boardNum, String title, String content);
    boolean checkBoard(long boardNum, Object id);
}
