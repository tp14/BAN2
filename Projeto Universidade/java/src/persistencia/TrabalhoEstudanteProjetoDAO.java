package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dados.TrabalhoEstudanteProjeto;
import exceptions.*;

public class TrabalhoEstudanteProjetoDAO {
	private static TrabalhoEstudanteProjetoDAO instance = null;
	
	private PreparedStatement newId;
	private PreparedStatement insert;
	private PreparedStatement delete;
	private PreparedStatement update;
	private PreparedStatement getAll;
	private PreparedStatement getTrabalhoEstudanteProjeto;

	
	private TrabalhoEstudanteProjetoDAO() throws ClassNotFoundException, SQLException {
		
		Connection conexao = Conexao.getConexao();
		
		newId = conexao.prepareStatement("select nextval('cod_trabalho_estu_projs')");
		insert = conexao.prepareStatement("insert into trabalha_estudante_projetos values(?,?,?)");
		update = conexao.prepareStatement("update trabalha_estudante_projetos set cod_trabalho_estu_proj = ?, cod_estu_participante = ?," + 
			"cod_prof_supervisor = ? where cod_trabalho_estu_proj = ?");
		delete = conexao.prepareStatement("delete from trabalha_estudante_projetos where cod_trabalho_estu_proj = ?");
		getAll = conexao.prepareStatement("select * from trabalha_estudante_projetos"); 
		getTrabalhoEstudanteProjeto = conexao.prepareStatement("select * from trabalha_estudante_projetos where cod_trabalho_estu_proj =?");
	}
	
	public static TrabalhoEstudanteProjetoDAO getInstance() throws ClassNotFoundException, SQLException {
		
		if(instance == null)
			instance = new TrabalhoEstudanteProjetoDAO();
		
		return instance;
	}
	
	public int newId() throws SelectException {
		
		try {
			ResultSet rs = newId.executeQuery();
			if(rs.next())
				return rs.getInt(1);
				
		} catch (SQLException e) {
			throw new SelectException("Nao foi possivel gerar um novo id para o trabalho do estudante no projeto.");
		}
		
		return 0;
	}
	
	public void insert(TrabalhoEstudanteProjeto trabalhoEstudanteProjeto) throws InsertException {
			
		try {
			
			trabalhoEstudanteProjeto.setCodTrabalhoEstudanteProjeto(newId());
			insert.setInt(1, trabalhoEstudanteProjeto.getCodTrabalhoEstudanteProjeto());
			insert.setInt(2, trabalhoEstudanteProjeto.getCodEstudanteParticipante());
			insert.setInt(3, trabalhoEstudanteProjeto.getCodProfessorSupervisor());
			insert.executeUpdate();
		} 
		catch (Exception e) {
			throw new InsertException("Nao foi possivel inserir o trabalho do estudante nesse projeto.");
		}	
	}
	
	public void update(TrabalhoEstudanteProjeto trabalhoEstudanteProjeto) throws UpdateException {
		
		try {
			update.setInt(1, trabalhoEstudanteProjeto.getCodTrabalhoEstudanteProjeto());
			update.setInt(2, trabalhoEstudanteProjeto.getCodEstudanteParticipante());
			update.setInt(3, trabalhoEstudanteProjeto.getCodProfessorSupervisor());
			update.setInt(4, trabalhoEstudanteProjeto.getCodTrabalhoEstudanteProjeto());
			update.executeUpdate();
		} 
		catch (Exception e) {
			throw new UpdateException("Nao foi possivel atualizar o trabalho do estudante nesse projeto.");
		}	
	}
	
	public void delete(TrabalhoEstudanteProjeto trabalhoEstudanteProjeto) throws DeleteException {
		
		try {
			delete.setInt(1, trabalhoEstudanteProjeto.getCodTrabalhoEstudanteProjeto());
			delete.executeUpdate();
		} 
		catch (Exception e) {
			throw new DeleteException("Erro ao deletar o trabalho do estudante nesse projeto.");
		}	
	}
	
	public List<TrabalhoEstudanteProjeto> getAll() throws SelectException {
		List<TrabalhoEstudanteProjeto> trabalhoEstudanteProjetos = new ArrayList<TrabalhoEstudanteProjeto>();
		TrabalhoEstudanteProjeto trabalhoEstudanteProjeto = null;
		
		try {
			ResultSet rs = getAll.executeQuery();
			while(rs.next()) {
				trabalhoEstudanteProjeto = new TrabalhoEstudanteProjeto();
				trabalhoEstudanteProjeto.setCodTrabalhoEstudanteProjeto(rs.getInt("cod_trabalho_estu_proj"));
				trabalhoEstudanteProjeto.setCodEstudanteParticipante(rs.getInt("cod_estu_participante"));
				trabalhoEstudanteProjeto.setCodProfessorSupervisor(rs.getInt("cod_prof_supervisor"));
				
				trabalhoEstudanteProjetos.add(trabalhoEstudanteProjeto);
			}
			return trabalhoEstudanteProjetos;
		} 
		catch (SQLException e) {
			throw new SelectException("Nao foi possivel encontrar o trabalho do estudante nesse projeto.");
		}
	}
	
	public TrabalhoEstudanteProjeto getTrabalhoEstudanteProjeto(int codTrabalhoEstudanteProjeto) throws SelectException {
		TrabalhoEstudanteProjeto trabalhoEstudanteProjeto = null;
		
		try {
			getTrabalhoEstudanteProjeto.setInt(1, codTrabalhoEstudanteProjeto);

			ResultSet rs = getTrabalhoEstudanteProjeto.executeQuery();

			while(rs.next()) {
				trabalhoEstudanteProjeto = new TrabalhoEstudanteProjeto();
				trabalhoEstudanteProjeto.setCodTrabalhoEstudanteProjeto(rs.getInt("cod_trabalho_estu_proj"));
				trabalhoEstudanteProjeto.setCodEstudanteParticipante(rs.getInt("cod_estu_participante"));
				trabalhoEstudanteProjeto.setCodProfessorSupervisor(rs.getInt("cod_prof_supervisor"));
			}
			
			return trabalhoEstudanteProjeto;
		} 
		catch (SQLException e) {
			throw new SelectException("Nao foi possivel encontrar o trabalho do estudante nesse projeto.");
		}
	}
}
