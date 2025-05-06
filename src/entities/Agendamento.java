package entities;

import java.time.LocalDate;

public class Agendamento {

    private Integer id;
    private LocalDate dataAgendamento;
    private Servicos servicos;
    //polimorfismo vai pegar todas as subclasses de serviços para o agendamento

    private Pet pet;

    private Funcionario funcionarioResponsavel;

    public Agendamento() {
    }
    public Agendamento(LocalDate dataAgendamento, Servicos servicos, Pet pet, Funcionario funcionarioResponsavel) {
        this.id = null;
        this.dataAgendamento = dataAgendamento;
        this.servicos = servicos;
        this.pet = pet;
        this.funcionarioResponsavel = funcionarioResponsavel;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public LocalDate getDataAgendamento() {
        return dataAgendamento;
    }
    public void setDataAgendamento(LocalDate dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }
    public Servicos getServicos() {
        return servicos;
    }
    public void setServicos(Servicos servicos) {
        this.servicos = servicos;
    }
    public Pet getPet() {
        return pet;
    }
    public void setPet(Pet pet) {
        this.pet = pet;
    }
    public Funcionario getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }
    public void setFuncionarioResponsavel(Funcionario funcionarioResponsavel) {
        this.funcionarioResponsavel = funcionarioResponsavel;
    }


    @Override
    public String toString(){

        return "DATA: " + dataAgendamento + "\nSERVICOS: " + servicos + "\nPET: " + pet + "\nFUNCIONARIO: " + funcionarioResponsavel.getNome() + " - " + funcionarioResponsavel.getCargoFuncionario();

    }

}
