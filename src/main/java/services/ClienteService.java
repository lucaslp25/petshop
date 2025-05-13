package services;

import entities.Cliente;

import java.util.List;

public interface ClienteService {

     void cadastrarCliente(Cliente cliente);
     List<Cliente> listarClientes();
     Cliente buscarClientePorCpf(String cpf);


}
