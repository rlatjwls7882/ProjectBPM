package kr.kro.projectbpm.service;

import kr.kro.projectbpm.dto.BoardDto;
import kr.kro.projectbpm.dto.UserDto;

public interface ViewService {
    void createView(BoardDto boardDto);
    long getViewCnt(UserDto userDto, String type);
}