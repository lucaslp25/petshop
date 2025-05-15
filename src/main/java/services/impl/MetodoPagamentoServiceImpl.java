package services.impl;

import dao.DaoFactory;
import dao.MetodoDePagamentoDao;
import entities.MetodoDePagamento;
import exceptions.ExceptionEntitieNotFound;
import services.MetodoPagamentoService;

import java.util.List;

public class MetodoPagamentoServiceImpl implements MetodoPagamentoService {


    private MetodoDePagamentoDao metodoDePagamentoDao = DaoFactory.createMetodoDePagamentoDao();

    @Override
    public void adicionarMetodo(MetodoDePagamento metodoDePagamento) {
        if (metodoDePagamento == null){
            throw new ExceptionEntitieNotFound("Método de pagamento veio nulo!");
        }
        metodoDePagamentoDao.insert(metodoDePagamento);
    }

    @Override
    public MetodoDePagamento buscarPorId(Integer id) {

        if(metodoDePagamentoDao.findById(id) == null){
            throw new ExceptionEntitieNotFound("Nenhum método de pagamento com o ID "+id +" registrado!");
        }
        return metodoDePagamentoDao.findById(id);
    }

    @Override
    public List<MetodoDePagamento> listarTodos() {
        if (metodoDePagamentoDao.findAll() == null){
            throw new ExceptionEntitieNotFound("Nenhum método de pagamento na lista ainda!");
        }
        return metodoDePagamentoDao.findAll();
    }

    @Override
    public void atualizarMetodo(MetodoDePagamento metodoDePagamento) {

        if (metodoDePagamento == null){
            throw new ExceptionEntitieNotFound("Método inválido para ser atualizado!");
        }
        metodoDePagamentoDao.update(metodoDePagamento);
    }

    @Override
    public void removerPorId(Integer id) {
        if (id == null) {
            throw new ExceptionEntitieNotFound("ID inválido 'nulo' para ser deletado!");
        }
        metodoDePagamentoDao.deleteById(id);
    }
}
