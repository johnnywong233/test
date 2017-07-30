package petshop.model;

import petshop.domain.Client;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/7/6
 * Time: 12:33
 */
public class ClientDataModel extends PrimeDataModel<Client> {
    public ClientDataModel() {
    }

    public ClientDataModel(Object data) {
        super(data);
    }

    @Override
    public Client getRowData(String rowKey) {
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data

        List<Client> clientes = (List<Client>) getWrappedData();

        for (Client cliente : clientes) {
            if (cliente.getModel().equals(rowKey))
                return cliente;
        }

        return null;
    }

    @Override
    public String getRowKey(Client client) {
        return client.getModel();
    }
}
