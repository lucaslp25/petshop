package services.impl;

import dao.DaoFactory;
import dao.VendaDao;
import entities.Venda;
import exceptions.ExceptionEntitieNotFound;
import services.VendaService;

import java.util.List;

public class VendaServiceImpl implements VendaService {

    private VendaDao vendaoDao = DaoFactory.createVendaDao();


    @Override
    public void addVenda(Venda venda) throws ExceptionEntitieNotFound {

        if (venda == null){
            throw new ExceptionEntitieNotFound("Venda nula!");
        }
        vendaoDao.insert(venda);
    }

    @Override
    public void updateVenda(Venda venda) throws ExceptionEntitieNotFound{
        if (venda == null){
            throw new ExceptionEntitieNotFound("Venda nula!");
        }
        vendaoDao.update(venda);
    }

    @Override
    public void deleteVendaById(Integer id) throws ExceptionEntitieNotFound {
        if (id == null){
            throw new ExceptionEntitieNotFound("Id nulo!");
        }
        Venda venda = vendaoDao.findById(id);
        if (venda == null){
            throw new ExceptionEntitieNotFound("Nenhuma venda com esse ID!");
        }
        vendaoDao.deleteById(id);
    }

    @Override
    public List<Venda> listarVendas() throws ExceptionEntitieNotFound{
        List<Venda> vendas = vendaoDao.findAll();
        if (vendas == null){
            throw new ExceptionEntitieNotFound("Nenhuma venda na lista ainda!");
        }
        return vendaoDao.findAll();
    }

    @Override
    public Venda buscarVendaById(Integer id) throws ExceptionEntitieNotFound {
        if (id == null){
            throw new ExceptionEntitieNotFound("Id nulo!");
        }
        Venda venda = vendaoDao.findById(id);
        if (venda == null){
            throw new ExceptionEntitieNotFound("Nenhuma venda com esse ID!");
        }
        return vendaoDao.findById(id);
    }
}
