package com.hospital.model;
public class PlanoDeSaude {
    private String nomeOperadora;
    private TipoLeito tipoPermitido;
    private double percentualCoparticipacao;

    public PlanoDeSaude(String nomeOperadora, TipoLeito tipoPermitido, double percentualCoparticipacao) {
        this.nomeOperadora = nomeOperadora;
        this.tipoPermitido = tipoPermitido;
        this.percentualCoparticipacao = percentualCoparticipacao;
    }
    public static PlanoDeSaude criarPlanoParticular() {
        return new PlanoDeSaude("Particular", TipoLeito.APARTAMENTO, 1.0);
    }
    public String getNomeOperadora() {
        return nomeOperadora;
    }

    public TipoLeito getTipoPermitido() {
        return tipoPermitido;
    }

    public double getPercentualCoparticipacao() {
        return percentualCoparticipacao;
    }
}