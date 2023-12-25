package dados;

public class Departamento {
    protected int codDepartamento;
    protected String nome;
    protected int escritorio;
    protected int codProfessorLider;
    
    public int getCodDepartamento() {
        return codDepartamento;
    }
    
    public void setCodDepartamento(int codDepartamento) {
        this.codDepartamento = codDepartamento;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getEscritorio() {
        return escritorio;
    }
    
    public void setEscritorio(int escritorio) {
        this.escritorio = escritorio;
    }
    
    public int getCodProfessorLider() {
        return codProfessorLider;
    }
    
    public void setCodProfessorLider(int codProfessorLider) {
        this.codProfessorLider = codProfessorLider;
    }
    
    public String toString() {
        return "Departamento: codDep=" + codDepartamento + ", nome=" + nome + ", escritorio=" + escritorio + ", codProfessorLider="
                + codProfessorLider + "\n";
    }
}
