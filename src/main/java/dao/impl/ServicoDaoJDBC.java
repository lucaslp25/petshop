package dao.impl;

import dao.DB;
import dao.DbExceptions;
import dao.ServicoDao;
import entities.*;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ServicoDaoJDBC implements ServicoDao {

    private Connection conn;

    public ServicoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Servicos servico) {

        String sql = "INSERT INTO servico " +
                "(nome, descricao, preco, duracao, tipo_servico) " +
                "VALUES " +
                "(?, ?, ?, ?, ?)";

        try(PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            st.setString(1, servico.getNome());
            st.setString(2, servico.getDescricao());
            st.setDouble(3, servico.getPreco());
            st.setLong(4, servico.getDuracao().toMinutes());
            st.setString(5, servico.getClass().getSimpleName().replace("Servico", "").toUpperCase().replace("VETERINARIA", ""));
            //aqui está uma lógica muito interessante, de pegar a super classe pela classe que foi instanciada, pegar o nome da classe, retirar do nome da classe os nomes "servico" e "veterinaria" e colocar tudo em letra maiuscula, assim eu consigo manipular a String para instanciar a classe da maneira correta para saber qual vai ser o tipo de serviço a ser instanciado!

            int rows = st.executeUpdate();
            if(rows > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    servico.setId(id);
                    System.out.println("Serviço inserido com sucesso! ID: " + id);
                }
                DB.closeResultSet(rs);
            }else {
                throw new DbExceptions("Algum erro inesperado aconteceu ao inserir o Serviço!");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao inserir serviço: " + e.getMessage());
        }
    }

    @Override
    public void update(Servicos servico) {

        String sql = "UPDATE servico " +
                "SET nome = ?, descricao = ?, preco = ?, duracao = ?, tipo_servico = ? " +
                "WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setString(1, servico.getNome());
            st.setString(2, servico.getDescricao());
            st.setDouble(3, servico.getPreco());
            st.setLong(4, servico.getDuracao().toMinutes());
            st.setString(5, servico.getClass().getSimpleName().replace("Servico", "").toUpperCase().replace("VETERINARIA", ""));
            st.setInt(6, servico.getId());

            st.executeUpdate();
            System.out.println("Atualização feita com sucesso!");
        }catch (SQLException e){
            throw new DbExceptions("Erro ao atualizar serviço: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM servico WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setInt(1, id);

            int rows = st.executeUpdate();

            if (rows > 0){
                System.out.println("Serviço deletado com sucesso! Linhas afetadas: " + rows);
            }else{
                throw new DbExceptions("Nenhum serviço com o ID: " + id + " encontrado!");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao deletar serviço: " + e.getMessage());
        }

    }

    @Override
    public Servicos findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM servico WHERE id = ?";

            st = conn.prepareStatement(sql);

                st.setInt(1, id);

                rs = st.executeQuery();

                if(rs.next()){

                    Servicos servico = intantiateServico(rs);
                    return servico;
                }
                return null;

        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar cliente: " + e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Servicos> findAll() {

        Statement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT * FROM servico";

            st = conn.createStatement();

            rs = st.executeQuery(sql);

            List<Servicos> servicos = new ArrayList<>();

            while(rs.next()){

                Servicos servico = intantiateServico(rs);
                servicos.add(servico);
            }
            return servicos;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar clientes: " + e.getMessage());
        }finally{
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    public Servicos intantiateServico(ResultSet rs) throws SQLException {

        Servicos servico = null;
        String tipoServico = rs.getString("tipo_servico").toUpperCase().replace(" ", "");

        Integer id = rs.getInt("id");
        String nome = rs.getString("nome");
        String descricao = rs.getString("descricao");
        Double preco = rs.getDouble("preco");
        Duration duracao = Duration.ofMinutes(rs.getLong("duracao"));

        //Switch para definir qual sera o tipo e cada serviço, de acordo com a coluna do tipo_servico, que essa coluna sera definida por uma manipulação de String, com getClass, e Replace como está no metodo insert!

        switch (tipoServico) {
            case "TOSA":
                servico = new ServicoTosa(nome, descricao, preco, duracao, null, null);
                break;
            case "VACINACAO":
                servico = new ServicoVacinacao(nome, descricao, preco, duracao, null);
                break;
            case "CONSULTA":
                servico = new ServicoConsultaVeterinaria(nome, descricao, preco, duracao, null);
                break;
            case "BANHO":
                servico = new ServicoBanho(nome, descricao, preco, duracao, null);
                break;

            // os atributos das classes especificas eu vou buscar com as InterfaceDao especificas delas!
        }
        servico.setId(id);
        return servico;
    }

}
