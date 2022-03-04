package io.springbatch.springbatch.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public void toUppercaseName() {
        this.name = this.name.toUpperCase();
    }
}
