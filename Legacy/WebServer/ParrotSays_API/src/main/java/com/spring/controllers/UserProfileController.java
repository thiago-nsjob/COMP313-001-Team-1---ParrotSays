package com.spring.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.spring.models.User;
import com.spring.models.UserDTO;
import com.spring.models.UserRepository;
import com.spring.security.jwt.ServletUtil;
import com.spring.security.jwt.JwtUtil;


@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private UserRepository repo;

    @Secured({ "ROLE_ADMIN" })
    @GetMapping(value = "/user/{username}",produces = "application/json")
    public UserDTO getUserById(@PathVariable String username, HttpServletResponse response) throws IOException {
    	User user = repo.findByUsername(username);//.orElseThrow(() -> new NotFoundException("UserId "+ id+ " Not found."));
    	
    	ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
	        
    }
    
    // Create a new user
    @Secured({ "ROLE_ADMIN" })
    @PostMapping(value = "/createuser", produces = "application/json")
    public void createUser(@Valid @RequestBody User user, HttpServletResponse response) throws IOException {
    	
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        try
        {
	        repo.save(user);
	        
	        String json = ServletUtil.getJson("ok", "New User Created successfully.");
	        ServletUtil.write(response, HttpStatus.OK, json);
        }
        catch(Exception exc)
        {
        	ServletUtil.write(response, HttpStatus.CONFLICT, ServletUtil.getJson("error", exc.getCause().toString()));
        }
     }
    
    //Change Password
    @PutMapping("/changepwd")
    public void changePassword(@Valid @RequestBody String newpassword, HttpServletRequest request, HttpServletResponse response) throws IOException{
    	try{
    		 String token = request.getHeader("Authorization");

	        if (StringUtils.isEmpty(token) || !token.startsWith("Bearer ")) {
	        	ServletUtil.write(response, HttpStatus.FORBIDDEN, ServletUtil.getJson("error", "Forbidden Access."));
	        }


            if(! JwtUtil.isTokenValid(token)) {
                throw new AccessDeniedException("Token is not valid.");
            }

            String login = JwtUtil.getLogin(token);
	            
    		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    		User actualUser = repo.findByUsername(login);
    		actualUser.setPassword(encoder.encode(newpassword));
    		repo.save(actualUser);
    		String json = ServletUtil.getJson("ok", "Password Changed successfully.");
	        ServletUtil.write(response, HttpStatus.OK, json);
    	}
    	catch(Exception exc)
    	{
    		ServletUtil.write(response, HttpStatus.BAD_REQUEST, ServletUtil.getJson("error", exc.getCause().toString()));
    	}
    }
    
    
}
