package dao;

import entities.ServicoConsultaVeterinaria;

import java.util.List;

public interface ServicoConsultaVeterinariaDao {

    void insert(ServicoConsultaVeterinaria servico);
    void update(ServicoConsultaVeterinaria servico);
    void deleteById(Integer id);
    ServicoConsultaVeterinaria findById(Integer id);
    List<ServicoConsultaVeterinaria> findAll();

}
