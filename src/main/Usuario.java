package main;

import java.util.List;

public class Usuario {
    protected  String nome;
	public  int cpf;
    protected  String email;
    protected  int idade;
	protected double debitoPendente;
	protected List<String> formasDePagamento;

	public Usuario() {
	}

	public Usuario(String nome, int cpf, String email, int idade, double debitoPendente, List<String> formasDePagamento) {
		this.nome = nome;
		this.cpf = cpf;
        this.email = email;
		this.idade = idade;
		this.debitoPendente = debitoPendente;
		this.formasDePagamento = formasDePagamento;
	}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

	public int getCpf() {
		return cpf;
	}

	public void setCpf(int cpf) {
		this.cpf = cpf;
	}

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public double getDebitoPendente() {
		return debitoPendente;
	}

	public void setDebitoPendente(double debitoPendente) {
		this.debitoPendente = debitoPendente;
	}

	public List<String> getFormasDePagemento() {
		return formasDePagamento;
	}

	public void setFormasDePagamento(List<String> formasDePagamento) {
		this.formasDePagamento = formasDePagamento;
	}

	public String print() {
		return "Nome:" + nome + " CPF:" + cpf + " Email:" + email + " Idade:" + idade + " Debitos pendente:" + debitoPendente + " Formas de Pagamento:" + formasDePagamento;
	}
	
	public boolean equals(Object obj) {
		Usuario cpf = (Usuario) obj;
		if(this.cpf == cpf.getCpf())
			return true ;
		else
			return false;
	}

	public void novaFormaDePagamento(String novaForma){
		formasDePagamento.add(novaForma);
	}
}