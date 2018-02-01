package com.hibernate.learning.domain;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name="HI_MEMBER")
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1, allocationSize = 1)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "MEMBER_SEQ_GENERATOR")
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name="NAME")
    private String username;

    private String email;

    private Integer age;

    @OneToOne(mappedBy = "member")
    private Card card;

    @OneToOne(mappedBy = "member") //BiDirectional Relationship
    private MemberDetail memberDetail;

    public void setMemberDetail(MemberDetail memberDetail){
        memberDetail.setMember(this);
        memberDetail.setId(this.id);
        this.memberDetail = memberDetail;
    }

    public void issueCard(Card card){
        card.assignTo(this);
        this.card = card;
    }
}
