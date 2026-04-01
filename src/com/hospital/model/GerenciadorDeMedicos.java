package com.hospital.model;

import java.io.Serializable;
import java.util.ArrayList;

public class GerenciadorDeMedicos implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Medico> medicos;

    public GerenciadorDeMedicos() {
        this.medicos = new ArrayList<>();
    }

    public void cadastrarMedico(String nome, String crm, Especialidade especialidade) {
        if (buscarMedico(crm) != null) {
            throw new IllegalArgumentException("CRM ja cadastrado no sistema");
        }

        Medico novoMedico = new Medico(nome, crm, especialidade);
        medicos.add(novoMedico);
    }

    public Medico buscarMedico(String crm) {
        for (int i = 0; i < medicos.size(); i++) {
            Medico m = medicos.get(i);
            if (m.getCrm().equals(crm)) {
                return m;
            }
        }
        return null;
    }

    public void atualizarEspecialidade(String crm, Especialidade novaEspecialidade) {
        Medico medico = buscarMedico(crm);
        if (medico != null) {
            medico.setEspecialidade(novaEspecialidade);
        } else {
            throw new IllegalArgumentException("Medico nao encontrado");
        }
    }

    public void removerMedico(String crm) {
        Medico medico = buscarMedico(crm);
        if (medico != null) {
            medico.setAtivo(false);
        } else {
            throw new IllegalArgumentException("Medico nao encontrado");
        }
    }

    public ArrayList<Medico> listarMedicosAtivos() {
        ArrayList<Medico> ativos = new ArrayList<>();
        for (int i = 0; i < medicos.size(); i++) {
            Medico m = medicos.get(i);
            if (m.isAtivo()) {
                ativos.add(m);
            }
        }
        return ativos;
    }
}