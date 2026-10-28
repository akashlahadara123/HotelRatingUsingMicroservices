package com.dn.tech.controller;

import com.dn.tech.dto.RatingDTO;
import com.dn.tech.model.Rating;
import com.dn.tech.service.RatingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {
	public static Logger logger=LoggerFactory.getLogger(RatingController.class);

	public RatingController() {
		System.out.println("User controller Object Created");
	}
    @Autowired
    private RatingService ratingService;
    @PostMapping("/saverating")
    public RatingDTO saveUser(@RequestBody RatingDTO RatingDTO) throws Exception {
        return ratingService.saveRating(RatingDTO);
    }
    @PutMapping("/updaterating")
	public RatingDTO updateUser(@RequestBody RatingDTO dto) throws Exception
	{
		return ratingService.updateRating(dto);
	}
	@DeleteMapping("/deleterating")
	public RatingDTO deleteUser(@RequestBody RatingDTO dto)
	{
		return ratingService.deleteRating(dto);
	}
	@GetMapping("/findbyuserid/{userId}")
	public List<RatingDTO> findRatingsByUserId(@PathVariable("userId") String userId) {
		logger.info("value of userId in  Rating Inside block of findRatingsByUserId: "+userId);
	    return ratingService.RatingSingleRecord(userId);
	}

	
	
	  @GetMapping("/findbyid/{id}")
	  public RatingDTO findUserById(@PathVariable("id") Long id) {
	        return ratingService.UserSingleRecord(id);
	    }
	
	@GetMapping("/findallrating")
	public List<RatingDTO> findAllUser()
	{
		return ratingService.RatingFindAllRecord();
	}
	
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId) {
        List<Rating> ratings = ratingService.getRatingByUserId(userId);
        if (ratings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelId) {
        List<Rating> ratings = ratingService.getRatingByHotelId(hotelId);
        if (ratings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @Value("${server.port}")
    private String port;

    @GetMapping("/loadbalance")
    public String testLoadBalancing() {
        return "Request handled by User Service on port: " + port;
    }
    
}
