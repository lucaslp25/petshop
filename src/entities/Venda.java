package entities;

import java.time.LocalDate;
import java.util.List;

public class Venda {
    private Integer id;
    private LocalDate dataVenda;
    private Double valorTotal;
    private Cliente cliente;

    private MetodoDePagamento meioDepagamento;
    private List<ItemVenda> itensVendidos;

    public Venda() {
    }

    public Venda (LocalDate dataVenda, Double valorTotal, Cliente cliente, MetodoDePagamento meioDepagamento) {
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.meioDepagamento = meioDepagamento;
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
    public MetodoDePagamento getMeioDepagamento(){
        return meioDepagamento;
    }
    public void setMeioDePagamento(MetodoDePagamento metodoDePagamento){
        this.meioDepagamento = meioDepagamento;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Venda ID: " + getId() + "\n");
        sb.append("Data Venda: " + getDataVenda() + "\n");
        sb.append("Cliente: " + getCliente().toString() + "\n");
        if (meioDepagamento != null){
            sb.append("Meio Depagamento: " + getMeioDepagamento().toString() + "\n");
        }
        return sb.toString();
    }

}
