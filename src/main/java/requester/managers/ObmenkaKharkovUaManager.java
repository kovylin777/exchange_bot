package requester.managers;

import models.Price;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ObmenkaKharkovUaManager extends BaseManager implements Manager {

    private final static String URL = "https://obmenka.kharkov.ua/usd-uah";

    private Price price = new Price(URL);

    @Override public Price getPrice() {
        setPrice();
        return price;
    }

    public void setPrice() {
        Document doc = getHtmlDocument(URL);
        Element buyUsdElement = doc.getElementsByAttributeValueMatching("data-title", "Покупка")
            .select(".pair__block-retail").select(".pair__block-num").first();
        Element sellUsdElement = doc.getElementsByAttributeValueMatching("data-title", "Продажа")
            .select(".pair__block-retail").select(".pair__block-num").first();
        price.setBuyingRate(getDecimal(buyUsdElement.text()));
        price.setSellingRate(getDecimal(sellUsdElement.text()));
        System.out.println(price);
    }
}
