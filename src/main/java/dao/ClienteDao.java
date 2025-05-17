package dao;

import entities.Cliente;

import java.util.List;

public interface ClienteDao {

     void insertCliente(Cliente cliente);
     void updateCliente(Cliente cliente);
     void deleteById(Integer id);
     Cliente findById(Integer id);
     List<Cliente> findClienteByEnderecoId(Integer id);
     List<Cliente> findClienteByCidade(String cidade);
     List<Cliente> findAll();
     Cliente findByCPF(String cpf);
}
