package com.hospital.service;

import com.hospital.model.Internacao;

import java.io.Serializable;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class GerenciadorDePagamentos implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> historicoPagamentos;
    private double totalRecebidoHospital;

    public GerenciadorDePagamentos() {
        this.historicoPagamentos = new ArrayList<>();
        this.totalRecebidoHospital = 0.0;
    }

    public double calcularValorFinal(double valorBase, int opcao) {
        if (opcao == 1) {
            return valorBase * 0.90; // PIX ou Dinheiro (10% desconto)
        } else if (opcao == 2) {
            return valorBase * 1.08; // Parcelado 3x (8% acréscimo)
        } else {
            return valorBase; // Cartão à vista (valor exato)
        }
    }

    public void registrarPagamento(Internacao internacao, double valorPago, String forma) {
        String registro = "ID: " + internacao.getId() +
                " | Entrada: " + internacao.getDataEntrada() +
                " | Alta: " + internacao.getDataAlta() +
                " | CPF: " + internacao.getPaciente().getCpf() +
                " | Valor: R$ " + valorPago +
                " | Forma: " + forma;

        this.historicoPagamentos.add(registro);
        this.totalRecebidoHospital += valorPago;
    }

    public void gerarRelatorioFinanceiroTXT() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("relatorio_pagamentos.txt"));
            writer.println("=== RELATORIO FINANCEIRO DE PAGAMENTOS ===");
            for (int i = 0; i < historicoPagamentos.size(); i++) {
                writer.println(historicoPagamentos.get(i));
            }
            writer.println("-------------------------------------------");
            writer.println("VALOR TOTAL ACUMULADO: R$ " + totalRecebidoHospital);
            writer.close();
            System.out.println("Relatorio gerado: relatorio_pagamentos.txt");
        } catch (Exception e) {
            System.out.println("Erro ao gerar o arquivo de texto.");
        }
    }
}