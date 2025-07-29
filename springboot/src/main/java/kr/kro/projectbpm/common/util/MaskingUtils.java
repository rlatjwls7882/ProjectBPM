package kr.kro.projectbpm.common.util;

public class MaskingUtils {
    private MaskingUtils() {} // 유틸 클래스 인스턴스화 방지
    /**
     * 주어진 ID의 앞 절반을 유지하고 나머지 부분을 '*'로 마스킹합니다.
     * @param id 마스킹할 ID
     * @return 마스킹된 ID
     */
    public static String encodeId(String id) {
        String result = id.substring(0, id.length()/2);
        while(result.length()<id.length()) result += '*';
        return result;
    }
}
