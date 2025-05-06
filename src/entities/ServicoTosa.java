package entities;

import java.time.Duration;

public class ServicoTosa extends Servicos {

    private Boolean incluiEscovacao;
    private Boolean incluiBanhoPrevio;

    private Integer id;

    public ServicoTosa() {
        super();
        this.id = null;
    }
    public ServicoTosa(String nome, String descricao, Double preco, Duration duracao, Boolean incluiEscovacao, Boolean incluiBanhoPrevio) {
        super(nome,descricao,preco,duracao);
        this.incluiEscovacao = incluiEscovacao;
        this.incluiBanhoPrevio = incluiBanhoPrevio;
        this.id = null;
    }

    public Boolean isIncluiEscovacao() {
        return incluiEscovacao;
    }
    public void setIncluiEscovacao(Boolean incluiEscovacao) {
        this.incluiEscovacao = incluiEscovacao;
    }
    public Boolean isIncluiBanhoPrevio() {
        return incluiBanhoPrevio;
    }
    public void setIncluiBanhoPrevio(Boolean incluiBanhoPrevio) {
        this.incluiBanhoPrevio = incluiBanhoPrevio;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    @Override
    public String getTipo(){
        return "TOSA";
    }

    @Override
    public String toString() {
        return super.toString() + "Tipo de serviço: Tosa | Escovação inclusa: " +incluiEscovacao + ", Banho prévio incluso: " + incluiBanhoPrevio;
    }

}
