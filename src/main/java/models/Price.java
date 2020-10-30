package models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Price {

    private int id;

    private String name;

    private String url;

    private BigDecimal buyingRate;

    private BigDecimal sellingRate;

    private String address;

    private String phone;

    public Price(String url) {
        setUrl(url);
    }
}
