package test;

import main.Aula;

import java.util.ArrayList;
import java.util.List;
import main.Professor;

public class AulaMock {

    public static Aula get() { 

        Aula mockAula = new Aula();
        List<Double> notas = new ArrayList<Double>();
        List<Professor> proprietarios = new ArrayList<Professor>();
        Professor professor = ProfessorMock.get();  

        mockAula.setId(1);
        mockAula.setNome("Aula de calculo");
        mockAula.setDisciplinas("Calculo 1 e Calculo 2");
        mockAula.setNota(7.5);
        mockAula.setQuantidadeNotas(2);
        mockAula.setValor(49.99);

        notas.add(7.0);
        notas.add(8.0);
        mockAula.setNotas(notas);

        mockAula.setNivelDeEnsino("Superior");

        proprietarios.add(professor);
        mockAula.setProprietarios(proprietarios);
        
        mockAula.setCargaHoraria(72);

        return mockAula;
    }
}

