package src.dados;

public class TrabalhoProfessorDepartamento {
    protected int codTrabalhoProfDep;
    protected int codProfessorTrabalho;
    protected int codDepartamentoTrabalho;
    protected float perTempoTrabalho;

    public int getCodTrabalhoProfDep() {
        return codTrabalhoProfDep;
    }

    public void setCodTrabalhoProfDep(int codTrabalhoProfDep) {
        this.codTrabalhoProfDep = codTrabalhoProfDep;
    }

    public int getCodProfessorTrabalho() {
        return codProfessorTrabalho;
    }

    public void setCodProfessorTrabalho(int codProfessorTrabalho) {
        this.codProfessorTrabalho = codProfessorTrabalho;
    }

    public int getCodDepartamentoTrabalho() {
        return codDepartamentoTrabalho;
    }

    public void setCodDepartamentoTrabalho(int codDepartamentoTrabalho) {
        this.codDepartamentoTrabalho = codDepartamentoTrabalho;
    }

    public float getPerTempoTrabalho() {
        return perTempoTrabalho;
    }

    public void setPerTempoTrabalho(float perTempoTrabalho) {
        this.perTempoTrabalho = perTempoTrabalho;
    }

    public String toString() {
        return "TrabalhoProfessorDepartamento [codTrabalhoProfDep=" + codTrabalhoProfDep + ", codProfessorTrabalho="
                + codProfessorTrabalho + ", codDepartamentoTrabalho=" + codDepartamentoTrabalho + ", perTempoTrabalho="
                + perTempoTrabalho + "]";
    }

    public int getInteger(String string) {
        return 0;
    }
}
