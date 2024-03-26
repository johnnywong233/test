package com.johnny.validator.bettermessages;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bid {

    @Size.List({
            @Size(min = 5, message = "{bid.bidder.min.message}"),
            @Size(max = 10, message = "{bid.bidder.max.message}")
    })
    private String bidder;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Future(message = "{bid.expiresAt.message}")
    private Date expiresAt;

    @NotNull
    @DecimalMin(value = "10.00", message = "{bid.price.message}")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal price;
}