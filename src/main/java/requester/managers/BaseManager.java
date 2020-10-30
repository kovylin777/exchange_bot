package requester.managers;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.math.BigDecimal;

public class BaseManager {

    @SneakyThrows protected String sendGet(String url) {
        HttpRequestFactory requestFactory
            = new NetHttpTransport().createRequestFactory();
        HttpRequest request = requestFactory.buildGetRequest(
            new GenericUrl(url));
        return request.execute().parseAsString();
    }

    protected BigDecimal getDecimal(String string) {
        BigDecimal a = new BigDecimal(string);
        BigDecimal floored;
        if (a.compareTo(new BigDecimal(1)) < 0) {
            floored = a.setScale(3, BigDecimal.ROUND_DOWN);
        } else {
            floored = a.setScale(2, BigDecimal.ROUND_DOWN);
        }
        return floored;
    }

    protected Document getHtmlDocument(String url) {
        String html = sendGet(url);
        return Jsoup.parse(html);
    }
}
