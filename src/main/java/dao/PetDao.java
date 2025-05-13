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
    Pet findByUniqueAtributs(String nome, String raca, Integer idade);
    //aqui irei usar apenas os atributos basicos para achar o pet, e colocar uma restrição no banco de dados pra não ter colunas repetidas com esses mesmos dados, caso aconteça, de coincidentemente ter, apenas coloque o nome com um um caracterer diferente.... Assim fica mais facil a implementação do sistema!

    List<Pet> findByCpfDono(String cpf);

}
