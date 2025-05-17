package services;

import entities.Pet;

import java.util.List;

public interface PetService {

    void cadastrarPet(Pet pet);
    List<Pet> listarPet();
    Pet buscarPets(Pet pet);
    void excluirPetById(Integer id);
}
