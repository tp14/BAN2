package test;

import main.Curso;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CursoTests {
    @Test
    public void adicionarNota() {
      Curso curso = CursoMock.get();
      
      assertEquals(2, curso.getQuantidadeNotas());
  
      curso.adicionarNota(8);
  
      assertEquals(3, curso.getQuantidadeNotas());
    }
  
    @Test
    public void calcularNota() {
      Curso curso = CursoMock.get();
      
      assertEquals(8.5, curso.getNota(),1);

      curso.adicionarNota(10);
  
      assertEquals(9.0, curso.getNota(),1);
    }
}