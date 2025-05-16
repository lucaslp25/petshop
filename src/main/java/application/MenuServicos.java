package application;

import dao.*;
import entities.*;
import enums.TiposDeConsultaVeterinaria;
import enums.TiposDeVacinacao;
import exceptions.ExceptionEntitieNotFound;
import exceptions.ExceptionIncorretDate;
import services.impl.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuServicos {

    Scanner sc = new Scanner(System.in);

    private ServicoBanhoServiceImpl servicoBanhoService = new ServicoBanhoServiceImpl();
    private ServicoTosaServiceImpl servicoTosaService = new ServicoTosaServiceImpl();
    private ServicoVacinacaoServiceImpl servicoVacinacaoService = new ServicoVacinacaoServiceImpl();
    private ServicoConsultaVeterinariaServiceImpl servicoConsultaVeterinariaService = new ServicoConsultaVeterinariaServiceImpl();

    private AgendamentoServiceImpl agendamentoService = new AgendamentoServiceImpl();
    private ClienteServiceImpl clienteService = new ClienteServiceImpl();
    private PetServiceImpl petService = new PetServiceImpl();
    private FuncionarioServiceImpl funcionarioService = new FuncionarioServiceImpl();
    private PetDao petDao = DaoFactory.createPetDao();
    private FuncionarioDao funcionarioDao = DaoFactory.createFuncionarioDao();
    private ServicoBanhoDao servicoBanhoDao = DaoFactory.createServicoBanhoDao();
    private ServicoVacinacaoDao servicoVacinacaoDao = DaoFactory.createServicoVacinacaoDao();
    private ServicoTosaDao servicoTosaDao = DaoFactory.createServicoTosaDao();
    private ServicoConsultaVeterinariaDao servicoConsultaVeterinariaDao = DaoFactory.createServicoConsultaVeterinariaDao();
    private AgendamentoDao agendamentoDao = DaoFactory.createAgendamentoDao();

    public void menuServicos() throws Exception {

        while (true) {
            System.out.println();
            System.out.println("== SUPER PET SHOP ==\n");
            System.out.println("[1] - Agendar Serviço");
            System.out.println("[2] - Listar Serviços ");
            System.out.println("[3] - Atualizar Agendamento");
            System.out.println("[4] - Remover Agendamento");
            System.out.println("[5] - Gerar relatórios");
            System.out.println("[0] - Voltar\n");

            System.out.println("Digite a opção que deseja: ");
            try {
                int op = sc.nextInt();
                sc.nextLine();
                switch (op) {
                    case 1:
                        menuServicos2();
                        break;
                    case 2:
                        listarServicos();
                        break;
                    case 3:
                        atualizarAgendamento();
                        break;
                    case 4:
                        removerAgendamento();
                        break;
                    case 5:

                        break;
                    case 0:
                        System.err.println("Voltando....");
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

    public void menuServicos2() throws Exception {

        while (true) {

            System.out.println();
            System.out.println("== SUPER PET SHOP ==\n");
            System.out.println("[1] - Serviço Banho");
            System.out.println("[2] - Serviço consulta Veterinária ");
            System.out.println("[3] - Serviço Tosa");
            System.out.println("[4] - Serviço Vacinação");
            System.out.println("[0] - Voltar\n");

            System.out.println("Digite a opção que deseja: ");
            try {
                int op = sc.nextInt();
                sc.nextLine();
                switch (op) {
                    case 1:
                        agendamentoBanho();
                        break;
                    case 2:
                        agendamentoConsultaVeterinaria();
                        break;
                    case 3:
                        agendamentoTosa();
                        break;
                    case 4:
                        agendamentoVacinacao();
                        break;
                    case 0:
                        System.err.println("Voltando....");
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

    public ServicoBanho servicoBanho() throws Exception {
        try {
            System.out.println("Agendamento de Serviço Banho!\n");

            System.out.println("Nome do serviço de banho (EX: BANHO RÁPIDO..) ");
            String nome = sc.nextLine();
            System.out.println("Descrição do serviço: ");
            String descricao = sc.nextLine();
            System.out.println("Preço do serviço: ");
            Double preco = sc.nextDouble();
            sc.nextLine();
            System.out.println("Duração do serviço (em minutos): ");
            Integer duracao = null;
            try {
                duracao = sc.nextInt();
                Duration duration = Duration.of(duracao, ChronoUnit.MINUTES);
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Somente Números inteiros!");
            }

            Boolean hidratacao = false;
            System.out.println("Incluir Hidratação profunda no banho?");
            System.out.println("[1] - SIM      [2] - NÃO");
            Integer op = null;
            try {
                op = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Apenas números!");
            }
            switch (op) {
                case 1:
                    hidratacao = true;
                    break;
                case 2:
                    hidratacao = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

            ServicoBanho servicoBanho = new ServicoBanho(nome, descricao, preco, Duration.ofMinutes(duracao), hidratacao);

            servicoBanhoService.cadastrarServicoBanho(servicoBanho);
            System.out.println("Serviço banho Agendado com sucesso!");

            return servicoBanho; // AQUI ESTA RETORNANDO SEM O ID, PRECISO RECUPERAR O ID AQUI E O DOS OUTROS SERVIÇOS TAMBE.... COM UMA FORMA DE PEGAR PELOS ATRIBUTOS UNICOS!

        } catch (ExceptionEntitieNotFound e) {
            System.out.println("Erro ao agendar serviço de banho: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao agendar serviço de banho: " + e.getMessage());
            return null;
        }
    }

    public ServicoTosa servicoTosa() {

        try {
            System.out.println("Cadastro de serviço Tosa!\n");

            System.out.println("Nome do serviço de Tosa (EX: TOSA NA TESOURA..) ");
            String nome = sc.nextLine();
            System.out.println("Descrição do serviço: ");
            String descricao = sc.nextLine();
            System.out.println("Preço do serviço: ");
            Double preco = sc.nextDouble();
            System.out.println("Duração do serviço (em minutos): ");
            Integer duracao = null;
            try {
                duracao = sc.nextInt();
                Duration duration = Duration.of(duracao, ChronoUnit.MINUTES);
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Somente Números inteiros!");
            }

            Boolean banhoPrevio = false;
            System.out.println("Incluir Banho Prévio?");
            System.out.println("[1]-SIM   [2]-NÃO");
            Integer op = null;
            try {
                op = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Apenas números!");
            }
            switch (op) {
                case 1:
                    banhoPrevio = true;
                    break;
                case 2:
                    banhoPrevio = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

            Boolean escovacao = false;
            System.out.println("Incluir Escovação");
            System.out.println("[1]-SIM   [2]-NÃO");
            Integer op2 = null;
            try {
                op2 = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Apenas números!");
            }
            switch (op2) {
                case 1:
                    escovacao = true;
                    break;
                case 2:
                    escovacao = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
            ServicoTosa servicoTosa = new ServicoTosa(nome, descricao, preco, Duration.ofMinutes(duracao), banhoPrevio, escovacao);

            servicoTosaService.cadastrarServicoTosa(servicoTosa);
            System.out.println("Serviço de tosa Agendado com sucesso!");

            return servicoTosa;
        } catch (ExceptionEntitieNotFound e) {
            System.out.println("Erro ao Agendar Serviço: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao Agendar serviço: " + e.getMessage());
        }
        return null;
    }

    public ServicoVacinacao servicoVacinacao() throws Exception {

        try {
            System.out.println("Agendamento de Serviço Vacinação!\n");

            System.out.println("Nome do serviço de Vacinação (EX: VACINA DE ROTINA..) ");
            String nome = sc.nextLine();
            System.out.println("Descrição do serviço: ");
            String descricao = sc.nextLine();
            System.out.println("Preço do serviço: ");
            Double preco = sc.nextDouble();
            System.out.println("Duração do serviço (em minutos): ");
            Integer duracao = null;
            try {
                duracao = sc.nextInt();
                Duration duration = Duration.of(duracao, ChronoUnit.MINUTES);
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Somente Números inteiros!");
            }
            String tipoVacina = null;

            System.out.println("Escolha o tipo de vacinação que será feita: ");
            System.out.println("[1] - OBRIGATORIA");
            System.out.println("[2] - RECOMENDADA_CAES");
            System.out.println("[3] - RECOMENDADA_GATOS");
            System.out.println("[4] - RECOMENDADA_ROEDORES");
            System.out.println("[5] - OUTRAS");
            Integer op = null;

            while (tipoVacina == null) {

                try {
                    op = sc.nextInt();
                    sc.nextLine();

                    switch (op) {
                        case 1:
                            tipoVacina = "OBRIGATORIA";
                            break;
                        case 2:
                            tipoVacina = "RECOMENDADA_CAES";
                            break;
                        case 3:
                            tipoVacina = "RECOMENDADA_GATOS";
                            break;
                        case 4:
                            tipoVacina = "RECOMENDADA_ROEDORES";
                            break;
                        case 5:
                            tipoVacina = "OUTRAS";
                            break;
                        default:
                            System.out.println("Opção inválida! Tente novamente!");
                            System.out.println("Selecione a opção da vacina correspondente: ");
                            break;
                    }
                }catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("Apenas números!");
                }
            }

            ServicoVacinacao servicoVacinacao = new ServicoVacinacao(nome, descricao, preco, Duration.ofMinutes(duracao), TiposDeVacinacao.valueOf(tipoVacina));

            servicoVacinacaoService.cadastrarServicoVacinacao(servicoVacinacao);

            System.out.println("Serviço Agendado com sucesso!");

            return servicoVacinacao;

        } catch (ExceptionEntitieNotFound e) {
            System.out.println("Erro ao agendar serviço de banho: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao agendar serviço de banho: " + e.getMessage());
        }
        return null;
    }

    public ServicoConsultaVeterinaria servicoConsultaVeterinaria() throws Exception {
        try {

            System.out.println("Agendamento de Serviço De Consulta Veterinária!\n");

            System.out.println("Nome do serviço de Consulta Veterinária (EX: CONSULTA DE ROTINA..) ");
            String nome = sc.nextLine();
            System.out.println("Descrição do serviço: ");
            String descricao = sc.nextLine();
            System.out.println("Preço do serviço: ");
            Double preco = sc.nextDouble();
            System.out.println("Duração do serviço (em minutos): ");
            Integer duracao = null;
            try {
                duracao = sc.nextInt();
                Duration duration = Duration.of(duracao, ChronoUnit.MINUTES);
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Somente Números inteiros!");
            }

            String tipoConsulta = null;

            System.out.println("Escolha o tipo de consulta que será feita: ");
            System.out.println("[1] - ROTINA");
            System.out.println("[2] - ESPECIALIZADA");
            System.out.println("[3] - EMERGENCIA");
            System.out.println("[4] - RETORNO");
            Integer op = null;

            while (tipoConsulta == null) {

                try {
                    op = sc.nextInt();
                    sc.nextLine();

                    switch (op) {
                        case 1:
                            tipoConsulta = "ROTINA";
                            break;
                        case 2:
                            tipoConsulta = "ESPECIALIZADA";
                            break;
                        case 3:
                            tipoConsulta = "EMERGENCIA";
                            break;
                        case 4:
                            tipoConsulta = "RETORNO";
                            break;
                        default:
                            System.out.println("Opção inválida! Tente novamente!");
                            System.out.println("Selecione a opção da Consulta correspondente: ");
                            break;
                    }
                }catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("Apenas números!");
                }
            }

            ServicoConsultaVeterinaria servicoConsultaVeterinaria = new ServicoConsultaVeterinaria(nome, descricao, preco, Duration.ofMinutes(duracao), TiposDeConsultaVeterinaria.valueOf(tipoConsulta));

            servicoConsultaVeterinariaService.cadastrarServicoConsultaVeterinaria(servicoConsultaVeterinaria);

            System.out.println("Serviço Agendado com sucesso!");

            return servicoConsultaVeterinaria;

        } catch (ExceptionEntitieNotFound e) {
            System.out.println("Erro ao agendar serviço de banho: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao agendar serviço de banho: " + e.getMessage());
        }
        return null;
    }

    public void agendamentoBanho()throws Exception{

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate agora = LocalDate.now(); //tratar erros para data não ser agendada para antes de hoje!
            LocalDate dataLimite = agora.plusDays(90); //não pode ultrapassar 90 dias o agendamento

            System.out.println("= AGENDAMENTO = \n");
            System.out.println("Digite a data do agendamento: (dd/MM/yyyy)");
            String data = sc.nextLine();
            LocalDate dataFormatada = LocalDate.parse(data, formatter);

            if(dataFormatada.isBefore(agora)) {
                throw new ExceptionIncorretDate("A Data não pode ser agendada para dias anteriores de hoje!");
            }
            if (dataFormatada.isAfter(dataLimite)) {
                throw new ExceptionIncorretDate("A data de agendamento tem prazo máximo de 90 dias! Escolha outra data");
            }

            System.out.println("Data disponivel!\n");

            System.out.println("Digite o CPF do cliente: ");
            String cpf = sc.nextLine();

            Cliente cliente = clienteService.buscarClientePorCpf(cpf);

            System.out.println("Bem-vindo " + cliente.getNome() + "!");

            List<Pet>petsCliente = petDao.findByCpfDono(cpf);

            System.out.println("Esses são seus Pets cadastrados: \n");
            for (Pet pet : petsCliente) {
                System.out.println(petsCliente);
                System.out.println("ID = " + pet.getId());
                System.out.println();
            }
            System.out.println("Escolha o ID do PET que deseja agendar o serviço: ");

            Integer id = null;
            try {
                id = sc.nextInt();
                sc.nextLine();
            }catch (InputMismatchException e){
                System.out.println("Apenas números!");
                sc.nextLine();
            }

            Pet pet = petDao.findById(id); //se der erro aqui fazer a logica no petIMPL com O ID!
            System.out.println("Hoje o serviço de banho será no " + pet.getNome() + "!");

            List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();

            System.out.println("Nossos funcinarios: \n");
            for (Funcionario funcionario : funcionarios) {
                System.out.println(funcionario);
                System.out.println("ID = " + funcionario.getId());
                System.out.println();
            }
            System.out.println("Escolha o ID do responsavel responsavel pelo serviço: ");

            Integer id2 = null;
            try {
                id2 = sc.nextInt();
                sc.nextLine();
            }catch (InputMismatchException e){
                System.out.println("Apenas números!");
                sc.nextLine();
            }
            Funcionario funcionario = funcionarioDao.findById(id2);

            System.out.println("O Funcionario " + funcionario.getNome() + " será o responsável pelo serviço!");

            ServicoBanho  servicoBanho = servicoBanho();

            if (servicoBanho == null){
                throw new ExceptionEntitieNotFound("Erro! Serviço nulo!");

            }
            Double valor = servicoBanho.getPreco();

            //pegando o id pelos atribrutos unicos do serviço de banho, metodo que tive que pensar lá atras no programa para aplicar agora!

            ServicoBanho servicoBanho1 =  servicoBanhoDao.findByUniqueAtributs(servicoBanho.getNome(), servicoBanho.getDescricao(), servicoBanho.getPreco(), servicoBanho.getComHidratacao());

            Agendamento agendamento = new Agendamento(dataFormatada, servicoBanho1, pet, funcionario, valor);

            agendamentoService.cadastrarAgendamento(agendamento);

            System.out.println("Agendamento realizado com sucesso!");

            //apenas uma firula para mostrar quantos dias faltam para o agendamento!
            //aproveitando op API de Date o máximo...
            long diasParaAgendamento = ChronoUnit.DAYS.between(agora, dataFormatada);

            System.out.println("Faltam " + diasParaAgendamento + " dias para o serviço de banho de " + pet.getNome() + "!");

        }catch (ExceptionEntitieNotFound e){
            System.out.println("Erro ao realizar Agendamento: " + e.getMessage());
        }catch (ExceptionIncorretDate e){
            System.out.println("Erro ao realizar agendamento: " + e.getMessage());
        }catch (DateTimeParseException e){
            System.out.println("Erro de formato de data: certifique que esteja escrevendo a data no formato (dd/MM/yyyy)!");
        }catch (Exception e) {
            System.out.println("Erro ao realizar agendamento: " + e.getMessage());
        }
    }

    public void agendamentoTosa()throws Exception{

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate agora = LocalDate.now(); //tratar erros para data não ser agendada para antes de hoje!
            LocalDate dataLimite = agora.plusDays(90); //não pode ultrapassar 90 dias o agendamento

            System.out.println("= AGENDAMENTO = \n");
            System.out.println("Digite a data do agendamento: (dd/MM/yyyy)");
            String data = sc.nextLine();
            LocalDate dataFormatada = LocalDate.parse(data, formatter);

            if(dataFormatada.isBefore(agora)) {
                throw new ExceptionIncorretDate("A Data não pode ser agendada para dias anteriores de hoje!");
            }
            if (dataFormatada.isAfter(dataLimite)) {
                throw new ExceptionIncorretDate("A data de agendamento tem prazo máximo de 90 dias! Escolha outra data");
            }

            System.out.println("Data disponivel!\n");

            System.out.println("Digite o CPF do cliente: ");
            String cpf = sc.nextLine();

            Cliente cliente = clienteService.buscarClientePorCpf(cpf);

            System.out.println("Bem-vindo " + cliente.getNome() + "!");

            List<Pet>petsCliente = petDao.findByCpfDono(cpf);

            System.out.println("Esses são seus Pets cadastrados: \n");
            for (Pet pet : petsCliente) {
                System.out.println(petsCliente);
                System.out.println("ID = " + pet.getId());
                System.out.println();
            }
            System.out.println("Escolha o ID do PET que deseja agendar o serviço: ");

            Integer id = null;
            try {
                id = sc.nextInt();
                sc.nextLine();
            }catch (InputMismatchException e){
                System.out.println("Apenas números!");
                sc.nextLine();
            }

            Pet pet = petDao.findById(id);
            System.out.println("Hoje o serviço de tosa será no pet " + pet.getNome() + "!");

            List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();

            System.out.println("Nossos funcinarios: \n");
            for (Funcionario funcionario : funcionarios) {
                System.out.println(funcionario);
                System.out.println("ID = " + funcionario.getId());
                System.out.println();
            }
            System.out.println("Escolha o ID do responsavel responsavel pelo serviço: ");

            Integer id2 = null;
            try {
                id2 = sc.nextInt();
                sc.nextLine();
            }catch (InputMismatchException e){
                System.out.println("Apenas números!");
                sc.nextLine();
            }
            Funcionario funcionario = funcionarioDao.findById(id2);

            System.out.println("O Funcionario " + funcionario.getNome() + " será o responsável pelo serviço!");

            ServicoTosa servicoTosa = servicoTosa();

            if (servicoTosa == null){
                throw new ExceptionEntitieNotFound("Erro! Serviço nulo!");
            }
            Double valor = servicoTosa.getPreco();

            //pegando o id pelos atribrutos unicos do serviço de banho, metodo que tive que pensar lá atras no programa para aplicar agora!

            ServicoTosa servicoTosa1 = servicoTosaDao.findByUniqueAtributs(servicoTosa.getNome(), servicoTosa.getDescricao(), servicoTosa.getPreco(), servicoTosa.isIncluiEscovacao(), servicoTosa.isIncluiBanhoPrevio());

            Agendamento agendamento = new Agendamento(dataFormatada, servicoTosa1, pet, funcionario, valor);

            agendamentoService.cadastrarAgendamento(agendamento);

            System.out.println("Agendamento realizado com sucesso!");

            long diasParaAgendamento = ChronoUnit.DAYS.between(agora, dataFormatada);

            System.out.println("Faltam " + diasParaAgendamento + " dias para a tosa de " + pet.getNome() + "!");

        }catch (ExceptionEntitieNotFound e){
            System.out.println("Erro ao realizar Agendamento: " + e.getMessage());
        }catch (ExceptionIncorretDate e){
            System.out.println("Erro ao realizar agendamento: " + e.getMessage());
        }catch (DateTimeParseException e){
            System.out.println("Erro de formato de data: certifique que esteja escrevendo a data no formato (dd/MM/yyyy)!");
        }catch (Exception e) {
            System.out.println("Erro ao realizar agendamento: " + e.getMessage());
        }
    }

    public void agendamentoVacinacao()throws Exception{

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate agora = LocalDate.now(); //tratar erros para data não ser agendada para antes de hoje!
            LocalDate dataLimite = agora.plusDays(90); //não pode ultrapassar 90 dias o agendamento

            System.out.println("= AGENDAMENTO = \n");
            System.out.println("Digite a data do agendamento: (dd/MM/yyyy)");
            String data = sc.nextLine();
            LocalDate dataFormatada = LocalDate.parse(data, formatter);

            if(dataFormatada.isBefore(agora)) {
                throw new ExceptionIncorretDate("A Data não pode ser agendada para dias anteriores de hoje!");
            }
            if (dataFormatada.isAfter(dataLimite)) {
                throw new ExceptionIncorretDate("A data de agendamento tem prazo máximo de 90 dias! Escolha outra data");
            }

            System.out.println("Data disponivel!\n");

            System.out.println("Digite o CPF do cliente: ");
            String cpf = sc.nextLine();

            Cliente cliente = clienteService.buscarClientePorCpf(cpf);

            System.out.println("Bem-vindo " + cliente.getNome() + "!");

            List<Pet>petsCliente = petDao.findByCpfDono(cpf);

            System.out.println("Esses são seus Pets cadastrados: \n");
            for (Pet pet : petsCliente) {
                System.out.println(petsCliente);
                System.out.println("ID = " + pet.getId());
                System.out.println();
            }
            System.out.println("Escolha o ID do PET que deseja agendar o serviço: ");

            Integer id = null;
            try {
                id = sc.nextInt();
                sc.nextLine();
            }catch (InputMismatchException e){
                System.out.println("Apenas números!");
                sc.nextLine();
            }

            Pet pet = petDao.findById(id);
            System.out.println("Hoje o serviço de tosa será no pet " + pet.getNome() + "!");

            List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();

            System.out.println("Nossos funcinarios: \n");
            for (Funcionario funcionario : funcionarios) {
                System.out.println(funcionario);
                System.out.println("ID = " + funcionario.getId());
                System.out.println();
            }
            System.out.println("Escolha o ID do responsavel responsavel pelo serviço: ");

            Integer id2 = null;
            try {
                id2 = sc.nextInt();
                sc.nextLine();
            }catch (InputMismatchException e){
                System.out.println("Apenas números!");
                sc.nextLine();
            }
            Funcionario funcionario = funcionarioDao.findById(id2);

            System.out.println("O Funcionario " + funcionario.getNome() + " será o responsável pelo serviço!");

            ServicoVacinacao servicoVacinacao = servicoVacinacao();

            if (servicoVacinacao == null){
                throw new ExceptionEntitieNotFound("Erro! Serviço nulo!");
            }
            Double valor = servicoVacinacao.getPreco();

            //pegando o id pelos atribrutos unicos do serviço de banho, metodo que tive que pensar lá atras no programa para aplicar agora!

            ServicoVacinacao servicoVacinacao1 = servicoVacinacaoDao.findByUniqueAtributs(servicoVacinacao.getNome(), servicoVacinacao.getDescricao(), servicoVacinacao.getTipoDeVacinacao());

            Agendamento agendamento = new Agendamento(dataFormatada, servicoVacinacao1, pet, funcionario, valor);

            agendamentoService.cadastrarAgendamento(agendamento);

            System.out.println("Agendamento realizado com sucesso!");

            long diasParaAgendamento = ChronoUnit.DAYS.between(agora, dataFormatada);

            System.out.println("Faltam " + diasParaAgendamento + " dias para a vacinação de " + pet.getNome() + "!");

        }catch (ExceptionEntitieNotFound e){
            System.out.println("Erro ao realizar Agendamento: " + e.getMessage());
        }catch (ExceptionIncorretDate e){
            System.out.println("Erro ao realizar agendamento: " + e.getMessage());
        }catch (DateTimeParseException e){
            System.out.println("Erro de formato de data: certifique que esteja escrevendo a data no formato (dd/MM/yyyy)!");
        }catch (Exception e) {
            System.out.println("Erro ao realizar agendamento: " + e.getMessage());
        }
    }

    public void agendamentoConsultaVeterinaria()throws Exception{

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate agora = LocalDate.now(); //tratar erros para data não ser agendada para antes de hoje!
            LocalDate dataLimite = agora.plusDays(90); //não pode ultrapassar 90 dias o agendamento

            System.out.println("= AGENDAMENTO = \n");
            System.out.println("Digite a data do agendamento: (dd/MM/yyyy)");
            String data = sc.nextLine();
            LocalDate dataFormatada = LocalDate.parse(data, formatter);

            if(dataFormatada.isBefore(agora)) {
                throw new ExceptionIncorretDate("A Data não pode ser agendada para dias anteriores de hoje!");
            }
            if (dataFormatada.isAfter(dataLimite)) {
                throw new ExceptionIncorretDate("A data de agendamento tem prazo máximo de 90 dias! Escolha outra data");
            }

            System.out.println("Data disponivel!\n");

            System.out.println("Digite o CPF do cliente: ");
            String cpf = sc.nextLine();

            Cliente cliente = clienteService.buscarClientePorCpf(cpf);

            System.out.println("Bem-vindo " + cliente.getNome() + "!");

            List<Pet>petsCliente = petDao.findByCpfDono(cpf);

            System.out.println("Esses são seus Pets cadastrados: \n");
            for (Pet pet : petsCliente) {
                System.out.println(petsCliente);
                System.out.println("ID = " + pet.getId());
                System.out.println();
            }
            System.out.println("Escolha o ID do PET que deseja agendar o serviço: ");

            Integer id = null;
            try {
                id = sc.nextInt();
                sc.nextLine();
            }catch (InputMismatchException e){
                System.out.println("Apenas números!");
                sc.nextLine();
            }

            Pet pet = petDao.findById(id);
            System.out.println("Hoje o serviço de tosa será no pet " + pet.getNome() + "!");

            List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();

            System.out.println("Nossos funcinarios: \n");
            for (Funcionario funcionario : funcionarios) {
                System.out.println(funcionario);
                System.out.println("ID = " + funcionario.getId());
                System.out.println();
            }
            System.out.println("Escolha o ID do responsavel responsavel pelo serviço: ");

            Integer id2 = null;
            try {
                id2 = sc.nextInt();
                sc.nextLine();
            }catch (InputMismatchException e){
                System.out.println("Apenas números!");
                sc.nextLine();
            }
            Funcionario funcionario = funcionarioDao.findById(id2);

            System.out.println("O Funcionario " + funcionario.getNome() + " será o responsável pelo serviço!");

            ServicoConsultaVeterinaria servicoConsultaVeterinaria = servicoConsultaVeterinaria();

            if (servicoConsultaVeterinaria == null){
                throw new ExceptionEntitieNotFound("Erro! Serviço nulo!");
            }
            Double valor = servicoConsultaVeterinaria.getPreco();

            //pegando o id pelos atribrutos unicos do serviço de banho, metodo que tive que pensar lá atras no programa para aplicar agora!

            ServicoConsultaVeterinaria servicoConsultaVeterinaria1 = servicoConsultaVeterinariaDao.findByUniqueAtributs(servicoConsultaVeterinaria.getNome(), servicoConsultaVeterinaria.getDescricao(), servicoConsultaVeterinaria.getPreco(), servicoConsultaVeterinaria.getTipoDeConsulta());

            Agendamento agendamento = new Agendamento(dataFormatada, servicoConsultaVeterinaria1, pet, funcionario, valor);

            agendamentoService.cadastrarAgendamento(agendamento);

            System.out.println("Agendamento realizado com sucesso!");

            long diasParaAgendamento = ChronoUnit.DAYS.between(agora, dataFormatada);

            System.out.println("Faltam " + diasParaAgendamento + " dias para a Consulta Veterinária de " + pet.getNome() + "!");

        }catch (ExceptionEntitieNotFound e){
            System.out.println("Erro ao realizar Agendamento: " + e.getMessage());
        }catch (ExceptionIncorretDate e){
            System.out.println("Erro ao realizar agendamento: " + e.getMessage());
        }catch (DateTimeParseException e){
            System.out.println("Erro de formato de data: certifique que esteja escrevendo a data no formato (dd/MM/yyyy)!");
        }catch (Exception e) {
            System.out.println("Erro ao realizar agendamento: " + e.getMessage());
        }
    }

    public void listarServicos()throws Exception{
        try {
            System.out.println("\n");
            int i = 0;
            List<ServicoBanho> servicoBanho = servicoBanhoService.listarServicoBanho();
            System.out.println("Serviços de banhos cadastrados: (max 10)\n");
            for (ServicoBanho servicoBanho1 : servicoBanho) {
                System.out.println(servicoBanho1);
                System.out.println("ID: " + servicoBanho1.getId());
                System.out.println();
                i++;
                if (i == 10) {
                    break;
                }
                //Não mostrar todos os serviços do banco de dados cadastrados
            }
            int i2 = 0;
            List<ServicoTosa> servicoTosa = servicoTosaService.listarServicoTosa();
            System.out.println("Serviços de Tosas cadastrados: (max 10)\n");
            for (ServicoTosa servicoTosa1 : servicoTosa) {
                System.out.println(servicoTosa1);
                System.out.println("ID: " + servicoTosa1.getId());
                System.out.println();
                i2++;
                if (i2 == 10) {
                    break;
                }
            }
            int i3 = 0;
            List<ServicoVacinacao> servicoVacinacao = servicoVacinacaoService.listarServicoVacinacao();
            System.out.println("Serviços de Vacinação cadastrados: (max 10)\n");
            for (ServicoVacinacao servicoVacinacao1 : servicoVacinacao) {
                System.out.println(servicoVacinacao1);
                System.out.println("ID: " + servicoVacinacao1.getId());
                System.out.println();
                i3++;
                if (i3 == 10) {
                    break;
                }
            }
            int i4 = 0;
            List<ServicoConsultaVeterinaria> servicoConsultaVeterinarias = servicoConsultaVeterinariaService.listarServicoConsultaVeterinaria();
            System.out.println("Serviços de Consultas veterinarias cadastradas: (max 10)\n");
            for (ServicoConsultaVeterinaria servicoConsultaVeterinarias1 : servicoConsultaVeterinarias) {
                System.out.println(servicoConsultaVeterinarias1);
                System.out.println("ID: " + servicoConsultaVeterinarias1.getId());
                System.out.println();
                i4++;
                if (i4 == 10) {
                    break;
                }
            }
        }catch (ExceptionEntitieNotFound e){
            System.out.println("Erro ao buscar lista: " + e.getMessage());
        }
    }

    public void atualizarAgendamento()throws ExceptionEntitieNotFound{

        try {
            System.out.println("Digite o ID do pet que tem o agendamento que deseja atualizar: ");
            Integer id = sc.nextInt();

            Pet pet = petDao.findById(id);

            List<Agendamento> agendamentosDoPet = agendamentoDao.findBypet(pet);
            if (agendamentosDoPet.isEmpty()) {
                throw new ExceptionEntitieNotFound("Nenhum agendamento para esse Pet ainda!");
            }
            System.out.println("Os agendamentos do pet " + pet.getNome() + "!\n");
            for (Agendamento agendamento : agendamentosDoPet) {
                System.out.println(agendamento);
                System.out.println("ID DO AGENDAMENTO:" + agendamento.getId());
                System.out.println();
            }
            System.out.println("Escolha o id do agendamento que deseja atualizar: ");
            Integer id2  = sc.nextInt();
            sc.nextLine();

            Agendamento agendamento = agendamentoDao.findById(id2);

            if (agendamento == null) {
                throw new ExceptionEntitieNotFound("Agendamento nulo!");
            }

            System.out.println("O que você deseja atualizar de agendamento? ");
            System.out.println("[1] - Data");
            System.out.println("[2] - Valor");
            Integer op3 = sc.nextInt();
            sc.nextLine();

            switch (op3) {
                case 1:
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate agora = LocalDate.now();
                    LocalDate dataLimite = agora.plusDays(90);

                    System.out.println("Digite a nova data do agendamento (dd/MM/yyyy) :");
                    String data = sc.nextLine();
                    LocalDate dataFormatada = LocalDate.parse(data, formatter);

                    if(dataFormatada.isBefore(agora)) {
                        throw new ExceptionIncorretDate("A Data não pode ser agendada para dias anteriores de hoje!");
                    }
                    if (dataFormatada.isAfter(dataLimite)) {
                        throw new ExceptionIncorretDate("A data de agendamento tem prazo máximo de 90 dias! Escolha outra data");
                    }
                    System.out.println("Data disponivel!\n");
                    agendamento.setDataAgendamento(dataFormatada);
                    agendamentoService.alterarAgendamento(agendamento);
                    System.out.println("Atualização feita com sucesso!");
                    break;
                case 2:
                    System.out.println("Digite o novo preço desse agendamento: ");
                    Double preco = sc.nextDouble();
                    sc.nextLine();

                    agendamento.setValor(preco);
                    agendamentoService.cadastrarAgendamento(agendamento);
                    System.out.println("Atualização feita com sucesso!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }catch (ExceptionEntitieNotFound e){
            System.out.println("Erro ao tentar atualizar agendamento: " + e.getMessage());
        }catch (InputMismatchException e){
            System.out.println("Apenas números!");
            sc.nextLine();
        }catch (Exception e){
            System.out.println("Erro ao atualiazar agendamento: " + e.getMessage());
        }
    }

    public void removerAgendamento()throws Exception{

        try {

            System.out.println("Digite o ID do pet que tem o agendamento que deseja remover: ");
            Integer id = sc.nextInt();

            Pet pet = petDao.findById(id);

            List<Agendamento> agendamentosDoPet = agendamentoDao.findBypet(pet);
            if (agendamentosDoPet.isEmpty()) {
                throw new ExceptionEntitieNotFound("Nenhum agendamento para esse Pet ainda!");
            }
            System.out.println("Os agendamentos do pet " + pet.getNome() + "!\n");
            for (Agendamento agendamento : agendamentosDoPet) {
                System.out.println(agendamento);
                System.out.println("ID DO AGENDAMENTO:" + agendamento.getId());
                System.out.println();
            }
            System.out.println("Escolha o id do agendamento que deseja remover: ");
            Integer id2 = sc.nextInt();
            sc.nextLine();

            Agendamento agendamento = agendamentoDao.findById(id2);

            if (agendamento == null) {
                throw new ExceptionEntitieNotFound("Agendamento nulo!");
            }
            System.out.println("Deseja remover o agendamento de " + agendamento.getPet().getNome() + " com a data de: " + agendamento.getDataAgendamento() + "?");
            System.out.println("[1]-SIM   [2]-NÃO");
            try {
                int op = sc.nextInt();
                sc.nextLine();
                switch (op) {
                    case 1:
                        agendamentoService.excluirAgendamentoById(agendamento.getId());
                        System.out.println("Agendamento excluido!");
                        break;
                    case 2:
                        System.out.println("Agendamento não excluido!");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Apenas números!");
            }
        }catch (ExceptionEntitieNotFound e){
            System.out.println("Erro ao excluir agendamento: " + e.getMessage());
        }
    }
}
