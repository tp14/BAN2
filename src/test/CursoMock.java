package test;

import java.util.ArrayList;
import java.util.List;

import main.Aula;
import main.Curso;
import main.Professor;

public class CursoMock {

    public static Curso get() { 

        Curso mockCurso = new Curso();
        List<Double> notas = new ArrayList<Double>();
        List<Aula> aulas = new ArrayList<Aula>();
        Aula aula = AulaMock.get();  
        List<Professor> proprietarios = new ArrayList<Professor>();
        Professor professor = ProfessorMock.get();  

        mockCurso.setId(1);
        mockCurso.setNome("Aula de calculo");
        mockCurso.setDisciplinas("Calculo 1 e Calculo 2");
        mockCurso.setNota(8.5);
        mockCurso.setQuantidadeNotas(2);

        notas.add(9.0);
        notas.add(8.0);
        mockCurso.setNotas(notas);

        mockCurso.setNivelDeEnsino("Superior");

        aulas.add(aula);
        mockCurso.setAulas(aulas);

        proprietarios.add(professor);
        mockCurso.setProprietarios(proprietarios);
        
        mockCurso.setCargaHoraria(72);

        return mockCurso;
    }
}

