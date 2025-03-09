package kr.kro.projectbpm.service;

import kr.kro.projectbpm.domain.Board;
import kr.kro.projectbpm.domain.User;

import java.util.List;

public interface BoardService {
    List<Board> getLists();
    List<Board> getLists(String sortType);
    List<Board> getLists(String sortType, String query);
    void createBoard(String title, String content, User user);
    Board getBoard(long boardNum);
    void deleteBoard(long boardNum);
    void editBoard(long boardNum, String title, String content);
    boolean checkBoard(long boardNum, Object id);
}
