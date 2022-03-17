package io.springbatch.springbatch.itemReader;

import io.springbatch.springbatch.itemWriter.Customer2;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private int age;
    @OneToOne(mappedBy = "customer", fetch = FetchType.LAZY)
    private Address address;

    public Customer2 toCustomer2() {
        return new Customer2(id, username, age);
    }
}
