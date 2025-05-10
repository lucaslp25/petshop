package entities;

public class ItemVenda {
    private Integer id;
    private Double precoUnitario;
    private Integer quantidade;

    private Venda venda;
    private Produto produto;
    private Servicos servicos;

    public ItemVenda() {
    }

    //sobrecarga feita para suporta a venda com os dois, e as outras com cada tipo de venda!

    public ItemVenda(Double precoUnitario, Integer quantidade, Produto produto, Servicos servicos, Venda venda) {
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.produto = produto;
        this.servicos = servicos;
        this.id = null;
        this.venda = venda;
    }
    public ItemVenda(Double precoUnitario, Integer quantidade, Servicos servicos,Venda venda) {
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.produto = null;    //opção sem produto
        this.servicos = servicos;
        this.id = null;
        this.venda = venda;
    }
    public ItemVenda(Double precoUnitario, Integer quantidade, Produto produto, Venda venda) {
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.produto = produto;
        this.servicos = null;   //opçõo sem serviço
        this.id = null;
        this.venda = venda;
    }

    public Double getPrecoUnitario(){
        return precoUnitario;
    }
    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public Servicos getServicos() {
        return servicos;
    }
    public void setServicos(Servicos servicos) {
        this.servicos = servicos;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Venda getVenda() {
        return venda;
    }
    public void setVenda(Venda venda){
        this.venda = venda;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: " + id + "\n");
        sb.append("Preço unitário: R$" + String.format("%.2f",precoUnitario) + "\n");
        sb.append("Quantidade: " + quantidade + "\n");
        if (produto != null) {
            sb.append("Produto: " + produto.toString() + "\n");
        }
        if (servicos != null) {
            sb.append("Servicos: " + servicos.toString() + "\n");
        }
        sb.append("Venda: " + venda.toString() + "\n");

        return sb.toString();
    }
}
