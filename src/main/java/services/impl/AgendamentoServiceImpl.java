package services.impl;

import dao.AgendamentoDao;
import dao.DaoFactory;
import entities.Agendamento;
import exceptions.ExceptionEntitieNotFound;
import services.AgendamentoService;

import java.util.List;

public class AgendamentoServiceImpl implements AgendamentoService {

    private AgendamentoDao agendamentoDao = DaoFactory.createAgendamentoDao();

    @Override
    public void cadastrarAgendamento(Agendamento agendamento)throws ExceptionEntitieNotFound {

        if (agendamento.getFuncionarioResponsavel() == null) {
            throw new ExceptionEntitieNotFound("Funcionario Responsavel Nulo!");
        }
        if (agendamento.getPet() == null) {
            throw new ExceptionEntitieNotFound("Pet Nulo!");
        }
        if (agendamento.getServicos().getId() == null) {
            throw new ExceptionEntitieNotFound("Servico Id Nulo!");
        }
        agendamentoDao.insert(agendamento);
    }

    @Override
    public void alterarAgendamento(Agendamento agendamento)throws ExceptionEntitieNotFound  {

        if (agendamento.getId() == null) {
            throw new ExceptionEntitieNotFound("ID NULO!");
        }
        if (agendamento.getFuncionarioResponsavel() == null) {
            throw new ExceptionEntitieNotFound("Funcionario Responsavel Nulo!");
        }
        if (agendamento.getPet() == null) {
            throw new ExceptionEntitieNotFound("Pet Nulo!");
        }
        agendamentoDao.update(agendamento);
    }

    @Override
    public void excluirAgendamentoById(Integer id) throws ExceptionEntitieNotFound {

        if (id == null) {
            throw new ExceptionEntitieNotFound("ID NULO!");
        }
        Agendamento agendamento = agendamentoDao.findById(id);
        if (agendamento == null) {
            throw new ExceptionEntitieNotFound("Nenhum agendamento com esse ID cencontrado!");
        }
        agendamentoDao.deleteById(id);
    }

    @Override
    public List<Agendamento> listarAgendamentos() throws ExceptionEntitieNotFound {
        List<Agendamento> agendamentos = agendamentoDao.findAll();
        if (agendamentos == null) {
            throw new ExceptionEntitieNotFound("Nenhum agendamento encontrado na lista ainda!");
        }
        return agendamentoDao.findAll();
    }

    @Override
    public Agendamento buscarAgendamentoById(Integer id) throws ExceptionEntitieNotFound {

        if (id == null) {
            throw new ExceptionEntitieNotFound("ID NULO!");
        }
        Agendamento agendamento = agendamentoDao.findById(id);
        if (agendamento == null) {
            throw new ExceptionEntitieNotFound("Nenhum agendamento com esse ID cencontrado!");
        }
       return agendamentoDao.findById(id);
    }
}
