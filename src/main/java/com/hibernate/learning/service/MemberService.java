package com.hibernate.learning.service;

import com.hibernate.learning.domain.Member;
import com.hibernate.learning.domain.MemberDetail;

import java.util.List;

public interface MemberService {

    Member save(Member member);
    Member find(String email);
    List<Member> findAll();
    MemberDetail addDetail(MemberDetail memberDetail);

}
