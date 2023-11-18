package com.jwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.entity.AuthRequest;
import com.jwt.entity.User;
import com.jwt.repository.UserRepository;
import com.jwt.util.JwtUtil;

@RestController
public class MainController {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private JwtUtil jwtUtil;
	

	@Autowired
	private UserRepository repository;
	
	@GetMapping("/")
	public String Welcome()
	{
		return "this is welcome page.";
	}
	
	@PostMapping("/auth")
	public String generateToken(@RequestBody AuthRequest request) throws Exception
	{
		try
		{
			manager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUserName(),request.getPassword())
					);
		}
		
		catch (Exception e) {
			throw new Exception("Invalid userName and password");
		}
		
		return  jwtUtil.generateToken(request.getUserName()) ;
	}
	
	@PostMapping("/user")
	  private User createUser(@RequestBody User user )
	  {
		  User result = this.repository.save(user);
		  return result;
	  }
	
	@GetMapping("/users")
	private List<User> getAllUsers()
	{
		List<User> list = this.repository.findAll();
		return list;
	}
	
}
