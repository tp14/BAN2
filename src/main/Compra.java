package main;

import java.util.List;

public class Compra {
	public int id;
    private int usuario; 
    private double valor;
    private int tipo; 
    private List<Aula> aulas;

	public Compra() {
	}

	public Compra(int id, int usuario, double valor, int tipo, List<Aula> aulas) {
		this.id = id;
		this.usuario = usuario;
        this.valor = valor;
		this.tipo = tipo;
        this.aulas = aulas;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

    public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

    public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

    public List<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(List<Aula> aulas) {
		this.aulas = aulas;
	}

	
	public String print() {
		return "Id:" + id + " Id usuario:" + usuario + " Valor:" + valor + " Tipo:" + tipo + " Aulas:" + aulas;
	}
	
	public boolean equals(Object obj) {
		Compra id = (Compra) obj;
		if(this.id == id.getId())
			return true ;
		else
			return false;
	}  
    
    public void calcularValor(){
		double soma = 0;
		for(int i=0; i<this.aulas.size(); i++){
            soma += aulas.get(i).getValor();
        }
		this.valor = soma;
	}

    public boolean realizarCompra(double valor){
		// Sistema chama serviço tercerizado
        return true;
	}

    public boolean quitarDebitos(Usuario usuario){
		this.valor = usuario.getDebitoPendente();
        realizarCompra(this.valor);
        return true;
	}
}
