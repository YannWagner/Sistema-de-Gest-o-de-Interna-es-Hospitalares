package com.hospital.model;
import java.io.Serializable;

public class Medico implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String crm;
    private Especialidade especialidade;
    private boolean ativo;

    public Medico(String nome, String crm, Especialidade especialidade) {
        validarDados(nome, crm);
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
        this.ativo = true;
    }

    private void validarDados(String nome, String crm) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome invalido");
        }
        if (crm == null || crm.length() != 9) {
            throw new IllegalArgumentException("CRM invalido. O tamanho deve ser exatamente 9 (ex: 123456/PB)");
        }
        for (int i = 0; i < 6; i++) {
            if (!Character.isDigit(crm.charAt(i))) {
                throw new IllegalArgumentException("CRM invalido. Os 6 primeiros caracteres devem ser numeros");
            }
        }
        if (crm.charAt(6) != '/') {
            throw new IllegalArgumentException("CRM invalido. Falta a barra (/) separando o estado");
        }
        if (!Character.isLetter(crm.charAt(7)) || !Character.isLetter(crm.charAt(8))) {
            throw new IllegalArgumentException("CRM invalido. A sigla do estado deve conter 2 letras");
        }
    }

    public String getNome() { return nome; }
    public String getCrm() { return crm; }
    public Especialidade getEspecialidade() { return especialidade; }
    public void setEspecialidade(Especialidade especialidade) { this.especialidade = especialidade; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}