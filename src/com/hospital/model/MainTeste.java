package com.hospital.model;
import java.util.Scanner;
public class MainTeste {
    public static void main(String[] args) {
        // Inicializa o Scanner para ler o teclado e o nosso Gerenciador
        Scanner scanner = new Scanner(System.in);
        GerenciadorDePacientes gerenciador = new GerenciadorDePacientes();
        boolean rodando = true;

        System.out.println("=== BEM-VINDO AO SISTEMA HOSPITALAR ===");

        // O 'while' mantém o menu rodando até o usuário escolher a opção de Sair
        while (rodando) {
            System.out.println("\nO que voce deseja fazer?");
            System.out.println("1. Cadastrar novo paciente");
            System.out.println("2. Buscar paciente pelo CPF");
            System.out.println("3. Atualizar plano de saude de um paciente");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opcao: ");

            // Lemos tudo como texto (String) para evitar o famoso bug do 'Enter' no Java
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    cadastrarViaChatbot(scanner, gerenciador);
                    break;
                case "2":
                    buscarViaChatbot(scanner, gerenciador);
                    break;
                case "3":
                    atualizarPlanoViaChatbot(scanner, gerenciador);
                    break;
                case "4":
                    System.out.println("Encerrando o sistema. Ate logo!");
                    rodando = false; // Isso quebra o loop e finaliza o programa
                    break;
                default:
                    System.out.println("Opcao invalida! Tente novamente.");
            }
        }

        scanner.close(); // Sempre bom fechar o scanner no final
    }

    // --- MÉTODOS AUXILIARES PARA DEIXAR O MAIN LIMPO E ORGANIZADO ---

    private static void cadastrarViaChatbot(Scanner scanner, GerenciadorDePacientes gerenciador) {
        System.out.println("\n-- CADASTRO DE PACIENTE --");
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CPF (apenas 11 numeros): ");
        String cpf = scanner.nextLine();

        System.out.print("Digite o telefone (apenas 11 numeros): ");
        String telefone = scanner.nextLine();

        System.out.println("O paciente possui plano de saude? (1 - Sim / 2 - Nao, Particular)");
        String respostaPlano = scanner.nextLine();

        PlanoDeSaude plano;

        if (respostaPlano.equals("2")) {
            plano = PlanoDeSaude.criarPlanoParticular();
        } else {
            System.out.print("Nome da operadora (Ex: Unimed, Hapvida): ");
            String operadora = scanner.nextLine();

            System.out.print("Tipo de internacao permitida (1 - ENFERMARIA / 2 - APARTAMENTO): ");
            String respostaLeito = scanner.nextLine();
            TipoLeito tipoLeito;
            if (respostaLeito.equals("2")) {
                tipoLeito = TipoLeito.APARTAMENTO;
            } else {
                tipoLeito = TipoLeito.ENFERMARIA;
            }

            System.out.print("O plano tem coparticipacao de 15%? (1 - Sim / 2 - Nao): ");
            String respostaCopart = scanner.nextLine();
            double coparticipacao;
            if (respostaCopart.equals("1")) {
                coparticipacao = 0.15;
            } else {
                coparticipacao = 0.0;
            }

            plano = new PlanoDeSaude(operadora, tipoLeito, coparticipacao);
        }

        // Tenta cadastrar. Se o CPF for inválido ou repetido, a exceção é capturada!
        try {
            gerenciador.cadastrarPaciente(nome, cpf, telefone, plano);
            System.out.println(">>> Paciente cadastrado com sucesso! <<<");
        } catch (IllegalArgumentException e) {
            System.out.println(">>> ERRO: " + e.getMessage() + " <<<");
        }
    }

    private static void buscarViaChatbot(Scanner scanner, GerenciadorDePacientes gerenciador) {
        System.out.println("\n-- BUSCA DE PACIENTE --");
        System.out.print("Digite o CPF do paciente: ");
        String cpf = scanner.nextLine();

        Paciente paciente = gerenciador.buscarPaciente(cpf);

        if (paciente != null) {
            System.out.println("\n>>> PACIENTE ENCONTRADO <<<");
            System.out.println("Nome: " + paciente.getNome());
            System.out.println("Telefone: " + paciente.getTelefone());
            System.out.println("Plano: " + paciente.getPlanoDeSaude().getNomeOperadora());
            System.out.println("Acomodacao: " + paciente.getPlanoDeSaude().getTipoPermitido());
        } else {
            System.out.println(">>> ERRO: Nenhum paciente encontrado com esse CPF. <<<");
        }
    }

    private static void atualizarPlanoViaChatbot(Scanner scanner, GerenciadorDePacientes gerenciador) {
        System.out.println("\n-- ATUALIZAR PLANO DE SAUDE --");
        System.out.print("Digite o CPF do paciente que deseja atualizar: ");
        String cpf = scanner.nextLine();

        // Verifica se o paciente existe antes de pedir os dados do novo plano
        if (gerenciador.buscarPaciente(cpf) == null) {
            System.out.println(">>> ERRO: Paciente nao encontrado! <<<");
            return; // Sai do método e volta pro menu
        }

        System.out.println("Criando novo plano para o paciente...");
        System.out.print("Nome da nova operadora: ");
        String operadora = scanner.nextLine();

        System.out.print("Tipo de internacao (1 - ENFERMARIA / 2 - APARTAMENTO): ");
        String respostaLeito = scanner.nextLine();
        TipoLeito tipoLeito;
        if (respostaLeito.equals("2")) {
            tipoLeito = TipoLeito.APARTAMENTO;
        } else {
            tipoLeito = TipoLeito.ENFERMARIA;
        }

        System.out.print("Tem coparticipacao de 15%? (1 - Sim / 2 - Nao): ");
        String respostaCopart = scanner.nextLine();
        double coparticipacao;
        if (respostaCopart.equals("1")) {
            coparticipacao = 0.15;
        } else {
            coparticipacao = 0.0;
        }

        PlanoDeSaude novoPlano = new PlanoDeSaude(operadora, tipoLeito, coparticipacao);

        try {
            gerenciador.atualizarPlanoDeSaude(cpf, novoPlano);
            System.out.println(">>> Plano de saude atualizado com sucesso! <<<");
        } catch (IllegalArgumentException e) {
            System.out.println(">>> ERRO: " + e.getMessage() + " <<<");
        }
    }
}