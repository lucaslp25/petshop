package services.impl;

import dao.ClienteDao;
import dao.DaoFactory;
import entities.Cliente;
import exceptions.ExceptionCpfAlreadyExists;
import exceptions.ExceptionEntitieNotFound;
import services.ClienteService;

import java.util.List;

public class ClienteServiceImpl implements ClienteService {

    private ClienteDao clienteDao = DaoFactory.createClienteDao();

    @Override
    public void cadastrarCliente(Cliente cliente) {

        if (cliente == null){
            throw new IllegalArgumentException("O cliente não pode ser nulo!");
        }

        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()){
            throw new IllegalArgumentException("Obrigatório o cadastro do CPF!");
        }

        if (clienteDao.findByCPF(cliente.getCpf()) != null){
            throw new ExceptionCpfAlreadyExists("Erro! CPF: " + cliente.getCpf()+" Já cadastrado no sistema!");
        }

        clienteDao.insertCliente(cliente);

    }

    @Override
    public List<Cliente> listarClientes() {

        if (clienteDao.findAll() == null){
            throw new IllegalArgumentException("A lista de clientes está vazia!");
        }

        return clienteDao.findAll();

    }

    @Override
    public Cliente buscarClientePorCpf(String cpf) throws ExceptionEntitieNotFound {


        cpf = cpf.trim();

        if (clienteDao.findByCPF(cpf) == null) {
            throw new ExceptionEntitieNotFound("Nenhum cliente com esse CPF na lista!");
        }

        return clienteDao.findByCPF(cpf);
    }

}
