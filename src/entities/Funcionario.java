package entities;

import enums.TiposDeCargoFuncionario;

import java.util.ArrayList;
import java.util.List;

public class Funcionario {
    private Integer id;
    private String nome;
    private  Double salario;

    private Endereco endereco;
    private TiposDeCargoFuncionario cargoFuncionario;

    private List<Agendamento> agendamentos = new ArrayList<>();
    //agendamentos de um funcionario

    //Se um funcionario tem varios agendamentos, e cada agendamento tem um fucionario, cabe mais eu colocar na tabela de agendamentos o funcionario como uma chave estrangeira, do que colocar vários agendamentos como chave estrangeira da tabela do funcionario!

    public Funcionario() {
    }
    public Funcionario( String nome, TiposDeCargoFuncionario cargoFuncionario, Endereco endereco, Double salario) {
        this.id = null;
        this.nome = nome;
        this.cargoFuncionario = cargoFuncionario;
        this.endereco = endereco;
        this.salario = salario;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public TiposDeCargoFuncionario getCargoFuncionario() {
        return cargoFuncionario;
    }
    public void setCargoFuncionario(TiposDeCargoFuncionario cargoFuncionario) {
        this.cargoFuncionario = cargoFuncionario;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    public Double getSalario() {
        return salario;
    }
    public void setSalario(Double salario) {
        this.salario = salario;
    }
    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: " + nome + "\n");
        sb.append("Cargo: " + cargoFuncionario.toString() + "\n");
        if (agendamentos.isEmpty()) {
            sb.append("  Nenhum agendamento atribuído.\n");
        } else {
            for (Agendamento agendamento : agendamentos) {
                sb.append("  - ID: ").append(agendamento.getId()).append(", Data: ").append(agendamento.getDataAgendamento()).append(", Serviço: ").append(agendamento.getServicos().getNome()).append("\n");
            }

        }
        sb.append("Salario: " + String.format("%.2f",salario) + "\n");
        sb.append("Endereco: " + endereco.toString() + "\n");

        return sb.toString();
    }
}
