package dao.impl;

import dao.*;
import entities.Cliente;
import entities.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDaoJDBC implements EnderecoDao {

    private Connection conn;

    public EnderecoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertEndereco(Endereco endereco) {

        String sql = "INSERT INTO endereco" +
                "(rua, numero, bairro, cidade, estado, cep, complemento)" +
                "VALUES" +
                "(?,?,?,?,?,?,?)";

        try(PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            st.setString(1, endereco.getRua());
            st.setString(2, endereco.getNumero());
            st.setString(3, endereco.getBairro());
            st.setString(4, endereco.getCidade());
            st.setString(5, endereco.getEstado());
            st.setString(6, endereco.getCep());
            st.setString(7, endereco.getComplemento());

            int rows = st.executeUpdate();
            if(rows > 0){
                ResultSet rs = st.getGeneratedKeys(); //atribuindo o id gerado ao result set
                if(rs.next()) {
                    int id = rs.getInt(1);
                    endereco.setId(id);
                    System.out.println("Endereço inserido com sucesso! ID: " + id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbExceptions("Erro inesperado ao inserir o endereço!");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao inserir endereço: " + e.getMessage());
        }
    }

    @Override
    public void updateEndereco(Endereco endereco) {

        String sql = "UPDATE endereco " +
                "SET rua = ?, numero = ?, bairro = ?, cidade = ?, estado = ?, cep = ?, complemento = ?" +
                "WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setString(1, endereco.getRua());
            st.setString(2, endereco.getNumero());
            st.setString(3, endereco.getBairro());
            st.setString(4, endereco.getCidade());
            st.setString(5, endereco.getEstado());
            st.setString(6, endereco.getCep());
            st.setString(7, endereco.getComplemento());
            st.setInt(8, endereco.getId());

            st.executeUpdate();
            System.out.println("Endereço atualizado com sucesso!");
        }catch (SQLException e){
            throw new DbExceptions("Erro ao atualizar o endereço: "+ e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

        ClienteDao clienteDao = DaoFactory.createClienteDao();

        List<Cliente> clientesAssociados = clienteDao.findClienteByEnderecoId(id);

        if (!clientesAssociados.isEmpty()) {
            throw new DbIntegrityException("Não é possível excluir o endereço com ID " + id + " Pois tem clientes associados nele!");
        }

        String sql = "DELETE FROM endereco WHERE id = ?";
        try(PreparedStatement st = conn.prepareStatement(sql)){
            st.setInt(1,id);

            int rows = st.executeUpdate();
            if (rows > 0){
                System.out.println("Endereço removido com sucesso!");
            }else{
                throw new DbExceptions("Nenhum endereço com esse id encontrado!");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao remover endereço: " + e.getMessage());
        }
    }

    @Override
    public Endereco findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String str = "SELECT id, rua, numero, bairro, cidade, estado, cep, complemento FROM endereco WHERE id = ?";

            st = conn.prepareStatement(str);

            st.setInt(1,id);
            rs = st.executeQuery();

            if(rs.next()){
                Endereco endereco = instantiateEndereco(rs);
                return endereco;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao achar endereço: " + e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Endereco> findAll() {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT endereco.* FROM endereco ORDER BY cidade";
            st = conn.prepareStatement(sql);

            rs = st.executeQuery();

            List<Endereco> enderecos = new ArrayList<>();
            while(rs.next()){
                Endereco endereco =  instantiateEndereco(rs);
                enderecos.add(endereco);
            }
            return enderecos;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar endereços: " + e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public Endereco findByUniqueAtributs(String rua, String numero, String bairro, String cidade, String estado, String cep, String complemento) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String str = "SELECT id, rua, numero, bairro, cidade, estado, cep, complemento FROM endereco WHERE rua = ? AND numero = ? AND bairro = ? AND cidade = ? AND estado = ? AND cep = ? AND complemento = ? ";

            st = conn.prepareStatement(str);

            st.setString(1,rua);
            st.setString(2,numero);
            st.setString(3,bairro);
            st.setString(4,cidade);
            st.setString(5,estado);
            st.setString(6,cep);
            st.setString(7,complemento);

            rs = st.executeQuery();

            if(rs.next()){
                Endereco endereco = instantiateEndereco(rs);
                return endereco;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao achar endereço: " + e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private Endereco instantiateEndereco(ResultSet rs) throws SQLException{
        Endereco endereco = new Endereco();
        endereco.setId(rs.getInt("id"));
        endereco.setRua(rs.getString("rua"));
        endereco.setNumero(rs.getString("numero"));
        endereco.setBairro(rs.getString("bairro"));
        endereco.setCidade(rs.getString("cidade"));
        endereco.setEstado(rs.getString("estado"));
        endereco.setCep(rs.getString("cep"));
        endereco.setComplemento(rs.getString("complemento"));
        return endereco;
    }
}
