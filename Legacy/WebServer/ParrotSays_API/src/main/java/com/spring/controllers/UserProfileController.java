package com.spring.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.spring.models.Report;
import com.spring.models.Roles;
import com.spring.models.RolesRepository;
import com.spring.models.User;
import com.spring.models.UserDTO;
import com.spring.models.UserRepository;
import com.spring.security.jwt.ServletUtil;

import ch.qos.logback.core.encoder.Encoder;
import javassist.NotFoundException;

import com.spring.security.jwt.JwtUtil;


@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private UserRepository repo;
    
    @Autowired
    private RolesRepository rolerepo;

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
        	Roles role = rolerepo.findById((long)3).orElseThrow(() -> new NotFoundException("RoleId 3 Not found."));
        	user.setSingleRole(role);
	        repo.save(user);
	        
	        String json = ServletUtil.getJson("ok", "New User Created successfully.");
	        ServletUtil.write(response, HttpStatus.OK, json);
        }
        catch(Exception exc)
        {
        	ServletUtil.write(response, HttpStatus.CONFLICT, ServletUtil.getJson("error", exc.getCause().toString()));
        }
     }
    
    @Secured({ "ROLE_USER" })
    // Updates the user password and/or e-mail based on username into the token.
    @PutMapping(value = "/updateuser", produces = "application/json")
    public void updateUser(@Valid @RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        try
        {
        	String token = request.getHeader("Authorization");

	        if (StringUtils.isEmpty(token) || !token.startsWith("Bearer ")) {
	        	ServletUtil.write(response, HttpStatus.FORBIDDEN, ServletUtil.getJson("error", "Forbidden Access."));
	        }

            if(! JwtUtil.isTokenValid(token)) {
                throw new AccessDeniedException("Token is not valid.");
            }

            String login = JwtUtil.getLogin(token);
    		
    		User actualUser = repo.findByUsername(login);
        	if (user.getEmail().length() > 0) actualUser.setEmail(user.getEmail());
        	if (user.getPassword().length() > 0) actualUser.setPassword(encoder.encode(user.getPassword()));
	        repo.save(actualUser);
	        
	        String json = ServletUtil.getJson("ok", "User updated successfully.");
	        ServletUtil.write(response, HttpStatus.OK, json);
        }
        catch(Exception exc)
        {
        	ServletUtil.write(response, HttpStatus.CONFLICT, ServletUtil.getJson("error", exc.getCause().toString()));
        }
     }
    
    
    // Assigns a role to a user
    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/addrole/{login}/{roleid}")
    public void addrole(@PathVariable String login, @PathVariable long roleid, HttpServletRequest request, HttpServletResponse response) throws IOException{
    	try{
    		 	            
    		User actualUser = repo.findByUsername(login);
    		Roles role = rolerepo.findById(roleid).orElseThrow(() -> new NotFoundException("RoleId "+ roleid+ " Not found."));
    		if(actualUser.setSingleRole(role)) 
    		{
	    		repo.save(actualUser);
	    		String json = ServletUtil.getJson("ok", "Role assigned successfully.");
		        ServletUtil.write(response, HttpStatus.OK, json);
    		}
    		else
    		{
    			ServletUtil.write(response, HttpStatus.BAD_REQUEST, ServletUtil.getJson("Error", "It is impossible to add this role."));
    		}
    	}
    	catch(Exception exc)
    	{
    		ServletUtil.write(response, HttpStatus.BAD_REQUEST, ServletUtil.getJson("error", exc.getCause().toString()));
    	}
    }
    
    
}
