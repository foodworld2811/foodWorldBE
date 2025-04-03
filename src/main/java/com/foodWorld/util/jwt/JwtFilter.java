package com.foodWorld.util.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.foodWorld.entity.User;
import com.foodWorld.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserService userServe;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = null;
		String userName = null;
		
		String header = request.getHeader("Authorization");
		if(header != null && header.startsWith("Bearer ")) {
			token = header.substring(7);
			userName = jwtUtil.extractUsername(token);
		}
		
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			Optional<User> optinalUser = userServe.getUserByUsername(userName);
			
			User user = optinalUser.get();
			
			if(jwtUtil.validateToken(token, userName)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			} 
		}
		filterChain.doFilter(request, response);
	}
	
	
}