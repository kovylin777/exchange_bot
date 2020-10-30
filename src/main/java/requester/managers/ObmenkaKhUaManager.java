package requester.managers;

import models.Price;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ObmenkaKhUaManager extends BaseManager implements Manager {

    private final static String URL = "http://obmenka.kh.ua/";

    private Price price = new Price(URL);

    @Override public Price getPrice() {
        setMetadata();
        setPrice();
        return price;
    }

    public void setPrice() {
        Document doc = getHtmlDocument(URL);

        Element baseElement = doc.getElementsContainingOwnText("РОЗНИЦА")
            .stream().filter(e -> e.hasClass("tablo-title")).findFirst().get().parent();

        Elements usdBuyElements = baseElement.getElementsByAttributeValueMatching("name", "usd_buy");
        Elements usdSaleElements = baseElement.getElementsByAttributeValueMatching("name", "usd_sale");
        price.setBuyingRate(getDecimal(usdBuyElements.get(0).html()));
        price.setSellingRate(getDecimal(usdSaleElements.get(0).html()));
        System.out.println(price);
    }

    @Override public void setMetadata() {
        price.setId(5);
        price.setName("obmenka.kh.ua");
        price.setAddress("пр. Героев Сталинграда, 1А\nул. Чеботарская, 5\nпл. Конституции, 1\nпр. Героев Труда, 6\n"
            + "Сумской рынок\nул. Рождественская, 33");
        price.setPhone("+380966028040");
    }
}
