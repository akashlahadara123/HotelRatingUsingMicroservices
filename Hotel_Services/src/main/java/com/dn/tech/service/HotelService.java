package com.dn.tech.service;


import com.dn.tech.dao.HotelDAO;
import com.dn.tech.dto.HotelDTO;
import com.dn.tech.exception.HotelNotFoundException;
import com.dn.tech.model.Hotel;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService  {

    @Autowired
    private HotelDAO dao;

    public HotelDTO saveHotel(HotelDTO HotelDTO) throws Exception {
        Hotel Hotel = new Hotel();
        BeanUtils.copyProperties(HotelDTO, Hotel);
        Hotel = dao.save(Hotel);
        BeanUtils.copyProperties(Hotel, HotelDTO);
        return HotelDTO;
    }

    public HotelDTO updateHotel(HotelDTO HotelDTO) throws Exception {
        if (!dao.existsById(HotelDTO.getId())) {
            throw new HotelNotFoundException("Hotel does not exist in the database for update.");
        }

        Hotel Hotel = new Hotel();
        BeanUtils.copyProperties(HotelDTO, Hotel);
        Hotel.setId(HotelDTO.getId()); // Make sure the ID is set for update
        dao.save(Hotel);

        return HotelDTO;
    }

    public HotelDTO deleteHotel(HotelDTO HotelDTO) {
        if (!dao.existsById(HotelDTO.getId())) {
            throw new HotelNotFoundException("Hotel does not exist in the database for deletion.");
        }

        Hotel Hotel = new Hotel();
        BeanUtils.copyProperties(HotelDTO, Hotel);
        dao.delete(Hotel);

        return HotelDTO;
    }

    public HotelDTO HotelSingleRecord(Long id) {
        Optional<Hotel> optionalHotel = dao.findById(id);
        if (!optionalHotel.isPresent()) {
            throw new HotelNotFoundException("Hotel does not exist in the database for ID: " + id);
        }

        HotelDTO HotelDTO = new HotelDTO();
        BeanUtils.copyProperties(optionalHotel.get(), HotelDTO);
        return HotelDTO;
    }

    public List<HotelDTO> HotelFindAllRecord() {
        List<Hotel> Hotels = dao.findAll();
        if (Hotels.isEmpty()) {
            throw new HotelNotFoundException("No Hotels are available in the database.");
        }

        return Hotels.stream()
                .map(Hotel -> {
                    HotelDTO dto = new HotelDTO();
                    BeanUtils.copyProperties(Hotel, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
