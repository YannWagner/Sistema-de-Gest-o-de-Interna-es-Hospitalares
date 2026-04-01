package com.hospital.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class GerenciadorDeInternacoes implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Internacao> internacoes;

    public GerenciadorDeInternacoes() {
        this.internacoes = new ArrayList<>();
    }

    public void registrarInternacao(Paciente p, Medico m, TipoLeito leito, String local, LocalDate entrada) {
        Internacao nova = new Internacao(p, m, leito, local, entrada);
        this.internacoes.add(nova);
        System.out.println("ID DA INTERNACAO GERADO: " + nova.getId());
    }

    public Internacao buscarPorId(String id) {
        for (int i = 0; i < internacoes.size(); i++) {
            Internacao inter = internacoes.get(i);
            if (inter.getId().equals(id)) {
                return inter;
            }
        }
        return null;
    }

    public ArrayList<Internacao> getInternacoes() {
        return internacoes;
    }
}