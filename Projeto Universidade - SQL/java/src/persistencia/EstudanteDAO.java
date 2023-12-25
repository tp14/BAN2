package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dados.*;
import exceptions.*;


public class EstudanteDAO {
	private static EstudanteDAO instance = null;
	
	private PreparedStatement newId;
	private PreparedStatement insert;
	private PreparedStatement delete;
	private PreparedStatement update;
	private PreparedStatement getEstudante;
	private PreparedStatement getAll;
	
	private EstudanteDAO() throws ClassNotFoundException, SQLException {
		
		Connection conexao = Conexao.getConexao();
		
		newId = conexao.prepareStatement("select nextval('cod_estudantes')");
		insert = conexao.prepareStatement("insert into estudantes values(?,?,?,?,?,?)");
		update = conexao.prepareStatement("update estudantes set cod_estudante = ?, nome = ?, idade = ?, tipo_curso = ?, cod_departamento_curso = ?, cod_aconselhador = ?  where cod_estudante = ?");
		delete = conexao.prepareStatement("delete from estudantes where cod_estudante = ?");
		getAll = conexao.prepareStatement("select * from estudantes"); 
		getEstudante = conexao.prepareStatement("select * from estudantes where cod_estudante =?");
		
	}
	
	public static EstudanteDAO getInstance() throws ClassNotFoundException, SQLException {
		
		if(instance == null)
			instance = new EstudanteDAO();
		
		return instance;
	}
	
	public int newId() throws SelectException {
		
		try {
			ResultSet rs = newId.executeQuery();
			if(rs.next())
				return rs.getInt(1);
				
		} catch (SQLException e) {
			throw new SelectException("Nao foi possivel gerar um novo id para o aluno.");
		}
		
		return 0;
	}
	
	public void insert(Estudante estudante) throws InsertException {
		
		try {
			estudante.setCodEstudante(newId());
			insert.setInt(1, estudante.getCodEstudante());
			insert.setString(2, estudante.getNome());
			insert.setInt(3, estudante.getIdade());
			insert.setString(4, estudante.getTipoCurso());
			insert.setInt(5, estudante.getCodDepartamentoCurso());
			insert.setInt(6, estudante.getCodAconselhador());
			insert.executeUpdate();
		} 
		catch (Exception e) {
			throw new InsertException("Nao foi possivel inserir o estudante.");
		}	
	}
	
	public void update(Estudante estudante) throws UpdateException {
		
		try {
			update.setInt(1, estudante.getCodEstudante());
			update.setString(2, estudante.getNome());
			update.setInt(3, estudante.getIdade());
			update.setString(4, estudante.getTipoCurso());
			update.setInt(5, estudante.getCodDepartamentoCurso());
			update.setInt(6, estudante.getCodAconselhador());
			update.setInt(7, estudante.getCodEstudante());
			update.executeUpdate();
		} 
		catch (Exception e) {
			throw new UpdateException("Nao foi possivel atualizar o estudante.");
		}	
	}
	
	public void delete(Estudante estudante ) throws DeleteException {
		
		try {
			delete.setInt(1, estudante.getCodEstudante());
			delete.executeUpdate();
		} 
		catch (Exception e) {
			throw new DeleteException("Erro ao deletar estudante.");
		}	
	}
	
	public List<Estudante> getAll() throws SelectException {
		List<Estudante> estudantes = new ArrayList<Estudante>();
		Estudante estudante = null;
		
		try {
			ResultSet rs = getAll.executeQuery();
			while(rs.next()) {
				estudante = new Estudante();
				estudante.setCodEstudante(rs.getInt("cod_estudante"));
				estudante.setNome(rs.getString("nome"));
				estudante.setIdade(rs.getInt("idade"));
				estudante.setTipoCurso(rs.getString("tipo_curso"));
				estudante.setCodDepartamentoCurso(rs.getInt("cod_departamento_curso"));
				estudante.setCodAconselhador(rs.getInt("cod_aconselhador"));

				estudantes.add(estudante);
			}
			return estudantes;
		} 
		catch (SQLException e) {
			throw new SelectException("Nao foi possivel encontrar o estudante.");
		}
	}
	
	public Estudante getEstudante(int codEstudante) throws SelectException {
		Estudante estudante = null;
		
		try {
			getEstudante.setInt(1, codEstudante);
			ResultSet rs = getEstudante.executeQuery();
			while(rs.next()) {
				estudante = new Estudante();
				estudante.setCodEstudante(rs.getInt("cod_estudante"));
				estudante.setNome(rs.getString("nome"));
				estudante.setIdade(rs.getInt("idade"));
				estudante.setTipoCurso(rs.getString("tipo_curso"));
				estudante.setCodDepartamentoCurso(rs.getInt("cod_departamento_curso"));
				estudante.setCodAconselhador(rs.getInt("cod_aconselhador"));
			}
			
			return estudante;
		} 
		catch (SQLException e) {
			throw new SelectException("Nao foi possivel encontrar o estudante.");
		}
	}
}
