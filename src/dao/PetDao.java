package dao;

import entities.Pet;

import java.util.List;

public interface PetDao {

    void insertPet(Pet pet);
    void updatePet(Pet pet);
    void deleteById(Integer id);
    Pet findById(Integer id);
    List<Pet> findByClienteId(Integer id);
    List<Pet> findAll();

}
