package com.hospital.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Internacao implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private Paciente paciente;
    private Medico medico;
    private TipoLeito tipoLeito;
    private String localizacao;
    private LocalDate dataEntrada;
    private LocalDate dataAlta;

    public Internacao(Paciente paciente, Medico medico, TipoLeito tipoLeito, String localizacao, LocalDate dataEntrada) {
        this.id = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        this.paciente = paciente;
        this.medico = medico;
        this.tipoLeito = tipoLeito;
        this.localizacao = localizacao;
        this.dataEntrada = dataEntrada;
    }

    public long getQuantidadeDeDias() {
        if (dataAlta == null) {
            return 0;
        }
        long dias = ChronoUnit.DAYS.between(dataEntrada, dataAlta);
        return (dias <= 0) ? 1 : dias;
    }

    public double calcularValorBruto() {
        return getQuantidadeDeDias() * tipoLeito.getValorDiaria();
    }

    public double calcularValorPaciente() {
        return calcularValorBruto() * paciente.getPlanoDeSaude().getPercentualCoparticipacao();
    }

    public String getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public TipoLeito getTipoLeito() { return tipoLeito; }
    public String getLocalizacao() { return localizacao; }
    public LocalDate getDataEntrada() { return dataEntrada; }
    public LocalDate getDataAlta() { return dataAlta; }

    public void setDataAlta(LocalDate dataAlta) {
        this.dataAlta = dataAlta;
    }
}