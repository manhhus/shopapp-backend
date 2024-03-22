package com.project.shopapp.repositories;

import com.project.shopapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    Page<Product> findAll(Pageable pageable);

    @Query(
            value = "SELECT * FROM products p " +
                    "WHERE (:categoryId is null or :categoryId = 0 or p.category_id = :categoryId) " +
                    "and (:keyword is null or :keyword = '' or p.name like %:keyword% ) " +
                    "LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<Product> searchProducts(@Param("keyword") String keyword,
                                 @Param("categoryId") Long categoryId,
                                 @Param("limit") int limit,
                                 @Param("offset") int offset);

    @Query(value = "SELECT CEIL(COUNT(*) / :limit) FROM products", nativeQuery = true)
    int getTotalPage(@Param("limit") int limit);

    @Query(value = "SELECT CEIL(COUNT(*) / :limit) FROM products p " +
            "WHERE (:categoryId is null or :categoryId = 0 or p.category_id = :categoryId) "+
            "and (:keyword is null or :keyword = '' or p.name like %:keyword%) "
            , nativeQuery = true)
    int getTotalPageSearch(@Param("keyword") String keyword,
                     @Param("categoryId") Long categoryId,
                     @Param("limit") int limit
                     );
    @Query(
        "SELECT p FROM Product p left join fetch p.productImages " +
                "left join fetch p.category where p.id = :productId"
    )
    Optional<Product> getDetaiProduct(@Param("productId") Long productId);
    
    @Query(
            "SELECT p FROM Product p left join fetch p.productImages " +
                    "left join fetch p.category where p.id IN :productIds"
    )
    List<Product> findProductsByIds(@Param("productIds") List<Long> productId);

}
