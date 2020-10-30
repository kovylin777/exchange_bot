package requester.managers;

import models.Company;
import org.jsoup.nodes.Document;
import requester.util.CurrencyManager;

import java.math.BigDecimal;

public class KharkovObmenkaUaManager extends BaseManager implements Manager {

    private final static String URL = "https://kharkov.obmenka.ua/";

    private Company company;

    @Override public Company getCompany() {
        company = new Company(URL);
        setMetadata();
        setCompanyPrice();
        return company;
    }

    public void setCompanyPrice() {
        Document doc = getHtmlDocument(URL);
        setCompanyPriceUsd(doc);
        setCompanyPriceEur(doc);
        setCompanyPriceRub(doc);
        System.out.println(company);
    }

    private void setCompanyPriceRub(Document doc) {
        company.addCurrency(CurrencyManager.getObjectRUB(getBuyPrice(doc, "RUB"), getSellPrice(doc, "RUB")));
    }

    private void setCompanyPriceEur(Document doc) {
        company.addCurrency(CurrencyManager.getObjectEUR(getBuyPrice(doc, "EUR"), getSellPrice(doc, "EUR")));
    }

    private void setCompanyPriceUsd(Document doc) {
        company.addCurrency(CurrencyManager.getObjectUSD(getBuyPrice(doc, "USD"), getSellPrice(doc, "USD")));
    }

    private BigDecimal getBuyPrice(Document doc, String code) {
        return getDecimal(doc.getElementsByAttributeValueMatching("href", "/ru/" + code + "-UAH")
            .select(".buy").get(0).text());
    }

    private BigDecimal getSellPrice(Document doc, String code) {
        return getDecimal(doc.getElementsByAttributeValueMatching("href", "/ru/" + code + "-UAH")
            .select(".sell").get(0).text());
    }

    @Override public void setMetadata() {
        company.setId(1);
        company.setName("kharkov.obmenka.ua");
        company.setAddress("пл. Павловская, 2");
        company.setPhone("+380965701110");
    }
}
