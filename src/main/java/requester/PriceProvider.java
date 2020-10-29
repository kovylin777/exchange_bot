package requester;

import models.Price;

import java.util.ArrayList;

public class PriceProvider {

    private final ObmenkaKhUaManager OBMENKA_KH_UA = new ObmenkaKhUaManager();

    private ArrayList<Price> prices = new ArrayList<>();

    private void setPrices() {
        prices.add(OBMENKA_KH_UA.getPrice());
    }

    public Price getBestPrice() {
        setPrices();
        return prices.get(0);
    }
}
