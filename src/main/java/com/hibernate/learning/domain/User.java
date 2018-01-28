package com.hibernate.learning.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter @Setter @Builder @ToString
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Entity @Table(name="HI_USER")
public class User {

    @Id
    private String email;

    private String name;

    @Column
    private LocalDateTime joinDate;


}
