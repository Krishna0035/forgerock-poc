package com.security.security;



import com.security.security.config.SecurityConfig;
import com.security.security.dto.UserToken;
import com.security.security.entity.User;
import com.security.security.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUserDetailService jwtUserDetailService;
	
	@Autowired
	private JwtUtils jwtUtils;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return Arrays.asList(SecurityConfig.URLS_THAT_DONT_NEED_AUTHENTICATION).contains(request.getRequestURI());
	}
	@Autowired
	private UserRepository userRepository;

	@Value("${active}")
	private Integer active;
	

	
	@Autowired
	private Environment environment;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String requestTokenHeader = request.getHeader("Authorization");
//		System.out.println(request.getRequestURI());
		String mobile = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		logger.info(" incoming token = "+requestTokenHeader);
		if (requestTokenHeader != null) {

			if(requestTokenHeader.startsWith("Bearer ")){
				jwtToken = requestTokenHeader.substring(7);
			}else {
				jwtToken=requestTokenHeader;
			}


			try {
				mobile = jwtUtils.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		// Once we get the token validate it.
		if (mobile != null && SecurityContextHolder.getContext().getAuthentication() == null) {


			// this will come from respective service

			Map<String, Object> responseMap = isTokenActiveAndUserActive(mobile, jwtToken);





			boolean isTokenActiveAndUserActiveBool = Boolean.parseBoolean(responseMap.get("isTokenActiveAndUserActive").toString());

			Object principle = responseMap.get("principle");

			UserDetails userDetails = (UserDetails) responseMap.get("userDetails");

			if (isTokenActiveAndUserActiveBool)
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						principle, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				filterChain.doFilter(request, response);
			}else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid User");
			}
		}else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid User");
		}
		System.out.println("out of filter");
	}


	private Map<String,Object> isTokenActiveAndUserActive(String email, String token){


		UserDetails userDetails = this.jwtUserDetailService.loadUserByUsername(email);


		//validates if the username is phone number or mobile address
		User user = userRepository.findByEmailAndStatus(email, active).orElseThrow(() -> new UsernameNotFoundException("User name not found"));




		Map<String,Object> responseMap = new HashMap<>();






		boolean isTokenValidForActiveUsers = jwtUtils.validateTokenWExpirationValidation(token, userDetails)
				&& user.getStatus().equals(active);

		UserToken principleForUser = getPrinciple(user,token);

		responseMap.put("isTokenActiveAndUserActive",isTokenValidForActiveUsers);
		responseMap.put("principle",principleForUser);
		responseMap.put("userDetails",userDetails);

		return responseMap;
	}



	private UserToken getPrinciple(User user,String token){

		UserToken principal=new UserToken();
		principal.setId(user.getId());
		principal.setStatus(user.getStatus());
		principal.setUsername(user.email);
		principal.setUser(user);
		principal.setToken(token);

		return principal;
	}




}
