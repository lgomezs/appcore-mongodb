package pe.com.core.mongo.mongodb.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CredentialsProperties {

	private String username;	
	private String password;	
	private Integer	securityMechanism;
	private String	salt;
	private String	key;
	
}
