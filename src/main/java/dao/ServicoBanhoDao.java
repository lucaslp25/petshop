package dao;

import entities.ServicoBanho;

import java.util.List;

public interface ServicoBanhoDao {

    void insert(ServicoBanho servico);
    void update(ServicoBanho servico);
    void deleteById(Integer id);
    ServicoBanho findById(Integer id);
    List<ServicoBanho> findAll();
    ServicoBanho findByUniqueAtributs(String nome, String descricao, Double preco, boolean comHidratacao);

}
