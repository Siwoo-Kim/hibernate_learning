package com.hibernate.learning.service;

import com.hibernate.learning.domain.Sight;
import com.hibernate.learning.domain.SightDetail;

public interface SightService {

    Sight save(Sight sight);
    Sight addSightDetail(Long id, SightDetail sightDetail);

}
