package com.project.shopapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.shopapp.services.ProductService;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    private Float price;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "description")
    private String description;

    // map by entity
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ProductImage> productImages;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
