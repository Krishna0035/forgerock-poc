package com.security.security.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@PropertySource("classpath:constant/constant.properties")
public class SecurityConfig {

    public static final List<GrantedAuthority> ROLE_USER = AuthorityUtils.createAuthorityList("ROLE_USER");
    public static final String[] URLS_THAT_DONT_NEED_AUTHENTICATION = {
            "/swagger-ui.html","/swagger-ui/index.html",
            "/favicon.ico","/swagger-ui/swagger-ui.css","/swagger-ui/index.css",
            "/swagger-ui/swagger-initializer.js",
            "/bus/v3/api-docs/", "/add/password","/swagger-ui/swagger-ui-standalone-preset.js",

            "/webjars/**","/swagger-ui/swagger-ui-bundle.js","/v3/api-docs/swagger-config","/v3/api-docs","/authentication/login/admin","/authentication/register",
            "/authentication/login", "/login", "/login.jsp","/loginnn","/party/customer","/swagger-ui/favicon-32x32.png","/demo"};

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${settings.cors.origin}")
    private String corsOrigin ;


//    @Bean
//    public SecurityFilterChain securityFilterChainForSwagger(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//
//                .antMatchers("/swagger-ui/**").hasRole("ADMIN")
//                .antMatchers("/authentication/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
////                .loginPage("/login")
//                .defaultSuccessUrl("/swagger-ui/index.html")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .permitAll();
//
//        return http.build();
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails adminUser = User
                .withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(adminUser);
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
//                .authenticationManager(authenticationManager)
                .csrf().disable()
                .cors(withDefaults())
                .authorizeHttpRequests((authz) -> authz

                                .antMatchers("/swagger-ui/index.html","/authentication/login","/authentication/login/admin","/authentication/register").permitAll()

                        .antMatchers(URLS_THAT_DONT_NEED_AUTHENTICATION).permitAll()
                        .antMatchers("/test/test").hasRole("USER")
                        .antMatchers("/test/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and().addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();



//        http
////                .authenticationManager(authenticationManager)
//                .csrf().disable()
//                .cors()
//                .and()
//                .authorizeRequests(authorize -> authorize
//                        .antMatchers(URLS_THAT_DONT_NEED_AUTHENTICATION).permitAll()
//                        .antMatchers("/swagger-ui/index.html").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login") // Specify the login page URL
//                        .permitAll()
////                        .defaultSuccessUrl("/swagger-ui/index.html") // Specify the default success URL after authentication
//                )
//                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .and()
//                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//
//        return http.build();


    }

    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern(corsOrigin);
//       configuration.addAllowedOrigin(corsOrigin);
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("X-Requested-With");
        configuration.addAllowedHeader("authorization");
        configuration.addAllowedHeader("multipart/form-data");
        configuration.setAllowCredentials(false);
        configuration.setAllowedMethods(Arrays.asList("GET","POST", HttpMethod.OPTIONS.toString()));
        configuration.setMaxAge((long) 86400);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
        return openApi -> openApi.getComponents()
                .addSecuritySchemes("token",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP));
    }


    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}


