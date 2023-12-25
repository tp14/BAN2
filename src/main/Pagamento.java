package main;

import java.util.List;

public class Pagamento {
    public int id;
    private int usuario; 
    private double valor;
    private int tipo; 
    private List<Aula> aulas;
    private double porcentagem;
    private String dadosNovaForma;
    private int tipoNovaForma;

	public Pagamento() {
	}

	public Pagamento(int id, int usuario, double valor, int tipo, List<Aula> aulas, double porcentagem, String dadosNovaForma, int tipoNovaForma) {
		this.id = id;
		this.usuario = usuario;
        this.valor = valor;
		this.tipo = tipo;
        this.aulas = aulas;
        this.porcentagem = porcentagem;
		this.dadosNovaForma = dadosNovaForma;
        this.tipoNovaForma = tipoNovaForma;
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

    public double getPorcentagem() {
		return porcentagem;
	}

	public void setPorcetagem(double porcentagem) {
		this.porcentagem = porcentagem;
	}

    public String getDadosNovaForma() {
        return dadosNovaForma;
    }

    public void setDadosNovaForma(String dadosNovaForma) {
        this.dadosNovaForma = dadosNovaForma;
    }

    public int getTipoNovaForma() {
		return tipoNovaForma;
	}

	public void setTipoNovaForma(int tipoNovaForma) {
		this.tipoNovaForma = tipoNovaForma;
	}

	public String print() {
		return "Id:" + id + " Id usuario:" + usuario + " Valor:" + valor + " Tipo:" + tipo + " Aulas:" + aulas + " Porcentagem:" + porcentagem;
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
		this.valor = soma * this.porcentagem;
	}

    public boolean pagar(double valor){
		// Sistema chama serviço tercerizado
        return true;
	}
}
