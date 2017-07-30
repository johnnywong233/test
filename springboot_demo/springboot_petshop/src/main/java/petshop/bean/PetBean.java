package petshop.bean;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import petshop.domain.Pet;
import petshop.service.PetService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/7/6
 * Time: 11:19
 */
@ManagedBean(name = "petBean")
@SessionScoped
public class PetBean {
    private Pet pet;
    private Pet selectedPet;
    private PetService petService;

    public PetBean() {
        WebApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext((ServletContext) FacesContext
                        .getCurrentInstance().getExternalContext().getContext());
        petService = context.getBean(PetService.class);
    }

    private String cadastrarPet() {
        pet = new Pet();
        return "/pages/forms/cadastrar_pet.xhtml";
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public List<Pet> getPets() {
        return petService.getAllPets();
    }

    public void salvar() {
        petService.save(pet);
        cadastrarPet();
    }

    public void editar() {

    }

    public void excluir() {
        petService.removePet(pet);
        cadastrarPet();
    }

    public Pet getSelectedPet() {
        return selectedPet;
    }

    public void setSelectedPet(Pet selectedPet) {
        this.selectedPet = selectedPet;
    }
}
