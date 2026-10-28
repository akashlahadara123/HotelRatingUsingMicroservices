package com.dn.tech.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.Nullable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dn.tech.controller.UserController.RatingServiceFeignClient;
import com.dn.tech.dao.UserDAO;
import com.dn.tech.dto.UserDTO;
import com.dn.tech.exception.EmailAlreadyExistsException;
import com.dn.tech.exception.UserNotFoundException;
import com.dn.tech.model.Hotel;
import com.dn.tech.model.Rating;
import com.dn.tech.model.User;
@Service
public class UserService {
	
	public UserService() {
		System.out.println("User Class Object Created");
	}
	@Autowired 
	private UserDAO userDao;
	
	@Autowired
	private RestTemplate restTempleate;
	
	@Autowired
	private RatingServiceFeignClient ratingServiceFeignClient;
	
	private org.slf4j.Logger log=LoggerFactory.getLogger(UserService.class);
	
	public UserDTO saveUser(UserDTO userDTO) throws Exception {

	    if (userDao.existsByEmail(userDTO.getEmail())) {
	        throw new EmailAlreadyExistsException(
	                "Email already exists: " + userDTO.getEmail());
	    }

	    User user = new User();
	    BeanUtils.copyProperties(userDTO, user);

	    user = userDao.save(user);

	    UserDTO result = new UserDTO();
	    BeanUtils.copyProperties(user, result);

	    return result;
	}
	
	 public UserDTO updateUser(UserDTO userDTO) throws Exception {
	        if (!userDao.existsById(userDTO.getId())) {
	            throw new UserNotFoundException("User does not exist in the database for update.");
	        }

	        User user = new User();
	        BeanUtils.copyProperties(userDTO, user);
	        user.setId(userDTO.getId());  //Make sure the ID is set for update
	        userDao.save(user);

	        return userDTO;
	    }

	    public UserDTO deleteUser(UserDTO userDTO) {
	        if (!userDao.existsById(userDTO.getId())) {
	            throw new UserNotFoundException("User does not exist in the database for deletion.");
	        }

	        User user = new User();
	        BeanUtils.copyProperties(userDTO, user);
	        userDao.delete(user);

	        return userDTO;
	    }
	    
	    public UserDTO findUserById(Long id) {
	        Optional<User> optionalUser = userDao.findById(id);
	        if (!optionalUser.isPresent()) {
	            throw new UserNotFoundException("User does not exist in the database for ID: " + id);
	        }

	        UserDTO userDTO = new UserDTO();
	        BeanUtils.copyProperties(optionalUser.get(), userDTO);

	        // Fetching ratings logic can go here if necessary
	        return userDTO;
	    }

	    public List<UserDTO> UserFindAllRecord() {

	        List<User> users = userDao.findAll();

	        if (users.isEmpty()) {
	            throw new UserNotFoundException(
	                    "No Users are available in the database.");
	        }

	        List<UserDTO> result = new ArrayList<>();

	        for (User user : users) {

	            UserDTO dto = new UserDTO();

	            BeanUtils.copyProperties(user, dto);

	            result.add(dto);
	        }

	        return result;
	    }
	    
	    public UserDTO findUserByUserId(String userId) {
	        Optional<User> optionalUser = userDao.findByUserId(userId);
	        if (!optionalUser.isPresent()) {
	            throw new UserNotFoundException("User does not exist in the database for User ID: " + userId);
	        }
	        UserDTO userDTO = new UserDTO();
	        BeanUtils.copyProperties(optionalUser.get(), userDTO);

	        // Fetching ratings logic can go here if necessary
	        return userDTO;
	        
	    }
	    
