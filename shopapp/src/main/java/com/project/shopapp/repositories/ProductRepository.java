package com.project.shopapp.repositories;

import com.project.shopapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    Page<Product> findAll(Pageable pageable);
    @Query(
            "SELECT p FROM Product p WHERE " +
                    "(:categoryId is null or :categoryId = 0 or p.category.id = :categoryId)" +
                    "and (:keyword is null or :keyword = '' or p.name like %:keyword% or p.description like %:keyword%)"
    )
    Page<Product> searchProducts(@Param("keyword") String keyword,
                                 @Param("categoryId") Long categoryId,
                                 Pageable pageable);

    @Query(
        "SELECT p FROM Product p left join fetch p.productImages where p.id = :productId"
    )
    Optional<Product> getDetaiProduct(@Param("productId") Long productId);

    @Query(
            "SELECT p FROM Product p where p.id IN :productIds"
    )
    List<Product> findProductsByIds(@Param("productIds") List<Long> productId);
}
