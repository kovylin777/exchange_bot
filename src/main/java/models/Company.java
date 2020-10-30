package models;

import lombok.Data;
import models.currency.Currency;
import requester.PriceProvider;

import java.util.ArrayList;
import java.util.List;

@Data
public class Company {

    private int id;

    private String name;

    private String url;

    private List<Currency> currencyRates = new ArrayList<>();

    private String address;

    private String phone;

    public Company(String url) {
        setUrl(url);
    }

    @Override
    public String toString() {
        return
            "[" + PriceProvider.getDateFormatter().format(PriceProvider.getActualityDate()) + "] " +
            url + " : " +
                currencyRates.toString().substring(1, currencyRates.toString().length() - 1);
    }

    public void addCurrency(Currency currency) {
        this.currencyRates.add(currency);
    }
}
