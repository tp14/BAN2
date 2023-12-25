package test;

import main.Compra;
import java.util.ArrayList;
import java.util.List;
import main.Aula;

public class CompraMock {

    public static Compra get() { 

        Compra mockCompra = new Compra();
        List<Aula> aulas = new ArrayList<Aula>();
        Aula aula = AulaMock.get();   

        mockCompra.setId(1);
        mockCompra.setUsuario(3);
        mockCompra.setValor(0.0);
        mockCompra.setTipo(1);

        aulas.add(aula);
        mockCompra.setAulas(aulas);

        return mockCompra;
    }
}



