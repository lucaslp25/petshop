package services.impl;

import dao.DaoFactory;
import dao.ProdutoDao;
import entities.Produto;
import exceptions.ExceptionEntitieNotFound;
import services.ProdutoService;

import java.util.List;

public class ProdutoServiceImpl implements ProdutoService {

    private ProdutoDao produtoDao = DaoFactory.createProdutoDao();


    @Override
    public void cadastrarProduto(Produto produto) throws ExceptionEntitieNotFound {

        if (produto == null) {
            throw new ExceptionEntitieNotFound("Produto cadastrado não pode ser nulo!");
        }
        if (produto.getPrecoDeCusto() < 0.00 || produto.getPrecoDeVenda() < 0.00) {
            throw new ExceptionEntitieNotFound("Produto não pode ter preço negativo!");
        }
        produtoDao.insertProduto(produto);
    }

    @Override
    public void alterarProduto(Produto produto) throws ExceptionEntitieNotFound {

        if (produto == null) {
            throw new ExceptionEntitieNotFound("Não pode atualizar por produto nulo!");
        }
        produtoDao.updateProduto(produto);
    }

    @Override
    public void excluirProduto(Integer id) throws ExceptionEntitieNotFound {

        Produto produto = produtoDao.findById(id);

        if (produto == null) {
            throw new ExceptionEntitieNotFound("Nenhum produto com esse ID na lista!");
        }

        if (id == null) {
            throw new ExceptionEntitieNotFound("Id inválido: nulo");
        }
        produtoDao.deleteProdutoById(id);
    }

    @Override
    public List<Produto> listarProduto() throws ExceptionEntitieNotFound {

        if(produtoDao.findAll() == null || produtoDao.findAll().isEmpty()) {
            throw new ExceptionEntitieNotFound("Nenhum produto na lista ainda!");
        }
        return produtoDao.findAll();
    }

    @Override
    public Produto buscarPorId(Integer id) throws ExceptionEntitieNotFound {

        if (id == null) {
            throw new ExceptionEntitieNotFound("ID " + id + " nulo!");
        }
        return produtoDao.findById(id);
    }
}
