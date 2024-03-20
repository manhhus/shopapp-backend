package com.project.shopapp.services;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO product) throws DataNotFoundException;
    Product getProductById(long id) throws Exception;
    List<ProductResponse> getAllProducts(String keyword, Long categoryId, int limit, int page);
    Product updateProduct(long id, ProductDTO product) throws Exception;
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception;
    List<ProductImage> getProductImages(Long productId);
    List<Product> createProducts();
    int getTotalPages(int limit);
    int totalPageSearch( String keyword,
                            Long categoryId,
                            int limit
                           );

}
