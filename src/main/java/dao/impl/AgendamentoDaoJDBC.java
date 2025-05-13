package dao.impl;

import dao.AgendamentoDao;
import dao.DB;
import dao.DbExceptions;

import entities.Agendamento;


import entities.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgendamentoDaoJDBC implements AgendamentoDao {

    private Connection conn;

    public AgendamentoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Agendamento agendamento) {

        String sql = "INSERT INTO agendamento " +
        "(data_hora, pet_id, funcionario_id, servico_id, valor) " +
                "VALUES (?, ?, ?, ?, ?) ";

        try(PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){

            java.sql.Date sqlDate = java.sql.Date.valueOf(agendamento.getDataAgendamento());

            st.setDate(1, sqlDate);
            st.setInt(2, agendamento.getPet().getId());
            st.setInt(3, agendamento.getFuncionarioResponsavel().getId());
            st.setInt(4, agendamento.getServicos().getId());
            st.setDouble(5, agendamento.getValor());

            int rows = st.executeUpdate();
            if(rows > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    agendamento.setId(id);
                    System.out.println("Agendamento inserido com sucesso! ID: " + id);
                }
                DB.closeResultSet(rs);
            }else {
                throw new DbExceptions("Não foi possivel inserir agendamento! ERRO INESPERADO.");
            }
        }catch(SQLException e){
            throw new DbExceptions("Erro ao inserir agendamento: " + e.getMessage());
        }
    }

    @Override
    public void update(Agendamento agendamento) {

        String sql = "UPDATE agendamento "+
                "SET data_hora = ?, pet_id = ?, funcionario_id = ?, servico_id = ?, valor = ? "
                + "WHERE id = ? ";
        try(PreparedStatement st = conn.prepareStatement(sql)){

            java.sql.Date sqlDate = java.sql.Date.valueOf(agendamento.getDataAgendamento());

            st.setDate(1, sqlDate);
            st.setInt(2, agendamento.getPet().getId());
            st.setInt(3, agendamento.getFuncionarioResponsavel().getId());
            st.setInt(4, agendamento.getServicos().getId());
            st.setDouble(5, agendamento.getValor());
            st.setInt(6, agendamento.getId());

            st.executeUpdate();
            System.out.println("Agendamento atualizado com sucesso!");
        }catch(SQLException e){
            throw new DbExceptions("Erro ao atualizar agendamento: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM agendamento WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setInt(1, id);

            int rows = st.executeUpdate();
            if(rows > 0){
                System.out.println("Agendamento deletado com sucesso!");
            }else{
                throw new DbExceptions("Erro ao deletar agendamento, ID: " + id + " inválido!");
            }
        }catch(SQLException e){
            throw new DbExceptions("Erro ao deletar agendamento: " + e.getMessage());
        }
    }

    @Override
    public Agendamento findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{

            String sql = "SELECT " +
                    "a.id AS agendamento_id, " +
                    "a.data_hora AS data_hora_agendamento, " +
                    "a.valor AS agendamento_valor, " +
                    "f.id AS funcionario_id, " +
                    "f.nome AS funcionario_nome, " +
                    "pet.id AS pet_id, " +
                    "pet.nome AS pet_nome, " +
                    "s.id AS servico_id, " +
                    "s.nome AS servico_nome, " +
                    "s.preco AS servico_preco, " +
                    "s.tipo_servico AS tipo_servico, " +
                    "c.id AS cliente_id, " +
                    "c.nome AS cliente_nome " +
                    "FROM " +
                    "agendamento a " +
                    "INNER JOIN " +
                    "funcionario f ON a.funcionario_id = f.id " +
                    "INNER JOIN " +
                    "pet ON a.pet_id = pet.id " +
                    "INNER JOIN " +
                    "servico s ON a.servico_id = s.id " +
                    "INNER JOIN " +
                    "cliente c ON pet.cliente_id = c.id "
                    + "WHERE a.id = ?";

            st = conn.prepareStatement(sql);

            st.setInt(1, id);

            rs = st.executeQuery();

            if(rs.next()){

                Funcionario funcionario = instantiateFuncionario(rs);
                Cliente cliente = instantiateCliente(rs);
                Pet pet = instantiatePet(rs, cliente);
                Servicos servico = instantiateServico(rs);
                Agendamento agendamento = instantiateAgendamento(rs, funcionario, pet, servico);
                return agendamento;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar agendamento: " + e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Agendamento> findAll() {

        Statement st = null;
        ResultSet rs = null;

        try{

            String sql = "SELECT " +
                    "a.id AS agendamento_id, " +
                    "a.data_hora AS data_hora_agendamento, " +
                    "a.valor AS agendamento_valor, " +
                    "f.id AS funcionario_id, " +
                    "f.nome AS funcionario_nome, " +
                    "pet.id AS pet_id, " +
                    "pet.nome AS pet_nome, " +
                    "s.id AS servico_id, " +
                    "s.nome AS servico_nome, " +
                    "s.preco AS servico_preco, " +
                    "s.tipo_servico AS tipo_servico, " +
                    "c.id AS cliente_id, " +
                    "c.nome AS cliente_nome " +
                    "FROM " +
                    "agendamento a " +
                    "INNER JOIN " +
                    "funcionario f ON a.funcionario_id = f.id " +
                    "INNER JOIN " +
                    "pet ON a.pet_id = pet.id " +
                    "INNER JOIN " +
                    "servico s ON a.servico_id = s.id " +
                    "INNER JOIN " +
                    "cliente c ON pet.cliente_id = c.id ";


            st = conn.createStatement();
            rs = st.executeQuery(sql);

            List<Agendamento> agendamentos = new ArrayList<>();
            Map<Integer, Cliente> clienteMap = new HashMap<>();
            Map<Integer, Pet> petMap = new HashMap<>();
            Map <Integer, Funcionario> funcionarioMap = new HashMap<>();
            Map<Integer, Servicos> servicoMap = new HashMap<>();

            while(rs.next()){

                Integer clienteId = rs.getInt("cliente_id");
                Cliente cliente = clienteMap.get(clienteId);

                if (cliente == null){
                    cliente = instantiateCliente(rs);
                    clienteMap.put(clienteId, cliente);
                }

                Integer petId = rs.getInt("pet_id");
                Pet pet = petMap.get(petId);
                if (pet == null){
                    pet = instantiatePet(rs, cliente);
                    petMap.put(petId, pet);
                }

                Integer funcionarioId = rs.getInt("funcionario_id");
                Funcionario funcionario = funcionarioMap.get(funcionarioId);
                if (funcionario == null){
                    funcionario = instantiateFuncionario(rs);
                    funcionarioMap.put(funcionarioId, funcionario);
                }

                Integer servicoId = rs.getInt("servico_id");
                Servicos servico = servicoMap.get(servicoId);
                if (servico == null){
                    servico = instantiateServico(rs);
                    servicoMap.put(servicoId, servico);
                }

                Agendamento agendamento = instantiateAgendamento(rs, funcionario, pet, servico);
                agendamentos.add(agendamento);

            }
            return agendamentos;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar agendamentos: " + e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public Agendamento findByUniqueAtributs(LocalDate dataAgendamento, Double valor, Servicos servicos, Funcionario funcionarioResponsavel) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{

            String sql = "SELECT " +
                    "a.id AS agendamento_id, " +
                    "a.data_hora AS data_hora_agendamento, " +
                    "a.valor AS agendamento_valor, " +
                    "f.id AS funcionario_id, " +
                    "f.nome AS funcionario_nome, " +
                    "pet.id AS pet_id, " +
                    "pet.nome AS pet_nome, " +
                    "s.id AS servico_id, " +
                    "s.nome AS servico_nome, " +
                    "s.preco AS servico_preco, " +
                    "s.tipo_servico AS tipo_servico, " +
                    "c.id AS cliente_id, " +
                    "c.nome AS cliente_nome " +
                    "FROM " +
                    "agendamento a " +
                    "INNER JOIN " +
                    "funcionario f ON a.funcionario_id = f.id " +
                    "INNER JOIN " +
                    "pet ON a.pet_id = pet.id " +
                    "INNER JOIN " +
                    "servico s ON a.servico_id = s.id " +
                    "INNER JOIN " +
                    "cliente c ON pet.cliente_id = c.id "
                    + "WHERE data_hora = ? AND valor = ? AND servico_id = ? AND funcionario_id = ? ";

            st = conn.prepareStatement(sql);

            java.sql.Date sqlDate = java.sql.Date.valueOf(dataAgendamento);

            st.setDate(1, sqlDate);
            st.setDouble(2, valor);
            st.setInt(3, servicos.getId());
            st.setInt(4, funcionarioResponsavel.getId());

            rs = st.executeQuery();

            if(rs.next()){

                Funcionario funcionario = instantiateFuncionario(rs);
                Cliente cliente = instantiateCliente(rs);
                Pet pet = instantiatePet(rs, cliente);
                Servicos servico = instantiateServico(rs);
                Agendamento agendamento = instantiateAgendamento(rs, funcionario, pet, servico);
                return agendamento;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar agendamento: " + e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }


    private Agendamento instantiateAgendamento(ResultSet rs, Funcionario funcionario, Pet pet, Servicos servico) throws SQLException {
        Agendamento agendamento = new Agendamento();
        agendamento.setId(rs.getInt("agendamento_id"));
        agendamento.setDataAgendamento(rs.getDate("data_hora_agendamento").toLocalDate());
        agendamento.setValor(rs.getDouble("agendamento_valor"));
        agendamento.setFuncionarioResponsavel(funcionario);
        agendamento.setPet(pet);
        agendamento.setServicos(servico);

        return agendamento;
    }


    private Funcionario instantiateFuncionario(ResultSet rs)throws SQLException {

        Funcionario funcionario = new Funcionario();
        funcionario.setId(rs.getInt("funcionario_id"));
        funcionario.setNome(rs.getString("funcionario_nome"));
        return funcionario;
    }

    private Pet instantiatePet(ResultSet rs, Cliente cliente) throws SQLException {

        Pet pet = new Pet();
        pet.setId(rs.getInt("pet_id"));
        pet.setNome(rs.getString("pet_nome"));
        pet.setDono(cliente);
        return pet;
    }

    private Cliente instantiateCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();

        cliente.setId(rs.getInt("cliente_id"));
        cliente.setNome(rs.getString("cliente_nome"));
        return cliente;
    }


    private Servicos instantiateServico(ResultSet rs) throws SQLException {

        Servicos servico = null;
        String tipoServico = rs.getString("tipo_servico");

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
                servico.setId(id);

        return servico;
    }


}
