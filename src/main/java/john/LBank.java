package john;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

/**
 * @author John
 */

@SuppressWarnings("all")
public class LBank {
    public static void main(String[] args) throws IOException {
        ObjectMapper lbankMapper = new ObjectMapper();
        JsonNode parseLbank= lbankMapper.readTree(getAllCurrencyPairs("https://api.lbkex.com/v2/currencyPairs.do"));

        // Return all pairs with their price
        for(int i = 0; i < 300; i++) {
            System.out.println(getOneSymbolFromLbank(parseLbank.get("data").get(i).textValue()));
        }

//        System.out.println(getOneSymbolFromLbank("btc_usdt"));  // Информация о BTC_USDT (name, price)

    }

    /**
     * @param requestLink Принимает ссылку на GET запрос для получение всех связок
     * @return название всех существующих пар
     */
    public static String getAllCurrencyPairs(String requestLink){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(requestLink, String.class);
    }


    /**
    * <h3>Парсит переданную связь в параментры(Например: btc_usdt) из биржи LBank</h3>
    * @param symbol Имя связки, которую нужно спарсить
    * @return Имя связи и цена
    * @throws JsonProcessingException
    */
    public static String getOneSymbolFromLbank(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String req = "https://api.lbkex.com/v2/ticker/24hr.do?symbol=" + symbol;
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);
        return obj.get("data").get(0).get("symbol") + " " + obj.get("data").get(0).get("ticker").get("latest");
    }
}