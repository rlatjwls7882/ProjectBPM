package kr.kro.projectbpm.common.util;

import jakarta.servlet.http.HttpSession;

public class SessionUtils {
    SessionUtils(){}
    /**
     * 세션에서 사용자 ID를 가져오거나 예외를 발생시킵니다.
     *
     * @param session HTTP 세션
     * @return UUID 형식의 사용자 ID
     * @throws IllegalStateException 로그인하지 않은 상태일 때 발생
     */
    public static String getUserIdOrThrow(HttpSession session) {
        String userId = (String) session.getAttribute("id");
        if(userId == null) {
            throw new IllegalStateException("로그인하지 않은 상태입니다.");
        }
        return userId;
    }
}
