package entities;

import enums.CategoriaDePorte;
import enums.TiposDeEspecies;

public class Pet {
    private String nome;
    private TiposDeEspecies especie;
    private String raca;
    private CategoriaDePorte porte;
    private Integer idade;

    private Cliente dono;

    private Integer id;

    public Pet(){
    }
    public Pet(String nome, TiposDeEspecies especie, String raca,CategoriaDePorte porte,Cliente dono, Integer idade) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dono = dono;
        this.porte = porte;
        this.idade = idade;
        this.id = null;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public TiposDeEspecies getEspecie() {
        return especie;
    }
    public void setEspecie(TiposDeEspecies especie) {
        this.especie = especie;
    }
    public String getRaca() {
        return raca;
    }
    public void setRaca(String raca) {
        this.raca = raca;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Cliente getDono() {
        return dono;
    }
    public void setDono(Cliente dono) {
        this.dono = dono;
    }
    public CategoriaDePorte getPorte() {
        return porte;
    }
    public void setPorte(CategoriaDePorte porte) {
        this.porte = porte;
    }
    public Integer getIdade() {
        return idade;
    }
    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "\n"+ "Nome: "+nome + "Idade: "+idade+" | Espécie: " + especie + " | Raça: " + raca +  "\nResponsável: " + dono.getNome();
    }

}
