package test.com.hospital;

import com.hospital.model.*;
import com.hospital.service.*;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;



public class InternacaoTest {

    @Test
    public void testCalculoQuantidadeDeDias() {
        PlanoDeSaude plano = new PlanoDeSaude("Unimed", TipoLeito.APARTAMENTO, 0.20);
        Paciente paciente = new Paciente("João", "12345678901", "83999999999", plano);
        Medico medico = new Medico("Dr. House", "123456/PB", Especialidade.CLINICA_MEDICA_GERAL);

        LocalDate dataEntrada = LocalDate.of(2026, 4, 1);
        Internacao inter = new Internacao(paciente, medico, TipoLeito.ENFERMARIA, "Ala B", dataEntrada);

        inter.setDataAlta(LocalDate.of(2026, 4, 5));

        assertEquals("A quantidade de dias calculada está errada", 4L, inter.getQuantidadeDeDias());
    }

    @Test
    public void testCalcularValorBrutoEPaciente() {
        PlanoDeSaude plano = new PlanoDeSaude("Bradesco", TipoLeito.UTI, 0.20);
        Paciente paciente = new Paciente("Maria", "10987654321", "83988888888", plano);
        Medico medico = new Medico("Dra. Grey", "654321/SP", Especialidade.CIRURGIA);

        LocalDate dataEntrada = LocalDate.of(2026, 4, 1);
        Internacao inter = new Internacao(paciente, medico, TipoLeito.UTI, "UTI 1", dataEntrada);

        inter.setDataAlta(LocalDate.of(2026, 4, 3));

        assertEquals("Valor bruto incorreto", 5000.0, inter.calcularValorBruto(), 0.001);
        assertEquals("Valor de coparticipação do paciente incorreto", 1000.0, inter.calcularValorPaciente(), 0.001);
    }
}