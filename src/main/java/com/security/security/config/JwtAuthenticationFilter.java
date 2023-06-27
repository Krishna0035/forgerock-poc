package com.security.security.config;



import com.google.gson.Gson;
import com.security.security.dto.UserToken;
import com.security.security.dto.response.ResponseDto;
import com.security.security.externalservice.ForgerockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

//	@Autowired
//	private JwtUserDetailService jwtUserDetailService;


	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return Arrays.asList(SecurityConfig.URLS_THAT_DONT_NEED_AUTHENTICATION).contains(request.getRequestURI());
	}


	@Value("${active}")
	private Integer active;
	
	@Autowired
	private ForgerockService forgerockService;
	
	@Autowired
	private Environment environment;

	@Autowired
	private Gson gson;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String requestTokenHeader = request.getHeader("token");
		final String username = request.getHeader("username");
//		System.out.println(request.getRequestURI());
		String mobile = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		logger.info(" incoming token = "+requestTokenHeader);
//		if (requestTokenHeader != null) {
//
////			if(requestTokenHeader.startsWith("Bearer ")){
////				jwtToken = requestTokenHeader.substring(7);
////			}else {
////				jwtToken=requestTokenHeader;
////			}
//
//
////			try {
////				mobile = jwtUtils.getUsernameFromToken(jwtToken);
////			} catch (IllegalArgumentException e) {
////				System.out.println("Unable to get JWT Token");
////			} catch (ExpiredJwtException e) {
////				System.out.println("JWT Token has expired");
////			}
//		} else {
//			logger.warn("JWT Token does not begin with Bearer String");
//		}

		// Once we get the token validate it.
//		if (mobile != null && SecurityContextHolder.getContext().getAuthentication() == null) {


			// this will come from respective service

//			Map<String, Object> responseMap = isTokenActiveAndUserActive(mobile, jwtToken);





			String role =isValidToken(requestTokenHeader,username);


			Object principle = getPrinciple(requestTokenHeader,username);

//			Object principle = responseMap.get("principle");
//
//			UserDetails userDetails = (UserDetails) responseMap.get("userDetails");

			if (role.equals("ROLE_ADMIN") || role.equals("ROLE_USER"))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						principle, null, Arrays.asList(role).stream()
						.map(r -> new SimpleGrantedAuthority(r))
						.collect(Collectors.toList()));
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
//		}else {
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid User");
//		}
		System.out.println("out of filter");
	}


//	private Map<String,Object> isTokenActiveAndUserActive(String email, String token){
//
//
//		UserDetails userDetails = this.jwtUserDetailService.loadUserByUsername(email);
//
//
//		//validates if the username is phone number or mobile address
//		User user = userRepository.findByEmailAndStatus(email, active).orElseThrow(() -> new UsernameNotFoundException("User name not found"));
//
//
//
//
//		Map<String,Object> responseMap = new HashMap<>();
//
//
//
//
//
//
//		boolean isTokenValidForActiveUsers = jwtUtils.validateTokenWExpirationValidation(token, userDetails)
//				&& user.getStatus().equals(active);
//
//		UserToken principleForUser = getPrinciple(user,token);
//
//		responseMap.put("isTokenActiveAndUserActive",isTokenValidForActiveUsers);
//		responseMap.put("principle",principleForUser);
//		responseMap.put("userDetails",userDetails);
//
//		return responseMap;
//	}



	private UserToken getPrinciple(String username,String token){

		UserToken principal=new UserToken();
//		principal.setId(user.getId());
		principal.setStatus(1);
		principal.setUsername(username);
//		principal.setUser(user);
		principal.setToken(token);

		return principal;
	}



	private String isValidToken(String token,String username){

		ResponseDto userDetails = forgerockService.getUserDetails(token, username);

		Map<String ,Object> map = gson.fromJson(String.valueOf(userDetails.getData()), Map.class);

		List<String> roles = gson.fromJson(gson.toJson(map.get("roles")), List.class);

		if(!Objects.isNull(roles)  && roles.size()>0 && roles.contains("ui-self-service-user")){
			return "ROLE_USER";
		}else {
			return isValidTokenForAdmin( token, username);
		}

	}


	private String  isValidTokenForAdmin(String token,String username){

		ResponseDto userDetails = forgerockService.getAdminDetails(token, username);

		Map<String ,Object> map = gson.fromJson(String.valueOf(userDetails.getData()), Map.class);

		List<String> roles = gson.fromJson(gson.toJson(map.get("roles")), List.class);

		if(!Objects.isNull(roles) && roles.size()>0 && roles.contains("ui-realm-admin")){
			return "ROLE_ADMIN";
		}else {
			return "";
		}

	}
}
