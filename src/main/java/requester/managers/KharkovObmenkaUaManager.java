package requester.managers;

import models.Price;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class KharkovObmenkaUaManager extends BaseManager implements Manager {

    private final static String URL = "https://kharkov.obmenka.ua/";

    private Price price = new Price(URL);

    @Override public Price getPrice() {
        setMetadata();
        setPrice();
        return price;
    }

    public void setPrice() {
        Document doc = getHtmlDocument(URL);

        Elements usdBuyElements = doc.getElementsByAttributeValueMatching("href", "/ru/USD-UAH").select(".buy");
        Elements usdSaleElements = doc.getElementsByAttributeValueMatching("href", "/ru/USD-UAH").select(".sell");
        price.setBuyingRate(getDecimal(usdBuyElements.get(0).text()));
        price.setSellingRate(getDecimal(usdSaleElements.get(0).text()));
        System.out.println(price);
    }

    @Override public void setMetadata() {
        price.setId(1);
        price.setName("kharkov.obmenka.ua");
        price.setAddress("пл. Павловская, 2");
        price.setPhone("+380965701110");
    }
}
