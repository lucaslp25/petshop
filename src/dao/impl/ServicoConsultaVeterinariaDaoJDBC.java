package dao.impl;

import dao.DB;
import dao.DbExceptions;
import dao.ServicoConsultaVeterinariaDao;
import dao.ServicoDao;
import entities.ServicoConsultaVeterinaria;
import enums.TiposDeConsultaVeterinaria;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ServicoConsultaVeterinariaDaoJDBC implements ServicoConsultaVeterinariaDao {

    private Connection conn;
    private ServicoDao servicoDao;

    public ServicoConsultaVeterinariaDaoJDBC(Connection conn, ServicoDao servicoDao) {
        this.conn = conn;
        this.servicoDao = servicoDao;
    }

    @Override
    public void insert(ServicoConsultaVeterinaria servico) {

        servicoDao.insert(servico);

        String sql = "INSERT INTO servico_consulta_veterinaria "
                +" (servico_id, tipo_de_consulta) "
                + "VALUES (?, ?)";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setInt(1, servico.getId());
            st.setString(2, servico.getTipoDeConsulta().name());

            int rows = st.executeUpdate();
            if(rows > 0){
                System.out.println("Consulta veterinaria inserida com sucesso! ");
            }else{
                throw new DbExceptions("Erro inesperado ao inserir serviço!");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao inserir serviço: " + e.getMessage());
        }
    }

    @Override
    public void update(ServicoConsultaVeterinaria servico) {

        servicoDao.update(servico);

        String sql = "UPDATE servico_consulta_veterinaria "
                +"SET tipo_de_consulta = ? " +
                "WHERE servico_id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setString(1, servico.getTipoDeConsulta().name());
            st.setInt(2, servico.getId());

            st.executeUpdate();

            System.out.println("Atualização de serviço concluida com sucesso!");

        }catch (SQLException e){
            throw new DbExceptions("Erro ao atualizar serviço de consulta veterinária: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM servico_consulta_veterinaria WHERE servico_id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setInt(1, id);
            int rows = st.executeUpdate();

            if(rows > 0){
                System.out.println("Serviço de consulta veterinário removido com sucesso!");
            }else{
                throw new DbExceptions("Erro ao achar o ID: " + id);
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao deletar serviço de consulta veterinária: " + e.getMessage());
        }
    }

    @Override
    public ServicoConsultaVeterinaria findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT  s.id AS servico_id, " +
                    "s.nome AS servico_nome, " +
                    "s.descricao AS servico_descricao, " +
                    "s.preco AS servico_preco, " +
                    "s.duracao AS servico_duracao, " +
                    "sc.tipo_de_consulta "+
                    "FROM servico s " +
                    "INNER JOIN servico_consulta_veterinaria sc ON s.id = sc.servico_id " +
                    "WHERE s.id = ? ";

           st = conn.prepareStatement(sql);

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()){

                ServicoConsultaVeterinaria servicoConsultaVeterinaria = instantiateServicoConsultaVeterinaria(rs);
                return servicoConsultaVeterinaria;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar serviço de consulta veterinaria: " + e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<ServicoConsultaVeterinaria> findAll() {

        Statement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT  s.id AS servico_id, " +
                    "s.nome AS servico_nome, " +
                    "s.descricao AS servico_descricao, " +
                    "s.preco AS servico_preco, " +
                    "s.duracao AS servico_duracao, " +
                    "sc.tipo_de_consulta "+
                    "FROM servico s " +
                    "INNER JOIN servico_consulta_veterinaria sc ON s.id = sc.servico_id ";

            st = conn.createStatement();
            rs = st.executeQuery(sql);

            List<ServicoConsultaVeterinaria> servicosConsultasVeterinaria = new ArrayList<>();

            while (rs.next()){
                ServicoConsultaVeterinaria servicoConsultaVeterinaria = instantiateServicoConsultaVeterinaria(rs);
                servicosConsultasVeterinaria.add(servicoConsultaVeterinaria);
            }
            return servicosConsultasVeterinaria;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar serviços de consultas veterinaria: " + e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private ServicoConsultaVeterinaria instantiateServicoConsultaVeterinaria(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("servico_id");
        String nome = rs.getString("servico_nome");
        String descricao = rs.getString("servico_descricao");
        Double preco = rs.getDouble("servico_preco");
        Duration duracao = Duration.ofMinutes(rs.getLong("servico_duracao"));
        TiposDeConsultaVeterinaria tipoDeConsulta = TiposDeConsultaVeterinaria.valueOf(rs.getString("tipo_de_consulta"));

        ServicoConsultaVeterinaria servicoConsultaVeterinaria = new ServicoConsultaVeterinaria(nome, descricao, preco, duracao, tipoDeConsulta);

        servicoConsultaVeterinaria.setId(id);
        return servicoConsultaVeterinaria;
    }

}
