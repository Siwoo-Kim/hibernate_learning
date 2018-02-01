package com.hibernate.learning.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter @ToString @Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Entity @Table(name="HI_MEMBER_DETAIL")
public class MemberDetail {

    @Id
    private Long id;
}
