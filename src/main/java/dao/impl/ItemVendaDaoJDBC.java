package dao.impl;

import dao.DB;
import dao.DbExceptions;
import dao.ItemVendaDao;
import entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemVendaDaoJDBC implements ItemVendaDao {

    private Connection conn;

    public ItemVendaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(ItemVenda itemVenda) {

        String sql = "INSERT INTO ItemVenda "
                +"(venda_id, preco_unitario, quantidade, produto_id, servico_id)"
                +" VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){

            st.setInt(1, itemVenda.getVenda().getId());
            st.setDouble(2, itemVenda.getPrecoUnitario());
            st.setInt(3, itemVenda.getQuantidade());
            st.setInt(4, itemVenda.getProduto().getId());
            st.setInt(5, itemVenda.getServicos().getId());

            int rows = st.executeUpdate();
            if(rows > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    itemVenda.setId(id);
                    System.out.println("Item de venda inserido com sucesso! ID: "+id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbExceptions("Erro inesperado ao inserir produto!");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao inserir produto! " + e.getMessage());
        }
    }

    @Override
    public void update(ItemVenda itemVenda) {
        String sql =
                "UPDATE ItemVenda "
                +" SET venda_id = ?, preco_unitario = ?, quantidade = ?, produto_id = ?, servico_id = ?"
                +" WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setInt(1, itemVenda.getVenda().getId());
            st.setDouble(2, itemVenda.getPrecoUnitario());
            st.setInt(3, itemVenda.getQuantidade());
            st.setInt(4, itemVenda.getProduto().getId());
            st.setInt(5, itemVenda.getServicos().getId());
            st.setInt(6, itemVenda.getId());

            st.executeUpdate();
            System.out.println("Item de venda atualizado com sucesso!");
        } catch (SQLException e){
            throw new DbExceptions("Erro ao atualizar produto! " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM itemVenda WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setInt(1, id);

            int rows = st.executeUpdate();
            if(rows > 0){
                System.out.println("Item de venda deletado com sucesso!");
            }else{
                throw new DbExceptions("Erro ao deletar item de venda: ID " + id + " Inválido!");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao deletar item de venda! " + e.getMessage());
        }
    }

    @Override
    public ItemVenda findById(int id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{

            String sql = "SELECT " +
                    "iv.id AS item_id, " +
                    "iv.quantidade, " +
                    "iv.preco_unitario, " +
                    "v.id AS venda_id, " +
                    "v.data_venda AS data_venda, " +
                    "p.id AS produto_id, " +
                    "p.nome AS produto_nome, " +
                    "p.preco_venda AS produto_preco, " +
                    "s.id AS servico_id, " +
                    "s.nome AS servico_nome, " +
                    "s.preco AS servico_preco, " +
                    "s.tipo_servico, " +
                    "c.id AS cliente_id, " +
                    "c.nome AS cliente_nome, " +
                    "c.cpf AS cliente_cpf " +
                    "FROM item_venda iv " +
                    "LEFT JOIN produto p ON iv.produto_id = p.id " +
                    "LEFT JOIN servico s ON iv.servico_id = s.id " +
                    "INNER JOIN venda v ON iv.venda_id = v.id " +
                    "INNER JOIN cliente c ON v.cliente_id = c.id "+
                    "WHERE iv.id = ? ";

            st = conn.prepareStatement(sql);

            st.setInt(1, id);

            rs = st.executeQuery();

            if(rs.next()){

                Cliente cliente = instantiateCliente(rs);
                Venda venda = instantiateVenda(rs, cliente);
                Produto produto = instantiateProduto(rs);
                Servicos servico = instantiateServico(rs);
                ItemVenda itemVenda = instantiateItemVenda(rs, venda, produto, servico);
                return itemVenda;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao selecionar item de venda! " + e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<ItemVenda> findAll() {

        Statement st = null;
        ResultSet rs = null;

        try{

            String sql = "SELECT " +
                    "iv.id AS item_id, " +
                    "iv.quantidade, " +
                    "iv.preco_unitario, " +
                    "v.id AS venda_id, " +
                    "v.data_venda AS data_venda, " +
                    "p.id AS produto_id, " +
                    "p.nome AS produto_nome, " +
                    "p.preco_venda AS produto_preco, " +
                    "s.id AS servico_id, " +
                    "s.nome AS servico_nome, " +
                    "s.preco AS servico_preco, " +
                    "s.tipo_servico, " +
                    "c.id AS cliente_id, " +
                    "c.nome AS cliente_nome, " +
                    "c.cpf AS cliente_cpf " +
                    "FROM item_venda iv " +
                    "LEFT JOIN produto p ON iv.produto_id = p.id " +
                    "LEFT JOIN servico s ON iv.servico_id = s.id " +
                    "INNER JOIN venda v ON iv.venda_id = v.id " +
                    "INNER JOIN cliente c ON v.cliente_id = c.id ";

            st = conn.createStatement();
            rs = st.executeQuery(sql);

            List<ItemVenda> itemVendas = new ArrayList<>();
            Map<Integer, Venda> vendaMap = new HashMap<>();
            Map<Integer, Produto> produtoMap = new HashMap<>();
            Map<Integer, Servicos> servicoMap = new HashMap<>();
            Map<Integer, Cliente> clienteMap = new HashMap<>();

            while(rs.next()){

                Integer clienteId = rs.getInt("cliente_id");
                Cliente cliente = clienteMap.get(clienteId);
                if (cliente == null){
                    cliente = instantiateCliente(rs);
                    clienteMap.put(clienteId, cliente);
                }

                Integer vendaId = rs.getInt("venda_id");
                Venda venda = vendaMap.get(vendaId);
                if (venda == null){
                    venda = instantiateVenda(rs, cliente);
                    vendaMap.put(vendaId, venda);
                }

                Integer produtoId = rs.getInt("produto_id");
                Produto produto = produtoMap.get(produtoId);
                if (produto == null){
                    produto = instantiateProduto(rs);
                    produtoMap.put(produtoId, produto);
                }

                Integer servicoId = rs.getInt("servico_id");
                Servicos servico = servicoMap.get(servicoId);
                if (servico == null){
                    servico = instantiateServico(rs);
                    servicoMap.put(servicoId, servico);
                }

                ItemVenda itemVenda = instantiateItemVenda(rs, venda, produto, servico);
                itemVendas.add(itemVenda);

            }
            return itemVendas;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar itens de venda! " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public ItemVenda findByUniqueAtributs(Double precoUnitario, Integer quantidade, Venda venda) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{

            String sql = "SELECT " +
                    "iv.id AS item_id, " +
                    "iv.quantidade, " +
                    "iv.preco_unitario, " +
                    "v.id AS venda_id, " +
                    "v.data_venda AS data_venda, " +
                    "p.id AS produto_id, " +
                    "p.nome AS produto_nome, " +
                    "p.preco_venda AS produto_preco, " +
                    "s.id AS servico_id, " +
                    "s.nome AS servico_nome, " +
                    "s.preco AS servico_preco, " +
                    "s.tipo_servico, " +
                    "c.id AS cliente_id, " +
                    "c.nome AS cliente_nome, " +
                    "c.cpf AS cliente_cpf " +
                    "FROM item_venda iv " +
                    "LEFT JOIN produto p ON iv.produto_id = p.id " +
                    "LEFT JOIN servico s ON iv.servico_id = s.id " +
                    "INNER JOIN venda v ON iv.venda_id = v.id " +
                    "INNER JOIN cliente c ON v.cliente_id = c.id "+
                    "WHERE preco_unitario = ? AND quantidade = ? AND venda_id = ?";

            st = conn.prepareStatement(sql);

            st.setDouble(1, precoUnitario);
            st.setInt(2, quantidade);
            st.setInt(3, venda.getId());

            rs = st.executeQuery();

            if(rs.next()){

                Cliente cliente = instantiateCliente(rs);
                Venda venda2 = instantiateVenda(rs, cliente);
                Produto produto = instantiateProduto(rs);
                Servicos servico = instantiateServico(rs);
                ItemVenda itemVenda = instantiateItemVenda(rs, venda2, produto, servico);
                return itemVenda;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao selecionar item de venda! " + e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }


    private Cliente instantiateCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("cliente_id"));
        cliente.setNome(rs.getString("cliente_nome"));
        cliente.setCpf(rs.getString("cliente_cpf"));
        return cliente;
        //aqui so os atributos que eu coloquei na minha query!
    }

    private Venda instantiateVenda(ResultSet rs, Cliente cliente) throws SQLException {
        Venda venda = new Venda();
        venda.setId(rs.getInt("venda_id"));
        venda.setDataVenda(rs.getDate("data_venda").toLocalDate());
        venda.setCliente(cliente);

        return venda;
    }

    private Produto instantiateProduto(ResultSet rs) throws SQLException {
        Produto produto = null;
        if (rs.getInt("produto_id") != 0) {
            produto = new Produto();
            produto.setId(rs.getInt("produto_id"));
            produto.setNome(rs.getString("produto_nome"));
            produto.setPrecoDeVenda(rs.getDouble("produto_preco"));
        }
        return produto;
        //produto ou serviço não obrigatorios para a query, por isso esse if
    }

    private Servicos instantiateServico(ResultSet rs) throws SQLException {

        Servicos servico = null;
        String tipoServico = rs.getString("tipo_servico");

        if (tipoServico != null) {
            tipoServico = tipoServico.toUpperCase().replace(" ", "");

            Integer id = rs.getInt("servico_id");
            String nome = rs.getString("servico_nome");
            Double preco = rs.getDouble("servico_preco");

            switch (tipoServico) {
                case "TOSA":
                    servico = new ServicoTosa(nome, null, preco, null, null, null);
                    break;
                case "VACINACAO":
                    servico = new ServicoVacinacao(nome, null, preco, null, null);
                    break;
                case "CONSULTA":
                    servico = new ServicoConsultaVeterinaria(nome, null, preco, null, null);
                    break;
                case "BANHO":
                    servico = new ServicoBanho(nome, null, preco, null, null);
                    break;

                default:
                    throw new DbExceptions("Nenhum serviço com este tipo encontrado!");
            }
            if (servico != null) {
                servico.setId(id);
            }
        }
        return servico;

        // caso bem interessante aqui nese metodo, porem na forma que eu faço a inserção dos serviços eu tenho um modo especifico para colocar o tipo, e assim sera instanciado cada um dos serviços!
    }

    private ItemVenda instantiateItemVenda(ResultSet rs, Venda venda, Produto produto, Servicos servico) throws SQLException {
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setId(rs.getInt("item_id"));
        itemVenda.setQuantidade(rs.getInt("quantidade"));
        itemVenda.setPrecoUnitario(rs.getDouble("preco_unitario"));
        itemVenda.setVenda(venda);
        itemVenda.setProduto(produto);
        itemVenda.setServicos(servico);
        return itemVenda;
    }

}
