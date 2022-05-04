package coltrolcoin.models;

import java.io.Serializable;
import java.util.Date;

import coltrolcoin.utils.JwtTokenUtil;

public class JwtResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public String token;
	public Date expirationTime;
	
	public JwtResponse(String jwttoken, JwtTokenUtil tokenUtil) {
		this.token = jwttoken;
		this.expirationTime = tokenUtil.decodeToken(jwttoken).getExpiration();
	}

}
