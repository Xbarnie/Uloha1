package sk.fri.uniza;

import retrofit2.Call;
import retrofit2.Response;
import sk.fri.uniza.model.Location;
import sk.fri.uniza.model.WeatherData;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Hello IoT!
 */
public class App {
    public static void main(String[] args) {
        IotNode iotNode = new IotNode();
        // Vytvorenie požiadavky na získanie údajov o aktuálnom počasí z
        // meteo stanice s ID: station_1
        Call<Map<String, String>> currentWeather =
                iotNode.getWeatherStationService()
                        .getCurrentWeatherAsMap("station_1",
                                List.of("time", "date",
                                        "airTemperature"));


        try {
            // Odoslanie požiadavky na server pomocou REST rozhranie
            Response<Map<String, String>> response = currentWeather.execute();

            if (response.isSuccessful()) { // Dotaz na server bol neúspešný
                //Získanie údajov vo forme Mapy stringov
                Map<String, String> body = response.body();
                System.out.println("aktualne pocasie zo stanice 1:");
                System.out.println(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Vytvorenie požiadavky na získanie údajov o všetkých meteo staniciach
        Call<List<Location>> stationLocations =
                iotNode.getWeatherStationService().getStationLocations();

        try {
            Response<List<Location>> response = stationLocations.execute();

            if (response.isSuccessful()) { // Dotaz na server bol neúspešný
                //Získanie údajov vo forme Zoznam lokacií
                List<Location> body = response.body();
                System.out.println("lokacia stanice 1:");
                System.out.println(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Vytvorenie požiadavky na získanie údajov o aktuálnom počasí z
        // meteo stanice s ID: station_1
        Call<WeatherData> currentWeatherPojo =
                iotNode.getWeatherStationService()
                        .getCurrentWeather("station_1");


        try {
            // Odoslanie požiadavky na server pomocou REST rozhranie
            Response<WeatherData> response = currentWeatherPojo.execute();

            if (response.isSuccessful()) { // Dotaz na server bol neúspešný
                //Získanie údajov vo forme inštancie triedy WeatherData
                WeatherData body = response.body();
                System.out.println("aktualne pocasie zo stanice 1, pouzitie Listu so vsetkymi polozkami:");
                System.out.println(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

///HISTORY s Fieldami
        Call<List<WeatherData>> historyWeatherWithFields = iotNode.getWeatherStationService()
                .getHistoryWeather("station_1", "11/05/2020 11:00", "20/05/2020 15:00", List.of("time", "date",
                                "airTemperature"));

        try {
            // Odoslanie požiadavky na server pomocou REST rozhranie
            Response<List<WeatherData>> response = historyWeatherWithFields.execute();

            if (response.isSuccessful()) { // Dotaz na server bol neúspešný
                //Získanie údajov vo forme inštancie triedy WeatherData
                List<WeatherData> body = response.body();
                System.out.println("Historia pocasia s fieldami:");
                System.out.println(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        double a = iotNode.getAverageTemperature("station_1", "11/05/2020 11:00", "20/05/2020 15:00");
        System.out.println("Priemerná teplota je: " + a);
    }
}