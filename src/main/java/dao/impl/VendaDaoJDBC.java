package dao.impl;

import dao.DB;
import dao.DbExceptions;
import dao.MetodoDePagamentoDao;
import dao.VendaDao;
import entities.Cliente;
import entities.Endereco;
import entities.MetodoDePagamento;
import entities.Venda;
import enums.TipoDePagamento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendaDaoJDBC implements VendaDao {

    private Connection conn;
    private MetodoDePagamentoDao metodoDePagamentoDao;

    public VendaDaoJDBC(Connection conn, MetodoDePagamentoDao metodoDePagamentoDao) {
        this.conn = conn;
        this.metodoDePagamentoDao = metodoDePagamentoDao;
    }

    @Override
    public void insert(Venda venda) {

        TipoDePagamento tipoPagamentoVenda = venda.getTipoPagamento();

        MetodoDePagamento metodoPagamento = metodoDePagamentoDao.findByTipo(tipoPagamentoVenda);

        if (metodoPagamento == null) {
            throw new DbExceptions("Método de pagamento não encontrado: " + tipoPagamentoVenda);
        }

        venda.setMetodoPagamentoId(metodoPagamento.getId()); // Seta o ID na entidade Venda

        String sql = "INSERT INTO venda "
                +"(data_venda, valor_total, cliente_id, metodo_pagamento_id) "
                +"VALUES (?, ?, ?, ?) ";

        try(PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){

            java.sql.Date sqlDate = java.sql.Date.valueOf(venda.getDataVenda()); //chamada da data
            st.setDate(1, sqlDate);
            st.setDouble(2, venda.getValorTotal());
            st.setInt(3, venda.getCliente().getId());
            st.setInt(4, venda.getMetodoPagamentoId());

            int rows = st.executeUpdate();
            if(rows > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    venda.setId(id);
                    System.out.println("Venda inserida com sucesso! " + id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbExceptions("Erro inesperado ao inserir Venda no banco de dados! ");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao inserir venda: " + e.getMessage());
        }
    }

    @Override
    public void update(Venda venda) {

        String sql = "UPDATE venda "
                + "SET data_venda = ?, valor_total = ?, cliente_id = ?, metodo_pagamento_id = ?"
                + "WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            java.sql.Date sqlDate = java.sql.Date.valueOf(venda.getDataVenda());
            st.setDate(1, sqlDate);
            st.setDouble(2, venda.getValorTotal());
            st.setInt(3, venda.getCliente().getId());
            st.setInt(4, venda.getMetodoPagamentoId());

            st.executeUpdate();
            System.out.println("Atualização de venda feita com sucesso!");

        }catch (SQLException e){
            throw new DbExceptions("Erro atualizar serviço: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM venda WHERE id = ?";
        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setInt(1, id);
            int rows = st.executeUpdate();

            if(rows > 0){
                System.out.println("Venda deletada com sucesso!");
            }else{
                throw new DbExceptions("Erro ao deletar serviço: ID "+ id + " não encontrado!");
            }
        } catch (SQLException e) {
            throw new DbExceptions("Erro ao deletar venda: " + e.getMessage());
        }
    }

    @Override
    public Venda findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            //alerta de script extenso, pois tem cliente e metodo de pagamento associado a vendas.. e consequentemente um endereço associado ao cliente, e como eu quero imprimir um relatorio com todas informações certinhas, eu preciso colocar todas essas informções nos meus finds!

            String sql ="SELECT v.id AS venda_id, " +
                    "v.data_venda, " +
                    "v.valor_total, " +
                    "c.id AS cliente_id, " +
                    "c.nome AS cliente_nome, " +
                    "c.cpf AS cliente_cpf, " +
                    "c.email AS cliente_email, " +
                    "c.telefone AS cliente_telefone, " +
                    "e.id AS endereco_id, " +
                    "e.rua AS endereco_rua, " +
                    "e.numero AS endereco_numero, " +
                    "e.bairro AS endereco_bairro, " +
                    "e.cidade AS endereco_cidade, " +
                    "e.estado AS endereco_estado, " +
                    "e.cep AS endereco_cep, " +
                    "e.complemento AS endereco_complemento, " +
                    "mp.id AS metodo_pagamento_id, " +
                    "mp.tipo AS metodo_pagamento_tipo " +
                    "FROM venda v " +
                    "INNER JOIN cliente c ON v.cliente_id = c.id " +
                    "INNER JOIN endereco e ON c.endereco_id = e.id " +
                    "INNER JOIN metodo_pagamento mp ON v.metodo_pagamento_id = mp.id "
                    +"WHERE v.id = ?";

            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){

                Endereco endereco = instantiateEndereco(rs);
                Cliente cliente = instantiateCliente(rs, endereco);
                MetodoDePagamento metodoDePagamento = instantiateMetodoDePagamento(rs);
                Venda venda = instantiateVenda(rs, cliente);
                return venda;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao achar venda: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Venda> findAll() {

        Statement st = null;
        ResultSet rs = null;

        try{
            String sql ="SELECT v.id AS venda_id, " +
                    "v.data_venda, " +
                    "v.valor_total, " +
                    "c.id AS cliente_id, " +
                    "c.nome AS cliente_nome, " +
                    "c.cpf AS cliente_cpf, " +
                    "c.email AS cliente_email, " +
                    "c.telefone AS cliente_telefone, " +
                    "e.id AS endereco_id, " +
                    "e.rua AS endereco_rua, " +
                    "e.numero AS endereco_numero, " +
                    "e.bairro AS endereco_bairro, " +
                    "e.cidade AS endereco_cidade, " +
                    "e.estado AS endereco_estado, " +
                    "e.cep AS endereco_cep, " +
                    "e.complemento AS endereco_complemento, " +
                    "mp.id AS metodo_pagamento_id, " +
                    "mp.tipo AS metodo_pagamento_tipo " +
                    "FROM venda v " +
                    "INNER JOIN cliente c ON v.cliente_id = c.id " +
                    "INNER JOIN endereco e ON c.endereco_id = e.id " +
                    "INNER JOIN metodo_pagamento mp ON v.metodo_pagamento_id = mp.id ";

            st = conn.createStatement();
            rs = st.executeQuery(sql);

            //questão aqui muito importante para não haver duplicidade de memoria, para isso usarei maps para cada entidade associada a minha venda e irei verificar se ja existe na lista ou não do map, o map se encarregara dessa função da duplicidade de cada entidade e tera uma List de Vendas para armazenar apenas os valores certos de venda sem duplicidade!

            Map<Integer, Endereco> enderecoMap = new HashMap<>();
            Map<Integer, Cliente> clienteMap = new HashMap<>();
            Map<Integer, MetodoDePagamento> metodoDePagamentoMap = new HashMap<>();
            List<Venda> vendas = new ArrayList<>();

            while(rs.next()){

                Integer enderecoId = rs.getInt("endereco_id"); //atribuo o id do resultSET do endereço para a chave do meu MAP
                Endereco endereco = enderecoMap.get(enderecoId);

                //aqui atribuimos o par chave\valor ao objeto endereço, e ai que entra a magica do Map, pois ele vai buscar os valores por pares de acordo com a chave que é o ID, então se a chave (ID) tiver algum valor de objeto endereço atribuido a ela, ja ira retornar direto esse valor, sem duplicar o valor!  Mas agora se for um valor novo que essa chave não tem nenhum valor atribuido ainda e for nulo, caira no if e sera atribuido um novo endereço de acordo com o que vier no ResultSet! e mandamos esse valor para a List<Vendas> evitando duplicação de Endereco, e faremos isso com os outros Maps, assim evitando a duplicação de qualquer entidade no metodo !!

                if(endereco == null){
                    endereco = instantiateEndereco(rs);
                    enderecoMap.put(enderecoId, endereco);
                }

                Integer clienteId = rs.getInt("cliente_id");
                Cliente cliente = clienteMap.get(clienteId);
                if(cliente == null){
                    cliente = instantiateCliente(rs, endereco);
                    clienteMap.put(clienteId, cliente);
                }

                Integer metodoDePagamentoId = rs.getInt("metodo_pagamento_id");
                MetodoDePagamento metodoDePagamento = metodoDePagamentoMap.get(metodoDePagamentoId);
                if(metodoDePagamento == null){
                    metodoDePagamento = instantiateMetodoDePagamento(rs);
                    metodoDePagamentoMap.put(metodoDePagamentoId, metodoDePagamento);
                }

                Venda venda = instantiateVenda(rs, cliente);
                vendas.add(venda);
            }
            return vendas;

        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar vendas: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Venda findByUniqueAtributs(LocalDate dataVenda, Double valorTotal, Cliente cliente, Integer metodoPagamentoId) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try{

            String sql ="SELECT v.id AS venda_id, " +
                    "v.data_venda, " +
                    "v.valor_total, " +
                    "c.id AS cliente_id, " +
                    "c.nome AS cliente_nome, " +
                    "c.cpf AS cliente_cpf, " +
                    "c.email AS cliente_email, " +
                    "c.telefone AS cliente_telefone, " +
                    "e.id AS endereco_id, " +
                    "e.rua AS endereco_rua, " +
                    "e.numero AS endereco_numero, " +
                    "e.bairro AS endereco_bairro, " +
                    "e.cidade AS endereco_cidade, " +
                    "e.estado AS endereco_estado, " +
                    "e.cep AS endereco_cep, " +
                    "e.complemento AS endereco_complemento, " +
                    "mp.id AS metodo_pagamento_id, " +
                    "mp.tipo AS metodo_pagamento_tipo " +
                    "FROM venda v " +
                    "INNER JOIN cliente c ON v.cliente_id = c.id " +
                    "INNER JOIN endereco e ON c.endereco_id = e.id " +
                    "INNER JOIN metodo_pagamento mp ON v.metodo_pagamento_id = mp.id "
                    +"WHERE data_venda = ? AND valor_total = ? AND cliente_id = ? AND metodo_pagamento_id = ? ";

            st = conn.prepareStatement(sql);
            st.setString(1, dataVenda.toString());
            st.setDouble(2, valorTotal);
            st.setInt(3, cliente.getId());
            st.setInt(4, metodoPagamentoId);

            rs = st.executeQuery();

            if(rs.next()){

                Endereco endereco = instantiateEndereco(rs);
                Cliente cliente2 = instantiateCliente(rs, endereco);
                MetodoDePagamento metodoDePagamento = instantiateMetodoDePagamento(rs);
                Venda venda = instantiateVenda(rs, cliente2);
                return venda;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao achar venda: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Venda> findVendasByClienteCpf(String cpf) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String sql ="SELECT v.id AS venda_id, " +
                    "v.data_venda, " +
                    "v.valor_total, " +
                    "c.id AS cliente_id, " +
                    "c.nome AS cliente_nome, " +
                    "c.cpf AS cliente_cpf, " +
                    "c.email AS cliente_email, " +
                    "c.telefone AS cliente_telefone, " +
                    "e.id AS endereco_id, " +
                    "e.rua AS endereco_rua, " +
                    "e.numero AS endereco_numero, " +
                    "e.bairro AS endereco_bairro, " +
                    "e.cidade AS endereco_cidade, " +
                    "e.estado AS endereco_estado, " +
                    "e.cep AS endereco_cep, " +
                    "e.complemento AS endereco_complemento, " +
                    "mp.id AS metodo_pagamento_id, " +
                    "mp.tipo AS metodo_pagamento_tipo " +
                    "FROM venda v " +
                    "INNER JOIN cliente c ON v.cliente_id = c.id " +
                    "INNER JOIN endereco e ON c.endereco_id = e.id " +
                    "INNER JOIN metodo_pagamento mp ON v.metodo_pagamento_id = mp.id "+
                    "WHERE c.cpf = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, cpf);

            rs = st.executeQuery();

            Map<Integer, Endereco> enderecoMap = new HashMap<>();
            Map<Integer, Cliente> clienteMap = new HashMap<>();
            Map<Integer, MetodoDePagamento> metodoDePagamentoMap = new HashMap<>();
            List<Venda> vendas = new ArrayList<>();

            while(rs.next()){

                Integer enderecoId = rs.getInt("endereco_id");
                Endereco endereco = enderecoMap.get(enderecoId);

                if(endereco == null){
                    endereco = instantiateEndereco(rs);
                    enderecoMap.put(enderecoId, endereco);
                }

                Integer clienteId = rs.getInt("cliente_id");
                Cliente cliente = clienteMap.get(clienteId);
                if(cliente == null){
                    cliente = instantiateCliente(rs, endereco);
                    clienteMap.put(clienteId, cliente);
                }

                Integer metodoDePagamentoId = rs.getInt("metodo_pagamento_id");
                MetodoDePagamento metodoDePagamento = metodoDePagamentoMap.get(metodoDePagamentoId);
                if(metodoDePagamento == null){
                    metodoDePagamento = instantiateMetodoDePagamento(rs);
                    metodoDePagamentoMap.put(metodoDePagamentoId, metodoDePagamento);
                }
                Venda venda = instantiateVenda(rs, cliente);
                vendas.add(venda);
            }
            return vendas;

        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar vendas: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Venda> findVendasByMonthAndYear(Integer month, Integer year) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String sql ="SELECT v.id AS venda_id, " +
                    "v.data_venda, " +
                    "v.valor_total, " +
                    "c.id AS cliente_id, " +
                    "c.nome AS cliente_nome, " +
                    "c.cpf AS cliente_cpf, " +
                    "c.email AS cliente_email, " +
                    "c.telefone AS cliente_telefone, " +
                    "e.id AS endereco_id, " +
                    "e.rua AS endereco_rua, " +
                    "e.numero AS endereco_numero, " +
                    "e.bairro AS endereco_bairro, " +
                    "e.cidade AS endereco_cidade, " +
                    "e.estado AS endereco_estado, " +
                    "e.cep AS endereco_cep, " +
                    "e.complemento AS endereco_complemento, " +
                    "mp.id AS metodo_pagamento_id, " +
                    "mp.tipo AS metodo_pagamento_tipo " +
                    "FROM venda v " +
                    "INNER JOIN cliente c ON v.cliente_id = c.id " +
                    "INNER JOIN endereco e ON c.endereco_id = e.id " +
                    "INNER JOIN metodo_pagamento mp ON v.metodo_pagamento_id = mp.id "+
                    "WHERE MONTH(v.data_venda) = ? AND YEAR(v.data_venda) = ?";
            st = conn.prepareStatement(sql);

            st.setInt(1, month);
            st.setInt(2, year);

            rs = st.executeQuery();

            Map<Integer, Endereco> enderecoMap = new HashMap<>();
            Map<Integer, Cliente> clienteMap = new HashMap<>();
            Map<Integer, MetodoDePagamento> metodoDePagamentoMap = new HashMap<>();
            List<Venda> vendas = new ArrayList<>();

            while(rs.next()){

                Integer enderecoId = rs.getInt("endereco_id");
                Endereco endereco = enderecoMap.get(enderecoId);

                if(endereco == null){
                    endereco = instantiateEndereco(rs);
                    enderecoMap.put(enderecoId, endereco);
                }

                Integer clienteId = rs.getInt("cliente_id");
                Cliente cliente = clienteMap.get(clienteId);
                if(cliente == null){
                    cliente = instantiateCliente(rs, endereco);
                    clienteMap.put(clienteId, cliente);
                }

                Integer metodoDePagamentoId = rs.getInt("metodo_pagamento_id");
                MetodoDePagamento metodoDePagamento = metodoDePagamentoMap.get(metodoDePagamentoId);
                if(metodoDePagamento == null){
                    metodoDePagamento = instantiateMetodoDePagamento(rs);
                    metodoDePagamentoMap.put(metodoDePagamentoId, metodoDePagamento);
                }
                Venda venda = instantiateVenda(rs, cliente);
                vendas.add(venda);
            }
            return vendas;

        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar vendas: " + e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Venda instantiateVenda(ResultSet rs, Cliente cliente) throws SQLException {
        Venda venda = new Venda();

        venda.setId(rs.getInt("venda_id"));
        venda.setDataVenda(LocalDate.parse(rs.getString("data_Venda")));
        venda.setValorTotal(rs.getDouble("valor_total"));
        venda.setCliente(cliente);
        venda.setMetodoPagamentoId(rs.getInt("metodo_pagamento_id"));
        return venda;
    }

    private Endereco instantiateEndereco(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setId(rs.getInt("endereco_id"));
        endereco.setRua(rs.getString("endereco_rua"));
        endereco.setNumero(rs.getString("endereco_numero"));
        endereco.setBairro(rs.getString("endereco_bairro"));
        endereco.setCidade(rs.getString("endereco_cidade"));
        endereco.setEstado(rs.getString("endereco_estado"));
        endereco.setCep(rs.getString("endereco_cep"));
        endereco.setComplemento(rs.getString("endereco_complemento"));
        return endereco;
    }

    private Cliente instantiateCliente(ResultSet rs, Endereco endereco) throws SQLException{
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("cliente_id"));
        cliente.setNome(rs.getString("cliente_nome"));
        cliente.setCpf(rs.getString("cliente_cpf"));
        cliente.setEmail(rs.getString("cliente_email"));
        cliente.setTelefone(rs.getString("cliente_telefone"));
        cliente.setEndereco(endereco);
        return cliente;
    }

    private MetodoDePagamento instantiateMetodoDePagamento(ResultSet rs)throws SQLException {
        MetodoDePagamento metodoDePagamento = new MetodoDePagamento();
        metodoDePagamento.setId(rs.getInt("metodo_pagamento_id"));
        metodoDePagamento.setTipoDePagamento(TipoDePagamento.valueOf(rs.getString("metodo_pagamento_tipo")));

        return metodoDePagamento;
    }
}
