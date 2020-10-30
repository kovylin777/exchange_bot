package requester.managers;

import models.Company;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import requester.util.CurrencyManager;

import java.math.BigDecimal;

public class Money24Manager extends BaseManager implements Manager {

    private final static String URL = "https://money24.kharkov.ua/";

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
        setCompanyPricePln(doc);
        setCompanyPriceRub(doc);
        System.out.println(company);
    }

    private void setCompanyPriceRub(Document doc) {
        company.addCurrency(
            CurrencyManager.getObjectRUB(getBuyingPrice(doc, "RUB"), getSellingPrice(doc, "RUB")));
    }

    private void setCompanyPricePln(Document doc) {
        company.addCurrency(
            CurrencyManager.getObjectPLN(getBuyingPrice(doc, "PLN"), getSellingPrice(doc, "PLN")));
    }

    private void setCompanyPriceEur(Document doc) {
        company.addCurrency(
            CurrencyManager.getObjectEUR(getBuyingPrice(doc, "EUR"), getSellingPrice(doc, "EUR")));
    }

    private void setCompanyPriceUsd(Document doc) {
        company.addCurrency(
            CurrencyManager.getObjectUSD(getBuyingPrice(doc, "USD"), getSellingPrice(doc, "USD")));
    }

    private BigDecimal getBuyingPrice(Document doc, String code) {
        Element baseElement = doc.getElementsByAttributeValueMatching("data-index", code + "/UAH")
            .stream().filter(e -> !e.attr("data-sale").isEmpty()).findFirst().get();
        return getDecimal(baseElement.attr("data-buy"));
    }

    private BigDecimal getSellingPrice(Document doc, String code) {
        Element baseElement = doc.getElementsByAttributeValueMatching("data-index", code + "/UAH")
            .stream().filter(e -> !e.attr("data-sale").isEmpty()).findFirst().get();
        return getDecimal(baseElement.attr("data-sale"));
    }

    @Override public void setMetadata() {
        company.setId(2);
        company.setName("Money 24");
        company.setPhone("+380991990001");
        company.setAddress("ул. Рымарская, 23\nул. Сумская, 82\nул. Сумская, 13\nул. Сумская, 47\n"
            + "пр. Науки, 21\nпр. Московский, 70\nпр. Юбилейный, 56\nул. Амурская, 1 (ТЦ Барабашово)\n"
            + "пр. Людвига Свободы, 52\nул. Валентиновская, 21\nул. Полтавский Шлях, 56/58");
    }
}
