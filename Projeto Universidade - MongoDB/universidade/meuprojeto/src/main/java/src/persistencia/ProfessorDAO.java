package src.persistencia;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

import src.dados.Professor;
import src.exceptions.*;

public class ProfessorDAO {
	private static ProfessorDAO instance = null;
	String connectionString = "mongodb+srv://Pimenta:1234@cluster0.kps4o7r.mongodb.net/?retryWrites=true&w=majority";

	ServerApi serverApi = ServerApi.builder()
			.version(ServerApiVersion.V1)
			.build();

	MongoClientSettings settings = MongoClientSettings.builder()
			.applyConnectionString(new ConnectionString(connectionString))
			.serverApi(serverApi)
			.build();

	MongoClient mongoClient = MongoClients.create(settings);
	MongoDatabase database = mongoClient.getDatabase("Universidade");
	 
	public static ProfessorDAO getInstance() throws ClassNotFoundException {
		
		if(instance == null)
			instance = new ProfessorDAO();
		
		return instance;
	}

	MongoCollection<Document> sequences = database.getCollection("sequences");
	Document codProfessores = sequences.find(eq("nome", "cod_professores")).first();
	
	MongoCollection<Document> professores = database.getCollection("professores");
	
	public void insert(Professor professor) throws InsertException, ClassNotFoundException {
		try {
			
			
			int codProfessor = codProfessores.getInteger("last_value") + 1;
			
			sequences.updateOne(eq("nome", "cod_professores"), set("last_value", codProfessor));

			Document professorDocument = new Document("cod_professor", codProfessor)
			.append("nome", professor.getNome())
			.append("idade", professor.getIdade())
			.append("sala", professor.getSala())
			.append("especialidade", professor.getEspecialidade())
			.append("projetos_geridos", professor.getProjetosGeridos())
			.append("projetos_trabalhados", professor.getProjetosTrabalhados());
			
			professores.insertOne(professorDocument);
		} 
		catch (Exception e) {
			throw new InsertException("Nao foi possivel inserir o professor.");
		}	
	}

	public void update(Professor professor) throws UpdateException, ClassNotFoundException {
		try {
			professores.updateOne(
				eq("cod_professor", professor.getCodProfessor()), 
				combine(
					set("nome", professor.getNome()),
					set("idade", professor.getIdade()),
					set("sala", professor.getSala()),
					set("especialidade", professor.getEspecialidade()),
					set("projetos_geridos", professor.getProjetosGeridos()),
					set("projetos_trabalhados", professor.getProjetosTrabalhados())
				)
			);
		} 
		catch (Exception e) {
			throw new UpdateException("Nao foi possivel atualizar o professor.");
		}	
	}
	
	public void delete(Professor professor ) throws DeleteException {
		
		try {
			professores.deleteOne(eq("cod_professor", professor.getCodProfessor()));

		} 
		catch (Exception e) {
			throw new DeleteException("Erro ao deletar professor.");
		}	
	}
	
	
	public List<Professor> getAll() throws SelectException {
		List<Professor> listProfessores = new ArrayList<Professor>();
		//List<Integer> projetosGeridos = new ArrayList<Integer>();
		//List<Integer> projetosTrabalhados = new ArrayList<Integer>();

		FindIterable<Document> iterable = professores.find();
		
		try {
			for (Document professorDocument : iterable) {
				Professor professor = new Professor();
				professor.setCodProfessor(professorDocument.getInteger("cod_professor"));
				professor.setNome(professorDocument.get("nome").toString());
				professor.setIdade(professorDocument.getInteger("idade"));
				professor.setSala(professorDocument.getInteger("sala"));
				professor.setEspecialidade(professorDocument.get("especialidade").toString());
				@SuppressWarnings("unchecked")
				List<Integer> projetosGeridos = (List<Integer>) professorDocument.get("projetos_geridos");
				professor.setProjetosGeridos(projetosGeridos);
				@SuppressWarnings("unchecked")
				List<Integer> projetosTrabalhados = (List<Integer>) professorDocument.get("projetos_trabalhados");
				professor.setProjetosTrabalhados(projetosTrabalhados);
				
				listProfessores.add(professor);
			}

			return listProfessores;
		} 
		catch (Exception e) {
			throw new SelectException("Nao foi possivel encontrar o professor.");
		}
	}
	
	public Professor getProfessor(int codProfessor) throws SelectException {
		//List<Integer> projetosGeridos= new ArrayList<Integer>();
		//List<Integer> projetosTrabalhados = new ArrayList<Integer>();
		Professor professor = new Professor();

		FindIterable<Document> iterable = professores.find(eq("cod_professor", codProfessor));
		Document professorDocument = iterable.first();

		try {
			professor.setCodProfessor(professorDocument.getInteger("cod_professor"));
			professor.setNome(professorDocument.get("nome").toString());
			professor.setIdade(professorDocument.getInteger("idade"));
			professor.setSala(professorDocument.getInteger("sala"));
			professor.setEspecialidade(professorDocument.getString("especialidade"));
			@SuppressWarnings("unchecked")
			List<Integer> projetosGeridos = (List<Integer>) professorDocument.get("projetos_geridos");
			professor.setProjetosGeridos(projetosGeridos);
			@SuppressWarnings("unchecked")
			List<Integer> projetosTrabalhados = (List<Integer>) professorDocument.get("projetos_trabalhados");
			professor.setProjetosTrabalhados(projetosTrabalhados);
			
			return professor;
		} 
		catch (Exception e) {
			throw new SelectException("Nao foi possivel encontrar o professor.");
		}
	}
}
