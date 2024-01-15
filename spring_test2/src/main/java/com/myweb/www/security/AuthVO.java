package com.myweb.www.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthVO {
	
	private String email;
	private String auth;

}
