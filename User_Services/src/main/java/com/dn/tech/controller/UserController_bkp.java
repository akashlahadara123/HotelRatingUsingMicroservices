//package com.dn.tech.controller;
//
//import com.dn.tech.dto.UserDTO;
//import com.dn.tech.service.UserService;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/users") // Base URL for the controller
//public class UserController {
//
//	Logger logger=LoggerFactory.getLogger(UserController.class);
//    public UserController() {
//        System.out.println("User controller Object Created");
//    }
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/saveuser")
//    public UserDTO saveUser(@RequestBody UserDTO userDTO) throws Exception {
//        System.out.println("value of Ccontrol Dto: " + userDTO.getUserId());
//        System.out.println("value of Ccontrol email: " + userDTO.getEmail());
//        return userService.saveUser(userDTO);
//    }
//
//    @PutMapping("/updateuser")
//    public UserDTO updateUser(@RequestBody UserDTO dto) throws Exception {
//        return userService.updateUser(dto);
//    }
//
//    @DeleteMapping("/deleteuser")
//    public UserDTO deleteUser(@RequestBody UserDTO dto) {
//        return userService.deleteUser(dto);
//    }
//
//    @GetMapping("/findbyid/{id}")
//    public UserDTO findUserById(@PathVariable("id") Long id) {
//        return userService.UserSingleRecord(id);
//    }
//
//    @GetMapping("/findbyuserid/{userId}") // New endpoint to find user by userId
//    public UserDTO findUserByUserId(@PathVariable("userId") String userId) {
//    	logger.info("value of UserId is: "+userId);
//        return userService.findUserByUserId(userId);
//    }
//
//    @GetMapping("/findalluser")
//    public List<UserDTO> findAllUser() {
//        return userService.UserFindAllRecord();
//    }
//    
//    @GetMapping("/externalalluser")
//    public ResponseEntity<List<UserDTO>> getAllUsersFromMicroservice() {
//        List<UserDTO> users = userService.UserFindAllRecordWithRatingFromAnotherMicroservice();
//        return ResponseEntity.ok(users);
//    }
//   
//    @GetMapping("/findByUserIdforRatingWithHotel/{userId}") // New endpoint to find user by userId
//    public UserDTO findUserWithRatingAndHotelByUserId(@PathVariable("userId") String userId) {
//    	
//        return userService.findUserWithHotelNameByUserId(userId);
//    }
//    
//    @GetMapping("/alluserwithnameandrating")
//    public ResponseEntity<List<UserDTO>> getAllUsersWithHotelAndRatingFromMicroservice() {
//        List<UserDTO> users = userService.UserFindAllRecordWithRatingAndHotelFromAnotherMicroservice();
//        return ResponseEntity.ok(users);
//    }
//    /*
//    @GetMapping("/findByIdUsingFeign/{userId}")
//    public UserDTO findByIdUsingFeign(@PathVariable String userId) {
//        return userService.findUserByUserIdWithFeignClient(userId);
//    }
//    */
//}


