package persistencia;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dados.Professor;
import exceptions.*;

public class ProfessorDAO {
	private static ProfessorDAO instance = null;
	
	private PreparedStatement newId;
	private PreparedStatement insert;
	private PreparedStatement delete;
	private PreparedStatement update;
	private PreparedStatement getAll;
	private PreparedStatement getProfessor;
	
	private ProfessorDAO() throws ClassNotFoundException, SQLException {
		
		Connection conexao = Conexao.getConexao();

		newId = conexao.prepareStatement("select nextval('cod_professores')");
		insert = conexao.prepareStatement("insert into professores values (?, ?, ?, ?, ?, ?, ?)");
		update = conexao.prepareStatement(
			"update professores set cod_professor = ?, nome = ?, idade = ?, sala = ?, especialidade = ?" 
			+ ", projetos_geridos = ?, projetos_trabalhados = ? where cod_professor = ?");
		delete = conexao.prepareStatement("delete from professores where cod_professor = ?");
		getAll = conexao.prepareStatement("select * from professores"); 
		getProfessor = conexao.prepareStatement("select * from professores where cod_professor =?");
		
	}
	
	public static ProfessorDAO getInstance() throws ClassNotFoundException, SQLException {
		
		if(instance == null)
			instance = new ProfessorDAO();
		
		return instance;
	}
	
	public int newId() throws SelectException {
		
		try {
			ResultSet rs = newId.executeQuery();
			if(rs.next())
				return rs.getInt(1);
				
		} catch (SQLException e) {
			throw new SelectException("Nao foi possivel gerar um novo id para o professor.");
		}
		
		return 0;
	}
	
	public void insert(Professor professor) throws InsertException, SQLException, ClassNotFoundException {

		Connection conexao = Conexao.getConexao();

		Integer[] projetosGeridos = professor.getProjetosGeridos().toArray(new Integer[0]);
		Array arraySqlprojetosGeridos = conexao.createArrayOf("integer", projetosGeridos);

		Integer[] projetosTrabalhados = professor.getProjetosTrabalhados().toArray(new Integer[0]);
		Array arraySqlprojetosTrabalhados= conexao.createArrayOf("integer", projetosTrabalhados);

		try {
			professor.setCodProfessor(newId());
			insert.setInt(1, professor.getCodProfessor());
			insert.setString(2, professor.getNome());
			insert.setInt(3, professor.getIdade());
			insert.setInt(4, professor.getSala());
			insert.setString(5, professor.getEspecialidade());
			insert.setArray(6, arraySqlprojetosGeridos);
			insert.setArray(7, arraySqlprojetosTrabalhados);
			insert.executeUpdate();
		} 
		catch (Exception e) {
			throw new InsertException("Nao foi possivel inserir o professor.");
		}	
	}
	
	public void update(Professor professor) throws UpdateException, ClassNotFoundException, SQLException {

		Connection conexao = Conexao.getConexao();

		Integer[] projetosGeridos = professor.getProjetosGeridos().toArray(new Integer[0]);
		Array arraySqlprojetosGeridos = conexao.createArrayOf("integer", projetosGeridos);

		Integer[] projetosTrabalhados = professor.getProjetosTrabalhados().toArray(new Integer[0]);
		Array arraySqlprojetosTrabalhados= conexao.createArrayOf("integer", projetosTrabalhados);
		
		try {
			update.setInt(1, professor.getCodProfessor());
			update.setString(2, professor.getNome());
			update.setInt(3, professor.getIdade());
			update.setLong(4, professor.getSala());
			update.setString(5, professor.getEspecialidade());
			update.setArray(6, arraySqlprojetosGeridos);
			update.setArray(7, arraySqlprojetosTrabalhados);
			update.setInt(8, professor.getCodProfessor());
			update.executeUpdate();
		} 
		catch (Exception e) {
			throw new UpdateException("Nao foi possivel atualizar o professor.");
		}	
	}
	
	public void delete(Professor professor ) throws DeleteException {
		
		try {
			delete.setInt(1, professor.getCodProfessor());
			delete.executeUpdate();
		} 
		catch (Exception e) {
			throw new DeleteException("Erro ao deletar professor.");
		}	
	}
	
	public List<Professor> getAll() throws SelectException {
		List<Professor> professores = new ArrayList<Professor>();
		List<Integer> projetosGeridos= new ArrayList<Integer>();
		List<Integer> projetosTrabalhados = new ArrayList<Integer>();
		Professor professor = null;
		
		try {
			ResultSet rs = getAll.executeQuery();
			while(rs.next()) {
				professor = new Professor();
				professor.setCodProfessor(rs.getInt("cod_professor"));
				professor.setNome(rs.getString("nome"));
				professor.setIdade(rs.getInt("idade"));
				professor.setSala(rs.getInt("sala"));
				professor.setEspecialidade(rs.getString("especialidade"));
				projetosGeridos = Arrays.asList((Integer[]) rs.getArray("projetos_geridos").getArray());
				professor.setProjetosGeridos(projetosGeridos);
				projetosTrabalhados = Arrays.asList((Integer[]) rs.getArray("projetos_trabalhados").getArray());
				professor.setProjetosTrabalhados(projetosTrabalhados);
				
				professores.add(professor);
			}
			return professores;
		} 
		catch (SQLException e) {
			throw new SelectException("Nao foi possivel encontrar o professor.");
		}
	}
	
	public Professor getProfessor(int codProfessor) throws SelectException {
		List<Integer> projetosGeridos= new ArrayList<Integer>();
		List<Integer> projetosTrabalhados = new ArrayList<Integer>();
		Professor professor = null;
		
		try {
			getProfessor.setInt(1, codProfessor);

			ResultSet rs = getProfessor.executeQuery();

			while(rs.next()) {
				professor = new Professor();
				professor.setCodProfessor(rs.getInt("cod_professor"));
				professor.setNome(rs.getString("nome"));
				professor.setIdade(rs.getInt("idade"));
				professor.setSala(rs.getInt("sala"));
				professor.setEspecialidade(rs.getString("especialidade"));
				projetosGeridos = Arrays.asList((Integer[]) rs.getArray("projetos_geridos").getArray());
				professor.setProjetosGeridos(projetosGeridos);
				projetosTrabalhados = Arrays.asList((Integer[]) rs.getArray("projetos_trabalhados").getArray());
				professor.setProjetosTrabalhados(projetosTrabalhados);
			}

			return professor;
		} 
		catch (SQLException e) {
			throw new SelectException("Nao foi possivel encontrar o professor.");
		}
	}
}
