package src.dados;

import java.util.ArrayList;
import java.util.List;

public class Professor{
    protected int codProfessor;
	protected String nome;
	protected int idade;
    protected int sala;
    protected String especialidade;
    protected List<Integer> projetosGeridos = new ArrayList<Integer>();
    protected List<Integer> projetosTrabalhados = new ArrayList<Integer>();

    public String getNome() {
    	return nome;
    }
    
    public void setNome(String nome) {
    	this.nome = nome;
    }
    
    public int getIdade() {
        return idade;
    }
    
    public void setIdade(int idade) {
        this.idade = idade;
    }


    public int getCodProfessor() {
        return codProfessor;
    }

    public void setCodProfessor(int codProfessor) {
        this.codProfessor = codProfessor;
    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public List<Integer> getProjetosGeridos() {
        return projetosGeridos;
    }

    public void setProjetosGeridos(List<Integer> projetosGeridos) {
        this.projetosGeridos = projetosGeridos;
    }

    public List<Integer> getProjetosTrabalhados() {
        return projetosTrabalhados;
    }

    public void setProjetosTrabalhados(List<Integer> projetosTrabalhados) {
        this.projetosTrabalhados = projetosTrabalhados;
    }

    public String toString() {
        return "Professor: Matricula do professor=" + codProfessor + ", nome=" + nome + ", idade=" + idade + 
               ", sala=" + sala + ", especialidade=" + especialidade + ", geridos=" + projetosGeridos +
              "e projetosTrabalhados=" + projetosTrabalhados + "\n";
    }

}