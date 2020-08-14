package com.thoughtworks.rslist.entity;
import com.thoughtworks.rslist.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "product")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ProductId;
    private String name;
    private int price;
    private int number;
    private String unit;

    public ProductEntity(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.number = product.getNumber();
        this.unit = product.getUnit();
    }
}