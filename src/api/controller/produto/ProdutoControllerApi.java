package api.controller.produto;

import api.controller.BasicControllerApi;
import api.models.entrada.Lote;
import api.models.produto.Categoria;
import api.models.produto.Produto;
import api.util.APIHelper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import util.Alert;

import java.io.File;
import java.util.*;

public class ProdutoControllerApi extends BasicControllerApi<Produto> {

    public ProdutoControllerApi() {
        BASE = "produto";
    }

    public List<Lote> findAllLote(Long id) throws Exception {

        Map<String, Object> uriParam = new HashMap<>();
        uriParam.put("id", id);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(APIHelper.BASE_URL.concat(BASE +  "/lote/all"))
                .queryParam("id", "{id}")
                .encode()
                .toUriString();

        ResponseEntity<List<Lote>> response = new RestTemplate().exchange(
                urlTemplate,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<Lote>>(){},
                uriParam);

        Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);
        return response.getBody();
    }

    @Override
    public List<Produto> findAll() throws Exception {

        ResponseEntity<List<Produto>> response = new RestTemplate().exchange(
                APIHelper.BASE_URL.concat(BASE + "/all"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Produto>>(){});
        if ( Objects.requireNonNull(response.getBody()).size()>0)
            Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);
        else
            Alert.Toast.showToast("Sem dados disponíveis", Alert.Type.INFO);
        return  response.getBody();
    }
    public static final class CategoriaControllerApi extends BasicControllerApi<Categoria> {

        public CategoriaControllerApi() {
            BASE = "produto/categoria";
        }

        @Override
        public List<Categoria> findAll() throws Exception {

            ResponseEntity<List<Categoria>> response = new RestTemplate().exchange(
                    APIHelper.BASE_URL.concat(BASE + "/all"),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Categoria>>() {
                    });
            if (Objects.requireNonNull(response.getBody()).size() > 0)
                Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);
            else
                Alert.Toast.showToast("Sem dados disponíveis", Alert.Type.INFO);
            return response.getBody();
        }
    }

    public Produto save(Produto t, File logo) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(Collections.singletonList(MediaType.MULTIPART_FORM_DATA));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();

        multipartBodyBuilder.part("logo", logo, MediaType.IMAGE_JPEG);
        multipartBodyBuilder.part("entity", t);

        // multipart/form-data request body
        MultiValueMap<String, HttpEntity<?>> multipartBody = multipartBodyBuilder.build();

        HttpEntity<MultiValueMap<String, HttpEntity<?>>> requestEntity = new HttpEntity<>(multipartBody, headers);

        ResponseEntity<Produto> response = restTemplate.postForEntity(
                APIHelper.BASE_URL.concat(BASE + "/create"),
                requestEntity,
                Produto.class);
        Alert.Toast.showToast("Salvo com sucesso!", Alert.Type.SUCCESS);
        return response.getBody();
    }


    @Override
    public Produto findById(Long id) throws Exception {

        Map<String, Object> uriParam = new HashMap<>();
        uriParam.put("id", id);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(APIHelper.BASE_URL.concat(BASE +  "/find"))
                .queryParam("id", "{id}")
                .encode()
                .toUriString();

        ResponseEntity<Produto> response = new RestTemplate().exchange(
                urlTemplate,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Produto.class,
                uriParam);

        Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);
        return response.getBody();
    }
}
