package api.controller;

import api.models.fornecedor.Fornecedor;
import api.util.APIHelper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import util.Alert;

import java.util.List;
import java.util.Objects;

public class FornecedorControllerApi extends BasicControllerApi<Fornecedor> {

    public FornecedorControllerApi() {
        BASE = "fornecedor";
    }


    @Override
    public List<Fornecedor> findAll() throws Exception {

        ResponseEntity<List<Fornecedor>> response = new RestTemplate().exchange(
                APIHelper.BASE_URL.concat(BASE + "/all"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Fornecedor>>(){});
        if ( Objects.requireNonNull(response.getBody()).size()>0)
            Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);
        else
            Alert.Toast.showToast("Sem dados disponíveis", Alert.Type.INFO);
        return  response.getBody();
    }
}
