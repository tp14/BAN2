package dados;

public class Estudante{
    protected int codEstudante;
    protected String nome;
	protected int idade;
    protected String tipoCurso;
    protected int codDepartamentoCurso;
    protected int codAconselhador;

    public String getNome() {
        return nome;
    }
    public int getCodEstudante() {
        return codEstudante;
    }

    public void setCodEstudante(int codEstudante) {
        this.codEstudante = codEstudante;
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
    
    public String getTipoCurso() {
        return tipoCurso;
    }
    
    public void setTipoCurso(String tipoCurso) {
        this.tipoCurso = tipoCurso;
    }
    
    public int getCodDepartamentoCurso() {
        return codDepartamentoCurso;
    }
    
    public void setCodDepartamentoCurso(int codDepartamentoCurso) {
        this.codDepartamentoCurso = codDepartamentoCurso;
    }
    
    public int getCodAconselhador() {
        return codAconselhador;
    }
    
    public void setCodAconselhador(int codAconselhador) {
        this.codAconselhador = codAconselhador;
    }
    
    public String toString() {
        return "Estudante: codEstudante=" + codEstudante +  ", nome=" + nome + ", idade=" + idade + ", tipoCurso=" + tipoCurso
                + ", codDepartamentoCurso=" + codDepartamentoCurso + ", codAconselhador=" + codAconselhador + "\n";
    }
}
