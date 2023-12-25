package test;

import main.Usuario;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UsuarioTest {
@Test
  public void novaFormaDePagamento() {
    Usuario usuario = UsuarioMock.get();    
    assertEquals(2, usuario.getFormasDePagemento().size());

    usuario.novaFormaDePagamento("Dados da nova forma");

    assertEquals(3, usuario.getFormasDePagemento().size());
  }
}