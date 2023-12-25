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

import src.dados.TrabalhoProfessorDepartamento;
import src.exceptions.*;

public class TrabalhoProfessorDepartamentoDAO {
	private static TrabalhoProfessorDepartamentoDAO instance = null;
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

	public static TrabalhoProfessorDepartamentoDAO getInstance() throws ClassNotFoundException {
		if(instance == null)
			instance = new TrabalhoProfessorDepartamentoDAO();
		
		return instance;
	}

	MongoCollection<Document> sequences = database.getCollection("sequences");
	Document codTrabalhoProfDeps = sequences.find(eq("nome", "cod_trab_prof_departamentos")).first();
	
	MongoCollection<Document> trabProfDepartamentos = database.getCollection("trabalho_professor_departamentos");
	
	public void insert(TrabalhoProfessorDepartamento trabProfDepartamento) throws InsertException, ClassNotFoundException {
		try {
			int codTrabalhoProfDep = codTrabalhoProfDeps.getInteger("last_value") + 1;

			sequences.updateOne(eq("nome", "cod_trab_prof_departamentos"), set("last_value", codTrabalhoProfDep));

			Document trabProfDepartamentoDocument = new Document("cod_trab_prof_departamento", codTrabalhoProfDep)
			.append("cod_prof_trabalho", trabProfDepartamento.getCodProfessorTrabalho())
			.append("cod_depart_trabalho", trabProfDepartamento.getCodDepartamentoTrabalho())
			.append("per_tempo_trabalho", trabProfDepartamento.getPerTempoTrabalho());   
			
			trabProfDepartamentos.insertOne(trabProfDepartamentoDocument);
		} 
		catch (Exception e) {
			throw new InsertException("Nao foi possivel inserir um novo trabalho para esse professor nesse departamento.");
		}	
	}

	public void update(TrabalhoProfessorDepartamento trabProfDepartamento) throws UpdateException, ClassNotFoundException {
		try {
			trabProfDepartamentos.updateOne(
				eq("cod_trab_prof_departamento", trabProfDepartamento.getCodTrabalhoProfDep()), 
				combine(
					set("cod_prof_trabalho", trabProfDepartamento.getCodProfessorTrabalho()),
					set("cod_depart_trabalho", trabProfDepartamento.getCodDepartamentoTrabalho()),
					set("per_tempo_trabalho", trabProfDepartamento.getPerTempoTrabalho())
				)
			);
		} 
		catch (Exception e) {
			throw new UpdateException("Nao foi possivel atualizar o trabalho desse professor nesse departamento.");
		}	
	}
	
	public void delete(TrabalhoProfessorDepartamento trabProfDepartamento ) throws DeleteException {
		
		try {
			trabProfDepartamentos.deleteOne(eq("cod_trab_prof_departamento", trabProfDepartamento.getCodTrabalhoProfDep()));

		} 
		catch (Exception e) {
			throw new DeleteException("Erro ao deletar o trabalho desse professor nesse departamento.");
		}	
	}
	
	
	public List<TrabalhoProfessorDepartamento> getAll() throws SelectException {
		List<TrabalhoProfessorDepartamento> listTrabProfDepartamentos = new ArrayList<TrabalhoProfessorDepartamento>();

		FindIterable<Document> iterable = trabProfDepartamentos.find();
		
		try {
			for (Document trabProfDepartamentoDocument : iterable) {
				TrabalhoProfessorDepartamento trabProfDepartamento = new TrabalhoProfessorDepartamento();
				trabProfDepartamento.setCodTrabalhoProfDep(trabProfDepartamentoDocument.getInteger("cod_trab_prof_departamento"));
                trabProfDepartamento.setCodProfessorTrabalho(trabProfDepartamentoDocument.getInteger("cod_prof_trabalho"));
				trabProfDepartamento.setCodDepartamentoTrabalho(trabProfDepartamentoDocument.getInteger("cod_depart_trabalho"));
				Double perTempoTrabalho = trabProfDepartamentoDocument.getDouble("per_tempo_trabalho");
                float perTempoTrabalhoFloat = perTempoTrabalho.floatValue();
                trabProfDepartamento.setPerTempoTrabalho(perTempoTrabalhoFloat);
				
				listTrabProfDepartamentos.add(trabProfDepartamento);
			}

			return listTrabProfDepartamentos;
		} 
		catch (Exception e) {
			throw new SelectException("Nao foi possivel encontrar o trabalho desse professor nesse departamento.");
		}
	}
	
	public TrabalhoProfessorDepartamento getTrabalhoProfessorDepartamento(int codTrabalhoProfessorDepartamento) throws SelectException {
		TrabalhoProfessorDepartamento trabProfDepartamento = new TrabalhoProfessorDepartamento();

		FindIterable<Document> iterable = trabProfDepartamentos.find(eq("cod_trab_prof_departamento", codTrabalhoProfessorDepartamento));
		Document trabProfDepartamentoDocument = iterable.first();

		
		try {
			trabProfDepartamento.setCodTrabalhoProfDep(trabProfDepartamentoDocument.getInteger("cod_trab_prof_departamento"));
			trabProfDepartamento.setCodProfessorTrabalho(trabProfDepartamentoDocument.getInteger("cod_prof_trabalho"));
			trabProfDepartamento.setCodDepartamentoTrabalho(trabProfDepartamentoDocument.getInteger("cod_depart_trabalho"));
			Double perTempoTrabalho = trabProfDepartamentoDocument.getDouble("per_tempo_trabalho");
            float perTempoTrabalhoFloat = perTempoTrabalho.floatValue();
            trabProfDepartamento.setPerTempoTrabalho(perTempoTrabalhoFloat);
			
			return trabProfDepartamento;
		} 
		catch (Exception e) {
			throw new SelectException("Nao foi possivel encontrar o trabalho desse professor nesse departamento.");
		}
	}
}
