package com.hibernate.learning_2.console_application.repository;

import com.hibernate.learning_2.console_application.domain.Member;
import org.springframework.stereotype.Repository;

public interface MemberRepository extends ApplicationRepository<Member>{

    Member findByUsername(String username);

}
