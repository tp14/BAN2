package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dados.*;
import exceptions.*;

public class DepartamentoDAO {
	private static DepartamentoDAO instance = null;
	
	private PreparedStatement newId;
	private PreparedStatement insert;
	private PreparedStatement delete;
	private PreparedStatement update;
	private PreparedStatement getAll;
	private PreparedStatement getDepartamento;

	
	private DepartamentoDAO() throws ClassNotFoundException, SQLException {
		
		Connection conexao = Conexao.getConexao();
		
		newId = conexao.prepareStatement("select nextval('cod_departamentos')");
		insert = conexao.prepareStatement("insert into departamentos values(?,?,?,?)");
		update = conexao.prepareStatement("update departamentos set cod_departamento = ?, nome = ?, escritorio = ?," +
			"cod_professor_lider = ? where cod_departamento = ?");
		delete = conexao.prepareStatement("delete from departamentos where cod_departamento = ?");
		getAll = conexao.prepareStatement("select * from departamentos"); 
		getDepartamento = conexao.prepareStatement("select * from departamentos where cod_departamento =?");
	}
	
	public static DepartamentoDAO getInstance() throws ClassNotFoundException, SQLException {
		
		if(instance == null)
			instance = new DepartamentoDAO();
		
		return instance;
	}
	
	public int newId() throws SelectException {
		
		try {
			ResultSet rs = newId.executeQuery();
			if(rs.next())
				return rs.getInt(1);
				
		} catch (SQLException e) {
			throw new SelectException("Nao foi possivel gerar um novo id para o departamento.");
		}
		
		return 0;
	}
	
	public void insert(Departamento departamento) throws InsertException {
			
		try {
			
			departamento.setCodDepartamento(newId());
			insert.setInt(1, departamento.getCodDepartamento());
			insert.setString(2, departamento.getNome());
			insert.setInt(3, departamento.getEscritorio());
			insert.setInt(4, departamento.getCodProfessorLider());
			insert.executeUpdate();
		} 
		catch (Exception e) {
			throw new InsertException("Nao foi possivel inserir o departamento.");
		}	
	}
	
	public void update(Departamento departamento) throws UpdateException {
	
		try {
			update.setInt(1, departamento.getCodDepartamento());
			update.setString(2, departamento.getNome());
			update.setInt(3, departamento.getEscritorio());
			update.setInt(4, departamento.getCodProfessorLider());
			update.setInt(5, departamento.getCodDepartamento());
			update.executeUpdate();
		} 
		catch (Exception e) {
			throw new UpdateException("Nao foi possivel atualizar o departamento.");
		}	
	}
	
	public void delete(Departamento departamento) throws DeleteException {
		
		try {
			delete.setInt(1, departamento.getCodDepartamento());
			delete.executeUpdate();
		} 
		catch (Exception e) {
			throw new DeleteException("Erro ao deletar departamento.");
		}	
	}
	
	public List<Departamento> getAll() throws SelectException {
		List<Departamento> departamentos = new ArrayList<Departamento>();
		Departamento departamento = null;
		
		try {
			ResultSet rs = getAll.executeQuery();
			while(rs.next()) {
				departamento = new Departamento();
				departamento.setCodDepartamento(rs.getInt("cod_departamento"));
				departamento.setNome(rs.getString("nome"));
				departamento.setEscritorio(rs.getInt("escritorio"));
				departamento.setCodProfessorLider(rs.getInt("cod_professor_lider"));
				
				departamentos.add(departamento);
			}
			return departamentos;
		} 
		catch (SQLException e) {
			throw new SelectException("Nao foi possivel encontrar o departamento.");
		}
	}
	
	public Departamento getDepartamento(int codDepartamento) throws SelectException {
		Departamento departamento = null;
		
		try {
			getDepartamento.setInt(1, codDepartamento);

			ResultSet rs = getDepartamento.executeQuery();

			while(rs.next()) {
				departamento = new Departamento();
				departamento.setCodDepartamento(rs.getInt("cod_departamento"));
				departamento.setNome(rs.getString("nome"));
				departamento.setEscritorio(rs.getInt("escritorio"));
				departamento.setCodProfessorLider(rs.getInt("cod_professor_lider"));
			}
			
			return departamento;
		} 
		catch (SQLException e) {
			throw new SelectException("Nao foi possivel encontrar o assistente.");
		}
	}
}
