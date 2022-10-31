package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery extends BaseEntity{

    @Id @GeneratedValue
    private Long id;
    private String city;
    private String street;

    @OneToOne(mappedBy = "delivery")
    private Order order;
    private DeliveryStatus status;


}
