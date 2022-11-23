package john;

import java.io.IOException;

/**
 * @author John
 */

@SuppressWarnings("all")
public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Here you can test something");

        System.out.println("Bybit: ");
        Bybit bybit = new Bybit();
        bybit.getInfo();
    }
}