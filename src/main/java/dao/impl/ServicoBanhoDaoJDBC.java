package dao.impl;

import dao.DB;
import dao.DbExceptions;
import dao.ServicoBanhoDao;
import dao.ServicoDao;
import entities.ServicoBanho;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ServicoBanhoDaoJDBC implements ServicoBanhoDao {

    private Connection conn;
    private ServicoDao servicoDao; // Referencia ao Dao da superclasse

    public ServicoBanhoDaoJDBC(Connection conn, ServicoDao servicoDao) {
        this.conn = conn;
        this.servicoDao = servicoDao;
    }

    @Override
    public void insert(ServicoBanho servico) {

        servicoDao.insert(servico); // <-- ponto chave da relação um-para-um nas tabelas do banco de dados!

        //eis a importancia de colocar a chave primaria e estrangeira das subclasses dos servicos sendo a mesma da superclase servicos! Com essa relação 'É UM' eu tenho esse controle nos metodos de deletar, inserir, e fazer atualizações no banco de dados com muito mais facilidade, apenas chamando o metodo da classe pai pra ca junto, eu garanto que os atributos vão ser mudados na classe pai tambem, e nessa classe mais especifica que estou chamando o metodo, ou seja, funciona de maneira muito eficiente dessa forma!

        //Inserindo os dados do serviço da tabela 'pai' na sub tabela, para depois pegar os dados mais especificos desse serviço!

        String sql = "INSERT INTO servico_banho "
                + "(servico_id, com_hidratacao) "
                + "VALUES (?, ?)";

        //nota-se que aqui nesse metodo pega o atributo que seria a chave primaria do serviço!

        try(PreparedStatement st = conn.prepareStatement(sql)){
            st.setInt(1, servico.getId());
            st.setBoolean(2, servico.getComHidratacao());

            int rows = st.executeUpdate();
            if(rows > 0){
                System.out.println("Serviço banho inserido com sucesso para o ID: " + servico.getId());
            }else {
                throw new DbExceptions("Erro inesperado ao inserir serviço de banho! ");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao inserir serviço de banho: " + e.getMessage());
        }
    }

    @Override
    public void update(ServicoBanho servico) {

        servicoDao.update(servico);

        String sql = "UPDATE servico_banho "
                + "SET com_hidratacao = ? "
                + "WHERE servico_id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setBoolean(1, servico.getComHidratacao());
            st.setInt(2, servico.getId());
            st.executeUpdate();
            System.out.println("Atualização de serviço feita com sucesso!");

        }catch (SQLException e){
            throw new DbExceptions("Erro ao atualizar serviço de banho: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

       // servicoDao.deleteById(id); problema de integridade ao fazer essa chamada

        String sql = "DELETE FROM servico_banho WHERE servico_id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){
            st.setInt(1, id);

            int rows = st.executeUpdate();

            if(rows > 0){
                System.out.println("Serviço de banho deletado com sucesso!");
            }else{
                throw new DbExceptions("Nenhum serviço com o id " + id + " encontrado para deletar!");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao deletar serviço: " + e.getMessage());
        }

    }

    @Override
    public ServicoBanho findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT  s.id AS servico_id, " +
                    "s.nome AS servico_nome, " +
                    "s.descricao AS servico_descricao, " +
                    "s.preco AS servico_preco, " +
                    "s.duracao AS servico_duracao, " +
                    "sb.com_hidratacao " +
                    "FROM servico s " +
                    "INNER JOIN servico_banho sb ON s.id = sb.servico_id " +
                    "WHERE s.id = ? ";

            st = conn.prepareStatement(sql);

            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){
                ServicoBanho servico = instantiateServicoBanho(rs);
                return servico;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar serviço de banho: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<ServicoBanho> findAll() {

        Statement st = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT  s.id AS servico_id, " +
                    "s.nome AS servico_nome, " +
                    "s.descricao AS servico_descricao, " +
                    "s.preco AS servico_preco, " +
                    "s.duracao AS servico_duracao, " +
                    "sb.com_hidratacao " +
                    "FROM servico s " +
                    "INNER JOIN servico_banho sb ON s.id = sb.servico_id ";

            st = conn.createStatement();
            rs = st.executeQuery(sql);

            List<ServicoBanho> servicosDeBanho = new ArrayList<>();

            while(rs.next()){

                ServicoBanho servicoBanho = instantiateServicoBanho(rs);
                servicosDeBanho.add(servicoBanho);
            }
            return servicosDeBanho;

        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar serviços de banho: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private ServicoBanho instantiateServicoBanho(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("servico_id");
        String nome = rs.getString("servico_nome");
        String descricao = rs.getString("servico_descricao");
        Double preco = rs.getDouble("servico_preco");
        Duration duracao = Duration.ofMinutes(rs.getLong("servico_duracao"));
        Boolean comHidratacao = rs.getBoolean("com_hidratacao");

        ServicoBanho servicoBanho = new ServicoBanho(nome, descricao, preco, duracao, comHidratacao);
        servicoBanho.setId(id);
        return servicoBanho;
    }

}
