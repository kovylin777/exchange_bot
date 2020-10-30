package requester;

import lombok.Getter;
import models.Price;
import requester.managers.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class PriceProvider {

    private ArrayList<Price> prices = new ArrayList<>();

    @Getter
    public static Price bestSellingPrice;

    @Getter
    public static Price bestBuyingPrice;

    @Getter
    private static Date actualityDate;

    @Getter
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

    public static boolean isUpdatingNow = false;

    public void setPrices() {
        isUpdatingNow = true;
        actualityDate = new Date(System.currentTimeMillis());
        prices.add(new ObmenkaKhUaManager().getPrice());
        prices.add(new KharkovObmenkaUaManager().getPrice());
        prices.add(new Money24Manager().getPrice());
        prices.add(new ObmenkaKharkovUaManager().getPrice());
        prices.add(new ObmenkaKharkivUaManager().getPrice());
        bestBuyingPrice = compare(true);
        bestSellingPrice = compare(false);
        isUpdatingNow = false;
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
