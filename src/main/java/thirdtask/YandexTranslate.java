package thirdtask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class YandexTranslate {

    private static final String KEY = "trnsl.1.1.20180218T075300Z.e9204452cbb69b62.f9ab10beae3b1d931bfe158a2207714e0924d51e";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your request: ");
        while (true) {
            String str = scanner.nextLine();
            if ("STOP".equals(str)) break;
            String result = createRequest(str);
            System.out.println(result);
        }
        System.out.println("Bye!");
    }

    private static String createRequest(String text) throws IOException {
        String  escapedText = text.replace(" ", "%20");
        String requestUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + KEY
                + "&text=" + escapedText
                + "&lang=en-ru";
        URL url = new URL(requestUrl);
        HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.connect();
        InputStream response = connection.getInputStream();
        String json = new Scanner(response).nextLine();
        int start = json.indexOf("[");
        int end = json.indexOf("]");
        return json.substring(start + 2, end - 1);

    }
}
