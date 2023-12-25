package test;

import main.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioMock {

    public static Usuario get() {

        Usuario mockUsuario = new Usuario();
        List<String> formasDePagamento = new ArrayList<String>();

        mockUsuario.setNome("Test Usuario");
        mockUsuario.setCpf(111);
        mockUsuario.setEmail("testUsuario@gmail.com");
        mockUsuario.setIdade(25);
        mockUsuario.setDebitoPendente(28.5);
     
        formasDePagamento.add("Forma 1");
        formasDePagamento.add("Forma 2");
        mockUsuario.setFormasDePagamento(formasDePagamento);

        return mockUsuario;
    }
}
