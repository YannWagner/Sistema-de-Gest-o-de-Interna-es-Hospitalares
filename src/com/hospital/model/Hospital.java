package com.hospital.model;

import java.io.Serializable;

public class Hospital implements Serializable {
    private static final long serialVersionUID = 1L;

    private GerenciadorDePacientes gerenciadorPacientes;
    private GerenciadorDeMedicos gerenciadorMedicos;
    private GerenciadorDeInternacoes gerenciadorInternacoes;
    private GerenciadorDePagamentos gerenciadorPagamentos;

    public Hospital() {
        this.gerenciadorPacientes = new GerenciadorDePacientes();
        this.gerenciadorMedicos = new GerenciadorDeMedicos();
        this.gerenciadorInternacoes = new GerenciadorDeInternacoes();
        this.gerenciadorPagamentos = new GerenciadorDePagamentos();
    }

    public GerenciadorDePacientes getGerenciadorPacientes() {
        return gerenciadorPacientes;
    }

    public GerenciadorDeMedicos getGerenciadorMedicos() {
        return gerenciadorMedicos;
    }

    public GerenciadorDeInternacoes getGerenciadorInternacoes() {
        return gerenciadorInternacoes;
    }

    public GerenciadorDePagamentos getGerenciadorPagamentos() {
        return gerenciadorPagamentos;
    }

    public void salvarHospital(String nomeArquivo) {
        Persistencia.salvar(this, nomeArquivo);
    }

    public static Hospital carregarHospital(String nomeArquivo) {
        Object objeto = Persistencia.carregar(nomeArquivo);
        if (objeto instanceof Hospital) {
            return (Hospital) objeto;
        }
        return null;
    }
}