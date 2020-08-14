package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
interface UserRepository extends CrudRepository<ProductEntity, Integer> {
    List<ProductEntity> findAll();

    ProductEntity getProductsByProductId(Integer productId);

    @Transactional
    void deleteByUserId(Integer productId);

    boolean existsByUserId(Integer productId);

    boolean existsByUserName(String getProductName);

}