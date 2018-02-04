package com.hibernate.learning.domain;

import com.hibernate.learning.domain.embeddable.ArrangeTimeTable;
import lombok.*;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Log
@Getter @Setter @Builder @ToString
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Entity @Table(name="HI_ITINERARY")
public class Itinerary {

    @Id @Column(name="ID_ITINERARY")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ITINERARY_SEQ_GENERATOR")
    @SequenceGenerator(name="ITINERARY_SEQ_GENERATOR",
    sequenceName = "ININERARY_SEQ",allocationSize = 1,initialValue = 1)
    private Long id;

    //JPA SUPPORTS COLLECTION VALUE TYPE
    //THE COLLECTION WILL BE SAVED IN DIFFERENT TABLE
    @ElementCollection  //@ElementCollection specify the type of property is collection
    @CollectionTable(name="ITINERARY_SITE", //@CollectionTable specify the table where collection is saved
    joinColumns = @JoinColumn(name="ITINERARY_ID"))
    @OrderColumn(name="SITE_INDEX") //@OrderColumn specify the column where the index of the collection is saved
    @Builder.Default
    private List<String> sites = new ArrayList<>();

    private String name;

    private String description;



    //THIS IS NON-SENSE BUT MAKING IT BECAUSE OF LEARNING PURPOSE
    @ElementCollection  //EMBEDDABLE TYPE ALSO CAN BE ELEMENTCOLLECTION WHICH SAVED IN
                        //DIFFERENT TABLE
    @CollectionTable(name="ITINERARY_TIMETABLE",
    joinColumns = @JoinColumn(name="ITINERARY_ID"))
    @OrderColumn(name="TIMETABLE_LIST")
    @Builder.Default
    private List<ArrangeTimeTable> arrangeTimeTable = new ArrayList<>();

    public void removeSites(){
        if(!this.sites.isEmpty()){
            this.sites.clear();
        }
    }

    public void deleteSite(Integer index) {

        if(this.sites.size() > index && this.sites.get(index) != null){

            //List remove method is overloaded. Be careful
            //If you want to remove by index, it must be int type not object(Integer)
            this.sites.remove(index.intValue());

        }

        log.warning(this.sites.toString());
    }
}
