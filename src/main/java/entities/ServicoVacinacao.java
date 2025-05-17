package entities;

import enums.TiposDeVacinacao;

import java.time.Duration;

public class ServicoVacinacao extends Servicos {
    private Integer id;

    private TiposDeVacinacao tipoDeVacinacao;

    public ServicoVacinacao() {
        super();
        this.id = null;
    }
    public ServicoVacinacao(String nome, String descricao, Double preco, Duration duracao, TiposDeVacinacao tipoDeVacinacao) {
        super(nome,descricao,preco,duracao);
        this.tipoDeVacinacao = tipoDeVacinacao;
        this.id = null;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public TiposDeVacinacao getTipoDeVacinacao() {
        return tipoDeVacinacao;
    }
    public void setTipoDeVacinacao(TiposDeVacinacao tipo) {
        this.tipoDeVacinacao = tipo;
    }

    @Override
    public String getTipo(){
        return "VACINAÇÃO";
    }


    @Override
    public String toString() {
        return super.toString() + "Tipo de serviço: Vacinação | Tipo de vacina: " +tipoDeVacinacao.toString();
    }
}
