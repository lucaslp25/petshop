package services.impl;

import dao.DaoFactory;
import dao.PetDao;
import entities.Pet;
import exceptions.ExceptionEntitieNotFound;
import services.PetService;

import java.util.List;

public class PetServiceImpl implements PetService {

    private PetDao petDao = DaoFactory.createPetDao();

    @Override
    public void cadastrarPet(Pet pet) {

        if (pet == null) {
            throw new ExceptionEntitieNotFound("Pet nulo!");
        }

        if (pet.getDono() == null) {
            throw new ExceptionEntitieNotFound("Precisa cadastrar o dono do Pet primeiro! ");
        }

        petDao.insertPet(pet);
    }

    @Override
    public List<Pet> listarPet() {

        if (petDao.findAll().isEmpty()) {
            throw new ExceptionEntitieNotFound("Nenhum pet na lista ainda! ");
        }
        return petDao.findAll();
    }

    @Override
    public Pet buscarPets(Pet pet) {

        if (pet == null) {
            throw new ExceptionEntitieNotFound("Não podemos buscar pet com valor nulo!");
        }
        pet = petDao.findByUniqueAtributs(pet.getNome(), pet.getRaca(), pet.getIdade());

        if (pet == null) {
            throw new ExceptionEntitieNotFound("Nenhum pet com esses parametros encontrado!");
        }

        return pet;
    }


}
