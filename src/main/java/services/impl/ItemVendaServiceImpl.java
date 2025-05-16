package services.impl;

import dao.DaoFactory;
import dao.ItemVendaDao;
import entities.ItemVenda;
import exceptions.ExceptionEntitieNotFound;
import services.ItemVendaService;

import java.util.List;

public class ItemVendaServiceImpl implements ItemVendaService {

    private ItemVendaDao itemVendaDao = DaoFactory.createItemVendaDao();

    @Override
    public void addItemVenda(ItemVenda itemVenda)throws ExceptionEntitieNotFound {

        if (itemVenda == null) {
            throw new ExceptionEntitieNotFound("ItemVenda nulo");
        }
        itemVendaDao.insert(itemVenda);
    }


    @Override
    public void addItemVenda2(ItemVenda itemVenda)throws ExceptionEntitieNotFound {

        if (itemVenda == null) {
            throw new ExceptionEntitieNotFound("ItemVenda nulo");
        }
        itemVendaDao.insert2(itemVenda);
    }


    @Override
    public void updateItemVenda(ItemVenda itemVenda) throws ExceptionEntitieNotFound{

        if (itemVenda == null) {
            throw new ExceptionEntitieNotFound("ItemVenda nulo");
        }
        itemVendaDao.update(itemVenda);
    }

    @Override
    public void deleteItemVendaById(Integer id)throws ExceptionEntitieNotFound {

        if (id == null) {
            throw new ExceptionEntitieNotFound("ID Nulo");
        }

        ItemVenda itemVenda = itemVendaDao.findById(id);

        if (itemVenda == null) {
            throw new ExceptionEntitieNotFound("ID inválido!");
        }
        itemVendaDao.deleteById(id);
    }

    @Override
    public List<ItemVenda> listarItemVendas() throws ExceptionEntitieNotFound{

        List<ItemVenda> itemVendas = itemVendaDao.findAll();
        if (itemVendas == null) {
            throw new ExceptionEntitieNotFound("nenhum itemVenda na lista ainda!");
        }
        return itemVendaDao.findAll();
    }

    @Override
    public ItemVenda buscarItemVendaById(Integer id)throws ExceptionEntitieNotFound {

        if (id == null) {
            throw new ExceptionEntitieNotFound("ID Nulo");
        }

        ItemVenda itemVenda = itemVendaDao.findById(id);

        if (itemVenda == null) {
            throw new ExceptionEntitieNotFound("ID inválido!");
        }
        return itemVendaDao.findById(id);
    }
}
