package com.dn.tech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dn.tech.dto.UserDTO;
import com.dn.tech.exception.EmailAlreadyExistsException;
import com.dn.tech.model.Rating;
import com.dn.tech.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("users")
public class UserController {

		public UserController() {
			System.out.println("User Conntroller object created");
		}
		Logger logger=LoggerFactory.getLogger(UserController.class);
		@Autowired
		private UserService userService;

		@PostMapping("/saveuser")
		public ResponseEntity<UserDTO> saveUser(
		        @RequestBody UserDTO userDTO) throws Exception {

		    UserDTO result = userService.saveUser(userDTO);

		    return ResponseEntity
		            .status(HttpStatus.CREATED)
		            .body(result);
		}
		
		 @PutMapping("/updateuser")
		    public UserDTO updateUser(@RequestBody UserDTO dto) throws Exception {
		        return userService.updateUser(dto);
		    }

		    @DeleteMapping("/deleteuser")
		    public UserDTO deleteUser(@RequestBody UserDTO dto) {
		        return userService.deleteUser(dto);
		    }
		    
		    @GetMapping("/findbyid/{Id}")  //New endpoint to find user by IdPK
		    public UserDTO findUserByUserId(@PathVariable("Id") Long userId) {
		    	logger.info("value of UserId is: "+userId);
		        return userService.findUserById(userId);
		    }

		    @GetMapping("/findalluser")
		    public List<UserDTO> findAllUser() {
		    	return userService.UserFindAllRecord();
		    }
		    
		    @GetMapping("/findbyuserid/{userId}")  //New endpoint to find user by userId
		    public UserDTO findUserByUserId(@PathVariable("userId") String userId) {
		    	logger.info("value of UserId is: "+userId);
		        return userService.findUserByUserId(userId);
		        
		   
		    }
		    
		    @GetMapping("/findByIdMS/{Id}")  //New endpoint to find user by IdPK
			public UserDTO findUserByUserIdUsingRatingMS(@PathVariable("Id") Long userId) {
		    	logger.info("value of UserId is: "+userId);
			    return userService.findUserByIdUsingRatingMS(userId);
			    }
		    
		    @GetMapping("/findByCustomuseridMS/{userId}")  //New endpoint to find user by userId
		    public UserDTO findUserByCustomUserIdUsingRatingMS(@PathVariable("userId") String userId) {
		    	logger.info("value of UserId is: "+userId);
		        return userService.findUserByUserCustomIdUsingMS(userId);
		        
		   
		    }
		    
		    @GetMapping("/findalluserUsingMS")
		    public List<UserDTO> findAllUserUsinRatingService() {
		    	return userService.UserFindAllRecordUsingRatingMS();
		    }
		    
		    @GetMapping("/findByCustomuseridWithHotelMS/{userId}")  //New endpoint to find user by userId
		    public UserDTO findUserByCustomUserIdUsingRatingMWithHotel(@PathVariable("userId") String userId) {
		    	logger.info("value of UserId is: "+userId);
		        return userService.findUserByUserCustomIdAllDetails(userId);
		        
		   
		    }
		    
		    @GetMapping("/findalluserUsingRatingWithHotelMS")
		    public List<UserDTO> findAllUserUsinRatingWithHotelService() {
		    	return userService.UserFindAllRecordUsingRatingMSWithHotels();
		    }
		    
		    @FeignClient(name = "RatingService")
		    public interface RatingServiceFeignClient {

		        @GetMapping("/ratings/findallrating")
		        List<Rating> getAllRatings();
		    }
		    
		    @Value("${server.port}")
		    private String port;

		    @GetMapping("/loadbalance")
		    public String testLoadBalancing() {
		        return "Request handled by User Service on port: " + port;
		    }
}
