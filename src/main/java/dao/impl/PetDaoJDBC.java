package dao.impl;

import dao.DB;
import dao.DbExceptions;
import dao.PetDao;
import entities.Cliente;
import entities.Endereco;
import entities.Pet;
import enums.CategoriaDePorte;
import enums.TiposDeEspecies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetDaoJDBC implements PetDao {

    private Connection conn;

    public PetDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertPet(Pet pet) {

        String sql = "INSERT INTO pet " +
                "(nome, especie, raca, porte, cliente_id, idade) " +
                "VALUES " +
                "(?,?,?,?,?,?)";

        try(PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){

            st.setString(1, pet.getNome());
            st.setString(2, pet.getEspecie().name());  //pegando o nome da constante dos enums
            st.setString(3, pet.getRaca());
            st.setString(4,pet.getPorte().name()); //pegando o nome da constante dos enums
            st.setInt(5, pet.getDono().GetId());
            st.setInt(6, pet.getIdade());

            //tipo de especie e porte são enums, então tenho que lidar com ele de jeito diferentes!

            int rows = st.executeUpdate();
            if(rows > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    pet.setId(id);
                    System.out.println("Pet inserido com sucesso! ID: " + id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbExceptions("Erro inesperado. Nenhum pet encontrado!");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao inserir pet: " + e.getMessage());
        }
    }

    @Override
    public void updatePet(Pet pet) {

        String sql = "UPDATE pet " +
                "SET nome = ?, especie = ?, raca = ?, porte = ?, cliente_id = ?, idade = ? " +
                "WHERE id = ?";
        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setString(1, pet.getNome());
            st.setString(2, pet.getEspecie().name());
            st.setString(3, pet.getRaca());
            st.setString(4, pet.getPorte().name());
            st.setInt(5, pet.getDono().GetId());
            st.setInt(6, pet.getIdade());
            st.setInt(7, pet.getId());

            st.executeUpdate();

        }catch (SQLException e){
            throw new DbExceptions("Erro ao atualizar pet: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM pet WHERE id = ?";

        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setInt(1,id);
            int rows = st.executeUpdate();
            if(rows > 0){
                System.out.println("Pet deletado com sucesso! linhas afetadas: " + rows);
            }else {
                throw new DbExceptions("Erro ao deletar pet! Nenhum pet encontrado!");
            }
        }catch (SQLException e){
            throw new DbExceptions("Erro ao deletar pet: " + e.getMessage());
        }
    }

    @Override
    public Pet findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT pet.id AS pet_id, pet.nome AS pet_nome, pet.especie, pet.raca, pet.porte, pet.idade, "
                    +"cliente.id AS cliente_id, cliente.nome AS cliente_nome, cliente.cpf, cliente.email, cliente.telefone, "
                    + "endereco.id AS endereco_id, endereco.rua, endereco.numero,"
                    + "endereco.bairro, endereco.cidade, endereco.estado, endereco.cep, endereco.complemento "
                    + "FROM pet "
                    + "INNER JOIN cliente ON pet.cliente_id = cliente.id "
                    + "INNER JOIN endereco ON cliente.endereco_id = endereco.id "
                    + "WHERE pet.id = ? ";


            //esse script esta pegando um unico pet do banco de dados pelo id, ao alternar o pet.id por cliente.id eu vou estar pegando todos os pets de um determinado cliente que eu informar o ID! Dai eu teria que fazer o metodo retornar uma lista

            st = conn.prepareStatement(sql);

            st.setInt(1,id);
            rs = st.executeQuery();

            if(rs.next()){
                Endereco endereco = instantiateEndereco(rs);    //metodo para instanciar o endereço que nao recebe chaves estrangeiras!
                Cliente cliente = instantiateCliente(rs, endereco); //aqui ja recebe chave estrangeira do endereço por isso associamos!
                Pet pet = instantiatePet(rs, cliente);
                //aqui recebe chave estrangeira de cliente que esta associado tambem ao endereço, então juntamos tudo! métodos no final da página para deixar mais limpo o codigo!
                return pet;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar pet: " + e.getMessage());
        }
    }

    @Override
    public List<Pet> findByClienteId(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT pet.id AS pet_id, pet.nome AS pet_nome, pet.especie, pet.raca, pet.porte, pet.idade, "
                    +"cliente.id AS cliente_id, cliente.nome AS cliente_nome, cliente.cpf, cliente.email, cliente.telefone, "
                    + "endereco.id AS endereco_id, endereco.rua, endereco.numero,"
                    + "endereco.bairro, endereco.cidade, endereco.estado, endereco.cep, endereco.complemento "
                    + "FROM pet "
                    + "INNER JOIN cliente ON pet.cliente_id = cliente.id "
                    + "INNER JOIN endereco ON cliente.endereco_id = endereco.id "
                    + "WHERE cliente.id = ? ";


            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();

            List<Pet> pets = new ArrayList<>();
            Map<Integer, Cliente> clienteMap = new HashMap<>();
            Map<Integer, Endereco> enderecoMap = new HashMap<>();

            while(rs.next()){

                Integer enderecoId = rs.getInt("endereco.id");
                Endereco endereco = enderecoMap.get(enderecoId);

                if(endereco == null){
                    endereco = instantiateEndereco(rs);
                    enderecoMap.put(enderecoId, endereco);
                }
                Integer clienteId = rs.getInt("cliente.id");
                Cliente cliente = clienteMap.get(clienteId);
                if (cliente == null) {
                    cliente = instantiateCliente(rs, endereco);
                    clienteMap.put(clienteId, cliente);
                }
                Pet pet = instantiatePet(rs, cliente);
                pets.add(pet);
            }
            return pets;

        }catch (SQLException e){
            throw new DbExceptions("Falha ao buscar pets: " + e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Pet> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT pet.id AS pet_id, pet.nome AS pet_nome, pet.especie, pet.raca, pet.porte, pet.idade, "
                    +"cliente.id AS cliente_id, cliente.nome AS cliente_nome, cliente.cpf, cliente.email, cliente.telefone, "
                    + "endereco.id AS endereco_id, endereco.rua, endereco.numero,"
                    + "endereco.bairro, endereco.cidade, endereco.estado, endereco.cep, endereco.complemento "
                    + "FROM pet "
                    + "INNER JOIN cliente ON pet.cliente_id = cliente.id "
                    + "INNER JOIN endereco ON cliente.endereco_id = endereco.id ";

            st = conn.prepareStatement(sql);
            rs = st.executeQuery();

            List<Pet> pets = new ArrayList<>();
            Map<Integer, Cliente> clienteMap = new HashMap<>();
            Map<Integer, Endereco> enderecoMap = new HashMap<>();

            while(rs.next()){

                Integer enderecoId = rs.getInt("endereco.id");
                Endereco endereco = enderecoMap.get(enderecoId);

                if(endereco == null){
                    endereco = instantiateEndereco(rs);
                    enderecoMap.put(enderecoId, endereco);
                }
                Integer clienteId = rs.getInt("cliente.id");
                Cliente cliente = clienteMap.get(clienteId);
                if (cliente == null) {
                    cliente = instantiateCliente(rs, endereco);
                    clienteMap.put(clienteId, cliente);
                }
                Pet pet = instantiatePet(rs, cliente);
                pets.add(pet);
            }
            return pets;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar Pets: " + e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public Pet findByUniqueAtributs(String nome, String raca, Integer idade) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT pet.id AS pet_id, pet.nome AS pet_nome, pet.especie, pet.raca, pet.porte, pet.idade, "
                    +"cliente.id AS cliente_id, cliente.nome AS cliente_nome, cliente.cpf, cliente.email, cliente.telefone, "
                    + "endereco.id AS endereco_id, endereco.rua, endereco.numero,"
                    + "endereco.bairro, endereco.cidade, endereco.estado, endereco.cep, endereco.complemento "
                    + "FROM pet "
                    + "INNER JOIN cliente ON pet.cliente_id = cliente.id "
                    + "INNER JOIN endereco ON cliente.endereco_id = endereco.id "
                    + "WHERE pet.nome = ? AND pet.raca = ? AND pet.idade = ? ";


            st = conn.prepareStatement(sql);
            st.setString(1, nome);
            st.setString(2, raca);
            st.setInt(3, idade);

            rs = st.executeQuery();

            if(rs.next()){
                Endereco endereco = instantiateEndereco(rs);
                Cliente cliente = instantiateCliente(rs, endereco);
                Pet pet = instantiatePet(rs, cliente);
                return pet;
            }
            return null;
        }catch (SQLException e){
            throw new DbExceptions("Erro ao buscar pet: " + e.getMessage());
        }
    }


    private Cliente instantiateCliente(ResultSet rs, Endereco endereco) throws SQLException{
        //Propagando as exceções pois já estou tratando nos outros metodos
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("cliente_id"));
        cliente.setNome(rs.getString("cliente_nome"));
        cliente.setCpf(rs.getString("cpf"));
        cliente.setEmail(rs.getString("email"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setEndereco(endereco);
        return cliente;
    }

    private Endereco instantiateEndereco(ResultSet rs) throws SQLException {    //apenas propagar o erro
        Endereco endereco = new Endereco();
        endereco.setId(rs.getInt("endereco_id"));
        endereco.setRua(rs.getString("rua"));
        endereco.setNumero(rs.getString("numero"));
        endereco.setBairro(rs.getString("bairro"));
        endereco.setCidade(rs.getString("cidade"));
        endereco.setEstado(rs.getString("estado"));
        endereco.setCep(rs.getString("cep"));
        endereco.setComplemento(rs.getString("complemento"));

        return endereco;
    }

    private Pet instantiatePet(ResultSet rs, Cliente cliente)throws SQLException{
        Pet pet = new Pet();
        pet.setId(rs.getInt("pet_id"));
        pet.setNome(rs.getString("pet_nome"));
        pet.setEspecie(TiposDeEspecies.valueOf(rs.getString("especie")));
        //enums fica desse jeito a conversão
        pet.setRaca(rs.getString("raca"));
        pet.setPorte(CategoriaDePorte.valueOf(rs.getString("porte")));  //possivel tratamento de erros futuro aqui
        pet.setDono(cliente);
        pet.setIdade(rs.getInt("idade"));
        return pet;
    }

}
