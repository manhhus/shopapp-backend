package com.project.shopapp.services;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.InvalidParamException;
import com.project.shopapp.mappers.ProductMapper;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final ProductImageRepository productImageRepository;
    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find category with it: " + productDTO.getCategoryId()));
        return productRepository.save(productMapper.convertToEntity(productDTO, Product.class));
    }

    @Override
    public Product getProductById(long id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with it: " + id));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(ProductResponse::fromProduct
        );

    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        Category exitsCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find category with it: " + productDTO.getCategoryId()));
        existingProduct.setName(productDTO.getName());
        existingProduct.setCategory(exitsCategory);
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setThumbnail(productDTO.getThumbnail());
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId,
                                           ProductImageDTO productImageDTO)
                                            throws Exception{
        Product exitsProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find Product Image with it: " + productImageDTO.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(exitsProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        int size = productImageRepository.findByProductId(productId).size();
        if(size > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Number of images must be <= " + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }
}
