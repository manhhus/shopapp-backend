package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetailDTO {
    @Min(value = 1, message = "OrderId must be > 0")
    @JsonProperty("order_id")
    private Long orderId;

    @Min(value = 1, message = "ProductId must be > 0")
    @JsonProperty("product_id")
    private Long productId;

    @Min(value = 0, message = "Price's product must be >= 0")
    private Long price;

    @Min(value = 1, message = "Number products must be > 0")
    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be >= 0")
    private Float totalMoney;

    private String color;

}
