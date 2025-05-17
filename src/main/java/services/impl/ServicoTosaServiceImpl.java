package services.impl;

import dao.DaoFactory;
import dao.ServicoTosaDao;
import entities.ServicoTosa;
import exceptions.ExceptionEntitieNotFound;
import services.ServicoTosaService;

import java.util.List;

public class ServicoTosaServiceImpl implements ServicoTosaService {

    private static ServicoTosaDao servicoTosaDao = DaoFactory.createServicoTosaDao();


    @Override
    public void cadastrarServicoTosa(ServicoTosa servicoTosa) throws ExceptionEntitieNotFound {

        if (servicoTosa == null) {
            throw new ExceptionEntitieNotFound("ServicoTosa nulo!");
        }
        servicoTosaDao.insert(servicoTosa);
    }

    @Override
    public void alterarServicoTosa(ServicoTosa servicoTosa) throws ExceptionEntitieNotFound {

        if (servicoTosa == null) {
            throw new ExceptionEntitieNotFound("ServicoTosa nulo!");
        }
        servicoTosaDao.update(servicoTosa);
    }

    @Override
    public void excluirServicoTosa(Integer id) throws ExceptionEntitieNotFound {

        if (id == null) {
            throw new ExceptionEntitieNotFound("ID nulo!");
        }
        ServicoTosa servicoTosa = servicoTosaDao.findById(id);
        if (servicoTosa == null) {
            throw new ExceptionEntitieNotFound("Nenhum servico encontrado com o ID: " + id);
        }
        servicoTosaDao.deleteById(id);
    }

    @Override
    public List<ServicoTosa> listarServicoTosa() throws ExceptionEntitieNotFound {
        List<ServicoTosa> listaServicoTosa = servicoTosaDao.findAll();
        if (listaServicoTosa == null){
            throw new ExceptionEntitieNotFound("Nenhum servicos encontrado na lista!");
        }
        return listaServicoTosa;
    }

    @Override
    public ServicoTosa buscarPorId(Integer id) throws ExceptionEntitieNotFound {
        if (id == null) {
            throw new ExceptionEntitieNotFound("ID nulo!");
        }
        ServicoTosa servicoTosa = servicoTosaDao.findById(id);
        if (servicoTosa == null) {
            throw new ExceptionEntitieNotFound("Nenhum servico encontrado com o ID: " + id);
        }
        return servicoTosaDao.findById(id);
    }
}
