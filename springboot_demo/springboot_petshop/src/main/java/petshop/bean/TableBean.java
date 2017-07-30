package petshop.bean;

import petshop.domain.Client;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: Johnny
 * Date: 2017/7/6
 * Time: 11:26
 */
@SessionScoped
@ManagedBean(name = "tableBean")
public class TableBean implements Serializable {
    private final static String[] nomes;

    private final static String[] pets;

    static {
        nomes = new String[10];
        nomes[0] = "João";
        nomes[1] = "José";
        nomes[2] = "Pedro";
        nomes[3] = "Ricardo";
        nomes[4] = "Vilma";
        nomes[5] = "Marcelle";
        nomes[6] = "Jéssica";
        nomes[7] = "Rita";
        nomes[8] = "Lúcia";
        nomes[9] = "Sarah";

        pets = new String[10];
        pets[0] = "Toto";
        pets[1] = "Bidu";
        pets[2] = "Pretinha";
        pets[3] = "Pan";
        pets[4] = "Lobo";
        pets[5] = "Rex";
        pets[6] = "Lessie";
        pets[7] = "Cast";
        pets[8] = "Peludo";
        pets[9] = "Tonton";
    }

    private List<Client> clientes;

    private Client selectedClient;

    private Client[] selectedClients;

    public TableBean() {
        clientes = new ArrayList<Client>();

        populateRandomClients(clientes, 50);
    }

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }

    private void populateRandomClients(List<Client> list, int size) {
        for (int i = 0; i < size; i++)
            list.add(new Client(getRandomModel(), getNome(), getNomePet()));
    }

    private String getNome() {
        return nomes[(int) (Math.random() * 10)];
    }

    private String getNomePet() {
        return pets[(int) (Math.random() * 10)];
    }

    public List<Client> getClients() {
        return clientes;
    }

    private String getRandomModel() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
