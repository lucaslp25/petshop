package dao.impl;

import dao.DB;
import dao.DbExceptions;
import dao.FuncionarioDao;
import entities.Funcionario;
import enums.TiposDeCargoFuncionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDaoJDBC implements FuncionarioDao {

    private Connection conn;

    public FuncionarioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertFuncionario(Funcionario funcionario) {

        String sql ="INSERT INTO funcionario " +
                "(nome, cargo) " +
                "VALUES " +
                "(?,?)";
        try(PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){

            st.setString(1, funcionario.getNome());
            st.setString(2, funcionario.getCargoFuncionario().name());

            int rows = st.executeUpdate();

            if (rows > 0){
                ResultSet rs = st.getGeneratedKeys();
               if(rs.next()){
                   int id = rs.getInt(1);
                   funcionario.setId(id);
                   System.out.println("Funcionario inserido com sucesso! ID: " + id);
               }
                DB.closeResultSet(rs);
            }else {
                throw new DbExceptions("Erro ao inserir funcionario!");
            }
        }catch(SQLException e){
            throw new DbExceptions(e.getMessage());
        }
    }


    @Override
    public void updateFuncionario(Funcionario funcionario) {

        String sql = "UPDATE funcionario " +
                "SET nome = ?, cargo = ? " +
                "WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setString(1, funcionario.getNome());
            st.setString(2, funcionario.getCargoFuncionario().name());
            st.setInt(3, funcionario.getId());

            st.executeUpdate();
            System.out.println("Funcionario atualizado com sucesso!");

        }catch (SQLException e){
            throw new DbExceptions("Erro ao atualizar o funcionario! " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM funcionario WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setInt(1, id);

            int rows = st.executeUpdate();
            if (rows > 0){
                System.out.println("Funcionario deletado com sucesso!");
            }else{
                throw new DbExceptions("Nenhum funcionario com esse ID!");
            }
        }catch (SQLException e){
            throw new DbExceptions(e.getMessage());
        }
    }

    @Override
    public Funcionario findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try{

            String sql = "SELECT * " +
                    "FROM funcionario " +
                    "WHERE id = ? ";

            st = conn.prepareStatement(sql);

            st.setInt(1,id);

            rs = st.executeQuery();

            if (rs.next()){
                Funcionario funcionario = instantiateFuncionario(rs);

                //poderia colocar um else, fazendo uma exceção, ou simplesmente voltando nulo, ou, fazer o funcionario retornar depois do if, porem não tera garantia se ele for nulo ou não então posso, fazer outra condição do tipo, se funcinario for null, eu lanço a exceção personalizada!

                return funcionario;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Funcionario instantiateFuncionario(ResultSet rs) throws SQLException {

        Funcionario funcionario = new Funcionario();

        funcionario.setId(rs.getInt("id"));
        funcionario.setNome(rs.getString("nome"));
        funcionario.setCargoFuncionario(TiposDeCargoFuncionario.valueOf(rs.getString("cargo")));
        return funcionario;
    }

    @Override
    public List<Funcionario> findAll() {

        PreparedStatement st = null;
        //poderia ter usado um statement normal aqui! pois não há parametros de pesquisa como um placeholder (?)
        ResultSet rs = null;

        try{
            String sql = "SELECT * " +
                    "FROM funcionario " +
                    "ORDER BY cargo ";

            st = conn.prepareStatement(sql);{

                rs = st.executeQuery();

                List<Funcionario> funcionarios = new ArrayList<>();

                while(rs.next()){

                    Funcionario funcionario = instantiateFuncionario(rs);
                    funcionarios.add(funcionario);
                }
                return funcionarios;

            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar funcionarios: "  + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Funcionario findByNomeECargo(String nome, String Cargo) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT * " +
                    "FROM funcionario " +
                    "WHERE nome = ? AND cargo = ? ";

            st = conn.prepareStatement(sql);{

                st.setString(1, nome);
                st.setString(2, Cargo);

                rs = st.executeQuery();

                if (rs.next()) {
                    Funcionario funcionario = instantiateFuncionario(rs);
                    return funcionario;
                }
                return null;
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao achar funcionario: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
