package services.impl;

import dao.DaoFactory;
import dao.ServicoVacinacaoDao;
import entities.ServicoVacinacao;
import exceptions.ExceptionEntitieNotFound;
import services.ServicoVacinacaoService;

import java.util.List;

public class ServicoVacinacaoServiceImpl implements ServicoVacinacaoService {

    private ServicoVacinacaoDao servicoVacinacaoDao = DaoFactory.createServicoVacinacaoDao();


    @Override
    public void cadastrarServicoVacinacao(ServicoVacinacao servicoVacinacao) throws ExceptionEntitieNotFound {
        if (servicoVacinacao == null) {
            throw new ExceptionEntitieNotFound("Serviço nulo!");
        }
        servicoVacinacaoDao.insert(servicoVacinacao);
    }

    @Override
    public void alterarServicoVacinacao(ServicoVacinacao servicoVacinacao)throws ExceptionEntitieNotFound {
        if (servicoVacinacao == null) {
            throw new ExceptionEntitieNotFound("Serviço nulo!");
        }
        servicoVacinacaoDao.update(servicoVacinacao);
    }

    @Override
    public void excluirServicoVacinacao(Integer id) throws ExceptionEntitieNotFound {

        if (id == null) {
            throw new ExceptionEntitieNotFound("ID nulo!");
        }
        ServicoVacinacao servicoVacinacao = servicoVacinacaoDao.findById(id);
        if (servicoVacinacao == null) {
            throw new ExceptionEntitieNotFound("Nenhum serviço com o ID " + id + " foi encontrado!");
        }
        servicoVacinacaoDao.deleteById(id);
    }

    @Override
    public List<ServicoVacinacao> listarServicoVacinacao()throws ExceptionEntitieNotFound {
        List<ServicoVacinacao> servicoVacinacao = servicoVacinacaoDao.findAll();
        if (servicoVacinacao == null){
            throw new ExceptionEntitieNotFound("Nenhum serviço na lista ainda!");
        }
        return servicoVacinacaoDao.findAll();
    }

    @Override
    public ServicoVacinacao buscarPorId(Integer id) throws ExceptionEntitieNotFound {

        if (id == null) {
            throw new ExceptionEntitieNotFound("ID nulo!");
        }
        ServicoVacinacao servicoVacinacao = servicoVacinacaoDao.findById(id);
        if (servicoVacinacao == null) {
            throw new ExceptionEntitieNotFound("Nenhum serviço com o ID " + id + " foi encontrado!");
        }
        return servicoVacinacaoDao.findById(id);
    }
}
