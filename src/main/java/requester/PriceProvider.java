package requester;

import models.Price;

import java.util.ArrayList;
import java.util.Comparator;

public class PriceProvider {

    private final ObmenkaKhUaManager OBMENKA_KH_UA = new ObmenkaKhUaManager();

    private ArrayList<Price> prices = new ArrayList<>();

    private Price bestSellingPrice;

    private Price bestBuyingPrice;

    public void setPrices() {
        prices.add(OBMENKA_KH_UA.getPrice());
        bestBuyingPrice = compare(true);
        bestSellingPrice = compare(false);
    }

    public Price getBestBuyingPrice() {
        return bestBuyingPrice;
    }

    public Price getBestSellingPrice() {
        return bestSellingPrice;
    }

    private Price compare(boolean isBestBuying) {
        if (isBestBuying) {
            return prices.stream()
                .max(Comparator.comparing(Price::getBuyingRate)).orElse(new Price(null));
        } else {
            return prices.stream()
                .min(Comparator.comparing(Price::getSellingRate)).orElse(new Price(null));
        }
    }


}
