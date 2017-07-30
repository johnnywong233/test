package petshop.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Author: Johnny
 * Date: 2017/7/6
 * Time: 11:19
 */
@SessionScoped
@ManagedBean(name = "clientBean")
public class ClientBean {
    public String cadastrarCliente() {
        return "/pages/forms/cadastrar_cliente.xhtml";
    }
}
