package application;

import dao.ClienteDao;
import dao.DaoFactory;
import dao.EnderecoDao;
import dao.PetDao;
import entities.Cliente;
import entities.Endereco;
import entities.Funcionario;
import entities.Pet;
import enums.CategoriaDePorte;
import enums.TiposDeCargoFuncionario;
import enums.TiposDeEspecies;

public class Main{
    public static void main(String[] args) {

        //primeira coisa para criar um cliente precisa de um endereço! Para ter a referencia no DB

        DaoFactory daoFactory = new DaoFactory();
        PetDao petDao = daoFactory.createPetDao();
        ClienteDao clienteDao = daoFactory.createClienteDao();
        EnderecoDao enderecoDao = daoFactory.createEnderecoDao();

        Endereco endereco = new Endereco("TESTE", "123", "TESTE", "PORTO", "RS", "789546123", "Casa");

        //exemplos de teste
        Cliente cliente = new Cliente("Lucas", "987654321", "lp.skt@gmail.com", "51 35795154", endereco);

        Funcionario funcionario = new Funcionario("João", TiposDeCargoFuncionario.GERENTE);

       // enderecoDao.insertEndereco(endereco2);

       // clienteDao.insertCliente(cliente);

        Pet pet = new Pet("Lord", TiposDeEspecies.GATO, "SIAMÊS", CategoriaDePorte.PEQUENO,
                clienteDao.findByCPF(cliente.getCpf()));

        petDao.insertPet(pet);



    }
}