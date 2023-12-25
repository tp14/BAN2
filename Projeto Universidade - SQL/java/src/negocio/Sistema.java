package negocio;

import java.sql.SQLException;
import java.util.List;
import dados.*;
import exceptions.*;
import persistencia.*;

/*FALTA:
- função para retornar todos os lideres de seus respectivos departamentos
- função para listar os assistentes
- função para listar os professores que trabalham em cada departamento
- função para listar todos os alunos que estão em cada departamento
- função para listar todos os projetos




*/


public class Sistema {
	public void cadastrarProfessor(Professor professor) throws InsertException, ClassNotFoundException, SQLException {
		ProfessorDAO.getInstance().insert(professor);
	}
	
	public List<Professor> buscarProfessores() throws SelectException, ClassNotFoundException, SQLException{
		return ProfessorDAO.getInstance().getAll();
	}

	public void removerProfessor(Professor professor) throws DeleteException, ClassNotFoundException, SQLException {
		ProfessorDAO.getInstance().delete(professor);
	}

	public void alterarProfessor(Professor professor) throws UpdateException, ClassNotFoundException, SQLException { 
		ProfessorDAO.getInstance().update(professor);
	}

	public Professor getProfessor(int codProfessor) throws SelectException, ClassNotFoundException, SQLException {
		return ProfessorDAO.getInstance().getProfessor(codProfessor);
	}

	public void cadastrarDepartamento(Departamento departamento) throws InsertException, ClassNotFoundException, SQLException {
		DepartamentoDAO.getInstance().insert(departamento);
	}
	
	public List<Departamento> buscarDepartamentos() throws SelectException, ClassNotFoundException, SQLException{
		return DepartamentoDAO.getInstance().getAll();
	}

	public void removerDepartamento(Departamento departamento) throws DeleteException, ClassNotFoundException, SQLException {
		DepartamentoDAO.getInstance().delete(departamento);
	}

	public void alterarDepartamento(Departamento departamento) throws UpdateException, ClassNotFoundException, SQLException { 
		DepartamentoDAO.getInstance().update(departamento);
	}

	public Departamento getDepartamento(int codDepartamento) throws SelectException, ClassNotFoundException, SQLException {
		return DepartamentoDAO.getInstance().getDepartamento(codDepartamento);
	}

	public void cadastrarEstudante(Estudante estudante) throws InsertException, ClassNotFoundException, SQLException {
		EstudanteDAO.getInstance().insert(estudante);
	}
	
	public List<Estudante> buscarEstudantes() throws SelectException, ClassNotFoundException, SQLException{
		return EstudanteDAO.getInstance().getAll();
	}

	public void removerEstudante(Estudante estudante) throws DeleteException, ClassNotFoundException, SQLException {
		EstudanteDAO.getInstance().delete(estudante);
	}

	public void alterarEstudante(Estudante estudante) throws UpdateException, ClassNotFoundException, SQLException { 
		EstudanteDAO.getInstance().update(estudante);
	}

	public Estudante getEstudante(int codEstudante) throws SelectException, ClassNotFoundException, SQLException {
		return EstudanteDAO.getInstance().getEstudante(codEstudante);
	}

	public void cadastrarTrabalhoEstudanteProjeto(TrabalhoEstudanteProjeto trabalhoEstudanteProjeto) throws InsertException, ClassNotFoundException, SQLException {
		TrabalhoEstudanteProjetoDAO.getInstance().insert(trabalhoEstudanteProjeto);
	}
	
	public List<TrabalhoEstudanteProjeto> buscarTrabalhoEstudanteProjeto() throws SelectException, ClassNotFoundException, SQLException{
		return TrabalhoEstudanteProjetoDAO.getInstance().getAll();
	}

	public void removerTrabalhoEstudanteProjeto(TrabalhoEstudanteProjeto trabalhoEstudanteProjeto) throws DeleteException, ClassNotFoundException, SQLException {
		TrabalhoEstudanteProjetoDAO.getInstance().delete(trabalhoEstudanteProjeto);
	}

	public void alterarTrabalhoEstudanteProjeto(TrabalhoEstudanteProjeto trabalhoEstudanteProjeto) throws UpdateException, ClassNotFoundException, SQLException { 
		TrabalhoEstudanteProjetoDAO.getInstance().update(trabalhoEstudanteProjeto);
	}

	public TrabalhoEstudanteProjeto getTrabalhoEstudanteProjeto(int codTrabalhoEstudanteProjeto) throws SelectException, ClassNotFoundException, SQLException {
		return TrabalhoEstudanteProjetoDAO.getInstance().getTrabalhoEstudanteProjeto(codTrabalhoEstudanteProjeto);
	}

	public void cadastrarProjeto(Projeto projeto) throws InsertException, ClassNotFoundException, SQLException {
		ProjetoDAO.getInstance().insert(projeto);
	}

	public List<Projeto> buscarProjetos() throws SelectException, ClassNotFoundException, SQLException{
		return ProjetoDAO.getInstance().getAll();
	}

	public void removerProjeto(Projeto projeto) throws DeleteException, ClassNotFoundException, SQLException {
		ProjetoDAO.getInstance().delete(projeto);
	}

	public void alterarProjeto(Projeto projeto) throws UpdateException, ClassNotFoundException, SQLException { 
		ProjetoDAO.getInstance().update(projeto);
	}

	public Projeto getProjeto(int codProjeto) throws SelectException, ClassNotFoundException, SQLException {
		return ProjetoDAO.getInstance().getProjeto(codProjeto);
	}

	public void cadastrarTrabalhoProfessorDepartamento(TrabalhoProfessorDepartamento trabalhoProfessorDepartamento) throws InsertException, ClassNotFoundException, SQLException {
		TrabalhoProfessorDepartamentoDAO.getInstance().insert(trabalhoProfessorDepartamento);
	}

	public List<TrabalhoProfessorDepartamento> buscarTrabalhoProfessorDepartamento() throws SelectException, ClassNotFoundException, SQLException{
		return TrabalhoProfessorDepartamentoDAO.getInstance().getAll();
	}

	public void removerTrabalhoProfessorDepartamento(TrabalhoProfessorDepartamento trabalhoProfessorDepartamento) throws DeleteException, ClassNotFoundException, SQLException {
		TrabalhoProfessorDepartamentoDAO.getInstance().delete(trabalhoProfessorDepartamento);
	}

	public void alterarTrabalhoProfessorDepartamento(TrabalhoProfessorDepartamento trabalhoProfessorDepartamento) throws UpdateException, ClassNotFoundException, SQLException { 
		TrabalhoProfessorDepartamentoDAO.getInstance().update(trabalhoProfessorDepartamento);
	}

	public TrabalhoProfessorDepartamento getTrabalhoProfessorDepartamento(int codTrabalhoProfessorDepartamento) throws SelectException, ClassNotFoundException, SQLException {
		return TrabalhoProfessorDepartamentoDAO.getInstance().getTrabalhoProfessorDepartamento(codTrabalhoProfessorDepartamento);
	}
}
