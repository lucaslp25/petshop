package services;

import entities.ServicoBanho;

import java.util.List;

public interface ServicoBanhoService {

    void cadastrarServicoBanho(ServicoBanho servicoBanho);
    void alterarServicoBanho(ServicoBanho servicoBanho);
    void excluirServicoBanho(Integer id);
    List<ServicoBanho>listarServicoBanho();
    ServicoBanho buscarPorId(Integer id);
}
