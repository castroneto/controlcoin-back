package coltrolcoin.utils;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class EncryptUtil {

    public String encode(String password) {
    	BCryptPasswordEncoder bycrypt = new BCryptPasswordEncoder();
    	return bycrypt.encode(password);
    }
    
    public Boolean compare(String password, String hash) {
    	BCryptPasswordEncoder bycrypt = new BCryptPasswordEncoder();
    	return bycrypt.matches(password, hash);
    }
    
}
