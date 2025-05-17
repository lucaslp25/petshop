package services;

import entities.Agendamento;

import java.util.List;

public interface AgendamentoService {

    void cadastrarAgendamento(Agendamento agendamento);
    void alterarAgendamento(Agendamento agendamento);
    void excluirAgendamentoById(Integer id);
    List<Agendamento> listarAgendamentos();
    Agendamento buscarAgendamentoById(Integer id);
}
