package requester;

import models.Price;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ObmenkaKhUaManager extends BaseManager implements Manager {

    private final static String URL = "http://obmenka.kh.ua/";

    private Price price = new Price(URL);

    @Override public Price getPrice() {
        setPrice();
        return price;
    }

    public void setPrice() {
        Document doc = getHtmlDocument(URL);

        Elements usdBuyElements = doc.getElementsByAttributeValueMatching("name", "usd_buy");
        Elements usdSaleElements = doc.getElementsByAttributeValueMatching("name", "usd_sale");
        price.setBuyingRate(getDecimal(usdBuyElements.get(0).html()));
        price.setSellingRate(getDecimal(usdSaleElements.get(0).html()));
        System.out.println(price);
    }
}
