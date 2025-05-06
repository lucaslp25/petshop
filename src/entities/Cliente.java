package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cliente {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    private Integer id;

    private Endereco endereco;
    List<Pet> pets = new ArrayList<>();

    public Cliente() {

    }
    public Cliente(String nome, String cpf, String email, String telefone, Endereco endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.id = null;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    public List<Pet> getPets() {
        return pets;
    }
    public Integer GetId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("INFORMAÇÕES DO CLIENTE:\n");
        sb.append("Nome: " + nome + "\n");
        sb.append("CPF: " + cpf + "\n");
        sb.append("Email: " + email + "\n");
        sb.append("Telefone: " + telefone + "\n");
        sb.append("Endereço: " + endereco.toString());
        return sb.toString();
    }

}
