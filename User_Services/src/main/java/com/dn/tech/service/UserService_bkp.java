//package com.dn.tech.service;
//
//import com.dn.tech.dao.UserDAO;
//import com.dn.tech.dto.UserDTO;
//import com.dn.tech.exception.EmailAlreadyExistsException;
//import com.dn.tech.exception.UserNotFoundException;
//import com.dn.tech.external.service.HotelServiceFeignClient;
//import com.dn.tech.external.service.RatingServiceFeignClient;
//import com.dn.tech.model.Hotel;
//import com.dn.tech.model.Rating;
//import com.dn.tech.model.User;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class UserService {
//
//    public UserService() {
//        System.out.println("User Service Object Created");
//    }
//
//    Logger log = LoggerFactory.getLogger(UserService.class);
//
//    @Autowired
//    private UserDAO dao;
//
//    @Autowired
//    RestTemplate restTemplate;
//    
//    @Autowired
//    HotelServiceFeignClient hs;
//    
////    @Autowired
////    private RatingServiceFeignClient rs;
//
//    public UserDTO saveUser(UserDTO userDTO) throws Exception {
//        if (dao.existsByEmail(userDTO.getEmail())) {
//            throw new EmailAlreadyExistsException("Email already exists: " + userDTO.getEmail() + " Please Try with Different Email");
//        }
//        User user = new User();
//        BeanUtils.copyProperties(userDTO, user);
//        user = dao.save(user);
//        UserDTO result = new UserDTO();
//        BeanUtils.copyProperties(user, result);
//        return result;
//    }
//
//    public UserDTO updateUser(UserDTO userDTO) throws Exception {
//        if (!dao.existsById(userDTO.getId())) {
//            throw new UserNotFoundException("User does not exist in the database for update.");
//        }
//
//        User user = new User();
//        BeanUtils.copyProperties(userDTO, user);
//        user.setId(userDTO.getId()); // Make sure the ID is set for update
//        dao.save(user);
//
//        return userDTO;
//    }
//
//    public UserDTO deleteUser(UserDTO userDTO) {
//        if (!dao.existsById(userDTO.getId())) {
//            throw new UserNotFoundException("User does not exist in the database for deletion.");
//        }
//
//        User user = new User();
//        BeanUtils.copyProperties(userDTO, user);
//        dao.delete(user);
//
//        return userDTO;
//    }
//
//    public UserDTO UserSingleRecord(Long id) {
//        Optional<User> optionalUser = dao.findById(id);
//        if (!optionalUser.isPresent()) {
//            throw new UserNotFoundException("User does not exist in the database for ID: " + id);
//        }
//
//        UserDTO userDTO = new UserDTO();
//        BeanUtils.copyProperties(optionalUser.get(), userDTO);
//
//        // Fetching ratings logic can go here if necessary
//        return userDTO;
//    }
//
//    public UserDTO findUserByUserId(String userId) {
//        Optional<User> optionalUser = dao.findByUserId(userId);
//        if (!optionalUser.isPresent()) {
//            throw new UserNotFoundException("User does not exist in the database for User ID: " + userId);
//        }
//
//        UserDTO userDTO = new UserDTO();
//        BeanUtils.copyProperties(optionalUser.get(), userDTO);
//        log.info("value of DTO is: "+userDTO);
//        log.info("value of specficUserId is: "+ userDTO.getUserId());
//        // Fetch ratings (assuming the service returns an array of ratings)
//        //String url = "http://localhost:8092/ratings/findbyuserid/" + userDTO.getUserId();
//      //this is also working but the problem is we change port no server ip it;s not working if used service then no need to use ip and port no
//        String url = "http://RATINGSERVICE/ratings/findbyuserid/" + userDTO.getUserId();
//        log.info("Fetching ratings from URL: " + url);
//
//        // Fetching an array of Rating objects
//        Rating[] ratings = restTemplate.getForObject(url, Rating[].class);
//        log.info("value of rating is for Record with UserId Specfic User ratings: "+ratings);
//        // Convert array to list and set the ratingsList in UserDTO
//        List<Rating> ratingsList = ratings != null ? Arrays.asList(ratings) : new ArrayList<>();
//        log.info("value of rating is for Record with UserId Specfic User ratingsList is: "+ratingsList);
//        /*
//         * At line no 104 we can do like this 
//         * 
//         * 	List<Rating> ratingsList;
//if (ratings != null) {
//    ratingsList = Arrays.asList(ratings);
//} else {
//    ratingsList = new ArrayList<>();
//}
//
//         * */
//        userDTO.setRatingsList(ratingsList);
//       // log.info(userDTO.setRatingsList(ratingsList).toString());
//        log.info("Retrieved ratings: " + ratingsList);
//
//        return userDTO;
//    }
//
//
//    public List<UserDTO> UserFindAllRecord() {
//        List<User> users = dao.findAll();
//        if (users.isEmpty()) {
//            throw new UserNotFoundException("No Users are available in the database.");
//        }
//
//        return users.stream()
//                .map(user -> {
//                    UserDTO dto = new UserDTO();
//                    BeanUtils.copyProperties(user, dto);
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//    
//    public List<UserDTO> UserFindAllRecordWithRatingFromAnotherMicroservice() {
//        List<User> users = dao.findAll();
//        if (users.isEmpty()) {
//            throw new UserNotFoundException("No Users are available in the database.");
//        }
//       // String url = "http://localhost:8092/ratings/findallrating";
//        String url = "http://RATINGSERVICE/ratings/findallrating";
//        log.info("Fetching ratings from URL: " + url);
//       
//        // Fetching an array of Rating objects
//        Rating[] ratings = restTemplate.getForObject(url, Rating[].class);
//        log.info("value of rating is from all user is: "+ratings);
//        
//        // Convert array to list and set the ratingsList in UserDTO
//        List<Rating> ratingsList = ratings != null ? Arrays.asList(ratings) : new ArrayList<>();
//        
//        return users.stream()
//                .map(user -> {
//                    UserDTO dto = new UserDTO();
//                    BeanUtils.copyProperties(user, dto);
//                    dto.setRatingsList(ratingsList);
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//    
//    
//    public UserDTO findUserWithHotelNameByUserId(String userId) {
//        Optional<User> optionalUser = dao.findByUserId(userId);
//        if (!optionalUser.isPresent()) {
//            throw new UserNotFoundException("User does not exist in the database for User ID: " + userId);
//        }
//
//        UserDTO userDTO = new UserDTO();
//        BeanUtils.copyProperties(optionalUser.get(), userDTO);
//
//        // Fetch ratings from the microservice
//        //String urlForRating = "http://localhost:8092/ratings/findbyuserid/" + userDTO.getUserId();
//        String urlForRating = "http://RATINGSERVICE/ratings/findbyuserid/" + userDTO.getUserId();
//        log.info("Fetching ratings from URL: " + urlForRating);
//        Rating[] ratings = restTemplate.getForObject(urlForRating, Rating[].class);
//
//        // Check if ratings were fetched and set them in UserDTO
//        List<Rating> ratingsList = ratings != null ? Arrays.asList(ratings) : new ArrayList<>();
//        userDTO.setRatingsList(ratingsList);
//
//        // For each rating, fetch the associated hotel and set it in the rating object
//        for (Rating rating : ratingsList) {
//            String urlForHotel = "http://HOTELSERVICE//hotels/findbyid/" + rating.getHotelId();
//           // String urlForHotel = "http://HOTELSERVICE/findbyid/" + rating.getHotelId();
//            log.info("Fetching hotel for rating from URL: " + urlForHotel);
//            Hotel hotel = restTemplate.getForObject(urlForHotel, Hotel.class);
//            log.info("Fetching hotel form rating " + hotel);
//            if (hotel != null) {
//                rating.setHotel(hotel);
//            }
//        }
//
//        log.info("Retrieved ratings with hotels: " + ratingsList);
//        return userDTO;
//    }
//
//    public List<UserDTO> UserFindAllRecordWithRatingAndHotelFromAnotherMicroservice() {
//        List<User> users = dao.findAll();
//        if (users.isEmpty()) {
//            throw new UserNotFoundException("No Users are available in the database.");
//        }
//       // String url = "http://localhost:8092/ratings/findallrating";
//        String url = "http://RATINGSERVICE/ratings/findallrating";
//        log.info("Fetching ratings from URL: " + url);
//
//        // Fetching an array of Rating objects
//        Rating[] ratings = restTemplate.getForObject(url, Rating[].class);
//        log.info("value of rating is from all user is: "+ratings);
//        
//        // Convert array to list and set the ratingsList in UserDTO
//        List<Rating> ratingsList = ratings != null ? Arrays.asList(ratings) : new ArrayList<>();
//        for (Rating rating : ratingsList) {
//            //String urlForHotel = "http://localhost:8091/hotels/findbyid/" + rating.getHotelId();
//            String urlForHotel = "http://HOTELSERVICE//hotels/findbyid/" + rating.getHotelId();
//            log.info("Fetching hotel for rating from URL All User Parts: " + urlForHotel);
//            Hotel hotel = restTemplate.getForObject(urlForHotel, Hotel.class);
//            log.info("Fetching hotel form rating all user part " + hotel);
//            if (hotel != null) {
//                rating.setHotel(hotel);
//            }
//        }
//
//        log.info("Retrieved ratings with hotels all user parts: " + ratingsList);
//        return users.stream()
//                .map(user -> {
//                    UserDTO dto = new UserDTO();
//                    BeanUtils.copyProperties(user, dto);
//                    dto.setRatingsList(ratingsList);
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//    
//    
//}



