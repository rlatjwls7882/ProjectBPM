package kr.kro.projectbpm.service;

public interface EncodeService {
    long WEIGHT = 128;
    long MOD = 1_000_000_007;
    String encodePassword(String password);
    String encodeId(String id);
}
