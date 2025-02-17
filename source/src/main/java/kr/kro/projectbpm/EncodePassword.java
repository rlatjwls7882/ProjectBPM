package kr.kro.projectbpm;

public class EncodePassword {
    final static long WEIGHT = 128;
    final static long MOD = 1_000_000_007;
    public static long encodePassword(String password) {
        long result=0, curWeight=1;
        for(int i=0;i<password.length();i++) {
            curWeight = curWeight * WEIGHT % MOD;
            result = (result + curWeight * password.charAt(i)) % MOD;
        }
        return result;
    }
}
