package api.controller;

import api.models.stock.Requisicao;
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

public class RequisicaoControllerApi extends BasicControllerApi<Requisicao> {

    public RequisicaoControllerApi() {
        BASE = "requisicao";
    }


    @Override
    public List<Requisicao> findAll() throws Exception {

        ResponseEntity<List<Requisicao>> response = new RestTemplate().exchange(
                APIHelper.BASE_URL.concat(BASE + "/all"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Requisicao>>(){});
        if ( Objects.requireNonNull(response.getBody()).size()>0)
            Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);
        else
            Alert.Toast.showToast("Sem dados disponíveis", Alert.Type.INFO);
        return  response.getBody();
    }

    @Override
    public Requisicao save(Requisicao t) throws Exception{
        ResponseEntity<Requisicao> response = new RestTemplate().postForEntity(
                APIHelper.BASE_URL.concat(BASE + "/create"),
                t,
                Requisicao.class);
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
}
