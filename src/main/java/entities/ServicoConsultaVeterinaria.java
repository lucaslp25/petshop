package entities;

import enums.TiposDeConsultaVeterinaria;

import java.time.Duration;

public class ServicoConsultaVeterinaria extends Servicos{

    private Integer id;
    private TiposDeConsultaVeterinaria tipoDeConsulta;

    public ServicoConsultaVeterinaria() {
        super();
        this.id = null;
    }

    public ServicoConsultaVeterinaria(String nome, String descricao, Double preco, Duration duracao, TiposDeConsultaVeterinaria tipoDeConsulta) {
        super(nome,descricao,preco,duracao);
        this.tipoDeConsulta = tipoDeConsulta;
        this.id = null;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public TiposDeConsultaVeterinaria getTipoDeConsulta() {
        return tipoDeConsulta;
    }
    public void setTipoConsulta(TiposDeConsultaVeterinaria tipoConsulta) {
        this.tipoDeConsulta = tipoConsulta;
    }

    @Override
    public String getTipo(){
        return "CONSULTA VETERINÁRIA";
    }

    @Override
    public String toString() {
        return super.toString() + "Tipo de serviço: Consulta veterinária";
    }
}
