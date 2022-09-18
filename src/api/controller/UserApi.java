package api.controller;

import api.models.Utilizador;
import api.util.APIHelper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import util.Alert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class UserApi extends BasicControllerApi<Utilizador> {

    public UserApi(){
        this.BASE = "user/";
        this.init();
    }

    @Override
    public List<Utilizador> findAll() throws Exception {

        ResponseEntity<List<Utilizador>> response = new RestTemplate().exchange(
                APIHelper.BASE_URL.concat(BASE + "/all"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Utilizador>>(){});
        if ( Objects.requireNonNull(response.getBody()).size()>0)
            Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);
        else
            Alert.Toast.showToast("Sem dados disponíveis", Alert.Type.INFO);
        return  response.getBody();
    }

    public static class Auth{
        private static final String BASE ="auth/";
        public static void teste(String username, String password) throws Exception {
            System.err.println(APIHelper.BASE_URL.concat(BASE + "signin"));
            URL url = new URL(APIHelper.BASE_URL.concat(BASE + "signin"));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            String jsonInputString = "{\"username\": "+ "\""+username+"\", \"password\": \""+password+"\"}";
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

        }

        public Utilizador signin(Utilizador model)  throws Exception{

            ResponseEntity<Utilizador> response = new RestTemplate().postForEntity(
                    APIHelper.BASE_URL.concat(BASE + "signin"),
                    model,
                    Utilizador.class);

                return response.getBody();
        }

        public Utilizador signup(Utilizador model)  throws Exception{
            ResponseEntity<Utilizador> response = new RestTemplate().postForEntity(
                    APIHelper.BASE_URL.concat(this.BASE + "/signup"),
                    model,
                    Utilizador.class);

                Alert.Toast.showToast("Salvo com sucesso!", Alert.Type.SUCCESS);
                return response.getBody();
        }
    }
}
