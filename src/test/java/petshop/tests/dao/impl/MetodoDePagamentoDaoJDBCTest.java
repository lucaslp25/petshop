package petshop.tests.dao.impl;

import dao.MetodoDePagamentoDao;
import dao.impl.MetodoDePagamentoDaoJDBC;
import entities.MetodoDePagamento;
import enums.TipoDePagamento;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class MetodoDePagamentoDaoJDBCTest {

    private MetodoDePagamentoDao metodoDePagamentoDao;
    private Connection connection;

    // metodo para obter uma conexão com o banco de dados para os testes!

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
        // 1. Obter uma conexão com o banco de dados
        connection = getConnection();

        // 2. Criar uma instância do seu DAO
        metodoDePagamentoDao = new MetodoDePagamentoDaoJDBC(connection);

        // 3. Limpar a tabela (opcional, mas recomendado para testes isolados)
        clearMetodoDePagamentoTable();

        // 4. Inserir os dados de teste necessários para os seus testes
        insertTestData();
    }

    // metodo para limpar a tabela metodo_pagamento
    private void clearMetodoDePagamentoTable() throws SQLException {
        String sql = "DELETE FROM metodo_pagamento";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.executeUpdate();
        }
    }

    // metodo para inserir os dados de teste
    private void insertTestData() throws SQLException {

        insertMetodoDePagamento(TipoDePagamento.DINHEIRO);

        insertMetodoDePagamento(TipoDePagamento.CARTAO_DE_CREDITO);
        insertMetodoDePagamento(TipoDePagamento.CARTAO_DE_DEBITO);
        insertMetodoDePagamento(TipoDePagamento.PIX);
        insertMetodoDePagamento(TipoDePagamento.BOLETO);
    }

    // metodo auxiliar para o insertTestData
    private void insertMetodoDePagamento(TipoDePagamento tipo) throws SQLException {
        String sql = "INSERT INTO metodo_pagamento (tipo) VALUES (?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, tipo.name());
            st.executeUpdate();
        }
    }

    // metodo para limpar o banco de dados após cada teste
    @AfterEach
    void tearDown() throws SQLException {
        if(connection != null) {
            connection.createStatement().executeUpdate("DELETE FROM metodo_pagamento");
        }
    }

    //testes abaixo

    @Test
    void testFindByTipo_ExistingType() throws SQLException {
        // Arrange - preparar os dados de teste e o ambiente
        connection = getConnection();
        metodoDePagamentoDao = new MetodoDePagamentoDaoJDBC(connection);
        TipoDePagamento tipoEsperado = TipoDePagamento.DINHEIRO;

        // Act - executar a ação que tu quer testar
        MetodoDePagamento metodoEncontrado = metodoDePagamentoDao.findByTipo(tipoEsperado);

        // Assert - aqui verifica qual é o resultado do teste
        assertNotNull(metodoEncontrado);
        assertEquals(tipoEsperado, metodoEncontrado.getTipoDePagamento());
    }

    @Test
    void testFindByTipo_NonExistingType() throws SQLException {

        connection = getConnection();
        metodoDePagamentoDao = new MetodoDePagamentoDaoJDBC(connection);
        TipoDePagamento tipoNaoExistente = TipoDePagamento.FIADO;


        MetodoDePagamento metodoEncontrado = metodoDePagamentoDao.findByTipo(tipoNaoExistente);

        assertNull(metodoEncontrado);
    }

    //os dois testes passaram!
}