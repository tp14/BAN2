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

import src.dados.TrabalhoEstudanteProjeto;
import src.exceptions.*;

public class TrabalhoEstudanteProjetoDAO {
	private static TrabalhoEstudanteProjetoDAO instance = null;
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

	public static TrabalhoEstudanteProjetoDAO getInstance() throws ClassNotFoundException {
		if(instance == null)
			instance = new TrabalhoEstudanteProjetoDAO();
		
		return instance;
	}

	MongoCollection<Document> sequences = database.getCollection("sequences");
	Document codTrabalhoProfDeps = sequences.find(eq("nome", "cod_trab_estu_projetos")).first();
	
	MongoCollection<Document> trabEstProjetos = database.getCollection("trabalha_estudante_projetos");
	
	public void insert(TrabalhoEstudanteProjeto trabEstProjeto) throws InsertException, ClassNotFoundException {
		try {
			int codTrabalhoProfDep = codTrabalhoProfDeps.getInteger("last_value") + 1;

			sequences.updateOne(eq("nome", "cod_trab_estu_projetos"), set("last_value", codTrabalhoProfDep));
            
			Document trabEstProjetoDocument = new Document("cod_trab_estu_projeto", codTrabalhoProfDep)
			.append("cod_estu_participante", trabEstProjeto.getCodEstudanteParticipante())
			.append("cod_prof_supervisor", trabEstProjeto.getCodProfessorSupervisor()); 
			
			trabEstProjetos.insertOne(trabEstProjetoDocument);
		} 
		catch (Exception e) {
			throw new InsertException("Nao foi possivel inserir um novo trabalho do estudante no projeto.");
		}	
	}

	public void update(TrabalhoEstudanteProjeto trabEstProjeto) throws UpdateException, ClassNotFoundException {
		try {
			trabEstProjetos.updateOne(
				eq("cod_trab_estu_projeto", trabEstProjeto.getCodTrabalhoEstudanteProjeto()), 
				combine(
					set("cod_estu_participante", trabEstProjeto.getCodEstudanteParticipante()),
					set("cod_prof_supervisor", trabEstProjeto.getCodProfessorSupervisor())
				)
			);
		} 
		catch (Exception e) {
			throw new UpdateException("Nao foi possivel atualizar o trabalho do estudante no projeto.");
		}	
	}
	
	public void delete(TrabalhoEstudanteProjeto trabEstProjeto ) throws DeleteException {
		
		try {
			trabEstProjetos.deleteOne(eq("cod_trab_estu_projeto", trabEstProjeto.getCodTrabalhoEstudanteProjeto()));

		} 
		catch (Exception e) {
			throw new DeleteException("Erro ao deletar o trabalho do estudante no projeto.");
		}	
	}
	
	
	public List<TrabalhoEstudanteProjeto> getAll() throws SelectException {
		List<TrabalhoEstudanteProjeto> listTrabEstProjetos = new ArrayList<TrabalhoEstudanteProjeto>();

		FindIterable<Document> iterable = trabEstProjetos.find();
		
		try {
			for (Document trabEstProjetoDocument : iterable) {
				TrabalhoEstudanteProjeto trabEstProjeto = new TrabalhoEstudanteProjeto();
				trabEstProjeto.setCodTrabalhoEstudanteProjeto(trabEstProjetoDocument.getInteger("cod_trab_estu_projeto"));
                trabEstProjeto.setCodEstudanteParticipante(trabEstProjetoDocument.getInteger("cod_estu_participante"));
				trabEstProjeto.setCodProfessorSupervisor(trabEstProjetoDocument.getInteger("cod_prof_supervisor"));
				
				listTrabEstProjetos.add(trabEstProjeto);
			}

			return listTrabEstProjetos;
		} 
		catch (Exception e) {
			throw new SelectException("Nao foi possivel encontrar o trabalho do estudante no projeto.");
		}
	}
	
	public TrabalhoEstudanteProjeto getTrabalhoEstudanteProjeto(int codTrabalhoEstudanteProjeto) throws SelectException {
		TrabalhoEstudanteProjeto trabEstProjeto = new TrabalhoEstudanteProjeto();

		FindIterable<Document> iterable = trabEstProjetos.find(eq("cod_trab_estu_projeto", codTrabalhoEstudanteProjeto));
		Document trabEstProjetoDocument = iterable.first();

		
		try {
			trabEstProjeto.setCodTrabalhoEstudanteProjeto(trabEstProjetoDocument.getInteger("cod_trab_estu_projeto"));
			trabEstProjeto.setCodEstudanteParticipante(trabEstProjetoDocument.getInteger("cod_estu_participante"));
			trabEstProjeto.setCodProfessorSupervisor(trabEstProjetoDocument.getInteger("cod_prof_supervisor"));
			
			return trabEstProjeto;
		} 
		catch (Exception e) {
			throw new SelectException("Nao foi possivel encontrar o trabalho do estudante no projeto.");
		}
	}
}
