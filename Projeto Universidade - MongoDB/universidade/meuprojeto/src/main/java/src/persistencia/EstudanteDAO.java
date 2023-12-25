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

import src.dados.Estudante;
import src.exceptions.*;

public class EstudanteDAO {
	private static EstudanteDAO instance = null;
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
	 
	public static EstudanteDAO getInstance() throws ClassNotFoundException {
		
		if(instance == null)
			instance = new EstudanteDAO();
		
		return instance;
	}

	MongoCollection<Document> sequences = database.getCollection("sequences");
	Document codEstudantes = sequences.find(eq("nome", "cod_estudantes")).first();
	
	MongoCollection<Document> estudantes = database.getCollection("estudantes");
	
	public void insert(Estudante estudante) throws InsertException, ClassNotFoundException {
		try {
			int codEstudante = codEstudantes.getInteger("last_value") + 1;
			
			sequences.updateOne(eq("nome", "cod_estudantes"), set("last_value", codEstudante));

			Document estudanteDocument = new Document("cod_estudante", codEstudante)
			.append("nome", estudante.getNome())
			.append("idade", estudante.getIdade())
			.append("tipo_curso", estudante.getTipoCurso())
			.append("cod_departamento_curso", estudante.getCodDepartamentoCurso())
			.append("cod_aconselhador", estudante.getCodAconselhador());
			
			estudantes.insertOne(estudanteDocument);
		} 
		catch (Exception e) {
			throw new InsertException("Nao foi possivel inserir o estudante.");
		}	
	}

	public void update(Estudante estudante) throws UpdateException, ClassNotFoundException {
		try {
			estudantes.updateOne(
				eq("cod_estudante", estudante.getCodEstudante()), 
				combine(
					set("nome", estudante.getNome()),
					set("idade", estudante.getIdade()),
					set("tipo_curso", estudante.getTipoCurso()),
					set("cod_departamento_curso", estudante.getCodDepartamentoCurso()),
					set("cod_aconselhador", estudante.getCodAconselhador())
				)
			);
		} 
		catch (Exception e) {
			throw new UpdateException("Nao foi possivel atualizar o estudante.");
		}	
	}
	
	public void delete(Estudante estudante ) throws DeleteException {
		
		try {
			estudantes.deleteOne(eq("cod_estudante", estudante.getCodEstudante()));

		} 
		catch (Exception e) {
			throw new DeleteException("Erro ao deletar estudante.");
		}	
	}
	
	
	public List<Estudante> getAll() throws SelectException {
		List<Estudante> listEstudantes = new ArrayList<Estudante>();

		FindIterable<Document> iterable = estudantes.find();
		
		try {
			for (Document estudanteDocument : iterable) {
				Estudante estudante = new Estudante();
				estudante.setCodEstudante(estudanteDocument.getInteger("cod_estudante"));
				estudante.setNome(estudanteDocument.get("nome").toString());
				estudante.setIdade(estudanteDocument.getInteger("idade"));
				estudante.setTipoCurso(estudanteDocument.get("tipo_curso").toString());
				estudante.setCodDepartamentoCurso(estudanteDocument.getInteger("cod_departamento_curso"));
				estudante.setCodAconselhador(estudanteDocument.getInteger("cod_aconselhador"));
				
				listEstudantes.add(estudante);
			}

			return listEstudantes;
		} 
		catch (Exception e) {
			throw new SelectException("Nao foi possivel encontrar o estudante.");
		}
	}
	
	public Estudante getEstudante(int codEstudante) throws SelectException {
		Estudante estudante = new Estudante();

		FindIterable<Document> iterable = estudantes.find(eq("cod_estudante", codEstudante));
		Document estudanteDocument = iterable.first();

		try {
			estudante.setCodEstudante(estudanteDocument.getInteger("cod_estudante"));
			estudante.setNome(estudanteDocument.getString("nome").toString());
			estudante.setIdade(estudanteDocument.getInteger("idade"));
			estudante.setTipoCurso(estudanteDocument.getString("tipo_curso").toString());
			estudante.setCodDepartamentoCurso(estudanteDocument.getInteger("cod_departamento_curso"));
			estudante.setCodAconselhador(estudanteDocument.getInteger("cod_aconselhador"));
			
			return estudante;
		} 
		catch (Exception e) {
			throw new SelectException("Nao foi possivel encontrar o estudante.");
		}
	}
}
