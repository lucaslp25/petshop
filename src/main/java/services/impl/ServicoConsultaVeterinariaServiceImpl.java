package services.impl;

import dao.DaoFactory;
import dao.ServicoConsultaVeterinariaDao;
import entities.ServicoConsultaVeterinaria;
import exceptions.ExceptionEntitieNotFound;
import services.ServicoConsultaVeterinariaService;

import java.util.List;

public class ServicoConsultaVeterinariaServiceImpl implements ServicoConsultaVeterinariaService {
    private ServicoConsultaVeterinariaDao servicoConsultaVeterinariaDao = DaoFactory.createServicoConsultaVeterinariaDao();


    @Override
    public void cadastrarServicoConsultaVeterinaria(ServicoConsultaVeterinaria consultaVeterinaria)throws ExceptionEntitieNotFound {

        if (consultaVeterinaria == null) {
            throw new ExceptionEntitieNotFound("Serviço nulo!");
        }
        servicoConsultaVeterinariaDao.insert(consultaVeterinaria);
    }

    @Override
    public void alterarServicoConsultaVeterinaria(ServicoConsultaVeterinaria consultaVeterinaria) throws ExceptionEntitieNotFound{
        if (consultaVeterinaria == null) {
            throw new ExceptionEntitieNotFound("Serviço nulo!");
        }
        servicoConsultaVeterinariaDao.update(consultaVeterinaria);
    }

    @Override
    public void excluirServicoConsultaVeterinaria(Integer id) throws ExceptionEntitieNotFound{
        if (id == null) {
            throw new ExceptionEntitieNotFound("ID nulo!");
        }
        ServicoConsultaVeterinaria servicoConsultaVeterinaria = servicoConsultaVeterinariaDao.findById(id);
        if (servicoConsultaVeterinaria == null) {
            throw new ExceptionEntitieNotFound("Nenhum serviço encontrado com o ID: " + id);
        }
        servicoConsultaVeterinariaDao.deleteById(id);
    }

    @Override
    public List<ServicoConsultaVeterinaria> listarServicoConsultaVeterinaria() throws ExceptionEntitieNotFound{
        List<ServicoConsultaVeterinaria> servicoConsultaVeterinarias = servicoConsultaVeterinariaDao.findAll();
        if (servicoConsultaVeterinarias == null) {
            throw new ExceptionEntitieNotFound("Nenhum serviço na de consulta veterinaria Agendado ainda! ");
        }
        return servicoConsultaVeterinariaDao.findAll();
    }

    @Override
    public ServicoConsultaVeterinaria buscarPorId(Integer id) throws ExceptionEntitieNotFound{
        if (id == null) {
            throw new ExceptionEntitieNotFound("ID nulo!");
        }
        ServicoConsultaVeterinaria servicoConsultaVeterinaria = servicoConsultaVeterinariaDao.findById(id);
        if (servicoConsultaVeterinaria == null) {
            throw new ExceptionEntitieNotFound("Nenhum serviço encontrado com o ID: " + id);
        }
        return servicoConsultaVeterinariaDao.findById(id);
    }
}
