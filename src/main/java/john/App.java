package john;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;


/**
 * @author John
 */

@SuppressWarnings("all")
public class App {
    public static void main(String[] args) throws IOException {
        //                             BINANCE
        Document binance = Jsoup.connect("https://www.binance.com/en/markets/spot-USDT").get();
        System.out.println(binance.title());
        Elements binanceCryptoNames = binance.getElementsByClass("css-vlibs4");
        for (Element crypto : binanceCryptoNames){
            System.out.println(crypto.getElementsByClass("css-17wnpgm").text() + " " + crypto.getElementsByClass("css-1vzayxe").text());
        }
        
        //                  MEXC
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.mexc.com/open/api/v2/market/ticker"; // Все связки

        String allSymbolsFromMexc = restTemplate.getForObject(url, String.class);
        ObjectMapper mexcMapper = new ObjectMapper();
        JsonNode parseMexc = mexcMapper.readTree(allSymbolsFromMexc);

        for(int i = 0; i < 2000; i++) {
            System.out.println(parseMexc.get("data").get(i).get("symbol") +" "+ parseMexc.get("data").get(i).get("last"));
        }


        // Получаем BTC_USDT из MEXC
        System.out.println(getOnSymbolFromMexc("BTC_USDT"));
    }

    /**
     * <h3>Парсит переданную связь в параментры(Например: BTC_USDT) из биржи MEXC</h3>
     * @param symbol Имя связки, которую нужно спарсить
     * @return Имя связи и цена
     * @throws JsonProcessingException
     */
    public static String getOnSymbolFromMexc(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String req = "https://contract.mexc.com/api/v1/contract/fair_price/" + symbol;
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);
        return obj.get("data").get("symbol") +" "+ obj.get("data").get("fairPrice");
    }
}