package dao;

import entities.Endereco;

import java.util.List;

public interface EnderecoDao {

    void insertEndereco(Endereco endereco);
    void updateEndereco(Endereco endereco);
    void deleteById(Integer id);
    Endereco findById(Integer id);
    List<Endereco> findAll();
}
