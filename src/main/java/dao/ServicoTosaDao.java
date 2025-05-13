package dao;

import entities.ServicoTosa;

import java.util.List;

public interface ServicoTosaDao {

    void insert(ServicoTosa servico);
    void update(ServicoTosa servico);
    void deleteById(Integer id);
    ServicoTosa findById(Integer id);
    List<ServicoTosa> findAll();
    ServicoTosa findByUniqueAtributs(String nome, String descricao, Double preco, boolean incluiEscovacao, boolean incluiBanhoPrevio);



    // private Boolean incluiEscovacao;
    //    private Boolean incluiBanhoPrevio;
    //
    //    private Integer id;
    //
    //    public ServicoTosa() {
    //        super();
    //        this.id = null;
    //    }
    //    public ServicoTosa(String nome, String descricao, Double preco, Duration duracao, Boolean incluiEscovacao, Boolean incluiBanhoPrevio)

}
