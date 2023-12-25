package test;

import main.Aluno;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AlunoTests {
    @Test
    public void adicionarAula() {
      Aluno aluno = AlunoMock.get();
      
      assertEquals(7, aluno.getAulasAssitidas());
  
      aluno.adicionarAula(2);
  
      assertEquals(9, aluno.getAulasAssitidas());
    } 
}
