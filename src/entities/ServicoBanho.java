package entities;

import java.time.Duration;

public class ServicoBanho extends Servicos {

    private Integer id;

    private Boolean comHidratacao;

    public ServicoBanho() {
        super();
        this.id = null;
    }
    public ServicoBanho(String nome, String descricao,Double preco, Duration duracao, Boolean comHidratacao) {
        super(nome, descricao, preco, duracao);
        this.id = null;
        this.comHidratacao = comHidratacao;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Boolean getComHidratacao() {
        return comHidratacao;
    }
    public void setComHidratacao(Boolean comHidratacao) {
        this.comHidratacao = comHidratacao;
    }

    @Override
    public String getTipo() {
        return "BANHO";
    }

    @Override
    public String toString() {
        return super.toString() + "Tipo de serviço: Banho | Hidratação: " +comHidratacao;
    }
}
