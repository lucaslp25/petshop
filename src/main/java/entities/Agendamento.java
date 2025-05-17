package entities;

import java.time.LocalDate;

public class Agendamento {

    private Integer id;
    private LocalDate dataAgendamento;
    private Double valor;
    private Servicos servicos;

    //polimorfismo vai pegar todas as subclasses de serviços para o agendamento

    private Pet pet;

    private Funcionario funcionarioResponsavel;

    public Agendamento() {
    }
    public Agendamento(LocalDate dataAgendamento, Servicos servicos, Pet pet, Funcionario funcionarioResponsavel, Double valor) {
        this.id = null;
        this.dataAgendamento = dataAgendamento;
        this.servicos = servicos;
        this.pet = pet;
        this.funcionarioResponsavel = funcionarioResponsavel;
        this.valor = valor;
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
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String toRelatorioString (){
        StringBuilder sb = new StringBuilder();
        sb.append("ID DO AGENDAMENTO: " + getId() + "\n");
        sb.append("DATA DO AGENDAMENTO: " + getDataAgendamento() + "\n");
        sb.append("NOME DO SERVIÇO: " + getServicos().getNome() + "\n");
        sb.append("PREÇO DO SERVIÇO: " + String.format("%.2f",getServicos().getPreco()) + "\n");
        sb.append("TIPO DE SERVIÇO: " + getServicos().getTipo() + "\n");
        sb.append("PET: " + getPet().getNome() + " | RESPONSÁVEL: " +getPet().getDono().getNome() + "\n" );
        sb.append("FUNCIONARIO RESPONSAVEL PELO AGENDAMENTO: " + getFuncionarioResponsavel().getNome() + "\n");
        return sb.toString();
    }

    @Override
    public String toString(){

        return "DATA: " + dataAgendamento + "\nSERVICOS: " + servicos + "\nPET: " + pet + "\nFUNCIONARIO: " + funcionarioResponsavel.getNome() + " - " + funcionarioResponsavel.getCargoFuncionario();
    }
}
