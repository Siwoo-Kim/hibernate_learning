package com.hibernate.learning.service;

import com.hibernate.learning.domain.Member;

import java.util.List;

public interface MemberService {

    Member save(Member member);
    Member find(String email);
    List<Member> findAll();

}