	    public UserDTO findUserByIdUsingRatingMS(Long id) {
	        Optional<User> optionalUser = userDao.findById(id);
	        if (!optionalUser.isPresent()) {
	            throw new UserNotFoundException("User does not exist in the database for ID: " + id);
	        }

	        UserDTO userDTO = new UserDTO();
	        BeanUtils.copyProperties(optionalUser.get(), userDTO);

	        // Fetching ratings logic by Single User
	        //url http://localhost:8090/users/findbyid/3
	        //String url="http://localhost:8090/users/findbyid/"+userDTO.getId();
	        String url="http://User-Service/users/findbyid/"+userDTO.getId();
	        log.info("url is: "+url);
	        try {
	        	 List<Rating> userRating=restTempleate.getForObject(url,ArrayList.class);
	 	        log.info("Rating Value is: "+userRating);
	 	        userDTO.setRatingsList(userRating);
			} catch (Exception e) {
				System.out.println(e);
				log.info("Exception is "+e);
			}
	       
	        return userDTO;
	    }
	    public UserDTO findUserByUserCustomIdUsingMS(String userId) {
	        Optional<User> optionalUser = userDao.findByUserId(userId);
	        if (!optionalUser.isPresent()) {
	            throw new UserNotFoundException("User does not exist in the database for User ID: " + userId);
	        }
	        UserDTO userDTO = new UserDTO();
	        BeanUtils.copyProperties(optionalUser.get(), userDTO);

	        // Fetching ratings logic can go here if necessary
	      //url http://localhost:8090/users/findbyid/3
	       // String url="http://localhost:8092/ratings/findbyuserid/"+userDTO.getUserId();
	        String url="http://Rating-Service/ratings/findbyuserid/"+userDTO.getUserId();
	        log.info("ID is "+userDTO.getUserId());
	        log.info("final Url is: "+url);
	        try {
	        	 List<Rating> userRating=restTempleate.getForObject(url,ArrayList.class);
	 	        log.info("Rating Value is: "+userRating);
	 	        userDTO.setRatingsList(userRating);
			} 
	        catch (Exception e) {
	        	log.info("Exception is "+e);
				System.out.println(e);
				
			}
	        return userDTO;
	        
	    }
	    
	  public List<UserDTO> UserFindAllRecordUsingRatingMS() {

    List<User> users = userDao.findAll();

    if (users.isEmpty()) {
        throw new UserNotFoundException(
                "No Users are available in the database.");
    }

    //String url = "http://localhost:8092/ratings/findallrating";
    String url = "http://Rating-Service/ratings/findallrating";
    log.info("final Url for ALL User With Rating: {}", url);

    List<Rating> ratings = null;

    try {

        ResponseEntity<List<Rating>> response =
                restTempleate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Rating>>() {
                        });

        ratings = response.getBody();

        log.info("All Rating is: {}", ratings);

    } catch (Exception e) {

        log.error("Exception while fetching ratings", e);
    }

    List<UserDTO> result = new ArrayList<>();

    for (User user : users) {

        UserDTO dto = new UserDTO();

        BeanUtils.copyProperties(user, dto);

        List<Rating> userRatings = new ArrayList<>();

        if (ratings != null) {

            for (Rating rating : ratings) {

                if (rating.getUserId().equals(user.getUserId())) {
                    userRatings.add(rating);
                }
            }
        }

        dto.setRatingsList(userRatings);

        result.add(dto);
    }

