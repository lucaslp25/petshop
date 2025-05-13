package petshop.tests.dao.impl;

import dao.*;
import entities.*;
import enums.CategoriaDePorte;
import enums.TiposDeCargoFuncionario;
import enums.TiposDeEspecies;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AgendamentoDaoJDBCTest {

    private Connection conn;
    private Agendamento agendamentoTeste;


    private Connection getConnection() throws SQLException {
        Properties info = new Properties();
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("db.test.properties")){
            if (input == null) {
                System.err.println("Não foi possível carregar o arquivo db.test.properties");
                return null;
            }
            info.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String url = info.getProperty("dburl");
        String user = info.getProperty("user");
        String password = info.getProperty("password");

        return DriverManager.getConnection(url, user, password);
    }

    @BeforeEach
    void setUp() throws SQLException {

        conn = getConnection();

        AgendamentoDao agendamentoDao = DaoFactory.createAgendamentoDao();

        clearTables(); //metodo para limpar as tabelas do database

        insertTestData(); // metodo para inserir o as entidades necessarias no teste
    }

    private void insertTestData() {

        Endereco enderecoTeste = new Endereco("Rua Teste", "123", "Bairro Teste", "Cidade Teste", "Estado Teste", "12345-678", "CASA");
        EnderecoDao enderecoDao = DaoFactory.createEnderecoDao();
        enderecoDao.insertEndereco(enderecoTeste);
        enderecoTeste = enderecoDao.findByUniqueAtributs(enderecoTeste.getRua(), enderecoTeste.getNumero(), enderecoTeste.getBairro(), enderecoTeste.getCidade(), enderecoTeste.getEstado(), enderecoTeste.getCep(), enderecoTeste.getComplemento());

        Cliente clienteTeste = new Cliente("Nome Teste", "123456789", "teste@email.com", "1234-5678", enderecoTeste);
        ClienteDao clienteDao = DaoFactory.createClienteDao();
        clienteDao.insertCliente(clienteTeste);
        clienteTeste = clienteDao.findByCPF(clienteTeste.getCpf());

        Pet petTeste = new Pet("Nome Pet Teste", TiposDeEspecies.CACHORRO, "Raça de teste", CategoriaDePorte.GRANDE ,clienteTeste, 10);
        PetDao petDao = DaoFactory.createPetDao();
        petDao.insertPet(petTeste);
        petTeste = petDao.findByUniqueAtributs(petTeste.getNome(), petTeste.getRaca() ,petTeste.getIdade());

        petTeste = petDao.findByUniqueAtributs(petTeste.getNome(), petTeste.getRaca() ,petTeste.getIdade());
        System.out.println("Pet encontrado após busca: " + petTeste);
        if (petTeste != null) {
            System.out.println("ID do Pet: " + petTeste.getId());
            if (petTeste.getDono() != null) {
                System.out.println("ID do Dono do Pet: " + petTeste.getDono().getId());
            } else {
                System.out.println("O Dono do Pet é NULO!");
            }
        } else {
            System.out.println("Pet NÃO encontrado após a busca!");
        }

        Funcionario funcionarioTeste = new Funcionario( "Nome Funcionario", TiposDeCargoFuncionario.VETERINARIO, enderecoTeste, 4000.00);

        FuncionarioDao funcionarioDao = DaoFactory.createFuncionarioDao();

        funcionarioDao.insertFuncionario(funcionarioTeste);
        funcionarioTeste = funcionarioDao.findByNomeECargo(funcionarioTeste.getNome(), funcionarioTeste.getCargoFuncionario().name());


        ServicoBanho servicoTeste = new ServicoBanho("Banho Teste", "Descricao", 50.0, Duration.ofMinutes(120), true);
        ServicoBanhoDao servicoBanhoDao = DaoFactory.createServicoBanhoDao();
        servicoBanhoDao.insert(servicoTeste);
        servicoTeste = servicoBanhoDao.findByUniqueAtributs(servicoTeste.getNome(), servicoTeste.getDescricao(), servicoTeste.getPreco(), servicoTeste.getComHidratacao());


        LocalDate dataAgendamentoTeste = LocalDate.now().plusDays(2);
        Agendamento agendamento = new Agendamento(dataAgendamentoTeste, servicoTeste, petTeste, funcionarioTeste, 50.0);

        AgendamentoDao agendamentoDao = DaoFactory.createAgendamentoDao();
        agendamentoDao.insert(agendamento);
        agendamentoTeste = agendamentoDao.findByUniqueAtributs(agendamento.getDataAgendamento(), agendamento.getValor(), agendamento.getServicos(), agendamento.getFuncionarioResponsavel());
    }

    private void clearTables() {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("DELETE FROM agendamento");
            st.executeUpdate("DELETE FROM servico_banho");
            st.executeUpdate("DELETE FROM funcionario");
            st.executeUpdate("DELETE FROM pet");
            st.executeUpdate("DELETE FROM cliente");
            st.executeUpdate("DELETE FROM endereco");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @AfterAll
    static void tearDownAll() throws SQLException {
        Connection conn = null;
        try {
            conn = getConnectionStatic();
            if (conn != null) {
                try (Statement st = conn.createStatement()) {
                    st.executeUpdate("DELETE FROM agendamento");
                    st.executeUpdate("DELETE FROM servico_banho");
                    st.executeUpdate("DELETE FROM funcionario");
                    st.executeUpdate("DELETE FROM pet");
                    st.executeUpdate("DELETE FROM cliente");
                    st.executeUpdate("DELETE FROM endereco");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    static Connection getConnectionStatic() throws SQLException {
        Properties info = new Properties();
        try (InputStream input = AgendamentoDaoJDBCTest.class.getClassLoader().getResourceAsStream("db.test.properties")) {
            if (input == null) {
                System.err.println("Não foi possível carregar o arquivo db.test.properties");
                return null;
            }
            info.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String url = info.getProperty("dburl");
        String user = info.getProperty("user");
        String password = info.getProperty("password");

        return DriverManager.getConnection(url, user, password);
    }

    //Testar o que eu achei mais necessário para esse meotodo, ver se encontrava o id, e ver se o metodo dos atributos unicos funcionaram também

    @Test
    void testInsertAndFindById() throws SQLException {

        AgendamentoDao agendamentoDao = DaoFactory.createAgendamentoDao();

        Agendamento agendamentoEncontrado = agendamentoDao.findById(agendamentoTeste.getId());

        assertNotNull(agendamentoEncontrado);
        assertEquals(agendamentoTeste.getId(), agendamentoEncontrado.getId());
        assertEquals(agendamentoTeste.getDataAgendamento(), agendamentoEncontrado.getDataAgendamento());
        assertEquals(agendamentoTeste.getValor(), agendamentoEncontrado.getValor(), 0.001);
        assertEquals(agendamentoTeste.getPet().getId(), agendamentoEncontrado.getPet().getId());
        assertEquals(agendamentoTeste.getFuncionarioResponsavel().getId(), agendamentoEncontrado.getFuncionarioResponsavel().getId());
        assertEquals(agendamentoTeste.getServicos().getId(), agendamentoEncontrado.getServicos().getId());
    }

    @Test
    void testFindAgendamentoByUniqueAtributs() throws SQLException {
        AgendamentoDao agendamentoDao = DaoFactory.createAgendamentoDao();

        Agendamento agendamentoEncontrado2 = agendamentoDao.findByUniqueAtributs(agendamentoTeste.getDataAgendamento(), agendamentoTeste.getValor(), agendamentoTeste.getServicos(), agendamentoTeste.getFuncionarioResponsavel());


        assertNotNull(agendamentoEncontrado2);
        assertEquals(agendamentoTeste.getId(), agendamentoEncontrado2.getId());
        assertEquals(agendamentoTeste.getDataAgendamento(), agendamentoEncontrado2.getDataAgendamento());
        assertEquals(agendamentoTeste.getValor(), agendamentoEncontrado2.getValor(), 0.001);

        assertEquals(agendamentoTeste.getServicos().getId(), agendamentoEncontrado2.getServicos().getId());
        assertEquals(agendamentoTeste.getServicos().getNome(), agendamentoEncontrado2.getServicos().getNome());

        assertEquals(agendamentoTeste.getFuncionarioResponsavel().getId(), agendamentoEncontrado2.getFuncionarioResponsavel().getId());
        assertEquals(agendamentoTeste.getFuncionarioResponsavel().getNome(), agendamentoEncontrado2.getFuncionarioResponsavel().getNome());
    }

}
