package com.hospital.model;
import java.io.Serializable;

public enum TipoLeito implements Serializable {
    ENFERMARIA(300.0),
    APARTAMENTO(800.0),
    UTI(2500.0);

    private final double valorDiaria;

    TipoLeito(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }
}