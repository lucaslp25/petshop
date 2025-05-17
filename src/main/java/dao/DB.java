package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    //objeto de conexão com o banco de dados
    private static Connection conn = null;

    public static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties props = new Properties();    //instanciando as propriedades
            props.load(fs); //carregando o que foi lido das propriedades
            return props;
        }catch (IOException e){     //Pega a exceção normal de leitura de arquivos
            throw new DbExceptions(e.getMessage()); //lanço minha exceção personalizada por ser RUNTIMES
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            try{
                Properties props = loadProperties();
                //aqui irei pegar as propriedades do banco de dados contido no arquivo properties!
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);

                //passando para o drive a url do banco de dados(que contem todo o caminho ate ela), e as propriedades do arquivo, como usuario e senha!
            }catch (SQLException e){
                throw new DbExceptions(e.getMessage()); //mais uma vez lançando minha exceção personalizada!
            }
        }
        return conn; // conexão salva na variavel conn!
    }

    // metodos abaixo para fechar os recursos com mais facilidade!

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            }catch (SQLException e){
                throw new DbExceptions(e.getMessage());
            }
        }
    }
    public static void closeStatement(Statement st) {
        if (st != null) {
            try{
                st.close();
            }catch (SQLException e){
                throw new DbExceptions(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try{
                rs.close();
            }catch (SQLException e){
                throw new DbExceptions(e.getMessage());
            }
        }
    }

}


