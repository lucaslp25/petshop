package entities;

public class ItemVenda {
    private Integer id;
    private Double precoUnitario;
    private Double quantidade;

    private Produto produto;
    private Servicos servicos;

    public ItemVenda() {
    }

    //sobrecarga feita para suporta a venda com os dois, e as outras com cada tipo de venda!

    public ItemVenda(Double precoUnitario, Double quantidade, Produto produto, Servicos servicos) {
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.produto = produto;
        this.servicos = servicos;
        this.id = null;
    }
    public ItemVenda(Double precoUnitario, Double quantidade, Servicos servicos) {
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.produto = null;    //opção sem produto
        this.servicos = servicos;
        this.id = null;
    }
    public ItemVenda(Double precoUnitario, Double quantidade, Produto produto) {
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.produto = produto;
        this.servicos = null;   //opçõo sem serviço
        this.id = null;
    }

    public String getPrecoUnitario() {
        return String.valueOf(precoUnitario);
    }
    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
    public String getQuantidade() {
        return String.valueOf(quantidade);
    }
    public void setQuantidade(Double quantidade) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(", Preço Unitário: R$").append(precoUnitario).append(", Quantidade: ").append(quantidade);
        if (produto != null) {
            sb.append(", Produto: ").append(produto.getNome());
        }
        if (servicos != null) {
            sb.append(", Serviço: ").append(servicos.getNome());
        }
        return sb.toString();
    }

}
