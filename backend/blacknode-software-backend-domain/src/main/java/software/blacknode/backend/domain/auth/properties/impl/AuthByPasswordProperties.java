package software.blacknode.backend.domain.auth.properties.impl;

import lombok.NoArgsConstructor;
import software.blacknode.backend.domain.auth.properties.AuthProperties;

@NoArgsConstructor
public class AuthByPasswordProperties implements AuthProperties {

	private String passwordHash;
	private String salt;
	
	public void changePassword(String password) {
		// Implement password hashing logic here
		// For example, generate a salt, hash the password with the salt, and store them
	}
	
	public boolean verifyPassword(String password) {
		// Implement password verification logic here
		// For example, hash the input password with the stored salt and compare with passwordHash
		return false;
	}
}
