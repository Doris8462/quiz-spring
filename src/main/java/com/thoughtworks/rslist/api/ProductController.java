package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Product;
import com.thoughtworks.rslist.entity.ProductEntity;
import com.thoughtworks.rslist.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    private final ProductRepository productRepository;
    public UserController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @PostMapping("/product")
    public void register(@RequestBody @Valid Product product){
        ProductEntity entity =ProductEntity.builder()
                .name(product.getName())
                .price(product.getPrice())
                .number(product.getNumber())
                .unit(product.getUnit())
                .build();
        productRepository.save(entity);
    }
}
