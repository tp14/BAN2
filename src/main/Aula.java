package main;

import java.util.List;

public class Aula {
    public int id;
    private int cargaHoraria;
    private String nome;
    private double valor;
    private double nota;
    private int quantidadeNotas;
    private List<Professor> proprietarios;
    private String disciplinas;
    private String nivelDeEnsino;
    private List<Double> notas;

    public Aula(){
    }

    public Aula(int id, int cargaHoraria, String nome, double valor, double nota, int quantidadeNotas, List<Professor> proprietarios, String disciplinas, String nivelDeEnsino, List<Double> notas) {
        this.id = id;
        this.cargaHoraria = cargaHoraria;
        this.nome = nome;
        this.valor = valor;
        this.nota = nota;
        this.quantidadeNotas = quantidadeNotas;
        this.proprietarios = proprietarios;
        this.disciplinas = disciplinas;
        this.nivelDeEnsino = nivelDeEnsino;
        this.notas = notas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

    public List<Professor> getPropritarios() {
		return proprietarios;
	}

	public void setProprietarios(List<Professor> proprietarios) {
		this.proprietarios = proprietarios;
	}

    public String getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(String disciplinas) {
		this.disciplinas = disciplinas;
	}

    public String getNivelDeEnsino() {
		return nivelDeEnsino;
	}

	public void setNivelDeEnsino(String nivelDeEnsino) {
		this.nivelDeEnsino = nivelDeEnsino;
	}

    public int getQuantidadeNotas() {
        return quantidadeNotas;
    }

    public void setQuantidadeNotas(int quantidadeNotas) {
        this.quantidadeNotas = quantidadeNotas;
    }

    public List<Double> getNotas() {
		return notas;
	}

	public void setNotas(List<Double> notas) {
		this.notas = notas;
	}

    public String toString(){
        String str = "Nome: " + this.nome;
        str += "\nCodigo: " + this.id;
        str += "\nCarga horaria: " + this.cargaHoraria;
        str += "\nProprietarios: " + this.proprietarios;
        str += "\nDisciplinas: " + this.disciplinas;
        str += "\nNota: " + this.nota;
        str += "\nNivel de Ensino: " + this.nivelDeEnsino;
        str += "\nValor " + this.valor;
        return str;
    }

    public boolean exibirAula(){
        System.out.println("Exibindo aula");
        return true;
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
