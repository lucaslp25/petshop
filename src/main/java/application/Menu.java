package application;

import dao.*;
import entities.*;
import enums.CategoriaDePorte;
import enums.TiposDeCargoFuncionario;
import enums.TiposDeEspecies;
import exceptions.ExceptionEntitieNotFound;
import exceptions.ExceptionOfIntegrity;
import services.impl.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    Scanner sc = new Scanner(System.in);
    MenuProduto menuProduto = new MenuProduto();
    MenuServicos menuServicos = new MenuServicos();

    public void menu() throws Exception {

        while (true) {
            System.out.println();
            System.out.println("== SUPER PET SHOP ==\n");
            System.out.println("[1] - Clientes");
            System.out.println("[2] - Pets");
            System.out.println("[3] - Funcionarios");
            System.out.println("[4] - Produtos");
            System.out.println("[5] - Serviços PetShop");
            System.out.println("[0] - Sair\n");

            System.out.println("Digite a opção que deseja: ");
            try {
                int op = sc.nextInt();
                sc.nextLine();
                switch (op) {
                    case 1:
                        menuClientes();
                        break;
                    case 2:
                        menuPets();
                        break;
                    case 3:
                        menuFuncionario();
                        break;
                    case 4:
                        menuProduto.menuProduto();
                        break;
                    case 5:
                        menuServicos.menuServicos();
                        break;
                    case 0:
                        System.out.println("Saindo....");
                        return;
                    default:
                        System.err.println("Opção inválida! ");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.err.println("Apenas números!");
            }
        }
    }

    public void menuFuncionario()throws Exception{

        while (true) {
            System.out.println();
            System.out.println("== SUPER PET SHOP ==\n");
            System.out.println("[1] - Cadastro de Funcionario");
            System.out.println("[2] - Lista de Funcionarios");
            System.out.println("[3] - Buscar Funcionario");
            System.out.println("[4] - Remover Funcionario");
            System.out.println("[0] - Voltar\n");

            System.out.println("Digite a opção que deseja: ");
            try {
                int op = sc.nextInt();
                sc.nextLine();
                switch (op) {
                    case 1:
                        cadastrarFuncionario();
                        break;
                    case 2:
                        listarFuncionarios();
                        break;
                    case 3:
                        buscarFuncionarios();
                        break;
                    case 4:
                        deletarFuncionario();
                        break;
                    case 0:
                        System.out.println("Voltando...");
                        return;
                    default:
                        System.err.println("Opção inválida! ");
                }

            } catch (InputMismatchException e) {
                sc.nextLine();
                System.err.println("Apenas números!");
            }
        }
    }

    public void menuClientes() throws Exception{

        while (true) {
            System.out.println();
            System.out.println("== SUPER PET SHOP ==\n");
            System.out.println("[1] - Cadastro de Cliente");
            System.out.println("[2] - Lista de Clientes");
            System.out.println("[3] - Buscar Cliente");
            System.out.println("[4] - Remover Cliente");
            System.out.println("[0] - Voltar\n");

            System.out.println("Digite a opção que deseja: ");
            try {
                int op = sc.nextInt();
                sc.nextLine();
                switch (op) {
                    case 1:
                        cadastrarCliente();
                        break;
                    case 2:
                        listaDeClientes();
                        break;
                    case 3:
                        buscarCliente();
                        break;
                    case 4:
                        deletarCliente();
                        break;
                    case 0:
                        System.out.println("Voltando...");
                        return;
                    default:
                        System.err.println("Opção inválida! ");
                }

            } catch (InputMismatchException e) {
                sc.nextLine();
                System.err.println("Apenas números!");
            }
        }
    }

    public void menuPets() throws Exception {

        while (true) {
            System.out.println();
            System.out.println("== SUPER PET SHOP ==\n");
            System.out.println("[1] - Cadastro de Pet");
            System.out.println("[2] - Lista de Pets");
            System.out.println("[3] - Buscar Pet");
            System.out.println("[4] - Deletar Pet");
            System.out.println("[0] - Voltar\n");

            System.out.println("Digite a opção que deseja: ");
            try {
                int op = sc.nextInt();
                sc.nextLine();
                switch (op) {
                    case 1:
                        cadastroPet();
                        break;
                    case 2:
                        listarPets();
                        break;
                    case 3:
                        buscarPet();
                        break;
                    case 4:
                        deletarPet();
                        break;
                    case 0:
                        System.out.println("Voltando...");
                        return;
                    default:
                        System.err.println("Opção inválida! ");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.err.println("Apenas números!");
            }
        }
    }

    public void listaDeClientes() {

        ClienteServiceImpl clienteService = new ClienteServiceImpl();

        if (clienteService.listarClientes() != null) {

            List<Cliente> clientes = clienteService.listarClientes();
            System.out.println("= CLIENTES CADASTRADOS = ");
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
        if (clienteService.listarClientes() == null) {
            System.err.println("Nenhum cliente cadastrado no nosso sistema!");
        }
    }

    public void cadastrarCliente() {

        System.out.print("Digite o nome do cliente: ");
        String nome = sc.nextLine();
        System.out.print("Digite o CPF do cliente: ");
        String cpf = sc.nextLine();
        System.out.print("Digite o email do cliente: ");
        String email = sc.nextLine();
        System.out.print("Digite o telefone do cliente: ");
        String telefone = sc.nextLine();

        System.out.println("\nCadastro do endereço do cliente: \n");
        System.out.print("Rua: ");
        String rua = sc.nextLine();
        System.out.print("Bairro: ");
        String bairro = sc.nextLine();
        System.out.print("Cidade: ");
        String cidade = sc.nextLine();
        System.out.print("Estado: ");
        String estado = sc.nextLine();
        System.out.print("Numero: ");
        String numero = sc.nextLine();
        System.out.print("cep: ");
        String cep = sc.nextLine();
        System.out.print("Complemento: ");
        String complemento = sc.nextLine();

        Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, cep, complemento);
        EnderecoServiceImpl enderecoService = new EnderecoServiceImpl();
        enderecoService.cadastrarEndereco(endereco);
        //pegando o id do banco de dados do endereço!
        endereco = enderecoService.buscarPorAtributos(endereco);

        Cliente cliente = new Cliente(nome, cpf, email, telefone, endereco);

        ClienteServiceImpl clienteService = new ClienteServiceImpl();
        clienteService.cadastrarCliente(cliente);
    }

    public void buscarCliente() throws ExceptionEntitieNotFound {

        ClienteServiceImpl clienteService = new ClienteServiceImpl();

        System.out.print("Digite o cpf do cliente: ");

        try {
            String cpf = sc.nextLine();

            Cliente cliente = clienteService.buscarClientePorCpf(cpf);

            if (cliente != null) {
                System.out.println("Cliente Encontrado!");
                System.out.println(cliente);
            }
        } catch (ExceptionEntitieNotFound e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }
    }

    public void cadastroPet() throws Exception {

        try {
            ClienteDao clienteDao = DaoFactory.createClienteDao();
            PetDao petDao = DaoFactory.createPetDao();

            System.out.print("Digite o CPF do dono do Pet: ");
            String cpf = sc.nextLine();

            Cliente cliente = clienteDao.findByCPF(cpf);

            if (cliente.getCpf() == null) {
                System.out.println("Nenhum cliente com esse cpf encontrado!");
                return;
            } else {
                System.out.println("Bem-vindo " + cliente.getNome() + "!\n");
            }


            System.out.print("Digite o nome do seu pet: ");
            String nome = sc.nextLine();

            String especie = null;

            System.out.println("SELECIONE A ESPÉCIE DO PET " + nome.toUpperCase());
            System.out.println("[1] - CACHORRO");
            System.out.println("[2] - GATO");
            System.out.println("[3] - AVE");
            System.out.println("[4] - PEIXE");
            System.out.println("[5] - PEQUENOS ROEDORES");
            System.out.print("Digite o número da espécie correspondente: ");
            while (especie == null) {
                Integer op = null;
                try {
                    op = sc.nextInt();
                    sc.nextLine();

                    switch (op) {
                        case 1:
                            especie = "CACHORRO";
                            break;
                        case 2:
                            especie = "GATO";
                            break;
                        case 3:
                            especie = "AVES";
                            break;
                        case 4:
                            especie = "PEIXES";
                            break;
                        case 5:
                            especie = "PEQUENOS-ROEDORES";
                            break;
                        default:
                            System.out.println("Opção inválida, Tente novamente!");
                            System.out.println("Digite o número da opção correspondente: ");
                            break;
                    }
                }catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("Apenas números!");
                }
            }
            System.out.println("Raça do pet: ");
            String raca = sc.nextLine();

            String porte = null;

            System.out.println("Digite o porte de " + nome);
            System.out.println("[1] - PEQUENO");
            System.out.println("[2] - MEDIO");
            System.out.println("[3] - GRANDE");
            System.out.println("Selecione o porte correspondente: ");
            Integer op2 = null;
            while(porte == null) {
                try {
                    op2 = sc.nextInt();
                    sc.nextLine();

                    switch (op2) {
                        case 1:
                            porte = "PEQUENO";
                            break;
                        case 2:
                            porte = "MEDIO";
                            break;
                        case 3:
                            porte = "GRANDE";
                            break;
                        default:
                            System.out.println("Opção inválida, Tente novamente!");
                            System.out.println("Digite a opção correspondente: ");
                            break;
                    }
                }catch (InputMismatchException e) {
                    sc.nextLine();
                    System.err.println("Apenas números!");
                }
            }

            System.out.println("Digite a idade do seu pet: ");
            int idade = sc.nextInt();
            sc.nextLine();

            Pet pet = new Pet(nome, TiposDeEspecies.valueOf(especie), raca, CategoriaDePorte.valueOf(porte), cliente, idade);

            PetServiceImpl petService = new PetServiceImpl();
            petService.cadastrarPet(pet);
            System.out.println("Cadastro efetuado com sucesso! ");


        }catch (ExceptionEntitieNotFound e) {
            System.out.println("Erro ao cadastrar pet: " + e.getMessage());
        }catch (Exception e) {
            System.out.println("Erro ao cadastrar pet: " + e.getMessage());
        }
    }

    public void listarPets()throws ExceptionEntitieNotFound{

        try {
            PetServiceImpl petService = new PetServiceImpl();

            List<Pet> pets = petService.listarPet();

            System.out.println("NOSSOS PETS CADASTRADOS: \n");
            for (Pet pet : pets) {
                System.out.println(pet);
                System.out.println();
            }
        }catch (ExceptionEntitieNotFound e ){
            System.out.println("Erro ao buscar lista de pet: " + e.getMessage());
        }
    }

    public void buscarPet()throws Exception{

        try {

            ClienteServiceImpl clienteService = new ClienteServiceImpl();

            System.out.print("Digite o CPF do dono: ");
            String cpf = sc.nextLine();

            Cliente cliente = clienteService.buscarClientePorCpf(cpf);
            if (cliente == null) {
                throw new ExceptionEntitieNotFound("Nenhum cliente com esse CPF cadastrado!");
            }
            PetDao petDao = DaoFactory.createPetDao();

            List<Pet> petsCliente = petDao.findByCpfDono(cpf);

            if (petsCliente == null) {
                throw new ExceptionEntitieNotFound("Nenhum Pet cadastrado para esse cliente!");
            }

            for (Pet pet : petsCliente) {
                System.out.println(pet);
                System.out.println("PET ID: " + pet.getId());
                System.out.println();
            }

            System.out.print("Agora selecione o ID do pet que deseja buscar: ");
            Integer id = sc.nextInt();
            sc.nextLine();
            Pet pet = petDao.findById(id);
            System.out.println(pet);

        }catch (ExceptionEntitieNotFound e){
            System.out.println("Erro ao cadastrar pet: " + e.getMessage());
        }catch (InputMismatchException e){
            System.out.println("Apenas números!");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar pet: " + e.getMessage());
        }
    }

    public void cadastrarFuncionario(){

        System.out.print("Digite o nome do funcionario: ");
        String nome = sc.nextLine();

        String cargo = null;
        System.out.println("[1] - ATENDENTE");
        System.out.println("[2] - VENDEDOR");
        System.out.println("[3] - VETERINARIO");
        System.out.println("[4] - GERENTE");
        Integer op = null;
        while (cargo == null) {
            try{
                op = sc.nextInt();
                sc.nextLine();
                switch (op) {
                    case 1:
                        cargo = "ATENDENTE";
                        break;
                    case 2:
                        cargo = "VENDEDOR";
                        break;
                    case 3:
                        cargo = "VETERINARIO";
                        break;
                    case 4:
                        cargo = "GERENTE";
                        break;
                    default:
                        System.out.println("Numeró inválido! Tente novamente!");
                        System.out.println("Digite a opção do cargo do Funcionario: ");
                        break;
                }
            }catch (InputMismatchException e){
                System.err.println("Apenas números!");
            }
        }
        Double salario = null;
        do {
            System.out.println("Defina o salário base do funcionario: ");
            salario = sc.nextDouble();
            sc.nextLine();

            if (salario < 0) {
                System.out.println("Salario não pode ser negativo! Tente novamente!");
            }

        } while (salario < 0);

        System.out.println("Salário definido como R$:" + String.format("%.2f", salario));

        System.out.println("\nCadastro do endereço do Funcionario: "+nome+"!\n");
        System.out.print("Rua: ");
        String rua = sc.nextLine();
        System.out.print("Bairro: ");
        String bairro = sc.nextLine();
        System.out.print("Cidade: ");
        String cidade = sc.nextLine();
        System.out.print("Estado: ");
        String estado = sc.nextLine();
        System.out.print("Numero: ");
        String numero = sc.nextLine();
        System.out.print("cep: ");
        String cep = sc.nextLine();
        System.out.print("Complemento: ");
        String complemento = sc.nextLine();

        Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, cep, complemento);
        EnderecoServiceImpl enderecoService = new EnderecoServiceImpl();
        enderecoService.cadastrarEndereco(endereco);
        endereco = enderecoService.buscarPorAtributos(endereco);

        Funcionario funcionario = new Funcionario(nome, TiposDeCargoFuncionario.valueOf(cargo), endereco, salario);
        FuncionarioServiceImpl funcionarioService = new FuncionarioServiceImpl();
        funcionarioService.cadastrarFuncionario(funcionario);
        System.out.println("Cadastro efeutado com sucesso!");
    }

    public void listarFuncionarios()throws ExceptionEntitieNotFound{
        try {

            FuncionarioServiceImpl funcionarioService = new FuncionarioServiceImpl();

            List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();

            if (funcionarios == null) {
                System.err.println("Nenhum funcionario na lista ainda!");
            }

            System.out.println("NOSSOS FUNCIONARIOS: ");

            for (Funcionario funcionario : funcionarios) {
                System.out.println(funcionario);
                System.out.println("Funcionairo ID: " + funcionario.getId());
                System.out.println();
            }
        } catch (ExceptionEntitieNotFound e) {
            System.out.println(e.getMessage());
        }
    }
    public void buscarFuncionarios()throws Exception{

        try {
            FuncionarioServiceImpl funcionarioService = new FuncionarioServiceImpl();

            System.out.print("Digite o nome do funcionario: ");
            String nome = sc.nextLine();
            System.out.println("Digite o cargo do funcionario; ");
            String cargo = sc.nextLine().toUpperCase();

            Funcionario funcionario = funcionarioService.buscarFuncionarioPorNomeECargo(nome, TiposDeCargoFuncionario.valueOf(cargo));

            System.out.println(funcionario);

        }catch (ExceptionEntitieNotFound e){
            System.out.println(e.getMessage());
        }catch (IllegalArgumentException e){
            System.out.println("Erro ao buscar funcionario! " + e.getMessage());
        }
    }

    public void deletarFuncionario()throws Exception{

        FuncionarioServiceImpl funcionarioService = new FuncionarioServiceImpl();

        FuncionarioDao funcionarioDao = DaoFactory.createFuncionarioDao();

        Integer op = null;

        try {
            List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();

            System.out.println("Nossos funcionarios: ");
            for (Funcionario funcionario : funcionarios) {

                System.out.println(funcionario);
                System.out.println("[ID = " + funcionario.getId() + "]");
                System.out.println();
            }
            System.out.println("Digite o ID do funcionario que deseja deletar: ");
            op = sc.nextInt();
            sc.nextLine();

            Funcionario funcionario = funcionarioDao.findById(op);

            if (funcionario == null){
                throw new ExceptionEntitieNotFound("Funcionario Nulo!");
            }

            System.out.println("Tem certeza que deseja deletar o funcionario(a) " + funcionario.getNome() + "?");
            System.out.println("[1]-SIM   [2]-NÃO");
            int op2 = sc.nextInt();
            sc.nextLine();
            switch (op2) {
                case 1:
                    AgendamentoDao agendamentoDao = DaoFactory.createAgendamentoDao();

                    List<Agendamento> agendamentos = agendamentoDao.findByFuncionario(funcionario);

                    //se funcionario não estiver nenhum agendamento associado a ele, pode então excluir, ou lançara uma exceção!
                    if (agendamentos.isEmpty()){
                        funcionarioService.excluirFuncionarioById(op);
                        System.out.println("Funcionario excluido com sucesso!");
                    }else{
                        throw new ExceptionOfIntegrity("Erro de integridade, esse funcionario tem agendamentos associados a ele ainda!");
                    }
                    break;
                case 2:
                    System.out.println("O funcionario " + funcionario.getNome() + " não foi removido!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }catch (InputMismatchException e) {
            System.out.println("Apenas números!");
            sc.nextLine();
        }catch (ExceptionOfIntegrity e){

            System.out.println("Erro de integridade ao deletar funcionário: " + e.getMessage());

            System.out.println("Deseja ver e gerenciar os agendamentos desse funcionário? ");
            System.out.println("[1]-SIM    [2]-NÃO");
            Integer opcaoVisualizarAgendamentos = sc.nextInt();
            sc.nextLine();

            switch (opcaoVisualizarAgendamentos) {
                case 1:
                    Funcionario funcionarioAlvo = funcionarioDao.findById(op);

                    if (funcionarioAlvo == null) {
                        System.out.println("Funcionário não encontrado.");
                        break;
                    }

                    AgendamentoDao agendamentoDao = DaoFactory.createAgendamentoDao();
                    List<Agendamento> agendamentos = agendamentoDao.findByFuncionario(funcionarioAlvo);

                    if (agendamentos.isEmpty()) {
                        System.out.println("Não há agendamentos ativos para este funcionário. Tente deletar o funcionário novamente.");
                        //erro estranho, mas pode acontecer

                        try {
                            funcionarioService.excluirFuncionarioById(op);
                            System.out.println("Funcionário deletado com sucesso após verificar agendamentos vazios!");
                        } catch (DbIntegrityException ex) {
                            System.out.println("Ainda não foi possível deletar o funcionário: " + ex.getMessage());
                        }
                        break;
                    }
                    System.out.println("Agendamentos ativos do funcionário " + funcionarioAlvo.getNome() + ":");
                    for (Agendamento agendamento : agendamentos) {
                        System.out.println(agendamento);
                        System.out.println("[ID = " + agendamento.getId() + "]");
                        System.out.println();
                    }

                    System.out.println("Deseja deletar TODOS os agendamentos listados acima?");
                    System.out.println("[1]-SIM   [2]-NÃO");
                    int deletarTodos = sc.nextInt();
                    sc.nextLine();

                    if (deletarTodos == 1) {
                        for (Agendamento agendamento : agendamentos) {
                            try {
                                agendamentoDao.deleteById(agendamento.getId());
                                System.out.println("Agendamento [ID = " + agendamento.getId() + "] deletado com sucesso!");
                            } catch (DbExceptions lp) {
                                System.out.println("Erro ao deletar agendamento [ID = " + agendamento.getId() + "]: " + lp.getMessage());
                            }
                        }
                        System.out.println("Todos os agendamentos foram Deletados!.");

                        try {
                            funcionarioService.excluirFuncionarioById(op); // O 'op' é o ID original do funcionário
                            System.out.println("Funcionário deletado com sucesso após remoção de agendamentos!");
                        } catch (DbIntegrityException lp) {
                            System.out.println("Ainda não foi possível deletar o funcionário: " + lp.getMessage());
                        } catch (Exception lp) {
                            System.out.println("Erro inesperado ao tentar deletar funcionário novamente: " + lp.getMessage());
                            e.printStackTrace();
                            //stacktrace sempre bom para erros inesperados!
                        }
                    } else {
                        System.out.println("Deleção cancelada, o funcionário não será deletado.");
                    }
                    break;
                case 2:
                    System.out.println("Operação de gerenciar agendamentos cancelada. Funcionário não deletado.");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }catch (ExceptionEntitieNotFound e) {
            System.out.println("Erro ao deletar funcionario! " + e.getMessage());
        }catch (Exception e){
            System.out.println("Erro inesperdo aconteceu ao deletar funcionario! " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletarPet()throws Exception{

        PetServiceImpl petService = new PetServiceImpl();
        PetDao petDao = DaoFactory.createPetDao();
        AgendamentoDao agendamentoDao = DaoFactory.createAgendamentoDao();

        Integer id = null;
        try {

            List<Pet> pets = petDao.findAll();

            if (pets.isEmpty()) {
                throw new ExceptionEntitieNotFound("Nenhum pet ainda cadastrada para poder ser excluido!");
            }

            System.out.println("PETS CADASTRADOS NO SISTEMA!\n");
            for (Pet pet : pets) {
                System.out.println(pet);
                System.out.println("[ID = " + pet.getId() + "]");
                System.out.println();
            }

            System.out.println("DIGITE O ID DO PET QUE DESEJA DELETAR: ");
            id = sc.nextInt();
            sc.nextLine();

            Pet pet = petDao.findById(id);
            System.out.println("Tem certeza que deseja deletar o " + pet.getNome() + " do sistema? ");
            System.out.println("[1]-SIM    [2]-NÃO");
            Integer op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    List<Agendamento> agendamentosParaEstePet = agendamentoDao.findBypet(pet);
                    if (agendamentosParaEstePet.isEmpty()) {
                        petService.excluirPetById(id);
                        System.out.println("Pet Excluido com sucesso!");
                    } else {
                        throw new ExceptionOfIntegrity("O Pet " + pet.getNome() + " tem agendamentos associados a ele ainda! portanto não podemos excluir do sistema!");
                    }
                    break;
                case 2:
                    System.out.println("O Pet " + pet.getNome() + " não foi deletado!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        }catch (InputMismatchException e){
            System.out.println("Apenas números são válidos!");
            sc.nextLine();
        }catch (ExceptionOfIntegrity e){

            System.out.println("Erro ao deletar pet! " + e.getMessage());

            Pet pet = petDao.findById(id);

            System.out.println("Deseja gerenciar os agendamentos do pet " + pet.getNome() + " ?");
            System.out.println("[1]-SIM   [2]-NÃO");
            Integer opGerenciar = sc.nextInt();
            sc.nextLine();
            switch (opGerenciar) {
                case 1:

                    List<Agendamento> agendamentos = agendamentoDao.findBypet(pet);
                    if (agendamentos.isEmpty()) {
                        System.out.println("Nenhum agendamento para esse Pet! Tente deletar o pet novamente!");
                        try {
                            petService.excluirPetById(id);
                            System.out.println("Pet deletado com sucesso após verificação de agendamento vazio!");
                        } catch (DbIntegrityException lp) {
                            System.out.println("Ainda não foi possivel deletar o pet: " + lp.getMessage());
                        }
                        break;
                    }
                    System.out.println("Lista de agendamentos do pet " +pet.getNome() + ".\n");
                    for (Agendamento agendamento : agendamentos) {
                        System.out.println(agendamento);
                        System.out.println("[ID = " + agendamento.getId() + "]");
                        System.out.println();
                    }

                    //aqui eu poderia dar a opção do usuario esolher deletar um agendamento por vez, ou todos de uma só vez, porem pensei no caso de um pet ja ter muitos agendamentos e no trabalho de ficar digitando o ID de todos eles... então deletar todos de uma vez será mais fácil!

                    System.out.println("Deseja deletar todos os agendamentos e o pet também?");
                    System.out.println("[1]-SIM     [2]-NÃO");
                    Integer opDeletar = sc.nextInt();
                    sc.nextLine();
                    switch (opDeletar) {
                        case 1:
                            for (Agendamento agendamento : agendamentos) {
                                try {
                                    agendamentoDao.deleteById(agendamento.getId());
                                    System.out.println("Agendamento - ID:" + agendamento.getId() + " deletado com sucesso!");
                                } catch (DbExceptions lp) {
                                    System.out.println("Erro ao deletar agendamento: " + lp.getMessage());
                                }
                            }
                            System.out.println("Todos agendamentos de Pet foram deletados com sucesso!");

                            //agora pet com mais nenhum agendamento, ja pode ser excluido!
                            try {
                                petService.excluirPetById(id);
                                System.out.println("Pet excluido com sucesso após remover os agendamentos!");
                            } catch (DbIntegrityException lp) {
                                System.out.println("Erro ainda ao deletar pet: " + e.getMessage());
                            } catch (Exception lp) {
                                System.out.println("Erro inesperado ao tentar deletar o pet: " + lp.getMessage());
                                lp.printStackTrace();
                            }
                    }
                    break;
                case 2:
                    System.out.println("Agendamentos não gerenciados! Pet não excluido.");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }catch (ExceptionEntitieNotFound e){
            System.out.println("Não foi possivel deletar Pet: " + e.getMessage());
        }
    }

    public void deletarCliente()throws Exception{

        ClienteDao clienteDao = DaoFactory.createClienteDao();
        ClienteServiceImpl clienteService = new ClienteServiceImpl();
        PetDao petDao = DaoFactory.createPetDao();
        PetServiceImpl petService = new PetServiceImpl();
        VendaDao vendaDao = DaoFactory.createVendaDao();
        VendaServiceImpl vendaService = new VendaServiceImpl();
        AgendamentoDao agendamentoDao = DaoFactory.createAgendamentoDao();
        AgendamentoServiceImpl agendamentoService = new AgendamentoServiceImpl();
        ItemVendaDao itemVendaDao = DaoFactory.createItemVendaDao();
        ItemVendaServiceImpl itemVendaService = new ItemVendaServiceImpl();

        String clienteCpf = null;
        try {
            System.out.println("Digite o CPF do cliente que deseja deletar do sistema: ");
            clienteCpf = sc.nextLine();

            Cliente cliente = clienteService.buscarClientePorCpf(clienteCpf);

            if (cliente == null){
                throw new ExceptionEntitieNotFound("Nenhum cliente com esse CPF no sistema!");
            }
            System.out.println("Cliente " + cliente.getNome() + " encontrado!");
            System.out.println("Deseja excluir o cliente " + cliente.getNome() + " do sistema?");
            System.out.println("[1]-SIM     [2]-NÃO");
            Integer op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    List<Pet>petsDocliente = petDao.findByCpfDono(clienteCpf);
                    List<Venda> vendasCliente = vendaDao.findVendasByClienteCpf(clienteCpf);
                    if (petsDocliente.isEmpty() && vendasCliente.isEmpty()){
                        clienteService.deletarClienteByCpf(clienteCpf);
                        System.out.println("Cliente deletado com sucesso!");
                    }else{
                        throw new ExceptionOfIntegrity("Erro de integridade! Ainda há Pets ou vendas associadas a esse cliente!");
                    }
                    break;
                case 2:
                    System.out.println("O Cliente " + cliente.getNome() + " não foi deletado!");
                    break;
                default:
                    System.out.println("opção inválida");
                    break;
            }
        }catch (InputMismatchException e){
            System.out.println("Apenas números!");
            sc.nextLine();
        }
        catch (ExceptionOfIntegrity e){
            System.out.println("Erro ao deletar cliente: " + e.getMessage());

            Cliente cliente = clienteService.buscarClientePorCpf(clienteCpf);

            System.out.println("Deseja gerenciar as dependências do cliente?");
            System.out.println("[1]-SIM   [2]-NÃO");
            Integer op = sc.nextInt();
            sc.nextLine();
            switch (op){
                case 1:

                    List<Pet>petsDocliente = petDao.findByCpfDono(clienteCpf);
                    List<Venda> vendasCliente = vendaDao.findVendasByClienteCpf(clienteCpf);
                    List<Agendamento> agendamentosCliente = agendamentoDao.findByClienteCpf(clienteCpf);
                    List<ItemVenda> itensDeVendaAssociados = new ArrayList<>();

                    if (petsDocliente.isEmpty() && vendasCliente.isEmpty()){
                        System.out.println("Nenhum Pet ou venda associados ao cliente "+cliente.getNome() + "! tentando deletar novamente.. ");
                        try{
                            clienteService.deletarClienteByCpf(clienteCpf);
                            System.out.println("Cliente deletado com sucesso após tentativa!");
                        }catch (DbIntegrityException lp){
                            System.out.println("Erro ao tentar deletar cliente mesmo com a lista de associações vazias!");
                        }
                        break;
                    }
                    System.out.println("Pets do cliente "+cliente.getNome()+" \n");
                    for (Pet pets: petsDocliente){
                        System.out.println(pets);
                        System.out.println("[ID = "+pets.getId()+"]");
                        System.out.println();

                    }
                    System.out.println("=|=|=|=|=|=|=|=|=|=|=|=|\n");

                    System.out.println("Agendamentos associados aos pets do cliente "+cliente.getNome()+" \n");
                    for (Agendamento agendamentos: agendamentosCliente){
                        System.out.println(agendamentos);
                        System.out.println("[ID = "+agendamentos.getId()+"]");
                        System.out.println();
                    }
                    System.out.println("=|=|=|=|=|=|=|=|=|=|=|=|\n");

                    System.out.println("Vendas associadas ao cliente "+cliente.getNome()+" \n");
                    for (Venda vendas: vendasCliente ){
                        System.out.println(vendas);
                        System.out.println("[ID = "+vendas.getId()+"]");
                        System.out.println();
                    }
                    System.out.println("Deseja excluir tudo que esta associado, e o cliente junto?");
                    System.out.println("[1]-SIM    [2]-NÃO");
                    Integer op2 = sc.nextInt();
                    sc.nextLine();

                    switch (op2){
                        case 1:
                            int c1 = 0;
                            for(Agendamento agendamentos: agendamentosCliente){
                                try {
                                    agendamentoService.excluirAgendamentoById(agendamentos.getId());
                                    System.out.println("Agendamento de ID: " + agendamentos.getId() + " deletado com sucesso!");
                                    c1++;
                                }catch (ExceptionEntitieNotFound lp){
                                    System.out.println("Não foi possivel deletar o agendamento de ID: " + agendamentos.getId() + ".");
                                }catch (Exception lp){
                                    System.out.println("Erro inesperado aconteceu ao tentar deletar o agendamento de ID: " + agendamentos.getId() + ".");
                                    lp.printStackTrace();
                                }
                            }
                            if(c1 == agendamentosCliente.size()){
                                System.out.println("Todos agendamentos do cliente foram deletados com sucesso!");
                            }else{
                                System.out.println(c1 + "de "+agendamentosCliente.size() +" agendamentos foram excluidos! ");
                                System.out.println(agendamentosCliente.size() - c1 + " Agendamentos pendentes para serem excluidos!");
                            }

                            int c2 = 0;
                            for (Pet pets: petsDocliente){
                                try{
                                    petService.excluirPetById(pets.getId());
                                    System.out.println("Pet " +pets.getNome() + " excluido com sucesso! ID: " + pets.getId());
                                    c2++;
                                }catch (ExceptionEntitieNotFound lp){
                                    System.out.println("Erro ao deletar pet "+pets.getNome() +", ID: " + pets.getId() + ".");
                                } catch (Exception lp) {
                                    System.out.println("Erro inesperado ao deletar Pet: "+pets.getNome()+" ID: " + pets.getId() + ".");
                                    lp.printStackTrace();
                                }
                            }

                            if(c2 == petsDocliente.size()){
                                System.out.println("Todos os pets deletados com sucesso!");
                            }else{
                                System.out.println(c2 + " de " + petsDocliente.size() + " pets foram excluidos!");
                                System.out.println(petsDocliente.size() - c2 + " Pets pendentes para serem excluidos!");
                            }

                            int c3 = 0;
                            List<Integer> ids = new ArrayList<>();
                            for (Venda vendas: vendasCliente){
                                ids.add(vendas.getId());
                            }
                            if (ids.isEmpty()){
                                throw new ExceptionEntitieNotFound("Nenhuma venda encontrada para este cliente!");
                            }else{
                                c3 = 0; //esta em outra escopo esse contador
                                for (int i = 0; i < ids.size(); i++) {
                                    try {
                                        itemVendaDao.deleteByVendaId(ids.get(i));
                                        System.out.println("Item de venda ID: " + ids.get(i) + " deletado com sucesso!");
                                        c3++;
                                    } catch (ExceptionEntitieNotFound lp) {
                                        System.out.println("Erro ao deletar item de venda ID: " + ids.get(i));
                                    }catch (Exception lp) {
                                        System.out.println("Erro inesperado ao deletar Item de venda ID: " + ids.get(i));
                                        lp.printStackTrace();
                                    }
                                }
                                if (c3 == ids.size()){
                                    System.out.println("Todos Itens de vendas deletados com sucesso!");
                                }else{
                                    System.out.println(c3 + " de "+ ids.size() + "Itens De Venda Excluidos!");
                                    System.out.println(ids.size() - c3 + "Itens de vendas pendentes a serem excluidos ainda!");
                                }
                            }

                            int c4 = 0;
                            for (Venda vendas: vendasCliente){
                                try{
                                    vendaService.deleteVendaById(vendas.getId());
                                    System.out.println("Venda de ID: " + vendas.getId() + " Deletada com sucesso!");
                                    c4++;
                                }catch (ExceptionEntitieNotFound lp){
                                    System.out.println("Não foi possivel deletar venda de ID: " + vendas.getId() + "." );
                                }catch (Exception lp){
                                    System.out.println("Erro inesperado aconteceu ao tentar deletar a venda de ID :" + vendas.getId() + ".");
                                    lp.printStackTrace();
                                }
                            }
                            if(c4 == vendasCliente.size()){
                                System.out.println("Todas as vendas Foram deletadas com sucesso! ");
                            }else{
                                System.out.println(c4 + " de " + vendasCliente.size() + " vendas excluidas!");
                                System.out.println(vendasCliente.size() - c4 + " vendas pendentes a serem excluidas ainda!");
                            }

                            if (c1 == agendamentosCliente.size() && c2 == petsDocliente.size() && c3 == ids.size() && c4 == vendasCliente.size()){
                                System.out.println("= TODAS DEPENDÊNCIAS DE CLIENTE REMOVIDAS! =");
                            }else{
                                System.out.println("NEM TODAS AS DEPENDÊNCIAS FORAM EXCLUIDAS, POSSIVEL PROBLEMA AO EXCLUIR CLIENTE!");
                            }
                            try{
                                clienteService.deletarClienteByCpf(clienteCpf);
                                System.out.println("Cliente deletado com sucesso após remoção de dependências!");
                            }catch (DbIntegrityException lp){
                                System.out.println("Erro ao remover deletar cliente ainda! " + lp.getMessage());
                            }catch (Exception lp) {
                                System.out.println("Erro inesperado ao tentar remover o cliente após remoção de dependências!");
                            }
                            break;
                        case 2:
                            System.out.println("As depêndencias não foram excluidas, o cliente " + cliente.getNome() + " também não!");
                            break;
                        default:
                            System.out.println("Opção inválida!");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Dependências não gerenciadas! Cliente não deletado.");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }catch (ExceptionEntitieNotFound e){
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }
    //para seguir com a exclusão de cliente eu preciso de uma lista de todos os pets de cliente e uma lista de todos agendamentos desses pets, preciso primeiro excluir todos agendamentos de todos os pets e depois, excluir os pets, então assim eu posso sefguir para a venda, tenho que também excluir todas as vendas relacionadas a esse cliente para poder prosseguir com a exclusão de maneira certa e guiada ao usuario!
}