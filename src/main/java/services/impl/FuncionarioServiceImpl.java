package services.impl;

import dao.DaoFactory;
import dao.FuncionarioDao;
import entities.Funcionario;
import enums.TiposDeCargoFuncionario;
import exceptions.ExceptionEntitieNotFound;
import services.FuncionarioService;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioServiceImpl implements FuncionarioService {

    private FuncionarioDao funcionarioDao = DaoFactory.createFuncionarioDao();

    @Override
    public void cadastrarFuncionario(Funcionario funcionario) {

        if(funcionario == null){
            throw new IllegalArgumentException("Erro! Dados do funcionario são nulos");
        }
        if(funcionario.getEndereco() == null){
            throw new IllegalArgumentException("Erro! O Funcionario precisa de um endereço!");
        }

        funcionarioDao.insertFuncionario(funcionario);
    }

    @Override
    public List<Funcionario> listarFuncionarios() {

        List<Funcionario> funcionarios = funcionarioDao.findAll();
        if (funcionarios.isEmpty()) {
            throw new ExceptionEntitieNotFound("Nenhum funcionario na lista ainda!");
        }

        return funcionarios;
    }

    @Override
    public Funcionario buscarFuncionarioPorNomeECargo(String nome, TiposDeCargoFuncionario cargo) {

        if (nome == null || cargo == null) {
            throw new IllegalArgumentException("Erro! Dados do funcionario nulos");
        }
        Funcionario funcionario = funcionarioDao.findByNomeECargo(nome, cargo.name());

        return funcionario;
    }

    @Override
    public void excluirFuncionarioById(Integer id)throws ExceptionEntitieNotFound {
        if (id == null){
            throw new IllegalArgumentException("Erro! Id nulo!");
        }
        Funcionario funcionario = funcionarioDao.findById(id);
        if (funcionario == null){
            throw new ExceptionEntitieNotFound("Nenhum funcionario com o ID " + id + " encontrado!");
        }
        funcionarioDao.deleteById(id);
    }
}
