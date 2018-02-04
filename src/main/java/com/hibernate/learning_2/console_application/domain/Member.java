package com.hibernate.learning_2.console_application.domain;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @ToString @Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE) @AllArgsConstructor
//@Entity annotation notice to the JPA that it is an entity
@Entity @Table(name="LEARN2_MEMBER")
        // @Table annotation will map the table name in the database with value of name property
public class Member {

    //@Id annotation will map the column with primary key.
    //The field is also called identified field or identifier
    @Id
    //@Column annotation will map the field to the column. Providing the information about the column
    @Column(name="MEMBER_ID")
    @SequenceGenerator(name="LEARN2_MEMBER_SEQ_GENERATOR",
    sequenceName = "LEARN2_MEMBER_SEQ",initialValue = 1,allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "LEARN2_MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name="NAME")
    private String username;

    private Integer age;

}