    return result;
} 
	  
	  public UserDTO findUserByUserCustomIdAllDetails(String userId) {
	        Optional<User> optionalUser = userDao.findByUserId(userId);
	        if (!optionalUser.isPresent()) {
	            throw new UserNotFoundException("User does not exist in the database for User ID: " + userId);
	        }
	        UserDTO userDTO = new UserDTO();
	        BeanUtils.copyProperties(optionalUser.get(), userDTO);

	        // Fetching ratings logic can go here if necessary
	      //url http://localhost:8090/users/findbyid/3
	        //String urlForRating="http://localhost:8092/ratings/findbyuserid/"+userDTO.getUserId();
	        String urlForRating="http://Rating-Service/ratings/findbyuserid/"+userDTO.getUserId();
	        log.info("ID is "+userDTO.getUserId());
	        log.info("final Url urlForRating : "+urlForRating);
	        List<Rating> ratings = null;
	        try {

	            ResponseEntity<List<Rating>> response =
	                    restTempleate.exchange(
	                    		urlForRating,
	                            HttpMethod.GET,
	                            null,
	                            new ParameterizedTypeReference<List<Rating>>() {
	                            });

	            ratings = response.getBody();

	            log.info("All Rating is: {}", ratings);
	            
	            for (Rating rating : ratings) {
	              //String urlForHotel = "http://localhost:8091/hotels/findbyid/" + rating.getHotelId();
	              String urlForHotel = "http://Hotel-Services/hotels/findbyid/" + rating.getHotelId();
	              log.info("Fetching hotel for rating from URL All User Parts: " + urlForHotel);
	             ResponseEntity<Hotel> hotelResponse =
		                    restTempleate.exchange(
		                    		urlForHotel,
		                            HttpMethod.GET,
		                            null,
		                            Hotel.class);

		           Hotel hotels = hotelResponse.getBody();
	              log.info("Fetching hotel form rating all user part " + hotels);
	              rating.setHotel(hotels);
	          }

	        
	       }
	        catch (Exception e) {

	            log.error("Exception while fetching ratings", e);
	        }
	        userDTO.setRatingsList(ratings);
	        return userDTO;
	        
	    }
	  public List<UserDTO> UserFindAllRecordUsingRatingMSWithHotels() {

		    List<User> users = userDao.findAll();

		    if (users.isEmpty()) {
		        throw new UserNotFoundException(
		                "No Users are available in the database."
		        );
		    }

		    // 1. Get all ratings from RatingService
		   // String urlForRating =
		         //   "http://localhost:8092/ratings/findallrating";
		    
		    String urlForRating =
		            "http://RATING-SERVICE/ratings/findallrating";
		    log.info("urlForRating is:======" +urlForRating);
		    List<Rating> ratings = null;
		    
		    try {

		        ResponseEntity<List<Rating>> response =
		                restTempleate.exchange(
		                        urlForRating,
		                        HttpMethod.GET,
		                        null,
		                        new ParameterizedTypeReference<List<Rating>>() {}
		                );

		        ratings = response.getBody();

		        log.info("All Ratings: {}", ratings);

		    } catch (Exception e) {

		        log.error("Exception while fetching ratings", e);
		    }

		    // 2. Create final UserDTO list
		    List<UserDTO> result = new ArrayList<>();
		    log.info("result for user===="+result );
		    // 3. Loop through all users
		    for (User user : users) {

		        UserDTO dto = new UserDTO();

		        BeanUtils.copyProperties(user, dto);

		        List<Rating> userRatings = new ArrayList<>();
		        log.info("result for userRatings===="+userRatings );
		        // 4. Find ratings belonging to current user
		        if (ratings != null) {

		            for (Rating rating : ratings) {

		                if (rating.getUserId().equals(user.getUserId())) {

		                    // 5. Get Hotel for this rating
		                   // String urlForHotel = "http://localhost:8091/hotels/findbyid/" + rating.getHotelId();
		                    
		                    String urlForHotel =
		                            "http://HOTEL-SERVICES/hotels/findbyid/"
		                            + rating.getHotelId();
		                    log.info("urlForHotel ===="+urlForHotel);
		                    try {

		                        ResponseEntity<Hotel> hotelResponse =
		                                restTempleate.exchange(
		                                        urlForHotel,
		                                        HttpMethod.GET,
		                                        null,
		                                        Hotel.class
		                                );

		                        Hotel hotel = hotelResponse.getBody();
		                        log.info("result for hotel is===="+hotel );
		                        // 6. Attach Hotel inside Rating
		                        rating.setHotel(hotel);

		                    } catch (Exception e) {

		                        log.error(
		                            "Exception while fetching hotel: {}",
		                            rating.getHotelId(),
		                            e
		                        );
		                    }

		                    // 7. Add rating to current user's rating list
		                    userRatings.add(rating);
		                }
		            }
		        }

		        // 8. Add ratings to current UserDTO
		        dto.setRatingsList(userRatings);

		        // 9. Add UserDTO to final result
		        result.add(dto);
		    }

		    return result;

	 }
	  
	  public List<UserDTO> UserFindAllRecordUsingRatingMSUsingFeignClient() {

	      List<User> users = userDao.findAll();

	      if (users.isEmpty()) {
	          throw new UserNotFoundException(
	                  "No Users are available in the database.");
	      }

	      List<Rating> ratings = null;

	      try {
	          ratings = ratingServiceFeignClient.getAllRatings();

	          log.info("All Rating is: {}", ratings);

	      } catch (Exception e) {
	          log.error("Exception while fetching ratings", e);
	      }

	      List<UserDTO> result = new ArrayList<>();

	      for (User user : users) {

	          UserDTO dto = new UserDTO();

	          BeanUtils.copyProperties(user, dto);

	          List<Rating> userRatings = new ArrayList<>();

	          if (ratings != null) {
	              for (Rating rating : ratings) {

	                  if (rating.getUserId().equals(user.getUserId())) {
	                      userRatings.add(rating);
	                  }
	              }
	          }

	          dto.setRatingsList(userRatings);

	          result.add(dto);
	      }

	      return result;
	  }
}
