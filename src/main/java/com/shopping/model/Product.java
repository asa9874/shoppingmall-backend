package com.shopping.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String image;

    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stock;

    private Category category;

    public enum Category {
        ELECTRONICS, // 전자제품
        FASHION, // 패션/의류
        HOME, // 가구/인테리어
        BEAUTY, // 뷰티/화장품
        FOOD, // 식품/건강
        SPORTS, // 스포츠/레저
        TOYS, // 장난감/취미
        BOOKS, // 도서/문구
        AUTOMOTIVE, // 자동차/오토바이
        PETS, // 반려동물 용품
        ETC // 기타
    }

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @ManyToMany
    @Builder.Default
    @JoinTable(name = "customer_product", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customers = new ArrayList<>();


    public boolean isOwnedBy(String memberId) {
        return seller.getMember().getMemberId().equals(memberId);
    }
}
