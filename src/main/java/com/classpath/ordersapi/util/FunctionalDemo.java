package com.classpath.ordersapi.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FunctionalDemo {

    static Predicate<Product> isAppleProduct = (product) -> product.getCompany() == Company.APPLE;
    static Predicate<Product> isLessThan50000 = (product) -> product.getPrice() < 50_000;

    public static void main(String[] args) {

        Product product1 = Product.builder().company(Company.APPLE).launchDate(LocalDate.now()).name("IPhone-12").price(1_00_000).build();
        Product product2 = Product.builder().company(Company.SAMSUNG).launchDate(LocalDate.now()).name("Galaxy-S12").price(1_10_000).build();
        Product product3 = Product.builder().company(Company.OPPO).launchDate(LocalDate.now()).name("OPPO-F12").price(30_000).build();
        Product product4 = Product.builder().company(Company.LG).launchDate(LocalDate.now()).name("LG-F12").price(30_000).build();

        List<Product> products = Arrays.asList(product1, product2, product3, product4);

        /*for(Product product: products){
            if(product.getCompany() == Company.APPLE){

            }
        }*/
        //products.stream().filter(isAppleProduct).forEach(System.out::println);
        //products.stream().filter(isAppleProduct.negate()).forEach(System.out::println);
        final Predicate<Product> noAppleAndLessThan50000 = isAppleProduct.negate().and(isLessThan50000);
        final Predicate<Product> noAppleAndMoreThan50000 = isAppleProduct.negate().and(isLessThan50000).negate();
        final Predicate<Product> appleAndMoreThan50000 = isAppleProduct.and(isLessThan50000).negate();
        final Predicate<Product> appleAndLessThan50000 = isAppleProduct.and(isLessThan50000);

    }
}