package com.hibernate.learning.domain;


import com.hibernate.learning.domain.embeddable.Address;
import lombok.*;

import javax.persistence.*;

@Getter @Setter @ToString @Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Entity @Table(name="HI_MEMBER_DETAIL")
public class MemberDetail {

    @Id @Column(name="MEMBER_ID")
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn //Mapping member_id in member to id property
    private Member member;

    @Embedded
    private Address address;

}
