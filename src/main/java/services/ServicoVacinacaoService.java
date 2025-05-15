package services;

import entities.ServicoVacinacao;

import java.util.List;

public interface ServicoVacinacaoService {

    void cadastrarServicoVacinacao(ServicoVacinacao servicoVacinacao);
    void alterarServicoVacinacao(ServicoVacinacao servicoVacinacao);
    void excluirServicoVacinacao(Integer id);
    List<ServicoVacinacao> listarServicoVacinacao();
    ServicoVacinacao buscarPorId(Integer id);

}
