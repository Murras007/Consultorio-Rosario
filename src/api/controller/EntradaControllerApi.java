package api.controller;

import api.models.entrada.Entrada;
import api.models.entrada.FormaEntrada;
import api.util.APIHelper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import util.Alert;
import util.FileUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EntradaControllerApi extends BasicControllerApi<Entrada> {

    public EntradaControllerApi() {
        BASE = "entrada";
    }


    @Override
    public List<Entrada> findAll() throws Exception {

        ResponseEntity<List<Entrada>> response = new RestTemplate().exchange(
                APIHelper.BASE_URL.concat(BASE + "/all"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Entrada>>(){});
        if ( Objects.requireNonNull(response.getBody()).size()>0)
            Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);
        else
            Alert.Toast.showToast("Sem dados disponíveis", Alert.Type.INFO);
        return  response.getBody();
    }

    @Override
    public Entrada save(Entrada t) throws Exception{
        ResponseEntity<Entrada> response = new RestTemplate().postForEntity(
                APIHelper.BASE_URL.concat(BASE + "/create"),
                t,
                Entrada.class);
        Alert.Toast.showToast("Salvo com sucesso!", Alert.Type.SUCCESS);
        return response.getBody();
    }

    public void findReport(Long id) throws Exception {
        Map<String, Object> uriParam = new HashMap<>();
        uriParam.put("id", id);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(APIHelper.BASE_URL.concat(BASE + "/find/report"))
                .queryParam("id", "{id}")
                .encode()
                .toUriString();

        ResponseEntity<byte[]> response = new RestTemplate().exchange(
                urlTemplate,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                byte[].class,
                uriParam);

        Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);
        FileUtil.openTempPDF(response.getBody());
    }

    public static class FormaEntradaControllerApi extends  BasicControllerApi<FormaEntrada> {

        public FormaEntradaControllerApi() {
            BASE = "entrada/forma";
        }
        @Override
        public List<FormaEntrada> findAll() throws Exception {

            ResponseEntity<List<FormaEntrada>> response = new RestTemplate().exchange(
                    APIHelper.BASE_URL.concat(BASE + "/all"),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<FormaEntrada>>(){});
            if ( Objects.requireNonNull(response.getBody()).size()>0)
                Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);
            else
                Alert.Toast.showToast("Sem dados disponíveis", Alert.Type.INFO);
            return  response.getBody();
        }

    }
}
