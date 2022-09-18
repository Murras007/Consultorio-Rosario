package views.components.dynamic.datatable;

import java.util.List;

public interface IDynamicDataTable<T> {

    void createColumns();

    List<T> getAllPages();
    List<T> getActualPage();
    List<T> getPage(int index);
    List<T> getPages(int start, int end);
}
