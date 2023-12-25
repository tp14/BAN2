package test;

import main.Pagamento;
import java.util.ArrayList;
import java.util.List;
import main.Aula;

public class PagamentoMock {

    public static Pagamento get() { 

        Pagamento mockPagamento = new Pagamento();
        List<Aula> aulas = new ArrayList<Aula>();
        Aula aula = AulaMock.get();   

        mockPagamento.setId(1);
        mockPagamento.setUsuario(3);
        mockPagamento.setValor(0.0);
        mockPagamento.setTipo(1);

        aulas.add(aula);
        mockPagamento.setAulas(aulas);

        mockPagamento.setPorcetagem(0.2);
        mockPagamento.setDadosNovaForma("Dados nova forma");
        mockPagamento.setTipoNovaForma(1);

        return mockPagamento;
    }
}


