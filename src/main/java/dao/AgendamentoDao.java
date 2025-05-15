package dao;

import entities.Agendamento;
import entities.Funcionario;
import entities.Pet;
import entities.Servicos;

import java.time.LocalDate;
import java.util.List;

public interface AgendamentoDao {

    void insert(Agendamento agendamento);
    void update(Agendamento agendamento);
    void deleteById(Integer id);
    Agendamento findById(Integer id);
    List<Agendamento> findAll();
    Agendamento findByUniqueAtributs(LocalDate dataAgendamento, Double valor, Servicos servicos, Funcionario funcionarioResponsavel);
    List<Agendamento> findBypet(Pet pet);

}
