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

import src.dados.Departamento;
import src.exceptions.*;

public class DepartamentoDAO {
	private static DepartamentoDAO instance = null;
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
	 
	public static DepartamentoDAO getInstance() throws ClassNotFoundException {
		
		if(instance == null)
			instance = new DepartamentoDAO();
		
		return instance;
	}

	MongoCollection<Document> sequences = database.getCollection("sequences");
	Document codDepartamentos = sequences.find(eq("nome", "cod_departamentos")).first();
	
	MongoCollection<Document> departamentos = database.getCollection("departamentos");
	
	public void insert(Departamento departamento) throws InsertException, ClassNotFoundException {
		try {
			int codDepartamento = codDepartamentos.getInteger("last_value") + 1;

			sequences.updateOne(eq("nome", "cod_departamentos"), set("last_value", codDepartamento));

			Document departamentoDocument = new Document("cod_departamento", codDepartamento)
			.append("nome", departamento.getNome())
			.append("escritorio", departamento.getEscritorio())
			.append("cod_professor_lider", departamento.getCodProfessorLider());   
			
			departamentos.insertOne(departamentoDocument);
		} 
		catch (Exception e) {
			throw new InsertException("Nao foi possivel inserir o departamento.");
		}	
	}

	public void update(Departamento departamento) throws UpdateException, ClassNotFoundException {
		try {
			departamentos.updateOne(
				eq("cod_departamento", departamento.getCodDepartamento()), 
				combine(
					set("nome", departamento.getNome()),
					set("escritorio", departamento.getEscritorio()),
					set("cod_professor_lider", departamento.getCodProfessorLider())
				)
			);
		} 
		catch (Exception e) {
			throw new UpdateException("Nao foi possivel atualizar o departamento.");
		}	
	}
	
	public void delete(Departamento departamento ) throws DeleteException {
		
		try {
			departamentos.deleteOne(eq("cod_departamento", departamento.getCodDepartamento()));

		} 
		catch (Exception e) {
			throw new DeleteException("Erro ao deletar departamento.");
		}	
	}
	
	
	public List<Departamento> getAll() throws SelectException {
		List<Departamento> listDepartamentos = new ArrayList<Departamento>();

		FindIterable<Document> iterable = departamentos.find();
		
		try {
			for (Document departamentoDocument : iterable) {
				Departamento departamento = new Departamento();
				departamento.setCodDepartamento(departamentoDocument.getInteger("cod_departamento"));
				departamento.setNome(departamentoDocument.get("nome").toString());
				departamento.setEscritorio(departamentoDocument.getInteger("escritorio"));
				departamento.setCodProfessorLider(departamentoDocument.getInteger("cod_professor_lider"));
				
				listDepartamentos.add(departamento);
			}

			return listDepartamentos;
		} 
		catch (Exception e) {
			throw new SelectException("Nao foi possivel encontrar o departamento.");
		}
	}
	
	public Departamento getDepartamento(int codDepartamento) throws SelectException {
		Departamento departamento = new Departamento();

		FindIterable<Document> iterable = departamentos.find(eq("cod_departamento", codDepartamento));
		Document departamentoDocument = iterable.first();

		
		try {
			departamento.setCodDepartamento(departamentoDocument.getInteger("cod_departamento"));
			departamento.setNome(departamentoDocument.get("nome").toString());
			departamento.setEscritorio(departamentoDocument.getInteger("escritorio"));
			departamento.setCodProfessorLider(departamentoDocument.getInteger("cod_professor_lider"));
			
			return departamento;
		} 
		catch (Exception e) {
			throw new SelectException("Nao foi possivel encontrar o departamento.");
		}
	}
}
