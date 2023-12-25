package src.persistencia;

import java.util.ArrayList;
import java.util.Date;
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

import src.dados.Projeto;
import src.exceptions.*;

public class ProjetoDAO {
	private static ProjetoDAO instance = null;
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
	 
	public static ProjetoDAO getInstance() throws ClassNotFoundException {
		
		if(instance == null)
			instance = new ProjetoDAO();
		
		return instance;
	}

	MongoCollection<Document> sequences = database.getCollection("sequences");
	Document codProjetos = sequences.find(eq("nome", "cod_projetos")).first();
	
	MongoCollection<Document> projetos = database.getCollection("projetos");
	
	public void insert(Projeto projeto) throws InsertException, ClassNotFoundException {
		try {
			int codProjeto = codProjetos.getInteger("last_value") + 1;

			sequences.updateOne(eq("nome", "cod_projetos"), set("last_value", codProjeto));

			Document projetoDocument = new Document("cod_projeto", codProjeto)
			.append("orgao", projeto.getOrgao())
			.append("orcamento", projeto.getOrcamento())
			.append("data_inicio", projeto.getDataInicio())
			.append("data_final", projeto.getDataFinal())
			.append("cod_professor_gerente", projeto.getCodProfessorGerente())
			.append("professores_participantes", projeto.getProfessoresParticipantes())
            .append("estudantes_assistentes", projeto.getEstudantesAssistentes());
			
			projetos.insertOne(projetoDocument);
		} 
		catch (Exception e) {
			throw new InsertException("Nao foi possivel inserir o projeto.");
		}	
	}

	public void update(Projeto projeto) throws UpdateException, ClassNotFoundException {
		try {
			projetos.updateOne(
				eq("cod_projeto", projeto.getCodProjeto()), 
				combine(
					set("orgao", projeto.getOrgao()),
					set("orcamento", projeto.getOrcamento()),
					set("data_inicio", projeto.getDataInicio()),
					set("data_final", projeto.getDataFinal()),
					set("cod_professor_gerente", projeto.getCodProfessorGerente()),
					set("professores_participantes", projeto.getProfessoresParticipantes()),
                    set("estudantes_assistentes", projeto.getEstudantesAssistentes())
				)
			);
		} 
		catch (Exception e) {
			throw new UpdateException("Nao foi possivel atualizar o projeto.");
		}	
	}
	
	public void delete(Projeto projeto ) throws DeleteException {
		
		try {
			projetos.deleteOne(eq("cod_projeto", projeto.getCodProjeto()));

		} 
		catch (Exception e) {
			throw new DeleteException("Erro ao deletar projeto.");
		}	
	}
	
	public List<Projeto> getAll() throws SelectException {
		List<Projeto> listProjetos = new ArrayList<Projeto>();

		FindIterable<Document> iterable = projetos.find();
		try {
			for (Document projetoDocument : iterable) {
				Projeto projeto = new Projeto();
				projeto.setCodProjeto(projetoDocument.getInteger("cod_projeto"));
				projeto.setOrgao(projetoDocument.get("orgao").toString());
                Double orcamento = projetoDocument.getDouble("orcamento");
                float orcamentoFloat = orcamento.floatValue();
				projeto.setOrcamento(orcamentoFloat);
				projeto.setDataInicio((Date) projetoDocument.getDate("data_inicio"));
				projeto.setDataFinal((Date) projetoDocument.getDate("data_final"));
                projeto.setCodProfessorGerente(projetoDocument.getInteger("cod_professor_gerente"));
				@SuppressWarnings("unchecked")
				List<Integer> professoresParticipantes = (List<Integer>) projetoDocument.get("professores_participantes");
				projeto.setProfessoresParticipantes(professoresParticipantes);
				@SuppressWarnings("unchecked")
				List<Integer> estudantesAssistentes = (List<Integer>) projetoDocument.get("estudantes_assistentes");
				projeto.setEstudantesAssistentes(estudantesAssistentes);
				
				listProjetos.add(projeto);
			}

			return listProjetos;
		} 
		catch (Exception e) {
			throw new SelectException("Nao foi possivel encontrar o projeto.");
		}
	}
	
	public Projeto getProjeto(int codProjeto) throws SelectException {
		//List<Integer> projetosGeridos= new ArrayList<Integer>();
		//List<Integer> projetosTrabalhados = new ArrayList<Integer>();
		Projeto projeto = new Projeto();

		FindIterable<Document> iterable = projetos.find(eq("cod_projeto", codProjeto));
		Document projetoDocument = iterable.first();

    try {
        projeto.setCodProjeto(projetoDocument.getInteger("cod_projeto"));
        projeto.setOrgao(projetoDocument.get("orgao").toString());
        Double orcamento = projetoDocument.getDouble("orcamento");
        float orcamentoFloat = orcamento.floatValue();
        projeto.setOrcamento(orcamentoFloat);
        projeto.setDataInicio((Date) projetoDocument.getDate("data_inicio"));
        projeto.setDataFinal((Date) projetoDocument.getDate("data_final"));
        projeto.setCodProfessorGerente(projetoDocument.getInteger("cod_professor_gerente"));
        @SuppressWarnings("unchecked")
        List<Integer> professoresParticipantes = (List<Integer>) projetoDocument.get("professores_participantes");
        projeto.setProfessoresParticipantes(professoresParticipantes);
        @SuppressWarnings("unchecked")
        List<Integer> estudantesAssistentes = (List<Integer>) projetoDocument.get("estudantes_assistentes");
        projeto.setEstudantesAssistentes(estudantesAssistentes);
        
        return projeto;
		} 
		catch (Exception e) {
			throw new SelectException("Nao foi possivel encontrar o projeto.");
		}
	}
}
