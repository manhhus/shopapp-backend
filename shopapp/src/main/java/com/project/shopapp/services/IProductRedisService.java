package com.project.shopapp.services;

import com.project.shopapp.responses.ProductResponse;

import java.util.List;

public interface IProductRedisService {
    void clear() ;
    List<ProductResponse> getAllProducts(String keyword,
                                         Long categoryId,
                                         int limit,
                                         int page) throws Exception;
    void saveAllProducts(List<ProductResponse> productResponses,
                         String keyword, Long categoryId,
                         int limit,
                         int page) throws Exception;
}
