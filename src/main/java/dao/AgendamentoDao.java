package dao;

import entities.Agendamento;

import java.util.List;

public interface AgendamentoDao {

    void insert(Agendamento agendamento);
    void update(Agendamento agendamento);
    void deleteById(Integer id);
    Agendamento findById(Integer id);
    List<Agendamento> findAll();

}
