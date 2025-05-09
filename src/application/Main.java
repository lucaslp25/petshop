package application;

import dao.*;
import entities.*;
import enums.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class Main{
    public static void main(String[] args) {



        //primeira coisa para criar um cliente precisa de um endereço! Para ter a referencia no DB

        //Notas sobre metodos implementados!
        /*Tive que fazer um metodo especifo em funcionario para buscar o ID, um metodo que pega o id pelo nome e cargo, pois no funcionario so tem cargo e nome de atributos, o que deixa muito generico, e pode gerar muitos resultados de ID se for buscar apenas por um dos atributod, então fiz esse metodo que busca pela combinação desses dois atributos, e la no banco de dados eu tambem fiz a logica da linha ser unica por nome e cargo, ou seja: uma linha não pode ter o mesmo nome e cargo iguais de outra linha, se não o banco de dados lança um erro no meu programa, isso deixa mais fácil de administrar os ids, e a forma tambem de como vou lidar com as outras entidades do meu programa, para eu não precisar ficar buscando manualmente no banco de dados op id de cada entidade que eu precisar para fazer cada CRUD!*/

        //Ponto importante sobre integridade dos dados no banco de dados, sobre o metodo de deletar.. eu estava chamando dentro do metodo da subclasse junto o deleteById da superclasse de Serviços, e isso estava causando erro de integridade, pois o banco de dados diz que não pode ter nada nas tabelas dependentes da coluna que tu for deletar... então tive que tirar a chamada dos metodos e deixar para fazer na ordem correta, deletar primeiro o serviço especifico, e depois o serviço no todo, para não dar erro de integridade no programa!

        /*

        DaoFactory daoFactory = new DaoFactory();
        PetDao petDao = daoFactory.createPetDao();
        ClienteDao clienteDao = daoFactory.createClienteDao();
        EnderecoDao enderecoDao = daoFactory.createEnderecoDao();

        Endereco endereco = new Endereco("TESTE", "123", "TESTE", "PORTO", "RS", "789546123", "Casa");

        //exemplos de teste
        Cliente cliente = new Cliente("Lucas", "123456789", "lp.skt@gmail.com", "51 35795154", endereco);

        Funcionario funcionario = new Funcionario("Lucas", TiposDeCargoFuncionario.GERENTE);

       // enderecoDao.insertEndereco(endereco2);

       // clienteDao.insertCliente(cliente);

        Pet pet = new Pet("Mike", TiposDeEspecies.AVES, "PERIQUITO", CategoriaDePorte.PEQUENO,
                clienteDao.findByCPF(cliente.getCpf()));

       // petDao.insertPet(pet);

        FuncionarioDao funcionarioDao = daoFactory.createFuncionarioDao();

        funcionario = funcionarioDao.findByNomeECargo("João", "GERENTE");


        ProdutoDao produtoDao = daoFactory.createProdutoDao();
        System.out.println(produtoDao.findById(1)); //Teste para recuperar info de produtos do db


        List<Produto> produtos = produtoDao.findAll();

        System.out.println("PRODUTOS DA LOJA!\n");
        produtos.forEach(p -> System.out.println("======================\n" + p.toString()));

        ServicoBanho servicoBanho = new ServicoBanho("Banho", "Banho rapido" , 59.00,Duration.ofMinutes(30),true );

        ServicoBanhoDao servicoBanhoDao = daoFactory.createServicoBanhoDao();

       // servicoBanhoDao.insert(servicoBanho); inserção de serviço de banho feita com SUCESSO!!!

       // servicoBanho = servicoBanhoDao.findById(1); buscou por id Corretamente!!

       // servicoBanho.setComHidratacao(false);
        // servicoBanhoDao.update(servicoBanho);  Update funcionando corretamente
        //Posteriormente ver algo para recuperar o ID  de forma mais robusta desse serviço

        List<ServicoBanho> banhosList = servicoBanhoDao.findAll();
       // banhosList.forEach(System.out::println); funcionando corretamente!


        ServicoTosa servicoTosa = new ServicoTosa("TOSA", "Tosa rapida na tesoura" , 79.00,Duration.ofMinutes(120),true, true );

        ServicoTosaDao tosaDao = daoFactory.createServicoTosaDao();

        /*

       // tosaDao.insert(servicoTosa); funcionou corretamente
        servicoTosa = tosaDao.findById(2);  //instnciei com o ID.. funcionando!
        servicoTosa.setIncluiEscovacao(false);
        servicoTosa.setDescricao("Mudar a descrição para ver se funciona! E aplicar desconto!");
        servicoTosa.setPreco(servicoTosa.getPreco() - servicoTosa.getPreco()*0.50); //desconto aplicado
        tosaDao.update(servicoTosa);

        System.out.println(servicoTosa.getId());

         */

        /*

        ServicoVacinacaoDao servicoVacinacaoDao = daoFactory.createServicoVacinacaoDao();

        ServicoVacinacao servicoVacinacao = new ServicoVacinacao("Vacina", "Vacina de rotina (padrão)" , 100.00,Duration.ofMinutes(10), TiposDeVacinacao.OBRIGATORIA);

        //servicoVacinacaoDao.insert(servicoVacinacao); funcionou corretamente!!

        //System.out.println(servicoVacinacaoDao.findById(3));
      //  servicoVacinacao = servicoVacinacaoDao.findById(3);
       // servicoVacinacao.setTipoDeVacinacao(TiposDeVacinacao.RECOMENDADA_ROEDORES);
      //  servicoVacinacao.setDescricao("uma pequena mudança para ver as funcionalidades!");
        //servicoVacinacaoDao.update(servicoVacinacao);

        //ServicoDao servicoDao = daoFactory.createServicoDao();

        /*

        servicoVacinacaoDao.findAll().forEach(System.out::println);
        servicoVacinacaoDao.deleteById(servicoVacinacao.getId());FUNCIONANDO
        servicoDao.deleteById(servicoVacinacao.getId());

         */

        /*

        ServicoConsultaVeterinaria consultaVeterinaria = new ServicoConsultaVeterinaria("Consulta padrão", "Consulta de rotina (padrão)" , 400.00,Duration.ofMinutes(50), TiposDeConsultaVeterinaria.ROTINA);

        ServicoConsultaVeterinariaDao consultaDao = daoFactory.createServicoConsultaVeterinariaDao();

        //consultaDao.insert(consultaVeterinaria); funcionando

        //consultaVeterinaria = consultaDao.findById(4);
       // consultaVeterinaria.setPreco(consultaVeterinaria.getPreco() + 50.00); //aumento de preço
        //consultaDao.update(consultaVeterinaria);

       // consultaDao.findAll().forEach(c -> System.out.println(c.toString())); funcionando

        MetodoDePagamento metodoDePagamento = new MetodoDePagamento(TipoDePagamento.DINHEIRO);
        MetodoDePagamentoDao pagamentoDao = DaoFactory.createMetodoDePagamentoDao();

       // pagamentoDao.insert(metodoDePagamento); funcionando
        /*

        metodoDePagamento = pagamentoDao.findById(3);
        metodoDePagamento.setTipoDePagamento(TipoDePagamento.CARTAO_DE_CREDITO);
        pagamentoDao.update(metodoDePagamento);
       // pagamentoDao.deleteById(metodoDePagamento.getId());

       Tudo funcionando adequadamente até aqui, correções necessarias são apenas, formalizações mas nada demais critico, apenas coisas que vão deixar com mais usabilidade ao usuario, e para o programador!
         */

        /*

        Endereco enderecoT = new Endereco("AAA", "123", "AAA", "AAA", "AAA", "9999999", "AAA");

        enderecoDao.insertEndereco(enderecoT);


        Cliente clienteT = new Cliente("Fulano", "07889760308", "fulano@gmail.com", "51 9111114", enderecoT);

        MetodoDePagamento metodoDePagamento2 = new MetodoDePagamento(TipoDePagamento.CARTAO_DE_DEBITO);
        pagamentoDao.insert(metodoDePagamento2);


       clienteDao.insertCliente(clienteT);
        clienteT = clienteDao.findByCPF(clienteT.getCpf());

        Venda venda = new Venda(LocalDate.now(), 199.00, clienteT, metodoDePagamento2);

        System.out.println(enderecoT.getId());
        System.out.println(clienteT.GetId());
        System.out.println(metodoDePagamento2.getId());



        // ... (seu código anterior até a inserção da venda) ...

        VendaDao vendaDao = daoFactory.createVendaDao();

        System.out.println(enderecoT.getId());
        System.out.println(clienteT.GetId());
        System.out.println(metodoDePagamento2.getId());

        vendaDao.insert(venda);

        // Obtenha uma nova DAO para a consulta (isso garantirá uma nova conexão)
        DaoFactory daoFactory2 = new DaoFactory();
        VendaDao vendaDao2 = daoFactory2.createVendaDao();

        // Use a nova DAO para a consulta findAll()
        vendaDao2.findAll().forEach(System.out::println);

        // O gerenciamento da conexão (abertura e fechamento) deve ser feito
        // dentro da classe DB ou por um gerenciador de transações,
        // e não explicitamente aqui após cada operação.
        // Remova as chamadas explícitas para DB.closeConnection() aqui.

         */

        DaoFactory daoFactory = new DaoFactory();
        VendaDao vendaDao = daoFactory.createVendaDao();

       // vendaDao.deleteById(1);

        //vendaDao.findAll().forEach(System.out::println);

        System.out.println(vendaDao.findById(2));

        //Main atual esta servindo apenas para testes, e esta tudo funcional do jeito que eu preciso até agora...


    }
}