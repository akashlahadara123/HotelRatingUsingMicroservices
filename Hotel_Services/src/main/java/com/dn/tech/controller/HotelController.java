package com.dn.tech.controller;

import com.dn.tech.dto.HotelDTO;
import com.dn.tech.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	public HotelController() {
		System.out.println("Hotel controller Object Created");
	}
    @Autowired
    private HotelService hotelService;
    @PostMapping("/savehotel")
    public HotelDTO saveUser(@RequestBody HotelDTO HotelDTO) throws Exception {
        return hotelService.saveHotel(HotelDTO);
    }
    @PutMapping("/updatehotel")
	public HotelDTO updateUser(@RequestBody HotelDTO dto) throws Exception
	{
		return hotelService.updateHotel(dto);
	}
	@DeleteMapping("/deletehotel")
	public HotelDTO deleteUser(@RequestBody HotelDTO dto)
	{
		return hotelService.deleteHotel(dto);
	}
	@GetMapping("/findbyid/{id}")
	public HotelDTO findanUserById(@PathVariable("id") Long id) {
	    return hotelService.HotelSingleRecord(id);
	}

	
	@GetMapping("/findallhotel")
	public List<HotelDTO> findAllUser()
	{
		return hotelService.HotelFindAllRecord();
	}
}
