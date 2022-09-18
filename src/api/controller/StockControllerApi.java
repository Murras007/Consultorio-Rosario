package api.controller;

import api.models.entrada.Lote;
import api.models.enums.Direction;
import api.models.stock.Stock;
import api.models.stock.StockProduto;
import api.models.stock.Transferencia;
import api.util.APIHelper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import util.Alert;

import java.util.*;

public class StockControllerApi extends BasicControllerApi<Stock> {

    public StockControllerApi() {
        BASE = "stock";
    }

    @Override
    public List<Stock> findAll() throws Exception {
        ResponseEntity<List<Stock>> response = new RestTemplate().exchange(
                APIHelper.BASE_URL.concat(BASE + "/all"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Stock>>(){});
        if ( Objects.requireNonNull(response.getBody()).size()>0)
            Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);
        else
            Alert.Toast.showToast("Sem dados disponíveis", Alert.Type.INFO);
        return  response.getBody();
    }

    public List<StockProduto> findAllStokProduto() throws Exception {
        List<StockProduto> stockProdutos = new ArrayList<>();
        ResponseEntity<List<Stock>> response = new RestTemplate().exchange(
                APIHelper.BASE_URL.concat(BASE + "/produto/all"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Stock>>(){});
        if ( Objects.requireNonNull(response.getBody()).size()>0)
            Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);
        else
            Alert.Toast.showToast("Sem dados disponíveis", Alert.Type.INFO);
        List<Stock> stockList  = response.getBody();
        if (stockList == null || stockList.isEmpty()) return stockProdutos;
       for(Stock stock: stockList){

           StockProduto sProduto = new StockProduto();

           sProduto.id = stock.id;
           sProduto.produto = stock.lote.produto.designacao;
           sProduto.quantidade = stock.quantidade;
           sProduto.movimento = stock.movimento;
           sProduto.armazem = stock.lote.armazem.designacao;
           sProduto.referencia = stock.lote.referencia;
           sProduto.feito_por = stock.feito_por;
           sProduto.stockAnterior = stock.stockAnterior;
           sProduto.stockActual = stock.stockActual;
           sProduto.lote = String.valueOf( stock.lote.id);

           sProduto.looter = stock.lote;
           sProduto.produto_id = stock.lote.produto.id;
           sProduto.armazem_id = stock.lote.armazem.id;

           sProduto.sinal =
                   Objects.equals(stock.stockActual, stock.stockAnterior)?Direction.STABLE:
                           stock.stockActual > stock.stockAnterior?Direction.UP:Direction.DOWN;
           stockProdutos.add(sProduto);
       }
        return stockProdutos;
    }

    public List<StockProduto> findHistoricoProdutoByLote(Lote lote) throws Exception {

        Map<String, Object> uriParam = new HashMap<>();
        uriParam.put("lote", lote);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(APIHelper.BASE_URL.concat(BASE +  "/produto/historico"))
                .queryParam("lote", "{lote}")
                .encode()
                .toUriString();

        ResponseEntity<Stock[]> response = new RestTemplate().
                        postForEntity(APIHelper.BASE_URL.concat(BASE +  "/produto/historico"),lote,Stock[].class);

        List<Stock> stockList  = Arrays.asList(Objects.requireNonNull(response.getBody()));
        if (stockList == null || stockList.isEmpty()) return null;

        List<StockProduto> stockProdutos = new ArrayList<>();

        Objects.requireNonNull(stockList);
        Alert.Toast.showToast("Dados carregado com sucesso", Alert.Type.SUCCESS);

        for(Stock stock: stockList){
            StockProduto sProduto = new StockProduto();

            sProduto.id = stock.id;
            sProduto.produto = stock.lote.produto.designacao;
            sProduto.quantidade = stock.quantidade;
            sProduto.movimento = stock.movimento;
            sProduto.armazem = stock.lote.armazem.designacao;
            sProduto.referencia = stock.lote.referencia;
            sProduto.feito_por = stock.feito_por;
            sProduto.stockAnterior = stock.stockAnterior;
            sProduto.stockActual = stock.stockActual;
            sProduto.lote = String.valueOf( stock.lote.id);

            sProduto.looter = stock.lote;
            sProduto.produto_id = stock.lote.produto.id;
            sProduto.armazem_id = stock.lote.armazem.id;



            sProduto.sinal =
                    Objects.equals(stock.stockActual, stock.stockAnterior) ?Direction.STABLE:
                            stock.stockActual > stock.stockAnterior?Direction.UP:Direction.DOWN;
            stockProdutos.add(sProduto);
        }
        return stockProdutos;

    }


    public Transferencia transferir(Transferencia t) throws Exception{
        ResponseEntity<Transferencia> response = new RestTemplate().postForEntity(
                APIHelper.BASE_URL.concat(BASE + "/transferir"),
                t,
                Transferencia.class);
        Alert.Toast.showToast("Salvo com sucesso!", Alert.Type.SUCCESS);
        return response.getBody();
    }
}
