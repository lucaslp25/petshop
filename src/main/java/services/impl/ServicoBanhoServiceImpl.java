package services.impl;

import dao.DaoFactory;
import dao.ServicoBanhoDao;
import entities.ServicoBanho;
import exceptions.ExceptionEntitieNotFound;
import services.ServicoBanhoService;

import java.util.List;

public class ServicoBanhoServiceImpl implements ServicoBanhoService {

    private ServicoBanhoDao servicoBanhoDao = DaoFactory.createServicoBanhoDao();

    @Override
    public void cadastrarServicoBanho(ServicoBanho servicoBanho)throws ExceptionEntitieNotFound {
        if (servicoBanho == null) {
            throw new ExceptionEntitieNotFound("ServicoBanho nulo!");
        }
        servicoBanhoDao.insert(servicoBanho);
    }

    @Override
    public void alterarServicoBanho(ServicoBanho servicoBanho) throws ExceptionEntitieNotFound {
        if (servicoBanho == null) {
            throw new ExceptionEntitieNotFound("ServicoBanho não pode ser nulo!");
        }
        servicoBanhoDao.update(servicoBanho);
    }

    @Override
    public void excluirServicoBanho(Integer id) throws ExceptionEntitieNotFound {

        if (id == null) {
            throw new ExceptionEntitieNotFound("ID nulo!");
        }

        ServicoBanho servicoBanho = servicoBanhoDao.findById(id);
        if (servicoBanho == null) {
            throw new ExceptionEntitieNotFound("Nenhum Serviço com o ID " + id + " foi encontrado!");
        }
        servicoBanhoDao.deleteById(id);
    }

    @Override
    public List<ServicoBanho> listarServicoBanho() throws ExceptionEntitieNotFound {

        List<ServicoBanho> servicoBanho = servicoBanhoDao.findAll();
        if (servicoBanho == null) {
            throw new ExceptionEntitieNotFound("Nenhum serviço na lista ainda!");
        }
        return servicoBanhoDao.findAll();
    }

    @Override
    public ServicoBanho buscarPorId(Integer id) throws ExceptionEntitieNotFound {

        if (id == null) {
            throw new ExceptionEntitieNotFound("ID nulo!");
        }
        ServicoBanho servicoBanho = servicoBanhoDao.findById(id);
        if (servicoBanho == null) {
            throw new ExceptionEntitieNotFound("Nenhum Serviço com esse ID encontrado!");
        }
        return servicoBanhoDao.findById(id);
    }
}
