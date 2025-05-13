package dao.impl;

import dao.DB;
import dao.DbExceptions;
import dao.ProdutoDao;
import entities.Produto;
import enums.CategoriaDeProdutos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDaoJDBC implements ProdutoDao {

    private Connection conn;
    public ProdutoDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void insertProduto(Produto produto) {

        String sql = "INSERT INTO produto " +
                "(nome, descricao, preco_custo, preco_venda, quantidade_estoque, fornecedor, categoria) "+
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){

            st.setString(1, produto.getNome());
            st.setString(2, produto.getDescricao());
            st.setDouble(3, produto.getPrecoDeCusto());
            st.setDouble(4, produto.getPrecoDeVenda());
            st.setInt(5, produto.getQuantidadeEstoque());
            st.setString(6, produto.getFornecedor());
            st.setString(7, produto.getCategoria().name());

            int rows = st.executeUpdate();
            if(rows > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    produto.setId(id);
                    System.out.println("Produto Inserido com sucesso! ID: " + id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbExceptions("Erro ao inserir produto: falha ao inserir linha na tabela.");   //um pouco mais especifica a mensagem
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao inserir produto! " + e.getMessage());
        }
    }

    @Override
    public void updateProduto(Produto produto) {

        String sql = "UPDATE produto " +
                "SET nome = ?, descricao = ?, preco_custo = ?, preco_venda = ?, quantidade_estoque = ?, fornecedor = ?, categoria = ?" +
                "WHERE id = ? ";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setString(1, produto.getNome());
            st.setString(2, produto.getDescricao());
            st.setDouble(3, produto.getPrecoDeCusto());
            st.setDouble(4, produto.getPrecoDeVenda());
            st.setInt(5, produto.getQuantidadeEstoque());
            st.setString(6, produto.getFornecedor());
            st.setString(7, produto.getCategoria().name());
            st.setInt(8, produto.getId());

            st.executeUpdate();
            System.out.println("Produto Atualizado com sucesso!");

        }catch (SQLException e){
            throw new DbExceptions("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    @Override
    public void deleteProdutoById(Integer id) {

        String sql = "DELETE FROM produto WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setInt(1, id);

            int rows = st.executeUpdate();
            if (rows > 0){
                System.out.println("Produto Deletado com sucesso!");
            }else{
                throw new DbExceptions("Erro ao deletar produto: Nenhum produto com esse ID encontrado!");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao deletar produto: " + e.getMessage());
        }
    }

    @Override
    public Produto findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT * FROM produto WHERE id = ?";

            st = conn.prepareStatement(sql);{

                st.setInt(1, id);
                rs = st.executeQuery();

                if(rs.next()){
                    Produto produto = instantiateProduto(rs);
                    return produto;
                }
                return null;
            }
        } catch (SQLException e ){
            throw new DbExceptions("Erro ao buscar produto: " + e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Produto instantiateProduto(ResultSet rs) throws SQLException{
        Produto produto = new Produto();
        produto.setId(rs.getInt("id"));
        produto.setNome(rs.getString("nome"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setPrecoDeCusto(rs.getDouble("preco_custo"));
        produto.setPrecoDeVenda(rs.getDouble("preco_venda"));
        produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
        produto.setFornecedor(rs.getString("fornecedor"));
        produto.setCategoria(CategoriaDeProdutos.valueOf(rs.getString("categoria")));
        return produto;
    }

    @Override
    public List<Produto> findAll() {

        Statement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT * FROM produto";

            st = conn.createStatement();
            //createStatement mais simples, não precisa de parametros nesse caso, é mais simples e direto para ser usado, em todos findAll pode ser usado ele

            rs = st.executeQuery(sql);  //agora a String sql, o "SCRIPT" do sql veio direto para ca no execute query, pois não tinha placeholders para ser modificados com os 'sets' !

            List<Produto> produtos = new ArrayList<>();

            while(rs.next()){

                Produto produto = instantiateProduto(rs);
                produtos.add(produto);
            }
            return produtos;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar produtos: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public Produto findByUniqueAtributs(String nome, String descricao, String fornecedor) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT * FROM produto " +
                    "WHERE nome = ? AND descricao = ? AND fornecedor = ? ";

            st = conn.prepareStatement(sql);{

                st.setString(1, nome);
                st.setString(2, descricao);
                st.setString(3, fornecedor);

                rs = st.executeQuery();

                if(rs.next()){
                    Produto produto = instantiateProduto(rs);
                    return produto;
                }
                return null;
            }
        } catch (SQLException e ){
            throw new DbExceptions("Erro ao buscar produto: " + e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }














    }
}
