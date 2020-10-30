package requester.managers;

import models.Company;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import requester.util.CurrencyManager;

public class ObmenkaKharkivUaManager extends BaseManager implements Manager {

    private final static String URL = "https://www.obmenka.kharkiv.ua/";

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

    private void setCompanyPriceUsd(Document doc) {
        Element baseElement = getBaseElement(doc, "USD/UAH");
        Element buyElement = baseElement.getElementsByAttributeValue("class", "cur_cell c2").first();
        Element sellElement = baseElement.getElementsByAttributeValue("class", "cur_cell c3").first();
        company.addCurrency(CurrencyManager
            .getObjectUSD(getDecimal(buyElement.text()), getDecimal(sellElement.text())));
    }

    private void setCompanyPriceEur(Document doc) {
        Element baseElement = getBaseElement(doc, "EUR/UAH");
        Element buyElement = baseElement.getElementsByAttributeValue("class", "cur_cell c2").first();
        Element sellElement = baseElement.getElementsByAttributeValue("class", "cur_cell c3").first();
        company.addCurrency(CurrencyManager
            .getObjectEUR(getDecimal(buyElement.text()), getDecimal(sellElement.text())));
    }

    private void setCompanyPricePln(Document doc) {
        Element baseElement = getBaseElement(doc, "PLN/UAH");
        Element buyElement = baseElement.getElementsByAttributeValue("class", "cur_cell c2").first();
        Element sellElement = baseElement.getElementsByAttributeValue("class", "cur_cell c3").first();
        company.addCurrency(CurrencyManager
            .getObjectPLN(getDecimal(buyElement.text()), getDecimal(sellElement.text())));
    }

    private void setCompanyPriceRub(Document doc) {
        Element baseElement = getBaseElement(doc, "RUB/UAH");
        Element buyElement = baseElement.getElementsByAttributeValue("class", "cur_cell c2").first();
        Element sellElement = baseElement.getElementsByAttributeValue("class", "cur_cell c3").first();
        company.addCurrency(CurrencyManager
            .getObjectRUB(getDecimal(buyElement.text()), getDecimal(sellElement.text())));
    }

    private Element getBaseElement(Document doc, String text) {
        return doc.getElementsContainingText(text)
            .parents().stream().filter(e -> e.hasClass("cur_row")).findFirst().get();
    }

    @Override public void setMetadata() {
        company.setId(3);
        company.setName("obmenka.kharkiv.ua");
        company.setAddress("ул. Полтавский шлях 23/25\nАвторынок Лоск");
        company.setPhone("+380677517434");
    }
}
