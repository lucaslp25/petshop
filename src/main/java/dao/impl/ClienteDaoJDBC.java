package dao.impl;

import dao.ClienteDao;
import dao.DB;
import dao.DbExceptions;
import entities.Cliente;
import entities.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteDaoJDBC implements ClienteDao {

    private Connection conn;

    //FORÇANDO A INJEÇÃO DE DEPENDENCIA AQUI DENTRO PELO CONSTRUTOR
    public ClienteDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertCliente(Cliente cliente) {

        String str = "INSERT INTO cliente (nome, cpf, email, telefone, endereco_id) VALUES (?, ?, ?, ?,?)";
        try(PreparedStatement st = conn.prepareStatement(str ,Statement.RETURN_GENERATED_KEYS)){
            st.setString(1, cliente.getNome());
            st.setString(2, cliente.getCpf());
            st.setString(3, cliente.getEmail());
            st.setString(4, cliente.getTelefone());
            st.setInt(5, cliente.getEndereco().getId());

            int rows = st.executeUpdate();
            if(rows > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()) {
                    int id = rs.getInt(1);  //colocando a id retornada numa variavel
                    cliente.setId(id);  //populando o meu objeto com o seu respectivo id que foi retornado
                    System.out.println("Cliente inserido com sucesso! ID: " + id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbExceptions("Erro inesperado. Nenhum cliente encontrado!");
            }

        }catch(SQLException e) {
            throw new DbExceptions("Erro ao inserir cliente: " + e.getMessage());
        }
        //como é um try resources, ele ja fecha os recursos que eu abri dentro dele automaticamente!
    }

    @Override
    public void updateCliente(Cliente cliente) {

        String sql ="UPDATE cliente " +
                "SET nome = ?, cpf = ?, email = ?, telefone = ?, endereco_id = ? " +
                "WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setString(1, cliente.getNome());
            st.setString(2, cliente.getCpf());
            st.setString(3, cliente.getEmail());
            st.setString(4, cliente.getTelefone());
            st.setInt(5, cliente.getEndereco().getId());
            st.setInt(6, cliente.getId());

            st.executeUpdate();

        }catch(SQLException e) {
            throw new DbExceptions("Erro ao atualizar cliente: " + e.getMessage());
        }
    }
    //deletar cliente por id!
    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM cliente " +
                "WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)){

            st.setInt(1,id);
            int rows = st.executeUpdate();
            if(rows > 0){
                System.out.println("Cliente removido com sucesso! ID: " + id + " | Linhas afetadas: " + rows);
            }else{
                throw new DbExceptions("Nenhum cliente com esse ID encontrado!");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao remover cliente: " + e.getMessage());
        }
    }

    @Override
    public Cliente findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            String str = "SELECT cliente.*, endereco.* "
                    + "FROM cliente INNER JOIN endereco "
                    + "ON cliente.endereco_id = endereco.id "
                    + "WHERE cliente.id = ?";

            st = conn.prepareStatement(str);
            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){
                Endereco endereco = instantiateEndereco(rs);    //CRIAR UM METODO PARA AS INSTANCIACOES!
                Cliente cliente = instantiateCliente(rs, endereco);
                return cliente;
            }
            return null;
            //se o id que veio por parametro não achar ninguem no banco de dados, vai voltar nulo, caso contrario vai retornar o cliente que foi instanciado acima !
        }catch(SQLException e){
            throw new DbExceptions("Erro ao inserir cliente: " + e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Cliente> findClienteByEnderecoId(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT cliente.*, endereco.* " +
                    "FROM cliente INNER JOIN endereco " +
                    "ON cliente.endereco_id = endereco.id " +
                    "where endereco_id = ? " +
                    "ORDER BY cliente.nome";

            st = conn.prepareStatement(sql);

            st.setInt(1, id);

            rs = st.executeQuery();

            List<Cliente> lista = new ArrayList<>();
            Map<Integer, Endereco> map = new HashMap<>();

            while(rs.next()){
                Endereco endereco = map.get(rs.getInt("endereco_id"));
                if(endereco == null){
                    endereco = instantiateEndereco(rs);
                    map.put(rs.getInt("endereco_id"), endereco);
                }
                Cliente cliente = instantiateCliente(rs, endereco);
                lista.add(cliente);
            }
            return lista;

        }catch (SQLException e){
            throw new DbExceptions("Erro ao encontrar cliente: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Cliente> findClienteByCidade(String cidade) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT cliente.*, endereco.* " +
                    "FROM cliente INNER JOIN endereco " +
                    "ON cliente.endereco_id = endereco.id " +
                    "where endereco.cidade = ? " +
                    "ORDER BY cliente.nome";

            st = conn.prepareStatement(sql);

            st.setString(1,cidade);

            rs = st.executeQuery();

            List<Cliente> lista = new ArrayList<>();
            Map<Integer, Endereco> map = new HashMap<>();

            while(rs.next()){       //o next vira falso automatico quando acaba, então o loop acaba!
                Endereco endereco = map.get(rs.getInt("endereco_id"));
                if(endereco == null){
                    endereco = instantiateEndereco(rs);
                    map.put(rs.getInt("endereco_id"), endereco);
                }
                Cliente cliente = instantiateCliente(rs, endereco);
                lista.add(cliente);
            }
            return lista;

        }catch (SQLException e){
            throw new DbExceptions("Erro ao encontrar cliente: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Cliente> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            String str = "SELECT cliente.*, endereco.* "
                    + "FROM cliente INNER JOIN endereco "
                    + "ON cliente.endereco_id = endereco.id "
                    + "ORDER BY cliente.nome";

            st = conn.prepareStatement(str);
            rs = st.executeQuery();
            List<Cliente> clientes = new ArrayList<>();
            Map<Integer, Endereco> map = new HashMap<>();
            while(rs.next()){

                Endereco endereco = map.get(rs.getInt("endereco_id"));

                if(endereco == null){
                    endereco = instantiateEndereco(rs);
                    map.put(rs.getInt("endereco_id"), endereco);
                }
                Cliente cliente = instantiateCliente(rs, endereco);
                clientes.add(cliente);
            }
            return clientes;
        }catch(SQLException e){
            throw new DbExceptions("Erro ao inserir cliente: " + e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Cliente findByCPF(String cpf) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT cliente.*, endereco.* " +
                    "FROM cliente " +
                    "INNER JOIN endereco ON cliente.endereco_id = endereco.id " +
                    "WHERE cliente.cpf = ? ";

            st = conn.prepareStatement(sql);
            st.setString(1, cpf);

            rs = st.executeQuery();

            if (rs.next()) {
                Endereco endereco = instantiateEndereco(rs);
                Cliente cliente = instantiateCliente(rs, endereco);
                return cliente;
            }
            return null;

        }catch (SQLException e){
             throw new DbExceptions("Erro ao Buscar cliente: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    //METODOS PARA AS INSTANCIACOES DE CLIENTE E ENDERECO PARA FACILITAR O USO NOS CODIGOS ACIMA, DEIXAR MAIS FLEXIVEL, E MAIS LIMPO!

    private Cliente instantiateCliente(ResultSet rs, Endereco endereco) throws SQLException{
        //Propagando as exceções pois já estou tratando nos outros metodos
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setCpf(rs.getString("cpf"));
        cliente.setEmail(rs.getString("email"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setEndereco(endereco);
        return cliente;
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
