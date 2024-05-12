package com.foodapp.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodapp.config.JwtProvider;
import com.foodapp.model.Cart;
import com.foodapp.model.USER_ROLE;
import com.foodapp.model.User;
import com.foodapp.repository.CartRepository;
import com.foodapp.repository.UserRepository;
import com.foodapp.request.LoginRequest;
import com.foodapp.response.AuthResponse;
import com.foodapp.service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception
	{
		
		User isEmailExist = userRepository.findByEmail(user.getEmail());
		
		if(isEmailExist!=null)
		{
			throw new Exception("Email is already used with another account!");
		}
		
		User createUser=new User();
		createUser.setEmail(user.getEmail());
		createUser.setFullName(user.getFullName());
		createUser.setRole(user.getRole());
		createUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		
		User savedUser = userRepository.save(createUser);
		
		Cart cart=new Cart();
		cart.setCustomer(savedUser);
		
		cartRepository.save(cart);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt=jwtProvider.generateToken(authentication);
		AuthResponse authResponse= new AuthResponse();
		
		authResponse.setJwt(jwt);
		authResponse.setMessage("Register Success!");
		authResponse.setRole(savedUser.getRole());
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
	}
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req )
	{
		
		String username = req.getEmail();
		String password = req.getPassword();
		
		Authentication  authentication=authenticate(username,password);
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
		String jwt = jwtProvider.generateToken(authentication);
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Login Success!");
		authResponse.setRole(USER_ROLE.valueOf(role));
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
	}

	private Authentication authenticate(String username, String password) {
		
		
		UserDetails userDetails=customUserDetailsService.loadUserByUsername(username);
		if(userDetails==null)
		{
			throw new BadCredentialsException("Invalid username!");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword()))
		{
			throw new BadCredentialsException("Invalid password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
	}
}
