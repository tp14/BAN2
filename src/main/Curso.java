package main;

import java.util.List;

public class Curso {
    public int id;
	private String nome;
    private String disciplinas;
    private double nota;
    private String nivelDeEnsino;
    private int cargaHoraria;
    private int quantidadeNotas;
    private List<Double> notas;
    private List<Aula> aulas;
    private List<Professor> proprietarios;

    public Curso(){
    }


    public Curso(int id, String nome, String disciplinas, double nota, String nivelDeEnsino, int cargaHoraria, int quantidadeNotas, List<Double> notas, List<Aula> aulas, List<Professor> proprietarios){
        this.id =  id;
        this.nome = nome;
        this.disciplinas = disciplinas;
        this.nota = nota;
        this.nivelDeEnsino = nivelDeEnsino;
        this.cargaHoraria = cargaHoraria;
        this.quantidadeNotas = quantidadeNotas;
        this.notas = notas;
        this.aulas = aulas;
        this.proprietarios = proprietarios;
   }

   public int getId() {
    return id;
   }

   public void setId(int id) {
    this.id = id;
   }

    public int getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public int getIdCurso() {
		return id;
	}

	public void setIdCurso(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(String disciplinas) {
		this.disciplinas = disciplinas;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public String getNivelDeEnsino() {
		return nivelDeEnsino;
	}

	public void setNivelDeEnsino(String nivelDeEnsino) {
		this.nivelDeEnsino = nivelDeEnsino;
	}

    public List<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(List<Aula> aulas) {
		this.aulas = aulas;
	}

    public List<Professor> getPropritarios() {
		return proprietarios;
	}

	public void setProprietarios(List<Professor> proprietarios) {
		this.proprietarios = proprietarios;
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
        str += "\nAulas: " + this.aulas;
        return str;
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
