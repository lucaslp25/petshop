package dao.impl;

import dao.DB;
import dao.DbExceptions;
import dao.MetodoDePagamentoDao;
import entities.MetodoDePagamento;
import enums.TipoDePagamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetodoDePagamentoDaoJDBC implements MetodoDePagamentoDao {

    private Connection conn;

    public MetodoDePagamentoDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(MetodoDePagamento metodoDePagamento) {

        String sql = "INSERT INTO metodo_pagamento "
                + "(tipo) VALUES (?)";

        try(PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){

            st.setString(1, metodoDePagamento.getTipoDePagamento().name());
            int rows = st.executeUpdate();
            if(rows > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    metodoDePagamento.setId(id);
                    System.out.println("Método de pagamento inserido com sucesso! ID: "+ id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbExceptions("Erro inesperado ao inserir método de pagamento! ");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao inserir método de pagamento: "+e.getMessage());
        }
    }

    @Override
    public void update(MetodoDePagamento metodoDePagamento) {

        String sql = "UPDATE metodo_pagamento SET tipo = ? WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setString(1, metodoDePagamento.getTipoDePagamento().name());
            st.setInt(2, metodoDePagamento.getId());

            st.executeUpdate();
            System.out.println("Atualização do método de pagamento realizado com sucesso!");
        }catch (SQLException e){
            throw new DbExceptions("Erro ao atualizar o método de pagamento! " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM metodo_pagamento WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setInt(1, id);
            int rows = st.executeUpdate();
            if(rows > 0){
                System.out.println("Método de pagamento deletado com sucesso!");
            }else{
                throw new DbExceptions("Erro ao deletar metodo de pagamento, ID " + id + " Incorreto!");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao deletar método de pagamento: " + e.getMessage());
        }
    }

    @Override
    public MetodoDePagamento findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT * " +
                    "FROM metodo_pagamento " +
                    "WHERE id = ? ";

            st = conn.prepareStatement(sql);
            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()){
                MetodoDePagamento metodoDePagamento = instantiateMetodoDePagamento(rs);
                return metodoDePagamento;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Falha ao buscar método de pagamento por id: " + e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<MetodoDePagamento> findAll() {

        Statement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT * " +
                    "FROM metodo_pagamento ";

            st = conn.createStatement();
            rs = st.executeQuery(sql);

            List<MetodoDePagamento> metodosDePagamento = new ArrayList<>();
            while(rs.next()){

                MetodoDePagamento metodoDePagamento = instantiateMetodoDePagamento(rs);
                metodosDePagamento.add(metodoDePagamento);

            }
            return metodosDePagamento;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar métodos de pagamentos! ");
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public MetodoDePagamento findByTipo(TipoDePagamento tipoDePagamento) throws DbExceptions{

        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT * " +
                    "FROM metodo_pagamento " +
                    "WHERE tipo = ? ";

            st = conn.prepareStatement(sql);
            st.setString(1, tipoDePagamento.name());

            rs = st.executeQuery();

            if (rs.next()){
                MetodoDePagamento metodoDePagamento = instantiateMetodoDePagamento(rs);
                return metodoDePagamento;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Falha ao buscar método de pagamento por tipo: " + e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private MetodoDePagamento instantiateMetodoDePagamento(ResultSet rs)throws SQLException {

        MetodoDePagamento metodoDePagamento = new MetodoDePagamento();
        metodoDePagamento.setId(rs.getInt("id"));
        metodoDePagamento.setTipoDePagamento(TipoDePagamento.valueOf(rs.getString("tipo")));

        return metodoDePagamento;

        //OBS: tem duas formas de instanciar os objetos com esses metodos, estou usando essa maneira para essas classes que não foram herdadas, e para as outras classes que são subclasses de serviços eu usei um outro padrão de instanciação no metodo, porem os dois metodos fazem absolutamente a mesma coisa!
    }
}
