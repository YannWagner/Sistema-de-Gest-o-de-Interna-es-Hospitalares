package com.hospital.model;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class MainTeste {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Tenta carregar os dados salvos anteriormente
        Hospital hospital = Hospital.carregarHospital("hospital.ser");
        if (hospital == null) {
            hospital = new Hospital();
        }

        // Puxa os gerenciadores de dentro do Hospital (para persistência funcionar)
        GerenciadorDePacientes gerenciadorPacientes = hospital.getGerenciadorPacientes();
        GerenciadorDeMedicos gerenciadorMedicos = hospital.getGerenciadorMedicos();

        boolean rodando = true;

        System.out.println("BEM-VINDO AO SISTEMA HOSPITALAR 2026");

        while (rodando) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Modulo de PACIENTES");
            System.out.println("2. Modulo de MEDICOS");
            System.out.println("3. Modulo de INTERNACOES E ALTAS");
            System.out.println("4. Gerar Relatorio Financeiro (TXT)");
            System.out.println("5. Sair e Salvar Sistema");
            System.out.print("Escolha uma opcao: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    menuPacientes(scanner, gerenciadorPacientes, hospital);
                    break;
                case "2":
                    menuMedicos(scanner, gerenciadorMedicos, hospital);
                    break;
                case "3":
                    menuInternacoes(scanner, hospital);
                    break;
                case "4":
                    hospital.getGerenciadorPagamentos().gerarRelatorioFinanceiroTXT();
                    break;
                case "5":
                    System.out.println("Salvando dados e encerrando. Ate logo!");
                    hospital.salvarHospital("hospital.ser");
                    rodando = false;
                    break;
                default:
                    System.out.println("Opcao invalida! Tente novamente.");
            }
        }
        scanner.close();
    }

    private static void menuPacientes(Scanner scanner, GerenciadorDePacientes gerenciador, Hospital hospital) {
        boolean noMenuPaciente = true;
        while (noMenuPaciente) {
            System.out.println("\n-- MODULO DE PACIENTES --");
            System.out.println("1. Cadastrar novo paciente");
            System.out.println("2. Buscar paciente pelo CPF");
            System.out.println("3. Atualizar plano de saude");
            System.out.println("4. Voltar ao Menu Principal");
            System.out.print("Escolha: ");
            String op = scanner.nextLine();

            switch (op) {
                case "1":
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("CPF (11 numeros): ");
                    String cpf = scanner.nextLine();
                    System.out.print("Telefone (11 numeros): ");
                    String telefone = scanner.nextLine();

                    System.out.println("Plano de saude? (1 - Sim / 2 - Nao, Particular)");
                    PlanoDeSaude plano;
                    if (scanner.nextLine().equals("2")) {
                        plano = PlanoDeSaude.criarPlanoParticular();
                    } else {
                        System.out.print("Operadora: ");
                        String operadora = scanner.nextLine();
                        System.out.print("Internacao (1 - ENFERMARIA / 2 - APARTAMENTO): ");
                        TipoLeito leito = scanner.nextLine().equals("2") ? TipoLeito.APARTAMENTO : TipoLeito.ENFERMARIA;
                        System.out.print("Coparticipacao 15%? (1 - Sim / 2 - Nao): ");
                        double coparticao = scanner.nextLine().equals("1") ? 0.15 : 0.0;
                        plano = new PlanoDeSaude(operadora, leito, coparticao);
                    }

                    try {
                        gerenciador.cadastrarPaciente(nome, cpf, telefone, plano);
                        hospital.salvarHospital("hospital.ser"); // Salva alteração
                        System.out.println("> Paciente cadastrado com sucesso!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("> ERRO: " + e.getMessage());
                    }
                    break;
                case "2":
                    System.out.print("CPF para busca: ");
                    Paciente p = gerenciador.buscarPaciente(scanner.nextLine());
                    if (p != null) System.out.println("Encontrado: " + p.getNome() + " | Plano: " + p.getPlanoDeSaude().getNomeOperadora());
                    else System.out.println("> ERRO: Paciente nao encontrado.");
                    break;
                case "3":
                    System.out.print("CPF do paciente: ");
                    String cpfAtualizar = scanner.nextLine();
                    if (gerenciador.buscarPaciente(cpfAtualizar) == null) {
                        System.out.println(">>> ERRO: Paciente nao encontrado! <<<");
                        break;
                    }
                    System.out.print("Nova Operadora: ");
                    String novaOp = scanner.nextLine();
                    System.out.print("Internacao (1 - ENFERMARIA / 2 - APARTAMENTO): ");
                    TipoLeito novoLeito = scanner.nextLine().equals("2") ? TipoLeito.APARTAMENTO : TipoLeito.ENFERMARIA;
                    System.out.print("Coparticipacao 15%? (1 - Sim / 2 - Nao): ");
                    double novaCopart = scanner.nextLine().equals("1") ? 0.15 : 0.0;

                    try {
                        gerenciador.atualizarPlanoDeSaude(cpfAtualizar, new PlanoDeSaude(novaOp, novoLeito, novaCopart));
                        hospital.salvarHospital("hospital.ser"); // Salva alteração
                        System.out.println("> Plano atualizado!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("> ERRO: " + e.getMessage());
                    }
                    break;
                case "4":
                    noMenuPaciente = false;
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }

    private static void menuMedicos(Scanner scanner, GerenciadorDeMedicos gerenciador, Hospital hospital) {
        boolean noMenuMedico = true;
        while (noMenuMedico) {
            System.out.println("\n-- MODULO DE MEDICOS --");
            System.out.println("1. Cadastrar novo medico");
            System.out.println("2. Buscar medico pelo CRM");
            System.out.println("3. Listar medicos ativos");
            System.out.println("4. Remover medico (Demitir/Inativar)");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha: ");
            String op = scanner.nextLine();

            switch (op) {
                case "1":
                    System.out.print("Nome do medico: ");
                    String nome = scanner.nextLine();
                    System.out.print("CRM (Ex: 123456/PB): ");
                    String crm = scanner.nextLine();

                    System.out.println("Escolha a Especialidade:");
                    System.out.println("1 - CLINICA MEDICA GERAL");
                    System.out.println("2 - CARDIOLOGIA");
                    System.out.println("3 - GERIATRIA");
                    System.out.println("4 - ORTOPEDIA");
                    System.out.println("5 - CIRURGIA");
                    System.out.println("6 - MEDICINA INTENSIVA");
                    System.out.print("Opcao: ");
                    String espOp = scanner.nextLine();

                    Especialidade especialidade;
                    switch (espOp) {
                        case "2": especialidade = Especialidade.CARDIOLOGIA; break;
                        case "3": especialidade = Especialidade.GERIATRIA; break;
                        case "4": especialidade = Especialidade.ORTOPEDIA; break;
                        case "5": especialidade = Especialidade.CIRURGIA; break;
                        case "6": especialidade = Especialidade.MEDICINA_INTENSIVA; break;
                        default: especialidade = Especialidade.CLINICA_MEDICA_GERAL; break;
                    }

                    try {
                        gerenciador.cadastrarMedico(nome, crm, especialidade);
                        hospital.salvarHospital("hospital.ser"); // Salva alteração
                        System.out.println("> Medico cadastrado com sucesso!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("> ERRO: " + e.getMessage());
                    }
                    break;
                case "2":
                    System.out.print("CRM para busca: ");
                    Medico m = gerenciador.buscarMedico(scanner.nextLine());
                    if (m != null) {
                        System.out.println("Encontrado: Dr(a). " + m.getNome() + " | Especialidade: " + m.getEspecialidade());
                        System.out.println("Status: " + (m.isAtivo() ? "ATIVO" : "INATIVO"));
                    } else {
                        System.out.println("> ERRO: Medico nao encontrado.");
                    }
                    break;
                case "3":
                    ArrayList<Medico> ativos = gerenciador.listarMedicosAtivos();
                    if (ativos.isEmpty()) {
                        System.out.println("Nenhum medico ativo no momento.");
                    } else {
                        System.out.println("\n--- MEDICOS ATIVOS ---");
                        for (int i = 0; i < ativos.size(); i++) {
                            Medico med = ativos.get(i);
                            System.out.println("- " + med.getNome() + " (CRM: " + med.getCrm() + ")");
                        }
                    }
                    break;
                case "4":
                    System.out.print("CRM do medico a ser removido: ");
                    String crmRemover = scanner.nextLine();
                    try {
                        gerenciador.removerMedico(crmRemover);
                        hospital.salvarHospital("hospital.ser"); // Salva alteração
                        System.out.println("> Medico inativado com sucesso!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("> ERRO: " + e.getMessage());
                    }
                    break;
                case "5":
                    noMenuMedico = false;
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }

    private static void menuInternacoes(Scanner scanner, Hospital hospital) {
        boolean noMenuInter = true;
        while (noMenuInter) {
            System.out.println("\n-- MODULO DE INTERNACOES E ALTAS --");
            System.out.println("1. Registrar nova internacao");
            System.out.println("2. Registrar alta e pagamento");
            System.out.println("3. Voltar ao Menu Principal");
            System.out.print("Escolha: ");
            String op = scanner.nextLine();

            switch (op) {
                case "1":
                    System.out.print("CPF do Paciente: ");
                    Paciente p = hospital.getGerenciadorPacientes().buscarPaciente(scanner.nextLine());
                    System.out.print("CRM do Medico: ");
                    Medico m = hospital.getGerenciadorMedicos().buscarMedico(scanner.nextLine());

                    if (p == null || m == null) {
                        System.out.println("> ERRO: Paciente ou Medico nao encontrado!");
                    } else {
                        System.out.println("Tipo de Leito: 1-ENFERMARIA 2-APARTAMENTO 3-UTI");
                        int t = Integer.parseInt(scanner.nextLine());
                        TipoLeito tl = (t == 2) ? TipoLeito.APARTAMENTO : (t == 3) ? TipoLeito.UTI : TipoLeito.ENFERMARIA;
                        System.out.print("Localizacao (Quarto/Ala): ");
                        String local = scanner.nextLine();
                        try {
                            hospital.getGerenciadorInternacoes().registrarInternacao(p, m, tl, local, LocalDate.now());
                            hospital.salvarHospital("hospital.ser");
                            System.out.println("> Internacao registrada!");
                        } catch (Exception e) {
                            System.out.println("> ERRO: " + e.getMessage());
                        }
                    }
                    break;
                case "2":
                    System.out.print("ID da Internacao: ");
                    Internacao inter = hospital.getGerenciadorInternacoes().buscarPorId(scanner.nextLine());
                    if (inter != null) {
                        inter.setDataAlta(LocalDate.now());
                        double vBase = inter.calcularValorPaciente();
                        System.out.println("Valor base com plano: R$ " + vBase);
                        System.out.println("Forma Pagamento: 1-PIX(-10%) 2-Parcelado(+8%) 3-Cartao Vista");
                        int pg = Integer.parseInt(scanner.nextLine());
                        double vFinal = hospital.getGerenciadorPagamentos().calcularValorFinal(vBase, pg);
                        hospital.getGerenciadorPagamentos().registrarPagamento(inter, vFinal, "Opcao " + pg);
                        hospital.salvarHospital("hospital.ser");
                        System.out.println("> Alta concluida! Valor final: R$ " + vFinal);
                    } else {
                        System.out.println("> ERRO: Internacao nao encontrada.");
                    }
                    break;
                case "3":
                    noMenuInter = false;
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }
}