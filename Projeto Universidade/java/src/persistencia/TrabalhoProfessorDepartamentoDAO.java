package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dados.*;
import exceptions.*;

public class TrabalhoProfessorDepartamentoDAO {
    private static TrabalhoProfessorDepartamentoDAO instance = null;

    private PreparedStatement newId;
    private PreparedStatement insert;
    private PreparedStatement delete;
    private PreparedStatement update;
    private PreparedStatement getAll;
    private PreparedStatement getTrabalhoProfessorDepartamento;

    public TrabalhoProfessorDepartamentoDAO() throws ClassNotFoundException, SQLException {
		
		Connection conexao = Conexao.getConexao();
		
		newId = conexao.prepareStatement("select nextval('cod_trabalho_prof_depts')");
		insert = conexao.prepareStatement("insert into trabalho_professor_departamentos values(?,?,?,?)");
		update = conexao.prepareStatement("update trabalho_professor_departamentos set cod_trabalho_prof_dept = ?, " +
			"cod_professor_trabalho = ?, cod_departamento_trabalho = ?, per_tempo_trabalho = ? where cod_trabalho_prof_dept = ?");
		delete = conexao.prepareStatement("delete from trabalho_professor_departamentos where cod_trabalho_prof_dept = ?");
		getAll = conexao.prepareStatement("select * from trabalho_professor_departamentos"); 
		getTrabalhoProfessorDepartamento = conexao.prepareStatement("select * from trabalho_professor_departamentos where cod_trabalho_prof_dept = ?");

	}

    public static TrabalhoProfessorDepartamentoDAO getInstance() throws ClassNotFoundException, SQLException {
        
        if(instance == null)
            instance = new TrabalhoProfessorDepartamentoDAO();
        
        return instance;
    }

    public int newId() throws SelectException {
		
		try {
			ResultSet rs = newId.executeQuery();
			if(rs.next())
				return rs.getInt(1);
				
		} catch (SQLException e) {
			throw new SelectException("Nao foi possivel gerar um novo id para o trabalho do professor nesse departamento.");
		}
		
		return 0;
	}

    public void insert(TrabalhoProfessorDepartamento trabalhoProfessorDepartamento) throws InsertException {
		
		try {
			trabalhoProfessorDepartamento.setCodTrabalhoProfDep(newId());
			insert.setInt(1, trabalhoProfessorDepartamento.getCodTrabalhoProfDep());
			insert.setInt(2, trabalhoProfessorDepartamento.getCodProfessorTrabalho());
			insert.setInt(3, trabalhoProfessorDepartamento.getCodDepartamentoTrabalho());
			insert.setFloat(4, trabalhoProfessorDepartamento.getPerTempoTrabalho());
			insert.executeUpdate();
		} 
		catch (Exception e) {
			throw new InsertException("Nao foi possivel inserir um novo trabalho para esse professor nesse departamento.");
		}	
	}

    public void update(TrabalhoProfessorDepartamento trabalhoProfessorDepartamento) throws UpdateException {
		try {
			update.setInt(1, trabalhoProfessorDepartamento.getCodTrabalhoProfDep());
			update.setInt(2, trabalhoProfessorDepartamento.getCodProfessorTrabalho());
			update.setInt(3, trabalhoProfessorDepartamento.getCodDepartamentoTrabalho());
			update.setFloat(4, trabalhoProfessorDepartamento.getPerTempoTrabalho());	
			update.setInt(5, trabalhoProfessorDepartamento.getCodTrabalhoProfDep());
			update.executeUpdate();
		} 
		catch (Exception e) {
			throw new UpdateException("Nao foi possivel atualizar o trabalho desse professor nesse departamento.");
		}	
	}
    
    public void delete(TrabalhoProfessorDepartamento trabalhoProfessorDepartamento) throws DeleteException {
		
		try {
			delete.setInt(1, trabalhoProfessorDepartamento.getCodTrabalhoProfDep());
			delete.executeUpdate();
		} 
		catch (Exception e) {
			throw new DeleteException("Erro ao deletar o trabalho desse professor nesse departamento.");
		}	
	}

    public List<TrabalhoProfessorDepartamento> getAll() throws SelectException {
		List<TrabalhoProfessorDepartamento> trabalhoProfessorDepartamentos = new ArrayList<TrabalhoProfessorDepartamento>();
		TrabalhoProfessorDepartamento trabalhoProfessorDepartamento = null;
		
		try {
			ResultSet rs = getAll.executeQuery();
			while(rs.next()) {
				trabalhoProfessorDepartamento = new TrabalhoProfessorDepartamento();
				trabalhoProfessorDepartamento.setCodTrabalhoProfDep(rs.getInt("cod_trabalho_prof_dept"));
                trabalhoProfessorDepartamento.setCodProfessorTrabalho(rs.getInt("cod_professor_trabalho"));
                trabalhoProfessorDepartamento.setCodDepartamentoTrabalho(rs.getInt("cod_departamento_trabalho"));
				trabalhoProfessorDepartamento.setPerTempoTrabalho(rs.getFloat("per_tempo_trabalho"));
				
				trabalhoProfessorDepartamentos.add(trabalhoProfessorDepartamento);
			}
			
			return trabalhoProfessorDepartamentos;
		} 
		catch (SQLException e) {
			throw new SelectException("Nao foi possivel encontrar o trabalho desse professor nesse departamento.");
		}
	}
	
	public TrabalhoProfessorDepartamento getTrabalhoProfessorDepartamento(int CodTrabalhoProfDep) throws SelectException {
		TrabalhoProfessorDepartamento trabalhoProfessorDepartamento = null;
		
		try {
			getTrabalhoProfessorDepartamento.setInt(1, CodTrabalhoProfDep);
			ResultSet rs = getTrabalhoProfessorDepartamento.executeQuery();
			while(rs.next()) {
				trabalhoProfessorDepartamento = new TrabalhoProfessorDepartamento();
				trabalhoProfessorDepartamento.setCodTrabalhoProfDep(rs.getInt("cod_trabalho_prof_dept"));
                trabalhoProfessorDepartamento.setCodProfessorTrabalho(rs.getInt("cod_professor_trabalho"));
                trabalhoProfessorDepartamento.setCodDepartamentoTrabalho(rs.getInt("cod_departamento_trabalho"));
				trabalhoProfessorDepartamento.setPerTempoTrabalho(rs.getFloat("per_tempo_trabalho"));
			}
			
			return trabalhoProfessorDepartamento;
		} 
		catch (SQLException e) {
			throw new SelectException("Nao foi possivel encontrar o trabalho desse professor nesse departamento.");
		}
	}
}

