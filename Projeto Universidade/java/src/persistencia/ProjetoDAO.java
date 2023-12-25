package persistencia;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.Date;

import dados.*;
import exceptions.*;

public class ProjetoDAO {
	private static ProjetoDAO instance = null;
	
	private PreparedStatement newId;
	private PreparedStatement insert;
	private PreparedStatement delete;
	private PreparedStatement update;
	private PreparedStatement getAll;
	private PreparedStatement getProjeto;
	
	private ProjetoDAO() throws ClassNotFoundException, SQLException {
		
		Connection conexao = Conexao.getConexao();
		
		newId = conexao.prepareStatement("select nextval('cod_projetos')");
		insert = conexao.prepareStatement("insert into projetos values(?,?,?,?,?,?,?,?)");
		update = conexao.prepareStatement("update projetos set cod_projeto = ?, orgao = ?, orcamento = ?, " + 
			"data_inicio = ?, data_final = ?, cod_professor_gerente = ?, professores_participantes = ?, " + 
			"estudantes_assistentes = ? where cod_projeto = ?");
		delete = conexao.prepareStatement("delete from projetos where cod_projeto = ?");
		getAll = conexao.prepareStatement("select * from projetos"); 
		getProjeto = conexao.prepareStatement("select * from projetos where cod_projeto =?");
	}
	
	public static ProjetoDAO getInstance() throws ClassNotFoundException, SQLException {
		
		if(instance == null)
			instance = new ProjetoDAO();
		
		return instance;
	}
	
	public int newId() throws SelectException {
		
		try {
			ResultSet rs = newId.executeQuery();
			if(rs.next())
				return rs.getInt(1);
				
		} catch (SQLException e) {
			throw new SelectException("Nao foi possivel gerar um novo id para o projeto.");
		}
		
		return 0;
	}
	
	public void insert(Projeto projeto) throws InsertException, ClassNotFoundException, SQLException {

		Connection conexao = Conexao.getConexao();

		Integer[] professoresParticipantes = projeto.getProfessoresParticipantes().toArray(new Integer[0]);
		Array arraySqlProfessoresParticipantes= conexao.createArrayOf("integer", professoresParticipantes);

		Integer[] estudantesAssistentes = projeto.getEstudantesAssistentes().toArray(new Integer[0]);
		Array arraySqlprojetosTrabalhados= conexao.createArrayOf("integer", estudantesAssistentes);

		try {
			projeto.setCodProjeto(newId());
			insert.setInt(1, projeto.getCodProjeto());
			insert.setString(2, projeto.getOrgao());
			insert.setFloat(3, projeto.getOrcamento());
			insert.setDate(4, new Date(projeto.getDataInicio().getTime()));
			insert.setDate(5, new Date(projeto.getDataFinal().getTime()));
			insert.setInt(6, projeto.getCodProfessorGerente());
			insert.setArray(7, arraySqlProfessoresParticipantes);
			insert.setArray(8, arraySqlprojetosTrabalhados);
			insert.executeUpdate();
		} 
		catch (Exception e) {
			throw new InsertException("Nao foi possivel inserir o projeto.");
		}	
	}
	
	public void update(Projeto projeto) throws UpdateException, ClassNotFoundException, SQLException {

		Connection conexao = Conexao.getConexao();

		Integer[] professoresParticipantes = projeto.getProfessoresParticipantes().toArray(new Integer[0]);
		Array arraySqlProfessoresParticipantes= conexao.createArrayOf("integer", professoresParticipantes);

		Integer[] estudantesAssistentes = projeto.getEstudantesAssistentes().toArray(new Integer[0]);
		Array arraySqlprojetosTrabalhados= conexao.createArrayOf("integer", estudantesAssistentes);
		

		try {
			update.setInt(1, projeto.getCodProjeto());
			update.setString(2, projeto.getOrgao());
			update.setFloat(3, projeto.getOrcamento());
			update.setDate(4, new Date(projeto.getDataInicio().getTime()));
			update.setDate(5, new Date(projeto.getDataFinal().getTime()));
			update.setInt(6, projeto.getCodProfessorGerente());
			update.setArray(7, arraySqlProfessoresParticipantes);
			update.setArray(8, arraySqlprojetosTrabalhados);
			update.setInt(9, projeto.getCodProjeto());
			update.executeUpdate();
		} 
		catch (Exception e) {
			throw new UpdateException("Nao foi possivel atualizar o projeto.");
		}	
	}
	
	public void delete(Projeto projeto) throws DeleteException {
		
		try {
			delete.setInt(1, projeto.getCodProjeto());
			delete.executeUpdate();
		} 
		catch (Exception e) {
			throw new DeleteException("Erro para deletar o projeto.");
		}	
	}
	
	public List<Projeto> getAll() throws SelectException {
		List<Projeto> projetos = new ArrayList<Projeto>();
		List<Integer> professoresParticipantes = new ArrayList<Integer>();
		List<Integer> estudantesAssistentes = new ArrayList<Integer>();
		Projeto projeto = null;
		
		try {
			ResultSet rs = getAll.executeQuery();
			while(rs.next()) {
				projeto = new Projeto();
				projeto.setCodProjeto(rs.getInt("cod_projeto"));
				projeto.setOrgao(rs.getString("orgao"));
				projeto.setOrcamento(rs.getFloat("orcamento"));
				projeto.setDataInicio(rs.getDate("data_inicio"));
				projeto.setDataFinal(rs.getDate("data_final"));
				projeto.setCodProfessorGerente(rs.getInt("cod_professor_gerente"));
				professoresParticipantes = Arrays.asList((Integer[]) rs.getArray("professores_participantes").getArray());
				projeto.setProfessoresParticipantes(professoresParticipantes);
				estudantesAssistentes = Arrays.asList((Integer[]) rs.getArray("estudantes_assistentes").getArray());
				projeto.setEstudantesAssistentes(estudantesAssistentes);

				projetos.add(projeto);
			}
			
			return projetos;
		} 
		catch (SQLException e) {
			throw new SelectException("Nao foi possivel encontrar o projeto.");
		}
	}
	
	public Projeto getProjeto(int codProjeto) throws SelectException {
		List<Integer> professoresParticipantes = new ArrayList<Integer>();
		List<Integer> estudantesAssistentes = new ArrayList<Integer>();
		Projeto projeto = null;
		
		try {
			getProjeto.setInt(1, codProjeto);
			ResultSet rs = getProjeto.executeQuery();
			while(rs.next()) {
				projeto = new Projeto();
				projeto.setCodProjeto(rs.getInt("cod_projeto"));
				projeto.setOrgao(rs.getString("orgao"));
				projeto.setOrcamento(rs.getFloat("orcamento"));
				projeto.setDataInicio(rs.getDate("data_inicio"));
				projeto.setDataFinal(rs.getDate("data_final"));
				projeto.setCodProfessorGerente(rs.getInt("cod_professor_gerente"));
				professoresParticipantes = Arrays.asList((Integer[]) rs.getArray("professores_participantes").getArray());
				projeto.setProfessoresParticipantes(professoresParticipantes);
				estudantesAssistentes = Arrays.asList((Integer[]) rs.getArray("estudantes_assistentes").getArray());
				projeto.setEstudantesAssistentes(estudantesAssistentes);
			}
			
			return projeto;
		} 
		catch (SQLException e) {
			throw new SelectException("Nao foi possivel encontrar o projeto.");
		}
	}
}
