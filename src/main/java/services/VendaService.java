package services;

import entities.Venda;

import java.util.List;

public interface VendaService {

    void addVenda(Venda venda);
    void updateVenda(Venda venda);
    void deleteVendaById(Integer id);
    List<Venda> listarVendas();
    Venda buscarVendaById(Integer id);
}
