package petshop.tests.dao.impl;

import dao.*;
import entities.MetodoDePagamento;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import entities.Venda;
import entities.Cliente;
import entities.Endereco;
import enums.TipoDePagamento;
import dao.impl.VendaDaoJDBC;
import dao.impl.MetodoDePagamentoDaoJDBC;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class VendaDaoJDBCTest {

    private VendaDao vendaDao;
    private MetodoDePagamentoDao metodoDePagamentoDao;
    private Connection connection;
    private Cliente clienteTeste;
    private Integer metodoPagamentoIdTeste;

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

        //aqui eu tive que criar um novo arquivo de properties e colocar nesse diretorio de testes para a classe conseguir ler, (esta no gitIgnore para não expor os dados)
    }

    @BeforeEach
    void setUp() throws SQLException {
        connection = getConnection();
        metodoDePagamentoDao = new MetodoDePagamentoDaoJDBC(connection);
        vendaDao = new VendaDaoJDBC(connection, metodoDePagamentoDao);

        clearTables();

        insertTestData();
    }

    private void clearTables() throws SQLException {

        try (Statement st = connection.createStatement()) {
            st.executeUpdate("DELETE FROM agendamento");
            st.executeUpdate("DELETE FROM servico_banho");
            st.executeUpdate("DELETE FROM funcionario");
            st.executeUpdate("DELETE FROM pet");
            st.executeUpdate("DELETE FROM cliente");
            st.executeUpdate("DELETE FROM endereco");
            st.executeUpdate("DELETE FROM metodo_pagamento");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDownAll () throws SQLException{

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
                    st.executeUpdate("DELETE FROM metodo_pagamento");
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

    private void insertTestData() throws SQLException {

        Endereco enderecoTeste = new Endereco("Rua Teste", "123", "Bairro Teste", "Cidade Teste", "Estado Teste", "12345-678", "CASA");
        EnderecoDao enderecoDao = DaoFactory.createEnderecoDao();
        enderecoDao.insertEndereco(enderecoTeste);
        enderecoTeste = enderecoDao.findByUniqueAtributs(enderecoTeste.getRua(), enderecoTeste.getNumero(), enderecoTeste.getBairro(), enderecoTeste.getCidade(), enderecoTeste.getEstado(), enderecoTeste.getCep(), enderecoTeste.getComplemento());
        //fazendo o esquema de instanciar e ja inserir no banco de dados e ja pegar o id pelos atributos unicos! Forma de testar se as outras classes ja deram certo!

        clienteTeste = new Cliente("Nome Teste", "123.456.789-00", "teste@email.com", "1234-5678", enderecoTeste);
        ClienteDao clienteDao = DaoFactory.createClienteDao();
        clienteDao.insertCliente(clienteTeste);
        clienteTeste = clienteDao.findByCPF(clienteTeste.getCpf());

        MetodoDePagamento metodoDePagamento = new MetodoDePagamento(TipoDePagamento.DINHEIRO);
        metodoDePagamentoDao.insert(metodoDePagamento);
        Integer metodoDePagamentoID = metodoDePagamento.getId();
        metodoPagamentoIdTeste = metodoDePagamentoID;

        LocalDate dataVendaTeste = LocalDate.now();
        Double valorTotalTeste = 99.99;
        Venda vendaTeste = new Venda(dataVendaTeste, valorTotalTeste, clienteTeste, metodoPagamentoIdTeste);
        vendaTeste.setTipoPagamento(TipoDePagamento.DINHEIRO);

        vendaTeste.setMeioDePagamento(metodoDePagamentoDao.findById(metodoPagamentoIdTeste));
        vendaDao.insert(vendaTeste);
    }

    @Test
    void testFindByUniqueAtributs_ExistingVenda() throws SQLException {
        LocalDate dataVendaBusca = LocalDate.now();
        Double valorTotalBusca = 99.99;

        Venda vendaEncontrada = vendaDao.findByUniqueAtributs(dataVendaBusca, valorTotalBusca, clienteTeste, metodoPagamentoIdTeste);

        assertNotNull(vendaEncontrada);
        assertEquals(dataVendaBusca, vendaEncontrada.getDataVenda());
        assertEquals(valorTotalBusca, vendaEncontrada.getValorTotal(), 0.001);
        // delta para comparação de doubles

        assertEquals(clienteTeste.getId(), vendaEncontrada.getCliente().getId());
        assertEquals(metodoPagamentoIdTeste, vendaEncontrada.getMeioDePagamento().getId());
    }

    @Test
    void testFindByUniqueAtributs_NonExistingVenda() throws SQLException {
        LocalDate dataVendaBusca = LocalDate.now().plusDays(1);
        Double valorTotalBusca = 199.99;

        Venda vendaEncontrada = vendaDao.findByUniqueAtributs(dataVendaBusca, valorTotalBusca, clienteTeste, metodoPagamentoIdTeste);

        assertNull(vendaEncontrada);
    }
}