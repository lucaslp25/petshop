package entities;

import enums.TiposDeCargoFuncionario;

import java.util.ArrayList;
import java.util.List;

public class Funcionario {
    private Integer id;
    private String nome;
    private TiposDeCargoFuncionario cargoFuncionario;

    private List<Agendamento> agendamentos = new ArrayList<>();
    //agendamentos de um funcionario

    public Funcionario() {
    }
    public Funcionario( String nome, TiposDeCargoFuncionario cargoFuncionario) {
        this.id = null;
        this.nome = nome;
        this.cargoFuncionario = cargoFuncionario;
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
        return sb.toString();
    }

}
