package dao.impl;

import dao.DB;
import dao.DbExceptions;
import dao.FuncionarioDao;
import entities.Endereco;
import entities.Funcionario;
import enums.TiposDeCargoFuncionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuncionarioDaoJDBC implements FuncionarioDao {

    private Connection conn;

    public FuncionarioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertFuncionario(Funcionario funcionario) {

        String sql ="INSERT INTO funcionario " +
                "(nome, cargo, endereco_id, salario) " +
                "VALUES " +
                "(?,?,?,?)";

        try(PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){

            st.setString(1, funcionario.getNome());
            st.setString(2, funcionario.getCargoFuncionario().name());
            st.setInt(3, funcionario.getEndereco().getId());
            st.setDouble(4, funcionario.getSalario());

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
                "SET nome = ?, cargo = ?, endereco_id = ?, salario = ?" +
                "WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setString(1, funcionario.getNome());
            st.setString(2, funcionario.getCargoFuncionario().name());
            st.setInt(3, funcionario.getEndereco().getId());
            st.setDouble(4, funcionario.getSalario());
            st.setInt(5, funcionario.getId());

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
            String sql = "SELECT " +
                    "funcionario.id AS funcionario_id, " +
                    "funcionario.nome, " +
                    "funcionario.cargo, " +
                    "funcionario.endereco_id, " +
                    "funcionario.salario, " +
                    "endereco.id AS endereco_id, " +
                    "endereco.rua, " +
                    "endereco.numero," +
                    "endereco.bairro," +
                    "endereco.cidade, " +
                    "endereco.estado, " +
                    "endereco.cep, " +
                    "endereco.complemento " +
                    "FROM funcionario " +
                    "INNER JOIN endereco " +
                    "ON funcionario.endereco_id = endereco.id " +
                    "WHERE funcionario.id = ? ";

            st = conn.prepareStatement(sql);

            st.setInt(1,id);

            rs = st.executeQuery();

            if (rs.next()){

                Endereco endereco = instantiateEndereco(rs);
                Funcionario funcionario = instantiateFuncionario(rs, endereco);

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

    @Override
    public List<Funcionario> findAll() {

        PreparedStatement st = null;
        //poderia ter usado um statement normal aqui! pois não há parametros de pesquisa como um placeholder (?)
        ResultSet rs = null;

        try{
            String sql = "SELECT " +
                    "funcionario.id AS funcionario_id, " +
                    "funcionario.nome, " +
                    "funcionario.cargo, " +
                    "funcionario.endereco_id, " +
                    "funcionario.salario, " +
                    "endereco.id AS endereco_id, " +
                    "endereco.rua, " +
                    "endereco.numero," +
                    "endereco.bairro," +
                    "endereco.cidade, " +
                    "endereco.estado, " +
                    "endereco.cep, " +
                    "endereco.complemento " +
                    "FROM funcionario " +
                    "INNER JOIN endereco " +
                    "ON funcionario.endereco_id = endereco.id ";

            st = conn.prepareStatement(sql);

                rs = st.executeQuery();

                List<Funcionario> funcionarios = new ArrayList<>();
                Map<Integer, Endereco> enderecoMap = new HashMap<>();

                while(rs.next()){
                    Integer enderecoId = rs.getInt("endereco_id");
                    Endereco endereco = enderecoMap.get(enderecoId);
                    if(endereco == null){
                        endereco = instantiateEndereco(rs);
                        enderecoMap.put(enderecoId, endereco);
                    }
                    Funcionario funcionario = instantiateFuncionario(rs, endereco);
                    funcionarios.add(funcionario);
                }
                return funcionarios;

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
            String sql = "SELECT " +
                    "funcionario.id AS funcionario_id, " +
                    "funcionario.nome, " +
                    "funcionario.cargo, " +
                    "funcionario.endereco_id, " +
                    "funcionario.salario, " +
                    "endereco.id AS endereco_id, " +
                    "endereco.rua, " +
                    "endereco.numero," +
                    "endereco.bairro," +
                    "endereco.cidade, " +
                    "endereco.estado, " +
                    "endereco.cep, " +
                    "endereco.complemento " +
                    "FROM funcionario " +
                    "INNER JOIN endereco " +
                    "ON funcionario.endereco_id = endereco.id " +
                    "WHERE nome = ? AND cargo = ? ";

            st = conn.prepareStatement(sql);

                st.setString(1, nome);
                st.setString(2, Cargo);

                rs = st.executeQuery();

                if (rs.next()) {
                    Endereco endereco = instantiateEndereco(rs);
                    Funcionario funcionario = instantiateFuncionario(rs, endereco);
                    return funcionario;
                }
                return null;

        }catch (SQLException e){
            throw new DbExceptions("Erro ao achar funcionario: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    private Endereco instantiateEndereco(ResultSet rs) throws SQLException{
        Endereco endereco = new Endereco();
        endereco.setId(rs.getInt("endereco_id"));
        endereco.setRua(rs.getString("rua"));
        endereco.setNumero(rs.getString("numero"));
        endereco.setBairro(rs.getString("bairro"));
        endereco.setCidade(rs.getString("cidade"));
        endereco.setEstado(rs.getString("estado"));
        endereco.setCep(rs.getString("cep"));
        endereco.setComplemento(rs.getString("complemento"));
        return endereco;
    }

    private Funcionario instantiateFuncionario(ResultSet rs, Endereco endereco) throws SQLException {
        Funcionario funcionario = new Funcionario();

        funcionario.setId(rs.getInt("funcionario_id"));
        funcionario.setNome(rs.getString("nome"));
        funcionario.setCargoFuncionario(TiposDeCargoFuncionario.valueOf(rs.getString("cargo")));
        funcionario.setEndereco(endereco);
        funcionario.setSalario(rs.getDouble("salario"));
        return funcionario;
    }
}
