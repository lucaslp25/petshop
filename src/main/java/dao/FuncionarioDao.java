package dao;

import entities.Funcionario;

import java.util.List;

public interface FuncionarioDao {

    void insertFuncionario(Funcionario funcionario);
    void updateFuncionario(Funcionario funcionario);
    void deleteById(Integer id);
    Funcionario findById(Integer id);
    List<Funcionario> findAll();
    Funcionario findByNomeECargo(String nome, String Cargo);
}
