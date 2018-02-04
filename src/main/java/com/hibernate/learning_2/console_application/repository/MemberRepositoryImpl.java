package com.hibernate.learning_2.console_application.repository;

import com.hibernate.learning_2.console_application.domain.Member;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @Override
    public Member findByUsername(String username) {
        return null;
    }

    @Override
    public Member persist(Member member) {
        return null;
    }

    @Override
    public Member delete(Member member) {
        return null;
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public Member findOne(Serializable id) {
        return null;
    }

    @Override
    public Member findAll() {
        return null;
    }
}
