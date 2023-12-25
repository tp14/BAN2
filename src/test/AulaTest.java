package test;

import main.Aula;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AulaTest {
@Test
  public void exibirAula() {
    Aula aula = AulaMock.get();   

    assertTrue(aula.exibirAula());
  }

  @Test
    public void adicionarNota() {
        Aula aula = AulaMock.get(); 
      
      assertEquals(2, aula.getQuantidadeNotas());
  
      aula.adicionarNota(9.5);
  
      assertEquals(3, aula.getQuantidadeNotas());
    }
  
    @Test
    public void calcularNota() {
        Aula aula = AulaMock.get(); 
      
      assertEquals(7.5, aula.getNota(),1);

      aula.adicionarNota(7.5);
  
      assertEquals(7.5, aula.getNota(),1);
    }
}
