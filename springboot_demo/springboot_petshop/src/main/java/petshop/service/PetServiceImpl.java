package petshop.service;

import org.springframework.transaction.annotation.Transactional;
import petshop.domain.Pet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/7/6
 * Time: 12:36
 */
@Transactional
public class PetServiceImpl implements PetService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("petshop");

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Pet pet) {
        entityManager.merge(pet);
    }

    public List<Pet> getAllPets() {
        Query query = entityManager.createQuery("SELECT pet FROM Pet pet");
        return query.getResultList();
    }

    public Pet searchPet(Long id) {
        Query query = entityManager.createQuery("SELECT pet FROM Pet pet WHERE pet.id = " + id);
        return (Pet) query.getSingleResult();
    }

    public void removePet(Pet pet) {
        Pet petAble = entityManager.find(Pet.class, pet.getId());
        entityManager.remove(petAble);
    }
}
