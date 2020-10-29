package models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Price {

    private String name;

    private BigDecimal buyingRate;

    private BigDecimal sellingRate;

    public Price(String name) {
        setName(name);
    }
}
