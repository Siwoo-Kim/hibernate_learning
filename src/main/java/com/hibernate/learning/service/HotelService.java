package com.hibernate.learning.service;

import com.hibernate.learning.domain.Hotel;
import com.hibernate.learning.domain.HotelReview;
import com.hibernate.learning.domain.embeddable.Address;

import java.util.List;

public interface HotelService {

    Hotel save(Hotel hotel);

    Hotel getHotelProxy(Long id);

    Hotel getHotelByName(String name);

    List<Hotel> getHotels();

    Hotel addAddress(String name,Address address);

    Hotel addOptionalAddress(String name, Address address);

    HotelReview addReview(HotelReview hotelReview);


    List<HotelReview> getReviews(String hotelName);

    List<HotelReview> getHotelReviewList(String hotelName, int maxResult);

    List<HotelReview> findByHotel(Hotel hotel, int startRow, int maxResult);

    Hotel findByName(String hotelName);
}
