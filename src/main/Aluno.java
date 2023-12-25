package main;

import java.util.List;

public class Aluno extends Usuario{
	private int aulasAssitidas;
	
	public Aluno() {
		super();
	}

	public Aluno(String nome, int cpf, String email, int idade, int aulasAssitidas, double debitoPendente, List<String> formasDePagamento) {
		super(nome, cpf, email, idade, debitoPendente, formasDePagamento);
		this.aulasAssitidas = aulasAssitidas;
	}
	
	public int getAulasAssitidas() {
		return aulasAssitidas;
	}
	
	public void setAulasAssitidas(int aulasAssitidas) {
		this.aulasAssitidas = aulasAssitidas;
	}
	
	public String print() {
		return "Nome:" + nome + " CPF:" + cpf + " Email:" + email + " Idade:" + idade + " Debitos pendente:" + debitoPendente + " Formas de Pagamento:" + formasDePagamento + " Aulas Assistidas:" + aulasAssitidas;
	}
	
	public void adicionarAula(int quantidadeAulas) {
		this.aulasAssitidas = aulasAssitidas + quantidadeAulas;
	}
}