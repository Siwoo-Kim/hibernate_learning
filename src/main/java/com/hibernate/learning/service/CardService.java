package com.hibernate.learning.service;

import com.hibernate.learning.domain.Card;
import com.hibernate.learning.domain.Member;

public interface CardService {

    void save(Card card);
    void assign(Member member, Card card);
    Card find(String cardNumber);

}
