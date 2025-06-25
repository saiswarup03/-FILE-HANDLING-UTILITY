import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
public class WeatherFetcher {
    private static final String API_KEY = "your_api_key_here"; // Get one for free from https://openweathermap.org/api
    private static final String CITY = "London";
    private static final String URL_STRING =
        "https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&appid=" + API_KEY + "&units=metric";
    public static void main(String[] args) {
        try {
            URL url = new URL(URL_STRING);       
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {                
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();               
                JSONObject json = new JSONObject(response.toString());               
                String cityName = json.getString("name");
                JSONObject main = json.getJSONObject("main");
                double temp = main.getDouble("temp");
                int humidity = main.getInt("humidity");
                JSONObject weather = json.getJSONArray("weather").getJSONObject(0);
                String description = weather.getString("description");               
                System.out.println("📍 Weather in " + cityName);
                System.out.println("----------------------------");
                System.out.println("🌡️ Temperature: " + temp + " °C");
                System.out.println("💧 Humidity   : " + humidity + "%");
                System.out.println("☁️ Condition  : " + description);
            } else {
                System.out.println("❌ Failed to fetch data. HTTP code: " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("❌ IOException: " + e.getMessage());
        }
    }
}
