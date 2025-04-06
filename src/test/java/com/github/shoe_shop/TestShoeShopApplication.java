package com.github.shoe_shop;

import org.springframework.boot.SpringApplication;

public class TestShoeShopApplication {

    public static void main(String[] args) {
        SpringApplication.from(ShoeShopApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
