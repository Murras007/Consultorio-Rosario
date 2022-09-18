package views.components.dynamic.datatable;

import api.models.enums.Direction;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import util.IconsPath;
import util.LocalDateTimeUtil;
import util.StringUtil;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractDynamicDataTable<T> {

    final JFXSpinner spinner = new JFXSpinner();
    protected final BorderPane MAIN_PANE = new BorderPane();
    protected final List<Map<String, Object>> columnData = new ArrayList<>();
    protected List<DynamicColumn> columns =  new ArrayList<>();
    protected List<T> data;
    protected List<T> displayedData;
    protected List<T> selectedData;
    protected List<T> filteredData;
    protected int displayRowCount = 10;
    protected int pageIndex = 0;
    private int numberOfPages = 0;
    private int displayIndex = 0;
    private String titleName;

    // JFX COMPONENTS
    protected final BorderPane borderContent = new BorderPane();
    final protected VBox listContent = new VBox();

    // JFX CONTROLOS DE PÁGINA
    final HBox paginationBox = new HBox();
    final JFXButton nextButton = new JFXButton();
    final JFXButton prevButton = new JFXButton();


    public AbstractDynamicDataTable(){
        this(null);
    }
    public AbstractDynamicDataTable(List<T> data){
        this("Title", data, 10);
    }
    public AbstractDynamicDataTable(List<T> data, DynamicColumn... columns){
        this("Title", data, 10, columns);
    }
    public AbstractDynamicDataTable(String title, List<T> data, int displayRowCount, DynamicColumn... columns){
        this.columns = Arrays.asList(columns);
        //STYLING
        MAIN_PANE.getStyleClass().add("data-table-container");

        // Anchoring
        AnchorPane.setRightAnchor(MAIN_PANE, 10.0);
        AnchorPane.setTopAnchor(MAIN_PANE, 10.0);
        AnchorPane.setLeftAnchor(MAIN_PANE, 10.0);
        AnchorPane.setBottomAnchor(MAIN_PANE, 10.0);

        // Spacing itens
        listContent.setSpacing(5.0);

        //Align to Center
        listContent.setAlignment(Pos.TOP_CENTER);

        //Data preparing
        this.data = data;
        this.filteredData = data;
        this.displayRowCount = displayRowCount;
        this.titleName = title;
        init();
    }

    public AbstractDynamicDataTable(String title, List<T> data, int displayRowCount){
        this(title, data, displayRowCount, (DynamicColumn) null);
    }


    public void init(){
        MAIN_PANE.getStyleClass().add("data-table-container");
        MAIN_PANE.setCenter(borderContent);
        this.borderContent.setCenter(listContent);

        // Definir colunas padrão
        initDefaultColumns();

        this.refresh();

        configHeader();
    }

    public BorderPane getTable() {
        return MAIN_PANE;
    }

    protected void setColumnsLabel(DynamicColumn... columnsLabel) {
        columns.clear();
        columns.addAll(Arrays.asList(columnsLabel));
        configColumns();
    }

    void configHeader() {
        final AnchorPane headerPanel = new AnchorPane();
        final TextField searchTextField = new TextField();
        searchTextField.setPromptText("Pesquise aqui por...");
        HBox boxSearch = new HBox();
        boxSearch.setAlignment(Pos.CENTER_LEFT);
        boxSearch.getChildren().addAll(searchTextField, IconsPath.iconFrom(IconsPath._24x.SEARCH));
        boxSearch.getStyleClass().add("search-box");

        headerPanel.getStyleClass().add("data-table-header");

        AnchorPane.setLeftAnchor(boxSearch, 10.0);
        AnchorPane.setTopAnchor(boxSearch, 10.0);

        headerPanel.getChildren().add(boxSearch);
        searchTextField.setOnKeyReleased(key-> filter(((TextField)key.getSource()).getText()));

        MAIN_PANE.setTop(headerPanel);


    }

    protected void configColumns(){
        final HBox row = new HBox();
        row.getStyleClass().add("data-table-column");

        for(DynamicColumn column: columns) {
            JFXButton columnBtn = new JFXButton(column.getName().toUpperCase());
            columnBtn.setTooltip(new Tooltip(column.getName()));
            columnBtn.setOnAction( e -> sortByColumn((JFXButton)e.getSource()));
            row.getChildren().add(columnBtn);
        }

        JFXButton columnBtn = new JFXButton("ACÇÕES");
        columnBtn.setDisable(true);
        row.getChildren().add(columnBtn);
        borderContent.setTop(row);
        simpleTable();
    }

    // abstract methods
    protected abstract void sortByColumn(JFXButton columnBtn);
    private void filter(String text) {
        this.filteredData = this.data.stream()
                .filter(t -> filterCondition(t, text))
                .collect(Collectors.toList());
        this.refresh();
    }
    protected abstract boolean filterCondition(T t, String search);
    public abstract void editItem(T t);
    public abstract void deleteItem(T t);

    protected void notFound(){
        listContent.getChildren().clear();
        Label labelNotFound = new Label("UPS! NÃO ENCONTRAMOS NENHUM DADO :(");
        labelNotFound.getStyleClass().add("data-table-row-label-not-found");
        listContent.getChildren().add(labelNotFound);
    }

    protected void paginate(){
        int lastDisplayIndex;
        if (pageIndex * displayRowCount > filteredData.size()) {
            displayIndex += (pageIndex * displayRowCount) -  filteredData.size();
        }else {
            displayIndex = pageIndex * displayRowCount;
        }
        lastDisplayIndex =  Math.min((displayIndex + Math.min(displayRowCount, filteredData.size())), filteredData.size());
        displayedData = filteredData.subList(displayIndex, lastDisplayIndex);
    }

    void initPages() {

//        final HBox  = new HBox();
        paginationBox.getChildren().clear();
        if (!filteredData.isEmpty()) {
            numberOfPages = (int) Math.ceil(filteredData.size() / ((double) displayRowCount));
            SVGPath iconPrev = new SVGPath();
            iconPrev.setContent(IconsPath._24x.PREV);
            prevButton.setGraphic(iconPrev);

            SVGPath iconNext = new SVGPath();
            iconNext.setContent(IconsPath._24x.NEXT);
            nextButton.setGraphic(iconNext);

            prevButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            nextButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            paginationBox.setAlignment(Pos.CENTER_RIGHT);
            paginationBox.getChildren().add(prevButton);

            prevButton.setOnAction(e-> {
                loadData((pageIndex - 1) < 0 ? numberOfPages-1:(pageIndex - 1));
                markSelection(paginationBox);
            });
            nextButton.setOnAction(e-> {
                loadData((pageIndex+1) % numberOfPages);
                markSelection(paginationBox);
            });
            for (int i = 0; i < numberOfPages; i++) {
                final JFXButton bPage = new JFXButton(String.valueOf(i+1));
                final int index = i;
                bPage.setOnAction(e-> {
                    loadData(index);
                    markSelection(paginationBox);
                } );

                paginationBox.getChildren().add(bPage);
            }
            markSelection(paginationBox);
            paginationBox.getStyleClass().add("data-table-footer");
            paginationBox.getChildren().add(nextButton);
            MAIN_PANE.setBottom(paginationBox);
        }
    }

    void markSelection(HBox paginationBox){
        paginationBox.getChildren().forEach(n -> {
            JFXButton btnPage = (JFXButton) n;
            btnPage.getStyleClass().remove("data-table-footer-button-selected");
            if (btnPage.getText().equalsIgnoreCase(String.valueOf(pageIndex + 1)))
                btnPage.getStyleClass().add("data-table-footer-button-selected");

        });
    }


    // Serve para mapear os dados de acordo as colunas
    // Usar de forma assíncrona
    void mapData(){

        columnData.clear();
        for (T t : displayedData) {
            try{
                Map<String, Object> item = new HashMap<>();
                for(Field field : t.getClass().getDeclaredFields()){
                    field.setAccessible(true);
                    item.put(field.getName(), field.get(t) == null? "-----":field.get(t).toString());
                }
                columnData.add(item);

            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    public void simpleTable() {
        listContent.getChildren().clear();
        int index = 0;
        for(Map<String, Object> item : columnData){
            final HBox row = new HBox();
            row.setAlignment(Pos.CENTER_LEFT);
            row.getStyleClass().add("data-table-row");

            for(DynamicColumn column: columns){
                final String val = String.valueOf(getItem(item.get(column.getKey())));
                Label cell = new Label(val);
                if(val.equals(Direction.UP.toString()))
                    cell.setStyle("-fx-text-fill:#0e44ff");
                else if (val.equals(Direction.DOWN.toString()))
                    cell.setStyle("-fx-text-fill:danger");

                cell.setTooltip(new Tooltip(val));
                cell.setAlignment(Pos.CENTER);
                row.getChildren().add(cell);
            }

            final HBox rowAction = new HBox();
            rowAction.setAlignment(Pos.CENTER);


            JFXButton editButton = new JFXButton();
            JFXButton deleteButton = new JFXButton();

            editButton.setGraphic(IconsPath.iconFrom(IconsPath._18x.EDIT));
            deleteButton.setGraphic(IconsPath.iconFrom(IconsPath._18x.DELETE));

            editButton.getStyleClass().add("data-table-row-btn");
            editButton.getStyleClass().add("data-table-row-edit-btn");

            deleteButton.getStyleClass().add("data-table-row-btn");
            deleteButton.getStyleClass().add("data-table-row-delete-btn");

            // Action
            final T element = displayedData.get(index++);
            editButton.setOnAction(e -> editItem(element));
            deleteButton.setOnAction(e -> deleteItem(element));

            rowAction.getChildren().addAll(editButton, deleteButton);
            row.getChildren().add(rowAction);
            listContent.getChildren().add(row);
        }
        if (listContent.getChildren().isEmpty()){
            notFound();
        }
    }

    protected String getItem(Object item) {

        // para formatar data
        LocalDateTime date = StringUtil.convertLocalDateTime(String.valueOf(item));
        if (date != null)
            return LocalDateTimeUtil.formatToHuman(date);

        // para formatar decimal
        Double dobuleValue = StringUtil.convertToDouble(String.valueOf(item));
        if (dobuleValue != null)
            return StringUtil.getNumberFormatted(dobuleValue);

        return item == null || item.equals("null")? "-----": item.toString();
    }

    // Inicialização padrão
    public void initDefaultColumns() {
        if (columns.isEmpty()) {
            for (String key : allColumns()) {
                columns.add(new DynamicColumn(key));
            }
        }
        configColumns();
    }

    // Retorna todas as colunas padrão
    public Set<String> allColumns(){
        return columnData.get(0).keySet();
    }


    /**
     * SERVICOS
     */
    void loadData(int page){
        this.pageIndex = page;
        refresh();
    }

    public void refresh() {
        spinner.setRadius(100.0);
        MAIN_PANE.setCenter(spinner);

        new Thread(()-> Platform.runLater(()-> {
            if (!filteredData.isEmpty()) {
                // Tratar os dados
                paginate();
                //Mapear os dados
                mapData();

                simpleTable();

                initPages();
                // if sucess, add listView
                MAIN_PANE.setCenter(borderContent);

            }else{
                notFound();
            }
        })).start();
    }
}
