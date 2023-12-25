package test;


import main.Professor;
import java.util.ArrayList;
import java.util.List;

public class ProfessorMock {

    public static Professor get() {

        Professor mockProfessor = new Professor();
        List<Double> notas = new ArrayList<Double>();

        mockProfessor.setNome("Test Professor");
        mockProfessor.setCpf(111);
        mockProfessor.setEmail("testProfessor@gmail.com");
        mockProfessor.setIdade(25);
        mockProfessor.setNota(8.5);
        mockProfessor.setQuantidadeNotas(4);
        mockProfessor.setNivelDeEnsino("Doutorado");
        mockProfessor.setCertificado("Doutorado em Física");
       
        notas.add(8.0);
        notas.add(9.0);
        notas.add(9.0);
        notas.add(8.0);
        mockProfessor.setNotas(notas);

        return mockProfessor;
    }

}
