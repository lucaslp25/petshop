package services;

import entities.Funcionario;
import enums.TiposDeCargoFuncionario;

import java.util.List;

public interface FuncionarioService {

    void cadastrarFuncionario(Funcionario funcionario);
    List<Funcionario> listarFuncionarios();
    Funcionario buscarFuncionarioPorNomeECargo(String nome, TiposDeCargoFuncionario cargo);
    void excluirFuncionarioById(Integer id);
}
