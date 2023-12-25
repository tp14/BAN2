package main;

import java.util.List;

public class Professor extends Usuario{
	private double nota;
    private int quantidadeNotas;
    private String nivelDeEnsino;
    private String certificado;
	private List<Double> notas;
	
	public Professor() {
		super();
	}

	public Professor(String nome, int cpf, String email, int idade, double debitoPendente, List<String> formasDePagamento, double nota, int quantidadeNotas, String nivelDeEnsino, String certificado) {
		super(nome, cpf, email, idade, debitoPendente, formasDePagamento);
		this.nota = nota;
		this.quantidadeNotas = quantidadeNotas;
		this.nivelDeEnsino = nivelDeEnsino;
		this.certificado = certificado;
	}
	
	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}
    
    public int getQuantidadeNotas() {
        return quantidadeNotas;
    }

    public void setQuantidadeNotas(int quantidadeNotas) {
        this.quantidadeNotas = quantidadeNotas;
    }

    public String getNivelDeEnsino() {
		return nivelDeEnsino;
	}

	public void setNivelDeEnsino(String nivelDeEnsino) {
		this.nivelDeEnsino = nivelDeEnsino;
	}

    public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public List<Double> getNotas() {
		return notas;
	}

	public void setNotas(List<Double> notas) {
		this.notas = notas;
	}

    public String print() {
		return "Nome:" + nome + " CPF:" + cpf + " Email:" + email + " Idade:" + idade + " Debitos pendente:" + debitoPendente + " Formas de Pagamento:" + formasDePagamento + " Nota:" + nota + " Nivel de Ensino:" + nivelDeEnsino;
	}

	public void adicionarNota(double novaNota) {
		this.quantidadeNotas += 1;
        this.notas.add(novaNota);
		this.calcularNota();
	}

	public void calcularNota(){
		double soma = 0;
		for(int i=0; i<this.notas.size(); i++){
            soma += notas.get(i);
        }
		this.nota = soma / this.quantidadeNotas;
	}
}