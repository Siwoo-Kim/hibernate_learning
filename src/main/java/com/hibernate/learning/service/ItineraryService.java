package com.hibernate.learning.service;

import com.hibernate.learning.domain.Itinerary;

import java.util.List;

public interface ItineraryService {

    Itinerary save(Itinerary itinerary);

    List<Itinerary> findAll();

    Itinerary findItinerary(String name);
}
