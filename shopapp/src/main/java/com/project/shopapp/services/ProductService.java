package com.project.shopapp.services;

import com.github.javafaker.Faker;
import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.InvalidParamException;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find category with it: " + productDTO.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);

    }

    @Override
    @Transactional
    public List<Product> createProducts() {
        Faker faker = new Faker();
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 2_000_000; i++) {
//            String productName = faker.commerce().productName();
//            if (productService.existsByName(productName)) {
//                continue;
//            }
            productList.add(Product.builder()
                    .name(faker.commerce().productName())
                    .price((float) faker.number().numberBetween(10, 90_000))
                    .description(faker.lorem().sentence())
                    .thumbnail("366f1993-fb03-4a2f-b939-a9c2cd62ec6f_021.jpg")
                    .category(categoryRepository.findById((long) faker.number().numberBetween(1, 5)).get())
                    .build()
            );
        }
//        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
//                .orElseThrow(() -> new DataNotFoundException("Cannot find category with it: " + productDTO.getCategoryId()));
//        Product newProduct = Product.builder()
//                .name(productDTO.getName())
//                .price(productDTO.getPrice())
//                .thumbnail(productDTO.getThumbnail())
//                .category(existingCategory)
//                .build();
        return productRepository.saveAll(productList);

    }

    @Override
    public Product getProductById(long id) throws Exception {
        return productRepository.getDetaiProduct(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with it: " + id));
    }

    @Override
    public Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest) {
        Page<Product> productPage = productRepository.searchProducts(keyword, categoryId, pageRequest);
        return productPage.map(ProductResponse::fromProduct);

    }

    @Override
    @Transactional
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
    @Transactional
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    @Transactional
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
    @Override
    public List<ProductImage> getProductImages(Long productId) {
        return productImageRepository.findByProductId(productId);
    }

    public List<Product> findProductsbyIds(List<Long> productIds) {
        return productRepository.findProductsByIds(productIds);
    }
}
