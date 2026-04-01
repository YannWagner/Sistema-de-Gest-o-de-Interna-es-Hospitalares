package com.hospital.model;
import java.io.Serializable;

public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String cpf;
    private String telefone;
    private PlanoDeSaude planoDeSaude;

    public Paciente(String nome, String cpf, String telefone, PlanoDeSaude planoDeSaude) {
        validarDados(nome, cpf, telefone);
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.planoDeSaude = planoDeSaude;
    }

    private void validarDados(String nome, String cpf, String telefone) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome invalido");
        }
        if (cpf == null || cpf.trim().isEmpty() || cpf.length() != 11 || !ehApenasNumero(cpf)) {
            throw new IllegalArgumentException("CPF invalido");
        }
        if (telefone == null || telefone.trim().isEmpty() || telefone.length() != 11 || !ehApenasNumero(telefone)) {
            throw new IllegalArgumentException("Telefone invalido");
        }
    }

    private boolean ehApenasNumero(String texto) {
        for (int i = 0; i < texto.length(); i++) {
            if (!Character.isDigit(texto.charAt(i))) return false;
        }
        return true;
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public PlanoDeSaude getPlanoDeSaude() { return planoDeSaude; }
    public void setPlanoDeSaude(PlanoDeSaude planoDeSaude) { this.planoDeSaude = planoDeSaude; }
}