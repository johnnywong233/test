package petshop.model;

import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;

/**
 * Author: Johnny
 * Date: 2017/7/6
 * Time: 12:34
 * An implementation of SelectableDataModel using a list as data
 */
public class PrimeDataModel<T> extends ListDataModel implements SelectableDataModel<T>, Serializable {

    public PrimeDataModel() {
    }

    public PrimeDataModel(Object data) {
        setWrappedData(data);
    }

    public String getRowKey(T object) {
        throw new UnsupportedOperationException("Must be implemented");
    }

    public T getRowData(String rowKey) {
        throw new UnsupportedOperationException("Must be implemented");
    }
}
