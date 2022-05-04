package coltrolcoin.models.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginRequestDTO {
	
	@NotNull()
	public String password;
	
	@Email()
	@NotNull()
	public String email;
	
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

