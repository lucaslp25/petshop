package services;

import entities.MetodoDePagamento;

import java.util.List;

public interface MetodoPagamentoService {

     void adicionarMetodo(MetodoDePagamento metodoDePagamento);
     MetodoDePagamento buscarPorId(Integer id);
     List<MetodoDePagamento> listarTodos();
     void atualizarMetodo(MetodoDePagamento metodoDePagamento);
     void removerPorId(Integer id);
}
