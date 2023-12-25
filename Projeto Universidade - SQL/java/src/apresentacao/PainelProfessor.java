package apresentacao;

import negocio.Sistema;
import persistencia.ProfessorDAO;
import dados.Professor;
import exceptions.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PainelProfessor {

    static Sistema sistema = new Sistema();

    private JPanel panelCadastrarProfessor;

    private JTextField textNomeProfessor;
    private JTextField textIdadeProfessor;
    private JTextField textSalaProfessor;
    private JTextField textEspecialidade;
	private JTextField textProjetosGeridos;
	private JTextField textProjetosTrabalhados;

    private JTable tableProfessores = new JTable(null, null, null);
    private JScrollPane scrollProfessores = new JScrollPane(tableProfessores);

    JTextField textDeletarProfessor = new JTextField();

    public void criarPainelCadastrarProfessor(JTabbedPane tabbedCadastrar) {
        panelCadastrarProfessor = new JPanel();
        panelCadastrarProfessor.setLayout(null);
        tabbedCadastrar.addTab("Professor", null, panelCadastrarProfessor, null);
        
        JLabel lblNomeProfessor = new JLabel("Nome:");
        lblNomeProfessor.setToolTipText("");
        lblNomeProfessor.setBounds(82, 31, 80, 22);
        panelCadastrarProfessor.add(lblNomeProfessor);
        
        textNomeProfessor = new JTextField();
        textNomeProfessor.setBounds(244, 31, 374, 22);
        textNomeProfessor.setColumns(10);
        panelCadastrarProfessor.add(textNomeProfessor);
        
        JLabel lblIdadeProfessor = new JLabel("Idade:");
        lblIdadeProfessor.setBounds(82, 84, 46, 22);
        panelCadastrarProfessor.add(lblIdadeProfessor);
        
        textIdadeProfessor = new JTextField();
        textIdadeProfessor.setBounds(244, 84, 374, 22);
        textIdadeProfessor.setColumns(10);
        panelCadastrarProfessor.add(textIdadeProfessor);
        
        JLabel lblSalaProfessor = new JLabel("Sala:");
        lblSalaProfessor.setBounds(82, 137, 46, 22);
        panelCadastrarProfessor.add(lblSalaProfessor);
        
        textSalaProfessor = new JTextField();
        textSalaProfessor.setBounds(244, 137, 374, 22);
        textSalaProfessor.setColumns(10);
        panelCadastrarProfessor.add(textSalaProfessor);
        
        JLabel lblEspecialidade = new JLabel("Especialidade:");
        lblEspecialidade.setBounds(82, 190, 92, 22);
        panelCadastrarProfessor.add(lblEspecialidade);
        
        textEspecialidade = new JTextField();
        textEspecialidade.setBounds(244, 190, 374, 22);
        textEspecialidade.setColumns(10);
        panelCadastrarProfessor.add(textEspecialidade);

		JLabel lblProjetosGeridos= new JLabel("Projetos Geridos:");
        lblProjetosGeridos.setBounds(82, 243, 110, 22);
        panelCadastrarProfessor.add(lblProjetosGeridos);
        
        textProjetosGeridos = new JTextField();
        textProjetosGeridos.setBounds(244, 243, 374, 22);
        textProjetosGeridos.setColumns(10);
        panelCadastrarProfessor.add(textProjetosGeridos);

		JLabel lblProjetosTrabalhados= new JLabel("Projetos Trabalhados:");
        lblProjetosTrabalhados.setBounds(82, 296, 130, 22);
        panelCadastrarProfessor.add(lblProjetosTrabalhados);
        
        textProjetosTrabalhados = new JTextField();
        textProjetosTrabalhados.setBounds(244, 296, 374, 22);
        textProjetosTrabalhados.setColumns(10);
        panelCadastrarProfessor.add(textProjetosTrabalhados);
        
        JButton btnRealizarCadastroProfessor = new JButton("Cadastrar Professor");
        btnRealizarCadastroProfessor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cadastrarProfessor();
            }
        });
        btnRealizarCadastroProfessor.setBounds(244, 350, 159, 22);
        panelCadastrarProfessor.add(btnRealizarCadastroProfessor);
    }
    
    public void cadastrarProfessor() {
        Professor professor = new Professor();
        
        professor.setNome(textNomeProfessor.getText());
        professor.setIdade(Integer.parseInt(textIdadeProfessor.getText()));		
        professor.setSala(Integer.parseInt(textSalaProfessor.getText()));						
        professor.setEspecialidade(textEspecialidade.getText());
		professor.setProjetosGeridos(parseList(textProjetosGeridos.getText()));
		professor.setProjetosTrabalhados(parseList(textProjetosTrabalhados.getText()));

        try {
            sistema.cadastrarProfessor(professor);
            limparCadastrarProfessor();
            JOptionPane.showMessageDialog(null, "Cadastro do Professor efetuado com sucesso!");
        } catch (ClassNotFoundException e1) {
            System.err.println(e1.getMessage());
            System.err.println("Erro, Professor nao encontrado!");
            JOptionPane.showMessageDialog(null, "Erro, Professor nao encontrado!");
        } catch (InsertException e2) {
            System.err.println(e2.getMessage());
            System.err.println("Erro ao inserir Professor!");
            JOptionPane.showMessageDialog(null, "Erro ao inserir Professor!");
        } catch (SQLException e3) {
            System.err.println(e3.getMessage());
            System.err.println("Erro ao acessar o banco de dados!");
            JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
        }
    }

    public void limparCadastrarProfessor() {
        textNomeProfessor.setText("");
        textIdadeProfessor.setText("");
        textSalaProfessor.setText("");
        textEspecialidade.setText("");
		textProjetosGeridos.setText("");
		textProjetosTrabalhados.setText("");
    }

   
	public void criarPainelBuscarProfessor(JPanel panelBuscar, JLayeredPane layeredBuscar) {
        JButton btnListarProfessores = new JButton("Listar Professores");
        JPanel panelBuscarProfessor = new JPanel();

        criaPainelTabelProfessor(panelBuscarProfessor, layeredBuscar, panelBuscar);

		btnListarProfessores.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panelBuscar.setVisible(false);
				listarProfessores();
				panelBuscarProfessor.setVisible(true);
			}
		});
		btnListarProfessores.setBounds(293, 78, 182, 23);
		panelBuscar.add(btnListarProfessores);

    }

    public void criaPainelTabelProfessor(JPanel panelBuscarProfessor, JLayeredPane layeredBuscar,JPanel panelBuscar) {
		panelBuscarProfessor.setLayout(null);
		layeredBuscar.add(panelBuscarProfessor, "name_184619852570200");

		scrollProfessores.setBounds(0, 0, 768, 333);
		scrollProfessores.setViewportView(tableProfessores);
		panelBuscarProfessor.add(scrollProfessores);
		
		JButton btnVoltaBuscaProfessores = new JButton("Voltar");
		btnVoltaBuscaProfessores.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panelBuscarProfessor.setVisible(false);
				panelBuscar.setVisible(true);
			}
		});
		btnVoltaBuscaProfessores.setBounds(164, 344, 89, 23);
		panelBuscarProfessor.add(btnVoltaBuscaProfessores);
		
		JButton bntAtualizarListaProfessores = new JButton("Atualizar Lista");
		bntAtualizarListaProfessores.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				limpaTabela(tableProfessores);
				atualizarTabelaProfessores();
			}
		});
		bntAtualizarListaProfessores.setBounds(417, 344, 170, 23);
		panelBuscarProfessor.add(bntAtualizarListaProfessores);
	}

    public void listarProfessores() {
		tableProfessores.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableProfessores.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Codigo", "Nome", "Idade", "Sala", "Especialidade", "Projetos Geridos", "Projetos Trabalhados"
			}
		));
		tableProfessores.setBounds(0, 0, 740, 342);
		
		tableProfessores.getColumnModel().getColumn(0).setPreferredWidth(20);
        tableProfessores.getColumnModel().getColumn(1).setPreferredWidth(20);
		tableProfessores.getColumnModel().getColumn(2).setPreferredWidth(20);
		tableProfessores.getColumnModel().getColumn(3).setPreferredWidth(20);
		tableProfessores.getColumnModel().getColumn(4).setPreferredWidth(20);
		tableProfessores.getColumnModel().getColumn(5).setPreferredWidth(20);
		tableProfessores.getColumnModel().getColumn(6).setPreferredWidth(40);
			
		atualizarTabelaProfessores();	
	}

    public void atualizarTabelaProfessores() {;
        List<Professor> professores;
		String linha[] = new String[] {"", "", "", "", "", "", ""};
		DefaultTableModel dadosProfessor = (DefaultTableModel) tableProfessores.getModel();
		
		try {
			professores = ProfessorDAO.getInstance().getAll();
			int pos = -1;
			for(Professor professor : professores) {
				pos++;
				dadosProfessor.addRow(linha);
				dadosProfessor.setValueAt(professor.getCodProfessor(), pos, 0);
				dadosProfessor.setValueAt(professor.getNome(), pos, 1);
				dadosProfessor.setValueAt(professor.getIdade(), pos, 2);
				dadosProfessor.setValueAt(professor.getSala(), pos, 3);
				dadosProfessor.setValueAt(professor.getEspecialidade(), pos, 4);
				dadosProfessor.setValueAt(professor.getProjetosGeridos(), pos, 5);
				dadosProfessor.setValueAt(professor.getProjetosTrabalhados(), pos, 6);
			}
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, Professor nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, Professor nao encontrado!");
		} catch (SelectException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao acessar Professor!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar Professor!");
		} catch (SQLException e3) {
			System.err.println(e3.getMessage());
			System.err.println("Erro ao acessar o banco de dados!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
		}
	}

    public void limpaTabela(JTable table) {
		DefaultTableModel limpa = (DefaultTableModel) table.getModel();
		while (limpa.getRowCount() > 0) {
			limpa.removeRow(0);
	    }
	}

    public void criarPainelDeletarProfessor(JPanel panelDeletar){
        JLabel lblDeletarProfessor = new JLabel();
		lblDeletarProfessor.setBackground(UIManager.getColor("Button.background"));
		lblDeletarProfessor.setText("Digite o codigo do professor:");
		lblDeletarProfessor.setBounds(62, 79, 169, 23);
		panelDeletar.add(lblDeletarProfessor);
		
		textDeletarProfessor.setBounds(282, 80, 144, 23);
		textDeletarProfessor.setColumns(10);
		panelDeletar.add(textDeletarProfessor);
		
		JButton btnDeletarProfessor = new JButton("Deletar Professor");
		btnDeletarProfessor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				deletarProfessor();
			}
		});
		btnDeletarProfessor.setBounds(499, 80, 144, 23);
		panelDeletar.add(btnDeletarProfessor);
    }

    public void deletarProfessor() {
		try {
			sistema.removerProfessor(sistema.getProfessor(Integer.parseInt(textDeletarProfessor.getText())));
			limparDeletarProfessor();
			JOptionPane.showMessageDialog(null, "Professor removido com sucesso!");
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, professor nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, professor nao encontrado!");
		} catch (DeleteException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao deletar professor!");
			JOptionPane.showMessageDialog(null, "Erro ao deletar professor!");
		} catch (SQLException e3) {
			System.err.println(e3.getMessage());
			System.err.println("Erro ao acessar o banco de dados!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
		} catch (NumberFormatException e4) {
			System.err.println(e4.getMessage());
			System.err.println("Erro no codigo do professor informado!");
			JOptionPane.showMessageDialog(null, "Erro no codigo do professor informado!");
		} catch (SelectException e5) {
			System.err.println(e5.getMessage());
			System.err.println("Erro ao acessar professor!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar professor!");
		}
	}

    public void limparDeletarProfessor() {
		textDeletarProfessor.setText("");
	}

    private JLabel lblAlterarProfessor = new JLabel();
    private JPanel panelAlterarProfessor = new JPanel();
    private JTextField textCodigoDigitadoProfessor = new JTextField();

    public void criarPainelAlterarProfessor(JPanel panelAlterar, JLayeredPane layeredAlterar){
        criarPanelAlterarProfessor(panelAlterar, layeredAlterar);

        lblAlterarProfessor.setBackground(UIManager.getColor("Button.background"));
        lblAlterarProfessor.setText("Digite o codigo do professor:");
        lblAlterarProfessor.setBounds(62, 79, 169, 23);

        panelAlterar.add(lblAlterarProfessor);

        textCodigoDigitadoProfessor.setBounds(282, 80, 144, 23);
        textCodigoDigitadoProfessor.setColumns(10);
        panelAlterar.add(textCodigoDigitadoProfessor);

        JButton  btnAlterarProfessor = new JButton ("Alterar Professor");
        btnAlterarProfessor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                panelAlterar.setVisible(false);
                panelAlterarProfessor.setVisible(true);
            }
        });
        btnAlterarProfessor.setBounds(499, 80, 144, 23);
        panelAlterar.add(btnAlterarProfessor);

    }

    private JTextField textAlterarNomeProfessor = new JTextField();
    private JTextField textAlterarIdadeProfessor = new JTextField();
    private JTextField textAlterarSalaProfessor = new JTextField();
    private JTextField textAlterarEspecialidadeProfessor = new JTextField();
	private JTextField textAlterarProjetosGeridosProfessor = new JTextField();
	private JTextField textAlterarProjetosTrabalhadosProfessor = new JTextField();

    public void criarPanelAlterarProfessor(JPanel panelAlterar,JLayeredPane layeredAlterar){
        panelAlterarProfessor.setLayout(null);
        panelAlterarProfessor.setVisible(false);
		layeredAlterar.add(panelAlterarProfessor, "name_185067340967200");

        JLabel lblMensagemAlterarProfessor = new JLabel();
        lblMensagemAlterarProfessor.setBackground(UIManager.getColor("Button.background"));
		lblMensagemAlterarProfessor.setText("Preencha os campos que deseja alterar:");
		lblMensagemAlterarProfessor.setBounds(248, 28, 266, 20);
		panelAlterarProfessor.add(lblMensagemAlterarProfessor);

        JLabel lblAlterarNomeProfessor = new JLabel("Nome:");
		lblAlterarNomeProfessor.setBounds(84, 76, 80, 22);
		panelAlterarProfessor.add(lblAlterarNomeProfessor);

        textAlterarNomeProfessor.setColumns(10);
		textAlterarNomeProfessor.setBounds(248, 76, 374, 22);
		panelAlterarProfessor.add(textAlterarNomeProfessor);

        JLabel lblAlterarIdadeProfessor = new JLabel("Idade:");
        lblAlterarIdadeProfessor.setBounds(84, 126, 46, 22);
		panelAlterarProfessor.add(lblAlterarIdadeProfessor);

		textAlterarIdadeProfessor.setColumns(10);
		textAlterarIdadeProfessor.setBounds(248, 126, 374, 22);
		panelAlterarProfessor.add(textAlterarIdadeProfessor);

        JLabel lblAlterarSalaProfessor = new JLabel("Sala:");
        lblAlterarSalaProfessor.setBounds(84, 176, 46, 22);
        panelAlterarProfessor.add(lblAlterarSalaProfessor);

        textAlterarSalaProfessor.setColumns(10);
        textAlterarSalaProfessor.setBounds(248, 176, 374, 22);
        panelAlterarProfessor.add(textAlterarSalaProfessor);

        JLabel lblAlterarEspecialidadeProfessor = new JLabel("Especialidade:");
        lblAlterarEspecialidadeProfessor.setBounds(84, 226, 120, 22);
        panelAlterarProfessor.add(lblAlterarEspecialidadeProfessor);
    
        textAlterarEspecialidadeProfessor.setColumns(10);
        textAlterarEspecialidadeProfessor.setBounds(248, 226, 374, 22);
        panelAlterarProfessor.add(textAlterarEspecialidadeProfessor);

		JLabel lblAlterarProjetosGeridosProfessor= new JLabel("Projetos Geridos:");
        lblAlterarProjetosGeridosProfessor.setBounds(84, 276, 120, 22);
        panelAlterarProfessor.add(lblAlterarProjetosGeridosProfessor);
    
        textAlterarProjetosGeridosProfessor.setColumns(10);
        textAlterarProjetosGeridosProfessor.setBounds(248, 276, 374, 22);
        panelAlterarProfessor.add(textAlterarProjetosGeridosProfessor);

		JLabel lblAlterarProjetosTrabalhadosProfessor = new JLabel("Projetos Trabalhados:");
        lblAlterarProjetosTrabalhadosProfessor.setBounds(84, 326, 140, 22);
        panelAlterarProfessor.add(lblAlterarProjetosTrabalhadosProfessor);
    
        textAlterarProjetosTrabalhadosProfessor.setColumns(10);
        textAlterarProjetosTrabalhadosProfessor.setBounds(248, 326, 374, 22);
        panelAlterarProfessor.add(textAlterarProjetosTrabalhadosProfessor);

        JButton btnAlterarProfessor = new JButton("Alterar Professor");
        btnAlterarProfessor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                alterarProfessor();
                panelAlterarProfessor.setVisible(false);
				panelAlterar.setVisible(true);
            }
        });
        btnAlterarProfessor.setBounds(248, 376, 144, 23);
        panelAlterarProfessor.add(btnAlterarProfessor);
    }

    public void alterarProfessor() {
		
		try {
			Professor professor = sistema.getProfessor(Integer.parseInt(textCodigoDigitadoProfessor.getText()));
			
			if(textAlterarNomeProfessor.getText().equalsIgnoreCase("") == false && professor.getNome().equalsIgnoreCase(textAlterarNomeProfessor.getText()) == false)
                professor.setNome(textAlterarNomeProfessor.getText());
			if(textAlterarIdadeProfessor.getText().equalsIgnoreCase("") == false && professor.getIdade() != Integer.parseInt(textAlterarIdadeProfessor.getText()))
                professor.setIdade(Integer.parseInt(textAlterarIdadeProfessor.getText()));		
			if(textAlterarSalaProfessor.getText().equalsIgnoreCase("") == false && professor.getSala() != Long.parseLong(textAlterarSalaProfessor.getText()))
                professor.setSala(Integer.parseInt(textAlterarSalaProfessor.getText()));
			if(textAlterarEspecialidadeProfessor.getText().equalsIgnoreCase("") == false && professor.getEspecialidade().equalsIgnoreCase(textAlterarEspecialidadeProfessor.getText()) == false)
                professor.setEspecialidade(textAlterarEspecialidadeProfessor.getText());
			if(textAlterarProjetosGeridosProfessor.getText().equalsIgnoreCase("") == false)
				professor.setProjetosGeridos(parseList(textAlterarProjetosGeridosProfessor.getText()));
			if(textAlterarProjetosTrabalhadosProfessor.getText().equalsIgnoreCase("") == false)
                professor.setProjetosTrabalhados(parseList(textAlterarProjetosTrabalhadosProfessor.getText()));
		
			sistema.alterarProfessor(professor);
			limparAlterarProfessor();
			JOptionPane.showMessageDialog(null, "Professor alterado!");	
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, professor nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, professor nao encontrado!");
		} catch (UpdateException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao atualizar professor!");
			JOptionPane.showMessageDialog(null, "Erro ao atualizar professor!");
		} catch (SQLException e3) {
			System.err.println(e3.getMessage());
			System.err.println("Erro ao acessar o banco de dados!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
		} catch (NumberFormatException e4) {
			System.err.println(e4.getMessage());
			System.err.println("Erro no codigo do professor informado ou no formato dos dados inseridos!");
			JOptionPane.showMessageDialog(null, "Erro no codigo do professor informado ou no formato dos dados inseridos!");
		} catch (SelectException e5) {
			System.err.println(e5.getMessage());
			System.err.println("Erro ao acessar professor!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar professor!");
		}
	}

    public void limparAlterarProfessor() {
		textCodigoDigitadoProfessor.setText("");
		textAlterarNomeProfessor.setText("");
		textAlterarIdadeProfessor.setText("");
		textAlterarSalaProfessor.setText("");
		textAlterarEspecialidadeProfessor.setText("");
		textAlterarProjetosGeridosProfessor.setText("");
		textAlterarProjetosTrabalhadosProfessor.setText("");
	}

	public static List<Integer> parseList(String input) {
		List<Integer> list = new ArrayList<>();
		String[] elements = input.split(",");
		
		for (String element : elements) {
			try {
				int value = Integer.parseInt(element.trim());
				list.add(value);
			} catch (NumberFormatException e) {
				System.err.println(e.getMessage());
				System.err.println("Erro no formato dos dados inseridos!");
				JOptionPane.showMessageDialog(null, "Erro no formato dos dados inseridos!");
			}
		}
		
		return list;
	}
}
