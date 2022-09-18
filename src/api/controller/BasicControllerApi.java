package api.controller;

import api.util.APIHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import util.Alert;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BasicControllerApi<T>{
    protected String BASE;
    private Class<T> persistentClass;

    protected void init (){
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T findById(Long id) throws Exception {

        Map<String, Object> uriParam = new HashMap<>();
        uriParam.put("id", id);

        ResponseEntity<T> response = new RestTemplate().getForEntity(
                APIHelper.BASE_URL.concat(BASE + "/find"),
                this.persistentClass, uriParam);
        Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);
        return response.getBody();
    }

    public abstract List<T> findAll()  throws Exception;

    public T save(T t) throws Exception{
        ResponseEntity<T> response = new RestTemplate().postForEntity(
                APIHelper.BASE_URL.concat(BASE + "/create"),
                t,
                this.persistentClass);
        Alert.Toast.showToast("Salvo com sucesso!", Alert.Type.SUCCESS);
        return response.getBody();
    }

    public T update(T t) throws Exception{
        ResponseEntity<T> response = new RestTemplate().postForEntity(
                APIHelper.BASE_URL.concat(BASE + "/update"),
                t,
                this.persistentClass);
        Alert.Toast.showToast("Atualizado com sucesso!", Alert.Type.SUCCESS);
        return response.getBody();
    }

    public T delete(T t) throws Exception{
        ResponseEntity<T> response = new RestTemplate().postForEntity(
                APIHelper.BASE_URL.concat(BASE + "/delete"),
                t,
                this.persistentClass);
        Alert.Toast.showToast("Eliminado com sucesso!", Alert.Type.SUCCESS);
        return response.getBody();
    }
}
