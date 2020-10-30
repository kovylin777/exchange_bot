package requester.managers;

import models.Price;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Money24Manager extends BaseManager implements Manager {

    private final static String URL = "https://money24.kharkov.ua/";

    private Price price = new Price(URL);

    @Override public Price getPrice() {
        setMetadata();
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

    @Override public void setMetadata() {
        price.setId(2);
        price.setName("Money 24");
        price.setPhone("+380991990001");
        price.setAddress("ул. Рымарская, 23\nул. Сумская, 82\nул. Сумская, 13\nул. Сумская, 47\n"
            + "пр. Науки, 21\nпр. Московский, 70\nпр. Юбилейный, 56\nул. Амурская, 1 (ТЦ Барабашово)\n"
            + "пр. Людвига Свободы, 52\nул. Валентиновская, 21\nул. Полтавский Шлях, 56/58");
    }
}
