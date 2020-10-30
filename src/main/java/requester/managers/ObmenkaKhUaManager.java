package requester.managers;

import models.Company;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import requester.util.CurrencyManager;

import java.math.BigDecimal;

public class ObmenkaKhUaManager extends BaseManager implements Manager {

    private final static String URL = "http://obmenka.kh.ua/";

    private Company company;

    @Override public Company getCompany() {
        company = new Company(URL);
        setMetadata();
        setCompanyPrice();
        return company;
    }

    public void setCompanyPrice() {
        Document doc = getHtmlDocument(URL);

        Element baseElement = doc.getElementsContainingOwnText("РОЗНИЦА")
            .stream().filter(e -> e.hasClass("tablo-title")).findFirst().get().parent();

        Elements usdBuyElements = baseElement.getElementsByAttributeValueMatching("name", "usd_buy");
        Elements usdSaleElements = baseElement.getElementsByAttributeValueMatching("name", "usd_sale");
        BigDecimal usdBuyRate = getDecimal(usdBuyElements.get(0).html());
        BigDecimal usdSellRate = getDecimal(usdSaleElements.get(0).html());
        company.addCurrency(CurrencyManager.getObjectUSD(usdBuyRate, usdSellRate));

        Elements eurBuyElements = baseElement.getElementsByAttributeValueMatching("name", "eur_buy");
        Elements eurSaleElements = baseElement.getElementsByAttributeValueMatching("name", "eur_sale");
        BigDecimal eurBuyRate = getDecimal(eurBuyElements.get(0).html());
        BigDecimal eurSellRate = getDecimal(eurSaleElements.get(0).html());
        company.addCurrency(CurrencyManager.getObjectEUR(eurBuyRate, eurSellRate));

        Elements plnBuyElements = baseElement.getElementsByAttributeValueMatching("name", "pln_buy");
        Elements plnSaleElements = baseElement.getElementsByAttributeValueMatching("name", "pln_sale");
        BigDecimal plnBuyRate = getDecimal(plnBuyElements.get(0).html());
        BigDecimal plnSellRate = getDecimal(plnSaleElements.get(0).html());
        company.addCurrency(CurrencyManager.getObjectPLN(plnBuyRate, plnSellRate));

        Elements rubBuyElements = baseElement.getElementsByAttributeValueMatching("name", "rub_buy");
        Elements rubSaleElements = baseElement.getElementsByAttributeValueMatching("name", "rub_sale");
        BigDecimal rubBuyRate = getDecimal(rubBuyElements.get(0).html());
        BigDecimal rubSellRate = getDecimal(rubSaleElements.get(0).html());
        company.addCurrency(CurrencyManager.getObjectRUB(rubBuyRate, rubSellRate));

        System.out.println(company);
    }

    @Override public void setMetadata() {
        company.setId(5);
        company.setName("obmenka.kh.ua");
        company.setAddress("пр. Героев Сталинграда, 1А\nул. Чеботарская, 5\nпл. Конституции, 1\nпр. Героев Труда, 6\n"
            + "Сумской рынок\nул. Рождественская, 33");
        company.setPhone("+380966028040");
    }
}
