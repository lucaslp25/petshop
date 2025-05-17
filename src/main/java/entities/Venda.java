package entities;

import enums.TipoDePagamento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Venda {
    private Integer id;
    private LocalDate dataVenda;
    private Double valorTotal;
    private Cliente cliente;
    private Integer metodoPagamentoId;
    private TipoDePagamento tipoPagamento;

    private MetodoDePagamento meioDePagamento;
    private List<ItemVenda> itensVendidos;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
    public void setMeioDePagamento(MetodoDePagamento meioDePagamento) {
        this.meioDePagamento = meioDePagamento;
    }
    public MetodoDePagamento getMeioDePagamento() {
        return meioDePagamento;
    }

    public String toRelatorioString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID DA VENDA: " + id + "\n");
        sb.append("Data: " + dataVenda.format(formatter) + "\n");
        sb.append("Valor: " + String.format("%.2f",valorTotal) + "\n");
        sb.append("Cliente: " + cliente.getNome() + "| CPF: " + cliente.getCpf() + "\n");
        sb.append("Método de pagamento ID: " + metodoPagamentoId + "\n");

        return sb.toString();
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
