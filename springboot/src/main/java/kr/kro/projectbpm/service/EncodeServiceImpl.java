package kr.kro.projectbpm.service;

import org.springframework.stereotype.Service;

@Service
public class EncodeServiceImpl implements EncodeService {
    @Override
    public String encodePassword(String password) {
        long result = 0, curWeight = 1;
        for (int i = 0; i < password.length(); i++) {
            curWeight = curWeight * WEIGHT % MOD;
            result = (result + curWeight * password.charAt(i)) % MOD;
        }
        return Long.toString(result);
    }

    @Override
    public String encodeId(String id) {
        String result = id.substring(0, id.length()/2);
        while(result.length()<id.length()) result += '*';
        return result;
    }
}
