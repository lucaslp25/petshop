package dao;

import dao.impl.*;

public class DaoFactory {

    public static ClienteDao createClienteDao() {
        return new ClienteDaoJDBC(DB.getConnection());
    }
    public static EnderecoDao createEnderecoDao() {
        return new EnderecoDaoJDBC(DB.getConnection());
    }
    public static PetDao createPetDao() {
        return new PetDaoJDBC(DB.getConnection());
    }
    public static FuncionarioDao createFuncionarioDao() {
        return new FuncionarioDaoJDBC(DB.getConnection());
    }
    public static ProdutoDao createProdutoDao() {
        return new ProdutoDaoJDBC(DB.getConnection());
    }
    public static ServicoDao createServicoDao() {
        return new ServicoDaoJDBC(DB.getConnection());
    }
    public static ServicoBanhoDao createServicoBanhoDao() {
        return new ServicoBanhoDaoJDBC(DB.getConnection(), createServicoDao());
        // aqui já é diferente a dependencia, já é mais forte, o ServicoBandoDao depende alem do conector, mas tambem do servico (superclasse) e dos seus respectivos atributos para ser inicializado.. isso força um programa mais, robusto.. um pouco mais acoplado nessa relaçãa, mas fica mais forte e menos propenso a erros!
    }
    public static ServicoTosaDao createServicoTosaDao() {
        return new ServicoTosaDaoJDBC(DB.getConnection(), createServicoDao());
    }
    public static ServicoVacinacaoDao createServicoVacinacaoDao() {
        return new ServicoVacinacaoDaoJDBC(DB.getConnection(), createServicoDao());
    }
    public static ServicoConsultaVeterinariaDao createServicoConsultaVeterinariaDao() {
        return new ServicoConsultaVeterinariaDaoJDBC(DB.getConnection(), createServicoDao());
    }
    public static MetodoDePagamentoDao createMetodoDePagamentoDao() {
        return new MetodoDePagamentoDaoJDBC(DB.getConnection());
    }
    public static VendaDao createVendaDao() {
        return new VendaDaoJDBC(DB.getConnection());
    }
    public static ItemVendaDao createItemVendaDao() {
        return new ItemVendaDaoJDBC(DB.getConnection());
    }
    public static AgendamentoDao createAgendamentoDao() {
        return new AgendamentoDaoJDBC(DB.getConnection());
    }

}
