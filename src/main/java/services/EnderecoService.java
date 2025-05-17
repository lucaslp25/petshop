package services;

import entities.Endereco;

import java.util.List;

public interface EnderecoService {

    void cadastrarEndereco(Endereco endereco);
    List<Endereco> listarEnderecos();
    Endereco buscarPorAtributos(Endereco endereco);
}
