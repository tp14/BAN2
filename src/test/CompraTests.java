package test;

import main.Compra;
import main.Usuario;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompraTests {
  @Test
  public void calcularValor() {
    Compra compra = CompraMock.get();
    
    assertEquals(0.0, compra.getValor(),1);

    compra.calcularValor();

    assertEquals(49.99, compra.getValor(),1);
  }

  @Test
  public void realizarCompra() {
    Compra compra = CompraMock.get();

    assertTrue(compra.realizarCompra(20.75));
  }

  @Test
  public void quitarDebitos() {
    Compra compra = CompraMock.get();
    Usuario usuario = UsuarioMock.get();

    assertTrue(compra.quitarDebitos(usuario));
  }
}