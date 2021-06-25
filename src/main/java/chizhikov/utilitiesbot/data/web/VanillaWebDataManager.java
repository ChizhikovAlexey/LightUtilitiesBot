package chizhikov.utilitiesbot.data.web;

import chizhikov.utilitiesbot.data.db.entities.MonthData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@AllArgsConstructor
public class VanillaWebDataManager implements WebDataManager {
    private final String login;
    private final String password;
    private final String WEB_URL;

    @Override
    public void sendMonthData(MonthData monthData) {
        //TODO
    }

    public String login() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        String body = "wlogin=" + login + "&" + "wpassword=" + password + "&soglasie=soglasie";
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(WEB_URL + "inner.php"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return httpResponse.statusCode() + "\n" + httpResponse.body();
    }
}
