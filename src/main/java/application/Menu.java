package application;

import dao.ClienteDao;
import dao.DaoFactory;
import dao.PetDao;
import entities.Cliente;
import entities.Endereco;
import entities.Funcionario;
import entities.Pet;
import enums.CategoriaDePorte;
import enums.TiposDeCargoFuncionario;
import enums.TiposDeEspecies;
import exceptions.ExceptionEntitieNotFound;
import services.impl.ClienteServiceImpl;
import services.impl.EnderecoServiceImpl;
import services.impl.FuncionarioServiceImpl;
import services.impl.PetServiceImpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    Scanner sc = new Scanner(System.in);

    public void menu() throws Exception {

        while (true) {
            System.out.println();
            System.out.println("== SUPER PET SHOP ==\n");
            System.out.println("[1] - Clientes");
            System.out.println("[2] - Pets");
            System.out.println("[3] - Funcionarios");
            System.out.println("[4] - Agendamento de Serviços");
            System.out.println("[5] - Comprar Produtos");
            System.out.println("[6] - Gerar relatórios");
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

                        break;
                    case 5:

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

    public void menuClientes() {

        while (true) {
            System.out.println();
            System.out.println("== SUPER PET SHOP ==\n");
            System.out.println("[1] - Cadastro de Cliente");
            System.out.println("[2] - Lista de Clientes");
            System.out.println("[3] - Buscar Cliente");
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

    public void listarPets(){

        PetServiceImpl petService = new PetServiceImpl();

        List<Pet> pets = petService.listarPet();

        System.out.println("NOSSOS PETS CADASTRADOS: \n");
        for (Pet pet : pets) {
            System.out.println(pet);
            System.out.println();
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
}