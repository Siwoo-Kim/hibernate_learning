package com.hibernate.learning.domain;

import lombok.*;

import javax.persistence.*;

@Entity @Getter @Setter @Builder @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SecondaryTable(
        name="SIGHT_DETAIL",
        pkJoinColumns = @PrimaryKeyJoinColumn(
                name ="SIGHT_ID",referencedColumnName = "SIGHT_ID"))
public class Sight {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SIGHT_SEQ_GENERATOR")
    @SequenceGenerator(
            name="SIGHT_SEQ_GENERATOR",
            sequenceName = "SIGHT_SEQ",
            initialValue = 1,allocationSize = 1)
    @Column(name="SIGHT_ID")
    private Long id;

    private String name;

    @Embedded
    private SightDetail sightDetail;

}
