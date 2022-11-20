package john;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

/**
 * @author John
 */


public class Binance {
    public static void main(String[] args) throws IOException {
        Document binance = Jsoup.connect("https://www.binance.com/en/markets/spot-USDT").get();
        System.out.println(binance.title());
        Elements binanceCryptoNames = binance.getElementsByClass("css-vlibs4");
        for (Element crypto : binanceCryptoNames){
            System.out.println(crypto.getElementsByClass("css-17wnpgm").text() + " " + crypto.getElementsByClass("css-1vzayxe").text());
        }
    }
}