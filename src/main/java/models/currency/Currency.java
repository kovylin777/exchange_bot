package models.currency;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
public class Currency {

    private String code;

    private String name;

    @Getter
    private BigDecimal buyingRate;

    @Getter
    private BigDecimal sellingRate;

    @Override
    public String toString() {
        return "\n" + code + ": " + "Buy = " + buyingRate + ", Sell = " + sellingRate;
    }

    public Currency(String code, String name, BigDecimal buyingRate, BigDecimal sellingRate) {
        this.code = code;
        this.name = name;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
    }
}
