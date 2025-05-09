package dao.impl;

import dao.DB;
import dao.DbExceptions;
import dao.ServicoDao;
import dao.ServicoTosaDao;
import entities.ServicoBanho;
import entities.ServicoTosa;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ServicoTosaDaoJDBC implements ServicoTosaDao {

    private Connection conn;
    private ServicoDao servicoDao;

    public ServicoTosaDaoJDBC(Connection conn, ServicoDao servicoDao) {
        this.conn = conn;
        this.servicoDao = servicoDao;
    }


    @Override
    public void insert(ServicoTosa servico) {

        servicoDao.insert(servico);

        String sql = "INSERT INTO servico_tosa "
                + "(servico_id, inclui_escovacao, inclui_banho_previo) "
                + "VALUES (?, ?, ?)";

        try(PreparedStatement st = conn.prepareStatement(sql)){
            //Return GENERETED KEYS não necessaria nesse caso

            st.setInt(1, servico.getId());
            st.setBoolean(2, servico.isIncluiEscovacao());
            st.setBoolean(3, servico.isIncluiBanhoPrevio());

            int rows = st.executeUpdate();
            if(rows > 0){
                System.out.println("Serviço de tosa inserido com sucesso! ID: " + servico.getId());
                //Pois o id será o mesmo do serviços!!
            }else{
                throw new DbExceptions("Erro inesperado ao inserir serviço de tosa!");
            }
        }catch(SQLException e){
            throw new DbExceptions("Erro ao inserir serviço de tosa: " + e.getMessage());
        }
    }

    @Override
    public void update(ServicoTosa servico) {

        servicoDao.update(servico);

        String sql = "UPDATE servico_tosa "
                +"SET inclui_escovacao = ?, inclui_banho_previo = ? "
                + "WHERE servico_id = ?";
        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setBoolean(1, servico.isIncluiEscovacao());
            st.setBoolean(2, servico.isIncluiBanhoPrevio());
            st.setInt(3, servico.getId());

            st.executeUpdate();
            System.out.println("Serviço de tosa atualizado com sucesso!");
        }catch(SQLException e){
            throw new DbExceptions("Erro ao atualizar serviço de tosa! " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

       // servicoDao.deleteById(id); problema de integridade ao fazer essa chamada

        String sql = "DELETE FROM servico_tosa WHERE servico_id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){
            st.setInt(1, id);

            int rows = st.executeUpdate();

            if(rows > 0){
                System.out.println("Serviço de tosa deletado com sucesso!");
            }else{
                throw new DbExceptions("Nnehum serviço com o ID " + id);
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao deletar seriço de tosa: " + e.getMessage());
        }
    }

    @Override
    public ServicoTosa findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT  s.id AS servico_id, " +
                    "s.nome AS servico_nome, " +
                    "s.descricao AS servico_descricao, " +
                    "s.preco AS servico_preco, " +
                    "s.duracao AS servico_duracao, " +
                    "st.inclui_escovacao, " +
                    "st.inclui_banho_previo "+
                    "FROM servico s " +
                    "INNER JOIN servico_tosa st ON s.id = st.servico_id " +
                    "WHERE s.id = ? ";

            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()){
                ServicoTosa servicoTosa = instantiateServicoTosa(rs);
                return servicoTosa;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar Serviço: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<ServicoTosa> findAll() {

        Statement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT  s.id AS servico_id, " +
                    "s.nome AS servico_nome, " +
                    "s.descricao AS servico_descricao, " +
                    "s.preco AS servico_preco, " +
                    "s.duracao AS servico_duracao, " +
                    "st.inclui_escovacao, " +
                    "st.inclui_banho_previo "+
                    "FROM servico s " +
                    "INNER JOIN servico_tosa st ON s.id = st.servico_id ";

            st = conn.createStatement();
            rs = st.executeQuery(sql);

            List<ServicoTosa> servicosDeTosa = new ArrayList<>();

            while(rs.next()){
                ServicoTosa servicoTosa = instantiateServicoTosa(rs);
                servicosDeTosa.add(servicoTosa);
            }
            return servicosDeTosa;
        }catch(SQLException e){
            throw new DbExceptions("Erro ao buscar serviços de tosa! " + e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private ServicoTosa instantiateServicoTosa(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("servico_id");
        String nome = rs.getString("servico_nome");
        String descricao = rs.getString("servico_descricao");
        Double preco = rs.getDouble("servico_preco");
        Duration duracao = Duration.ofMinutes(rs.getLong("servico_duracao"));
        Boolean incluiEscovacao = rs.getBoolean("inclui_escovacao");
        Boolean incluiBanhoPrevio = rs.getBoolean("inclui_banho_previo");

        ServicoTosa servicoTosa = new ServicoTosa(nome, descricao, preco, duracao, incluiEscovacao, incluiBanhoPrevio);
        servicoTosa.setId(id);
        return servicoTosa;
    }
}
