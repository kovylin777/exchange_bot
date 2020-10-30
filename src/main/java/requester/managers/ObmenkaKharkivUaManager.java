package requester.managers;

import models.Price;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ObmenkaKharkivUaManager extends BaseManager implements Manager {

    private final static String URL = "https://www.obmenka.kharkiv.ua/";

    private Price price = new Price(URL);

    @Override public Price getPrice() {
        setMetadata();
        setPrice();
        return price;
    }

    public void setPrice() {
        Document doc = getHtmlDocument(URL);
        Element baseElement = doc.getElementsContainingText("USD/UAH")
            .parents().stream().filter(e -> e.hasClass("cur_row")).findFirst().get();
        Element buyUsdElement = baseElement.getElementsByAttributeValue("class", "cur_cell c2").first();
        Element sellUsdElement = baseElement.getElementsByAttributeValue("class", "cur_cell c3").first();
        price.setBuyingRate(getDecimal(buyUsdElement.text()));
        price.setSellingRate(getDecimal(sellUsdElement.text()));
        System.out.println(price);
    }

    @Override public void setMetadata() {
        price.setId(3);
        price.setName("obmenka.kharkiv.ua");
        price.setAddress("ул. Полтавский шлях 23/25\nАвторынок Лоск");
        price.setPhone("+380677517434");
    }
}
