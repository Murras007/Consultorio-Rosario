package util;

import java.util.Stack;

public class Routes {

    private static Stack<String> History = new Stack();
    private static String actual_page = "";

    public static void addToHistory(String route) {
        History.push(route);
        actual_page = route;
    }

    public static Stack<String> getHistory() {
        return History;
    }

    public static String getActual_page() {
        return actual_page;
    }

    public static final class Page {

        public final static class Requisicao {
            public final static String MAIN = "/views/pages/requisicao/main.fxml";
        }
        public final static class Main {
            public final static String MAIN = "/views/main/main.fxml";
        }
        public final static class Home{
            public final static String MAIN = "/views/pages/home/home.fxml";
            public final static String ARMAZEM_PANE = "/views/pages/home/armazem_card.fxml";
        }

        public final static class Produto{
            public final static String MAIN = "/views/pages/produto/produto.fxml";
        }
        public final static class AUTH {
            public final static String LOGIN = "/views/pages/auth/auth.fxml";
            public final static String SIGNUP = "/views/fxml/home.fxml";
        }
    }
    public final static class Component {
        public final static class Departamento {
            public final static String MAIN = "/views/pages/departamentos/departamento.fxml";
            public final static String EDITOR = "/views/components/armazem/editor.fxml";
        }
        public final static class Armazem {
            public final static String MAIN = "/views/pages/armazem/armazem.fxml";
            public final static String EDITOR = "/views/components/armazem/editor.fxml";
        }
        public final static class Produto {
            public final static String EDITOR = "/views/components/produto/editor.fxml";
            public final static String HISTORICO = "/views/components/produto/produto_stock_historico.fxml";
            public final static String TRANSFERENCIA = "/views/components/produto/produto_stock_transferencia.fxml";
            public final static String CARD_TRANSFERENCIA = "/views/components/produto/produto_card_transferencia.fxml";
            public final static String CARD_TRANSFERENCIA_DESTINO = "/views/components/produto/produto_card_transferencia_destino.fxml";
        }
        public final static class Entrada {
            public final static String EDITOR = "/views/components/entrada/entrada.fxml";
            public final static String PAGAMENTO_ITEM = "/views/components/entrada/pagamento_item.fxml";
        }
        public final static class Requisicao {
            public final static String EDITOR = "/views/components/requisicao/requisicao.fxml";
            public final static String PAGAMENTO_ITEM = "/views/components/entrada/pagamento_item.fxml";
        }
    }
    public final static class Menu {
        public final static String FRONT_OFFICE = "/views/components/menus/frontoffice/menu.fxml";
        public final static String BACK_OFFICE = "/views/components/menus/backoffice/menu.fxml";
    }
}
