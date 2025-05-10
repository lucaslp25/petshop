package entities;

import enums.TipoDePagamento;

public class MetodoDePagamento {
    private Integer id;
    private TipoDePagamento tipoDePagamento;

    public MetodoDePagamento(){
        this.id = null;
    }
    public MetodoDePagamento(TipoDePagamento tipoDePagamento){
        this.tipoDePagamento = tipoDePagamento;
        this.id = null;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public TipoDePagamento getTipoDePagamento() {
        return tipoDePagamento;
    }
    public void setTipoDePagamento(TipoDePagamento tipoDePagamento) {
        this.tipoDePagamento = tipoDePagamento;
    }

    @Override
    public String toString(){
        return tipoDePagamento.toString();
    }

}
