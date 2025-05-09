package entities;

import java.time.Duration;

public abstract class Servicos {
    private String nome;
    private String descricao;
    private Double preco;
    private Duration duracao;
    private Integer id;

    public Servicos() {
    }
    public Servicos(String nome, String descricao, Double preco, Duration duracao) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.duracao = duracao;
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
    public Double getPreco() {
        return preco;
    }
    public void setPreco(Double preco) {
        this.preco = preco;
    }
    public Duration getDuracao() {
        return duracao;
    }
    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo(){
        return null;
    }

    @Override
    public String toString(){
        return nome + " | " + descricao+ " | R$" + preco + " - "+duracao + "\n";
    }
}
