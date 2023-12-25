package dados;

public class TrabalhoEstudanteProjeto {
    protected int codTrabalhoEstudanteProjeto;
    protected int codEstudanteParticipante;
    protected int codProfessorSupervisor;

    public int getCodTrabalhoEstudanteProjeto() {
        return codTrabalhoEstudanteProjeto;
    }

    public void setCodTrabalhoEstudanteProjeto(int codTrabalhoEstudanteProjeto) {
        this.codTrabalhoEstudanteProjeto = codTrabalhoEstudanteProjeto;
    }

    public int getCodEstudanteParticipante() {
        return codEstudanteParticipante;
    }

    public void setCodEstudanteParticipante(int codEstudanteParticipante) {
        this.codEstudanteParticipante = codEstudanteParticipante;
    }

    public int getCodProfessorSupervisor() {
        return codProfessorSupervisor;
    }

    public void setCodProfessorSupervisor(int codProfessorSupervisor) {
        this.codProfessorSupervisor = codProfessorSupervisor;
    }

    public String toString() {
        return "TrabalhaEstudanteProjeto [codTrabalhoEstudanteProjeto=" + codTrabalhoEstudanteProjeto
                + ", codEstudanteParticipante=" + codEstudanteParticipante + ", codProfessorSupervisor="
                + codProfessorSupervisor + "]";
    }
}
