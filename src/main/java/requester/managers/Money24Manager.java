package requester.managers;

import models.Price;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Money24Manager extends BaseManager implements Manager {

    private final static String URL = "https://money24.kharkov.ua/";

    private Price price = new Price(URL);

    @Override public Price getPrice() {
        setPrice();
        return price;
    }

    public void setPrice() {
        Document doc = getHtmlDocument(URL);
        Element usdElement = doc.getElementsByAttributeValueMatching("data-index", "USD/UAH")
            .stream().filter(e -> !e.attr("data-sale").isEmpty()).findFirst().get();
        price.setBuyingRate(getDecimal(usdElement.attr("data-buy")));
        price.setSellingRate(getDecimal(usdElement.attr("data-sale")));
        System.out.println(price);
    }
}
