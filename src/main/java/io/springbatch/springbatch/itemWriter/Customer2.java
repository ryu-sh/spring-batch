package io.springbatch.springbatch.itemWriter;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Customer2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private int age;

    protected Customer2() {
    }

    public Customer2(Long id, String username, int age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }
}
