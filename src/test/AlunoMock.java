package test;


import main.Aluno;

public class AlunoMock {

    public static Aluno get() { 

        Aluno mockAluno = new Aluno();

        mockAluno.setNome("Test Aluno");
        mockAluno.setCpf(111);
        mockAluno.setEmail("testAluno@gmail.com");
        mockAluno.setIdade(25);
        mockAluno.setAulasAssitidas(7);

        return mockAluno;
    }

}
