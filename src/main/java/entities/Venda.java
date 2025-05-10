package entities;

import enums.TipoDePagamento;

import java.time.LocalDate;
import java.util.List;

public class Venda {
    private Integer id;
    private LocalDate dataVenda;
    private Double valorTotal;
    private Cliente cliente;
    private Integer metodoPagamentoId;
    private TipoDePagamento tipoPagamento;
  //  private MetodoDePagamento meioDepagamento; mudança de lógica...
    private List<ItemVenda> itensVendidos;

    public Venda() {
    }

    public Venda (LocalDate dataVenda, Double valorTotal, Cliente cliente, Integer metodoPagamentoId) {
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.metodoPagamentoId = metodoPagamentoId;
        this.id = null;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public LocalDate getDataVenda() {
        return dataVenda;
    }
    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }
    public Double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Integer getMetodoPagamentoId(){
        return metodoPagamentoId;
    }
    public void setMetodoPagamentoId(Integer metodoPagamentoId){
        this.metodoPagamentoId = metodoPagamentoId;
    }
    public TipoDePagamento getTipoPagamento() {
        return tipoPagamento;
    }
    public void setTipoPagamento(TipoDePagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Venda ID: " + getId() + "\n");
        sb.append("Data Venda: " + getDataVenda() + "\n");
        sb.append("Cliente: " + getCliente().toString() + "\n");

        return sb.toString();
    }

}
