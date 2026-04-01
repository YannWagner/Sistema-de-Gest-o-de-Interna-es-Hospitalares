package com.hospital.service;

import com.hospital.model.Paciente;
import com.hospital.model.PlanoDeSaude;

import java.io.Serializable;
import java.util.ArrayList;

public class GerenciadorDePacientes implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Paciente> pacientes;

    public GerenciadorDePacientes() {
        this.pacientes = new ArrayList<>();
    }

    public void cadastrarPaciente(String nome, String cpf, String telefone, PlanoDeSaude plano) {
        Paciente existente = buscarPaciente(cpf);
        if (existente != null) {
            throw new IllegalArgumentException("CPF ja cadastrado");
        }

        Paciente novoPaciente = new Paciente(nome, cpf, telefone, plano);
        pacientes.add(novoPaciente);
    }

    public Paciente buscarPaciente(String cpf) {
        for (int i = 0; i < pacientes.size(); i++) {
            Paciente p = pacientes.get(i);
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }

    public void atualizarPlanoDeSaude(String cpf, PlanoDeSaude novoPlano) {
        Paciente paciente = buscarPaciente(cpf);
        if (paciente != null) {
            paciente.setPlanoDeSaude(novoPlano);
        } else {
            throw new IllegalArgumentException("Paciente nao encontrado");
        }
    }
}