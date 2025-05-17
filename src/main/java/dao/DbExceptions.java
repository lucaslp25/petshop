package dao;

public class DbExceptions extends RuntimeException
{
    //tratar exceções expecificas do banco de dados
    private static final long serialVersionUID = 1L;
    public DbExceptions(String message) {
        super(message);
    }
}
