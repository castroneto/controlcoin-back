package coltrolcoin.models.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;


public class SignupRequestDTO {
	
	@NotNull()
	public String username;
	
	@NotNull()
	public String password;
	
	@Email()
	@NotNull()
	public String email;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
