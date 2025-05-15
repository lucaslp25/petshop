package services;

import java.util.List;
import entities.ServicoTosa;
public interface ServicoTosaService {

    void cadastrarServicoTosa(ServicoTosa servicoTosa);
    void alterarServicoTosa(ServicoTosa servicoTosa);
    void excluirServicoTosa(Integer id);
    List<ServicoTosa> listarServicoTosa();
    ServicoTosa buscarPorId(Integer id);

}
