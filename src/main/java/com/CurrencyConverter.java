import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Type currency to convert from");
            String convertFrom = scanner.nextLine().toUpperCase();
            System.out.println("Type currency to convert to");
            String convertTo = scanner.nextLine().toUpperCase();
            System.out.println("Type quantity to convert");
            BigDecimal quantity = scanner.nextBigDecimal();

            String accessKey = "a6f7a9fb4ea81fa734cca141"; // Replace this with your actual API access key
            String urlstring = "https://api.exchangerate.host/latest?base=" + convertFrom + "&access_key=" + accessKey;


            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urlstring)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();

            String stringResponse = response.body().string();
            System.out.println("JSON Response: " + stringResponse); // Print the JSON response

            JSONObject jsonObject = new JSONObject(stringResponse);
            JSONObject ratesobject = jsonObject.getJSONObject("rates");
            BigDecimal rate = ratesobject.getBigDecimal(convertTo);

            BigDecimal result = rate.multiply(quantity);
            System.out.println("Result: " + result);
        } catch (IOException e) {
            System.err.println("Error occurred during HTTP request: " + e.getMessage());
        } catch (org.json.JSONException e) {
            System.err.println("Error occurred during JSON parsing: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
