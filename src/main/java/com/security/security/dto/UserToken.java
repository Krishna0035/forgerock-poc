package com.security.security.dto;

import lombok.*;
import org.springframework.lang.NonNull;

@EqualsAndHashCode(callSuper=false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToken {
	
	@NonNull
	private long id;
	
	@NonNull
	private String username;
	
	@NonNull
	private Integer status;
	
	@NonNull
	private String channel;
	
	@NonNull
	private Object user;


	private String token;

}
