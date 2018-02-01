package com.hibernate.learning.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @ToString @Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Entity @Table(name = "HI_CARD")
public class Card {

    @Id @Column(name="CARD_ID")
    @SequenceGenerator(name = "CARD_SEQ_GENERATOR",
    sequenceName = "CARD_SEQ",initialValue = 1,allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "CARD_SEQ_GENERATOR")
    private Long id;

    private String cardNumber;

    @OneToOne // (fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    private LocalDate expireDate;

    private boolean enabled;

    public void assignTo(Member member){
        if(this.member != null)
            throw new IllegalStateException("Card already assigned");
        this.member = member;
    }

    public void cancelAssignment(){
        this.member = null;
    }
}
