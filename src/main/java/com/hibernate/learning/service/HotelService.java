package com.hibernate.learning.service;

import com.hibernate.learning.domain.Hotel;
import com.hibernate.learning.domain.embeddable.Address;

import java.util.List;

public interface HotelService {

    Hotel save(Hotel hotel);

    Hotel getHotelProxy(Long id);

    List<Hotel> getHotels();

    Hotel addAddress(String name,Address address);

    Hotel addOptionalAddress(String name, Address address);

}
