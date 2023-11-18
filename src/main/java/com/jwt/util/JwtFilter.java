package com.jwt.util;

import java.io.IOException;
import java.security.Security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.service.MyUserDetailsService;

@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private MyUserDetailsService service;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authorizationHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
		{
			token = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(token);
		}
		
		if(username != null  && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails details = service.loadUserByUsername(username);
			if(jwtUtil.validateToken(token, details))
			{
				UsernamePasswordAuthenticationToken authenticationToken = new 
						UsernamePasswordAuthenticationToken(details,null,details.getAuthorities());
			
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}
		}
		filterChain.doFilter(request, response);
	}
}
