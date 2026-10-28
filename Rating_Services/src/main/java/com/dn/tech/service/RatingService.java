package com.dn.tech.service;

import com.dn.tech.controller.RatingController;
import com.dn.tech.dao.RatingDAO;
import com.dn.tech.dto.RatingDTO;

import com.dn.tech.model.Rating;

import com.dn.tech.exception.RatingNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {
	public static Logger logger=LoggerFactory.getLogger(RatingController.class);

    @Autowired
    private RatingDAO dao;

    public RatingDTO saveRating(RatingDTO ratingDTO) throws Exception {
        Rating rating = new Rating();
        BeanUtils.copyProperties(ratingDTO, rating);
        rating = dao.save(rating);
        BeanUtils.copyProperties(rating, ratingDTO);
        return ratingDTO;
    }

    public RatingDTO updateRating(RatingDTO ratingDTO) throws Exception {
        if (!dao.existsById(ratingDTO.getId())) {
            throw new RatingNotFoundException("Rating does not exist in the database for update.");
        }

        Rating rating = new Rating();
        BeanUtils.copyProperties(ratingDTO, rating);
        rating.setId(ratingDTO.getId()); // Make sure the ID is set for update
        dao.save(rating);

        return ratingDTO;
    }

    public RatingDTO deleteRating(RatingDTO ratingDTO) {
        if (!dao.existsById(ratingDTO.getId())) {
            throw new RatingNotFoundException("Rating does not exist in the database for deletion.");
        }

        Rating rating = new Rating();
        BeanUtils.copyProperties(ratingDTO, rating);
        dao.delete(rating);

        return ratingDTO;
    }

    // Method updated to fetch based on userId
    public List<RatingDTO> RatingSingleRecord(String userId) {
    	logger.info("value of user id inside service class Method of ratingservice"+userId);
        List<Rating> ratings = dao.findByUserId(userId);
        logger.info("value of ratings  inside service class Method of ratingservice"+ratings);
        if (ratings.isEmpty()) {
            throw new RatingNotFoundException("No ratings found for the user with userId: " + userId);
        }

        return ratings.stream()
                .map(rating -> {
                    RatingDTO ratingDTO = new RatingDTO();
                    logger.info("value of ratingDTO inside service class Method of ratingservice"+ratingDTO);
                    BeanUtils.copyProperties(rating, ratingDTO);
                    logger.info("value of rating inside service class Method of ratingservice"+rating);
                    logger.info("value of ratingDTO inside service class Method of ratingservice at last"+ratingDTO);
                    return ratingDTO;
                    
                })
                .collect(Collectors.toList());
    }

    public List<RatingDTO> RatingFindAllRecord() {
        List<Rating> ratings = dao.findAll();
        if (ratings.isEmpty()) {
            throw new RatingNotFoundException("No Ratings are available in the database.");
        }

        return ratings.stream()
                .map(rating -> {
                    RatingDTO dto = new RatingDTO();
                    BeanUtils.copyProperties(rating, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    
    public RatingDTO UserSingleRecord(Long id) {
        Optional<Rating> optionalUser = dao.findById(id);
        if (!optionalUser.isPresent()) {
            throw new RatingNotFoundException("Rating does not exist in the database for ID: " + id);
        }

        RatingDTO ratingDTO = new RatingDTO();
        BeanUtils.copyProperties(optionalUser.get(), ratingDTO);

        // Fetching ratings logic can go here if necessary
        return ratingDTO;
    }
    
    public List<Rating> getRatingByUserId(String userId) {
        return dao.findByUserId(userId);
    }
    
    
    

    public List<Rating> getRatingByHotelId(String hotelId) {
        return dao.findByHotelId(hotelId);
    }
}
