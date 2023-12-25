package src.dados;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Projeto {
    protected int codProjeto;
    protected String orgao;
    protected float orcamento;
    protected Date dataInicio;
    protected Date dataFinal;
    protected int codProfessorGerente;
    protected List<Integer> professoresParticipantes = new ArrayList<Integer>();
    protected List<Integer> estudantesAssistentes = new ArrayList<Integer>();

    public int getCodProjeto() {
        return codProjeto;
    }

    public void setCodProjeto(int codProjeto) {
        this.codProjeto = codProjeto;
    }
    
    public String getOrgao() {
        return orgao;
    }
    
    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }
    
    public float getOrcamento() {
        return orcamento;
    }
    
    public void setOrcamento(float orcamento) {
        this.orcamento = orcamento;
    }
    
    public Date getDataInicio() {
        return dataInicio;
    }
    
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public Date getDataFinal() {
        return dataFinal;
    }
    
    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }
    
    public int getCodProfessorGerente() {
        return codProfessorGerente;
    }

    public void setCodProfessorGerente(int codProfessorGerente) {
        this.codProfessorGerente = codProfessorGerente;
    }

    public List<Integer> getProfessoresParticipantes() {
        return professoresParticipantes;
    }

    public void setProfessoresParticipantes(List<Integer> professoresParticipantes) {
        this.professoresParticipantes = professoresParticipantes;
    }

    public List<Integer> getEstudantesAssistentes() {
        return estudantesAssistentes;
    }

    public void setEstudantesAssistentes(List<Integer> estudantesAssistentes) {
        this.estudantesAssistentes = estudantesAssistentes;
    }

    public String toString() {
        return "Projeto [codProjeto=" + codProjeto + ", orgao=" + orgao + ", orcamento=" + orcamento + ", dataInicio="
                + dataInicio + ", dataFinal=" + dataFinal + ", codProfessorGerente=" + codProfessorGerente
                + ", professoresParticipantes=" + professoresParticipantes + ", estudantesAssistentes="
                + estudantesAssistentes + "]";
    }
}
