package test;

import main.Pagamento;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PagamentoTests {
  @Test
  public void calcularValor() {
    Pagamento pagamento = PagamentoMock.get();
    
    assertEquals(0.0, pagamento.getValor(),1);

    pagamento.calcularValor();

    assertEquals(9.9, pagamento.getValor(),1);
  }

  @Test
  public void pagar() {
    Pagamento pagamento = PagamentoMock.get();

    assertTrue(pagamento.pagar(20.75));
  }
}