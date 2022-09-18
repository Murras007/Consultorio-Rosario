package test;

import api.models.Utilizador;
import api.util.APIHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MainTest {

    public static void main(String[] args) {
        Utilizador usr = new Utilizador("admdsa","12343");
        try {
            ResponseEntity<Utilizador> response =  new RestTemplate().postForEntity(APIHelper.BASE_URL.concat("auth" + "/signin"), usr, Utilizador.class);
        }catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }

    }
}
