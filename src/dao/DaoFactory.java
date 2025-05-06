package dao;

import dao.impl.ClienteDaoJDBC;
import dao.impl.EnderecoDaoJDBC;
import dao.impl.PetDaoJDBC;

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
}
