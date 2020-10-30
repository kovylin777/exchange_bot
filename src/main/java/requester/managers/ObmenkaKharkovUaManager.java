package requester.managers;

import models.Company;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import requester.util.CurrencyManager;

public class ObmenkaKharkovUaManager extends BaseManager implements Manager {

    private final static String URL = "https://obmenka.kharkov.ua/";

    private Company company;

    @Override public Company getCompany() {
        company = new Company(URL);
        setMetadata();
        setCompanyPrice();
        return company;
    }

    public void setCompanyPrice() {
        setCompanyPriceUsd();
        setCompanyPriceEur();
        setCompanyPricePln();
        setCompanyPriceRub();
        System.out.println(company);
    }

    private void setCompanyPriceUsd() {
        Document doc = getHtmlDocument(URL + "usd-uah");
        Element buyElement = getBuyElement(doc);
        Element sellElement = getSellElement(doc);
        company.addCurrency(CurrencyManager
            .getObjectUSD(getDecimal(buyElement.text()), getDecimal(sellElement.text())));
    }

    private void setCompanyPriceEur() {
        Document doc = getHtmlDocument(URL + "eur-uah");
        Element buyElement = getBuyElement(doc);
        Element sellElement = getSellElement(doc);
        company.addCurrency(CurrencyManager
            .getObjectEUR(getDecimal(buyElement.text()), getDecimal(sellElement.text())));
    }

    private void setCompanyPricePln() {
        Document doc = getHtmlDocument(URL + "pln-uah");
        Element buyElement = getBuyElement(doc);
        Element sellElement = getSellElement(doc);
        company.addCurrency(CurrencyManager
            .getObjectPLN(getDecimal(buyElement.text()), getDecimal(sellElement.text())));
    }

    private void setCompanyPriceRub() {
        Document doc = getHtmlDocument(URL + "rub-uah");
        Element buyElement = getBuyElement(doc);
        Element sellElement = getSellElement(doc);
        company.addCurrency(CurrencyManager
            .getObjectRUB(getDecimal(buyElement.text()), getDecimal(sellElement.text())));
    }

    private Element getBuyElement(Document doc) {
        return doc.getElementsByAttributeValueMatching("data-title", "Покупка")
            .select(".pair__block-retail").select(".pair__block-num").first();
    }

    private Element getSellElement(Document doc) {
        return doc.getElementsByAttributeValueMatching("data-title", "Продажа")
            .select(".pair__block-retail").select(".pair__block-num").first();
    }

    @Override public void setMetadata() {
        company.setId(4);
        company.setName("КИТ Group");
        company.setAddress("ул. Кооперативная 6/8 (за магазином \"Сладкий Мир\")");
        company.setPhone("+380800211053");
    }
}
