package services;

import entities.ServicoConsultaVeterinaria;

import java.util.List;

public interface ServicoConsultaVeterinariaService {


    void cadastrarServicoConsultaVeterinaria(ServicoConsultaVeterinaria consultaVeterinaria);
    void alterarServicoConsultaVeterinaria(ServicoConsultaVeterinaria consultaVeterinaria);
    void excluirServicoConsultaVeterinaria(Integer id);
    List<ServicoConsultaVeterinaria> listarServicoConsultaVeterinaria();
    ServicoConsultaVeterinaria buscarPorId(Integer id);
}
