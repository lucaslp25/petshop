package application;

import dao.AgendamentoDao;
import dao.DaoFactory;
import dao.ItemVendaDao;
import dao.VendaDao;
import entities.Agendamento;
import entities.ItemVenda;
import entities.Venda;
import exceptions.ExceptionEntitieNotFound;
import services.impl.AgendamentoServiceImpl;
import services.impl.ItemVendaServiceImpl;
import services.impl.VendaServiceImpl;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.format.TextStyle;

public class MenuRelatorios {

    Scanner sc = new Scanner(System.in);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void relatorioVendas() {

        VendaDao vendaDao = DaoFactory.createVendaDao();
        ItemVendaDao itemVendaDao = DaoFactory.createItemVendaDao();

        try {
            System.out.println("Digite a data que desejar ver o relatorio: (dd/mm/yyyy) ");
            String data = sc.nextLine();
            LocalDate dataFormatada = LocalDate.parse(data, formatter);
            String dataAno = data.substring(data.length() - 4);

            Integer dataMes = dataFormatada.getMonthValue();
            Integer ano = dataFormatada.getYear();

            Month mes = dataFormatada.getMonth();
            //um jeito de se obter o nome do mes em portugues e de uma maneira amigavel
            String nomeDoMes = mes.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));

            //pegando a lista de vendas do respectivo mês e ano!
            List<Venda> vendasMensais = vendaDao.findVendasByMonthAndYear(dataMes, ano);

            if (vendasMensais.isEmpty()) {
                throw new ExceptionEntitieNotFound("Nenhum venda para esse mês!");
            } else {
                System.out.println("RELATÓRIO DE VENDAS DE PRODUTOS NO MÊS DE " + nomeDoMes.toUpperCase() + " DE " + dataAno);
            }
            System.out.println();
            for (Venda vendas : vendasMensais) {
                System.out.println(vendas.toRelatorioString());
            }

            Map<Integer, List<ItemVenda>> itensVenda = new TreeMap<>();

            for (Venda vendas : vendasMensais) {
                itensVenda.put(vendas.getId(), itemVendaDao.findByVendaId(vendas.getId()));
            }

            if (itensVenda.isEmpty()) {
                throw new ExceptionEntitieNotFound("Nenhum item na lista de Itens de venda!");
            } else {
                System.out.println("ITENS DE VENDA DO RELATÓRIO!\n");
            }

            List<ItemVenda> itensVendaLista = new ArrayList<>();
            for (Map.Entry<Integer, List<ItemVenda>> entry : itensVenda.entrySet()) {
                Integer id = entry.getKey();
                itensVendaLista.addAll(entry.getValue());
            }

            for (ItemVenda itens : itensVendaLista) {
                System.out.println(itens.relatorioTostring());
                System.out.println();
            }

            Double valorTotalMes = 0.0;
            if (vendasMensais.isEmpty()) {
                throw new ExceptionEntitieNotFound("Nenhum valor arrecado esse mês!");
            } else {
                for (Venda vendas : vendasMensais) {
                    valorTotalMes += vendas.getValorTotal();
                }
                System.out.println("O VALOR TOTAL ARRECADADO TOTAL DO MÊS DE " + nomeDoMes.toUpperCase() + " DE " + dataAno + " FOI DE R$" + String.format("%.2f", valorTotalMes));
            }
        } catch (DateTimeParseException e) {
            System.out.println("Erro de formato de data inválido: " + e.getMessage());
        } catch (ExceptionEntitieNotFound e) {
            System.out.println("Erro ao exibir relatório mensal: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao exibir relatório: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void relatorioDeAgendamentos() {

        AgendamentoDao agendamentoDao = DaoFactory.createAgendamentoDao();

        try {
            System.out.println("Digite a data que desejar ver o relatorio: (dd/mm/yyyy) ");
            String data = sc.nextLine();
            LocalDate dataFormatada = LocalDate.parse(data, formatter);
            String dataAno = data.substring(data.length() - 4);

            Integer dataMes = dataFormatada.getMonthValue();
            Integer ano = dataFormatada.getYear();

            Month mes = dataFormatada.getMonth();

            String nomeDoMes = mes.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));

            List<Agendamento> agendamentosMensais = agendamentoDao.findByMonthAndYear(dataMes, ano);

            if (agendamentosMensais.isEmpty()) {
                throw new ExceptionEntitieNotFound("Nenhum Agendamento para esse mês!");
            } else {
                System.out.println("RELATÓRIO DE AGENDAMENTOS DE SERVIÇOS NO MÊS DE " + nomeDoMes.toUpperCase() + " DE " + dataAno);

                System.out.println();
                for (Agendamento agendamentos: agendamentosMensais){
                    System.out.println(agendamentos.toRelatorioString());
                    System.out.println();
                }

                Integer totalAgendamentos = agendamentosMensais.size();
                Double valorTotal = 0.0;

                for (Agendamento agendamentos : agendamentosMensais) {
                    valorTotal += agendamentos.getValor();
                }

                System.out.println("MÊS DE " + nomeDoMes.toUpperCase() + " DE " + dataAno);
                System.out.println("\n TEVE UM TOTAL DE "+ totalAgendamentos + " AGENDAMENTOS!");
                System.out.println("\n ARRECADAMENTO DE " + String.format("%.2f",valorTotal) + " COM AGENDAMENTOS!");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Erro de formato de data inválido: " + e.getMessage());
        } catch (ExceptionEntitieNotFound e) {
            System.out.println("Erro ao exibir relatório mensal: " + e.getMessage());

        }catch(NullPointerException e){
            System.out.println("Erro de nullPointer " + e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println("Erro inesperado ao exibir relatório: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
