package dao.impl;

import dao.DB;
import dao.DbExceptions;
import dao.ServicoDao;
import dao.ServicoVacinacaoDao;
import entities.ServicoVacinacao;
import enums.TiposDeVacinacao;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ServicoVacinacaoDaoJDBC implements ServicoVacinacaoDao {

    private Connection conn;
    private ServicoDao servicoDao;

    public ServicoVacinacaoDaoJDBC(Connection conn, ServicoDao servicoDao) {
        this.conn = conn;
        this.servicoDao = servicoDao;
    }

    @Override
    public void insert(ServicoVacinacao servico) {

        servicoDao.insert(servico);

        String sql = "INSERT INTO servico_vacinacao "
                + "(servico_id, tipo_de_vacinacao) "
                + "VALUES (?, ?)";
        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setInt(1, servico.getId());
            st.setString(2, servico.getTipoDeVacinacao().name()); //pega o nome dos ENUMS

            int rows = st.executeUpdate();
            if(rows > 0){
                System.out.println("Serviço de vacinacao inserido com sucesso!");
            }else{
                throw new DbExceptions("Falha inesperada ao inserir serviço de vacinação no Banco de Dados.");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao inserir serviço no banco de dados: " + e.getMessage());
        }
    }

    @Override
    public void update(ServicoVacinacao servico) {

        servicoDao.update(servico);

        String sql = "UPDATE servico_vacinacao "
                +"SET tipo_de_vacinacao = ? "
                +"WHERE servico_id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)){

            st.setString(1, servico.getTipoDeVacinacao().name());
            st.setInt(2, servico.getId());

            st.executeUpdate();
            System.out.println("Atualização feita com sucesso! ");
        }catch (SQLException e){
            throw new DbExceptions("Não foi possivel atualizar o serviço de vacinação: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

       // servicoDao.deleteById(id);    problema de integridade aqui

        String sql = "DELETE FROM servico_vacinacao WHERE servico_id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setInt(1, id);

            int rows = st.executeUpdate();

            if (rows > 0) {
                System.out.println("Serviço de vacinação deletado com sucesso! ");
            }else {
                throw new DbExceptions("Nenhum serviço com o ID "+ id + " encontrado!");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao deletar serviço: " + e.getMessage());
        }
    }

    @Override
    public ServicoVacinacao findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT  s.id AS servico_id, " +
                    "s.nome AS servico_nome, " +
                    "s.descricao AS servico_descricao, " +
                    "s.preco AS servico_preco, " +
                    "s.duracao AS servico_duracao, " +
                    "sv.tipo_de_vacinacao " +
                    "FROM servico s " +
                    "INNER JOIN servico_vacinacao sv ON s.id = sv.servico_id " +
                    "WHERE s.id = ? ";

            st = conn.prepareStatement(sql);
            st.setInt(1, id);

            rs = st.executeQuery();
            if(rs.next()){
                ServicoVacinacao servicoVacinacao = instantiateServicoVacinacao(rs);
                return servicoVacinacao;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar serviço de vacinação: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<ServicoVacinacao> findAll() {

        Statement st = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT  s.id AS servico_id, " +
                    "s.nome AS servico_nome, " +
                    "s.descricao AS servico_descricao, " +
                    "s.preco AS servico_preco, " +
                    "s.duracao AS servico_duracao, " +
                    "sv.tipo_de_vacinacao " +
                    "FROM servico s " +
                    "INNER JOIN servico_vacinacao sv ON s.id = sv.servico_id ";

            st = conn.createStatement();
            rs = st.executeQuery(sql);
            List<ServicoVacinacao> servicoVacinacoes = new ArrayList<>();
            while(rs.next()){

                ServicoVacinacao servicoVacinacao = instantiateServicoVacinacao(rs);
                servicoVacinacoes.add(servicoVacinacao);
            }
            return servicoVacinacoes;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar todos os serviços de vacinação: " + e.getMessage());
        }
    }

    private ServicoVacinacao instantiateServicoVacinacao(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("servico_id");
        String nome = rs.getString("servico_nome");
        String descricao = rs.getString("servico_descricao");
        Double preco = rs.getDouble("servico_preco");
        Duration duracao = Duration.ofMinutes(rs.getLong("servico_duracao"));
        TiposDeVacinacao tiposDeVacinacao = TiposDeVacinacao.valueOf(rs.getString("tipo_de_vacinacao"));
        //essa coluna como pertence a essa mesma classe, pertence com o mesmo nome que foi dada na hora de criar o banco de dados, diferente das outras, que foi atribuido um nome diferente por ser da superClasse!!

        ServicoVacinacao servicoVacinacao = new ServicoVacinacao(nome, descricao, preco, duracao, tiposDeVacinacao);
        servicoVacinacao.setId(id);
        return servicoVacinacao;
    }
}
