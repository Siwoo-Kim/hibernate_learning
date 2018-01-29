package com.hibernate.learning.service;

import com.hibernate.learning.domain.Hotel;

import java.util.List;

public interface HotelService {

    public Hotel save(Hotel hotel);

    List<Hotel> getHotels();
}
