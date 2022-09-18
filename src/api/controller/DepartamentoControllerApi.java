package api.controller;

import api.models.armazem.Armazem;
import api.util.APIHelper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import util.Alert;

import java.util.List;
import java.util.Objects;

public class DepartamentoControllerApi extends BasicControllerApi<Armazem> {

    public DepartamentoControllerApi() {
        BASE = "armazem";
    }


    @Override
    public List<Armazem> findAll() throws Exception {

        ResponseEntity<List<Armazem>> response = new RestTemplate().exchange(
                APIHelper.BASE_URL.concat(BASE + "/all"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Armazem>>(){});
        if ( Objects.requireNonNull(response.getBody()).size()>0)
            Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);
        else
            Alert.Toast.showToast("Sem dados disponíveis", Alert.Type.INFO);
        return  response.getBody();
    }
}
