package entities;

import enums.CategoriaDeProdutos;

public class Produto {
    private String nome;
    private String descricao;
    private Double precoDeCusto;
    private Double precoDeVenda;
    private Integer quantidadeEstoque;
    private String fornecedor;

    private Integer id;
    private CategoriaDeProdutos categoria;

    public Produto(){
    }
    public Produto(String nome, String descricao, Double precoDeCusto, Double precoDeVenda, Integer quantidadeEstoque, String fornecedor, CategoriaDeProdutos categoria){
        this.nome = nome;
        this.descricao = descricao;
        this.precoDeCusto = precoDeCusto;
        this.precoDeVenda = precoDeVenda;
        this.quantidadeEstoque = quantidadeEstoque;
        this.fornecedor = fornecedor;
        this.categoria = categoria;
        this.id = null;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Double getPrecoDeCusto() {
        return precoDeCusto;
    }
    public void setPrecoDeCusto(Double precoDeCusto) {
        this.precoDeCusto = precoDeCusto;
    }
    public Double getPrecoDeVenda() {
        return precoDeVenda;
    }
    public void setPrecoDeVenda(Double precoDeVenda) {
        this.precoDeVenda = precoDeVenda;
    }
    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
    public String getFornecedor() {
        return fornecedor;
    }
    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public CategoriaDeProdutos getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaDeProdutos categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: " + nome + "\n");
        sb.append("Descricao: " + descricao + "\n");
        sb.append("Preco de Custo: " + precoDeCusto + "\n");
        sb.append("Preco de Venda: " + precoDeVenda + "\n");
        sb.append("Quantidade Estoque: " + quantidadeEstoque + "\n");
        sb.append("Fornecedor: " + fornecedor + "\n");
        if (categoria != null) {
            sb.append("Categoria: " + categoria.toString() + "\n");
        }

        return sb.toString();
    }


}
