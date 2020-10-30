package requester.util;

import models.currency.Currency;

import java.math.BigDecimal;

public class CurrencyManager {

    public static Currency getObjectUSD(BigDecimal buyingRate, BigDecimal sellingRate) {
        return new Currency("USD", "Доллар США", buyingRate, sellingRate);
    }

    public static Currency getObjectEUR(BigDecimal buyingRate, BigDecimal sellingRate) {
        return new Currency("EUR", "Евро", buyingRate, sellingRate);
    }

    public static Currency getObjectPLN(BigDecimal buyingRate, BigDecimal sellingRate) {
        return new Currency("PLN", "Польский Злотый", buyingRate, sellingRate);
    }

    public static Currency getObjectRUB(BigDecimal buyingRate, BigDecimal sellingRate) {
        return new Currency("RUB", "Российский Рубль", buyingRate, sellingRate);
    }
}
