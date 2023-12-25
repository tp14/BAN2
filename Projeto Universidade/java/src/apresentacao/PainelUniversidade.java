 package apresentacao;

import libs.java.awt.EventQueue;
import libs.javax.swing.JFrame;
import libs.javax.swing.JTabbedPane;
import libs.javax.swing.JPanel;

import negocio.Sistema;

import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;

public class PainelUniversidade {
    static Sistema sistema = new Sistema();
	
	private JFrame frameSistemaUniversidade;
	
	private JTabbedPane tabbedOpcoes;
	private JTabbedPane tabbedCadastrar;
	
	private JLayeredPane layeredBuscar;
	private JLayeredPane layeredAlterar;

	private JPanel panelBuscar;
	private JPanel panelDeletar;
	private JPanel panelAlterar;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PainelUniversidade window = new PainelUniversidade();
					window.frameSistemaUniversidade.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public PainelUniversidade() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Criando frame do sistema
		frameSistemaUniversidade = new JFrame();
		frameSistemaUniversidade.setBounds(200, 100, 789, 550);
		frameSistemaUniversidade.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabbedOpcoes = new JTabbedPane(JTabbedPane.TOP);
		frameSistemaUniversidade.getContentPane().add(tabbedOpcoes, BorderLayout.CENTER);
		
		tabbedCadastrar = new JTabbedPane(JTabbedPane.TOP);
		tabbedOpcoes.addTab("Cadastrar", null, tabbedCadastrar, null);
		
		layeredBuscar = new JLayeredPane();
		layeredBuscar.setLayout(new CardLayout(0, 0));
		tabbedOpcoes.addTab("Buscar", null, layeredBuscar, null);

		panelBuscar = new JPanel();
		panelBuscar.setLayout(null);
		layeredBuscar.add(panelBuscar, "name_184619816412700");

		panelDeletar = new JPanel();
		panelDeletar.setLayout(null);
		tabbedOpcoes.addTab("Deletar", null, panelDeletar, null);

		layeredAlterar = new JLayeredPane();
		layeredAlterar.setLayout(new CardLayout(0, 0));
		tabbedOpcoes.addTab("Alterar", null, layeredAlterar, null);

		panelAlterar = new JPanel();
		panelAlterar.setLayout(null);
		layeredAlterar.add(panelAlterar, "name_185067173012800");

		PainelProfessor painelProfessor = new PainelProfessor();
		painelProfessor.criarPainelCadastrarProfessor(tabbedCadastrar);
		painelProfessor.criarPainelBuscarProfessor(panelBuscar, layeredBuscar);
		painelProfessor.criarPainelDeletarProfessor(panelDeletar);
		painelProfessor.criarPainelAlterarProfessor(panelAlterar, layeredAlterar);

		PainelDepartamento painelDepartamento = new PainelDepartamento();
		painelDepartamento.criarPainelCadastrarDepartamento(tabbedCadastrar);
		painelDepartamento.criarPainelBuscarDepartamento(panelBuscar, layeredBuscar);
		painelDepartamento.criarPainelDeletarDepartamento(panelDeletar);
		painelDepartamento.criarPainelAlterarDepartamento(panelAlterar, layeredAlterar);

		PainelEstudante painelEstudante = new PainelEstudante();
		painelEstudante.criarPainelCadastrarEstudante(tabbedCadastrar);
		painelEstudante.criarPainelBuscarEstudante(panelBuscar, layeredBuscar);
		painelEstudante.criarPainelDeletarEstudante(panelDeletar);
		painelEstudante.criarPainelAlterarEstudante(panelAlterar, layeredAlterar);

		PainelTrabalhoEstudanteProjeto trabalhoEstudanteProjeto = new PainelTrabalhoEstudanteProjeto();
		trabalhoEstudanteProjeto.criarPanelCadastrarTrabalhoEstudanteProjeto(tabbedCadastrar);
		trabalhoEstudanteProjeto.criarPainelBuscarTrabalhoEstudanteProjeto(panelBuscar, layeredBuscar);
		trabalhoEstudanteProjeto.criarPainelDeletarTrabalhoEstudanteProjeto(panelDeletar);
		trabalhoEstudanteProjeto.criarPainelAlterarTrabalhoEstudanteProjeto(panelAlterar, layeredAlterar);

		PainelTrabalhoProfessorDepartamento trabalhoProfessorDepartamento = new PainelTrabalhoProfessorDepartamento();
		trabalhoProfessorDepartamento.criarPanelCadastrarTrabalhoProfessorDepartamento(tabbedCadastrar);
		trabalhoProfessorDepartamento.criarPainelBuscarTrabalhoProfessorDepartamento(panelBuscar, layeredBuscar);
		trabalhoProfessorDepartamento.criarPainelDeletarTrabalhoProfessorDepartamento(panelDeletar);
		trabalhoProfessorDepartamento.criarPainelAlterarTrabalhoProfessorDepartamento(panelAlterar, layeredAlterar);

		PainelProjeto painelProjeto = new PainelProjeto();
		painelProjeto.criarPainelCadastrarProjeto(tabbedCadastrar);
		painelProjeto.criarPainelBuscarProjeto(panelBuscar, layeredBuscar);
		painelProjeto.criarPainelDeletarProjeto(panelDeletar);
		painelProjeto.criarPainelAlterarProjeto(panelAlterar, layeredAlterar);
	}
}
