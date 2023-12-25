package test;

import main.Professor;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ProfessorTests {
  @Test
  public void adicionarNota() {
    Professor professor = ProfessorMock.get();
    
    assertEquals(4, professor.getQuantidadeNotas());

    professor.adicionarNota(8);

    assertEquals(5, professor.getQuantidadeNotas());
  }

  @Test
  public void calcularNota() {
    Professor professor = ProfessorMock.get();
    
    assertEquals(8.5, professor.getNota(),1);

    professor.adicionarNota(10);

    assertEquals(9, professor.getNota(),1);
  }
}