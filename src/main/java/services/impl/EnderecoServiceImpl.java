package services.impl;

import dao.DaoFactory;
import dao.EnderecoDao;
import entities.Endereco;
import services.EnderecoService;

import java.util.List;

public class EnderecoServiceImpl implements EnderecoService {

    private EnderecoDao enderecoDao = DaoFactory.createEnderecoDao();


    @Override
    public void cadastrarEndereco(Endereco endereco) {

        if (endereco == null) {
            throw new IllegalArgumentException("Erro ao cadastrar: endereço nulo!");
        }

        enderecoDao.insertEndereco(endereco);
    }

    @Override
    public List<Endereco> listarEnderecos() {
        if (enderecoDao.findAll().isEmpty()) {
            throw new IllegalArgumentException("Nenhum endereço cadastrado ainda!");
        }

        return enderecoDao.findAll();
    }

    @Override
    public Endereco buscarPorAtributos(Endereco endereco) {

        if (endereco == null) {
            throw new IllegalArgumentException("Endereço nulo!");
        }

        endereco = enderecoDao.findByUniqueAtributs(endereco.getRua(), endereco.getNumero(), endereco.getBairro(), endereco.getCidade(), endereco.getEstado(), endereco.getCep(), endereco.getComplemento());

        return endereco;

    }
}
