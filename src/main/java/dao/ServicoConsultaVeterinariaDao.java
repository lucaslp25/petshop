package dao;

import entities.ServicoConsultaVeterinaria;
import enums.TiposDeConsultaVeterinaria;

import java.util.List;

public interface ServicoConsultaVeterinariaDao {

    void insert(ServicoConsultaVeterinaria servico);
    void update(ServicoConsultaVeterinaria servico);
    void deleteById(Integer id);
    ServicoConsultaVeterinaria findById(Integer id);
    List<ServicoConsultaVeterinaria> findAll();

    ServicoConsultaVeterinaria findByUniqueAtributs(String nome, String descricao, Double preco, TiposDeConsultaVeterinaria tiposDeConsultaVeterinaria);



}
