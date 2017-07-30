package petshop.service;

import petshop.domain.Pet;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/7/6
 * Time: 12:36
 */
public interface PetService {
    void save(Pet pet);

    Pet searchPet(Long id);

    void removePet(Pet pet);

    List<Pet> getAllPets();
}
