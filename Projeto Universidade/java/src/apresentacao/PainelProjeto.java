package apresentacao;

import negocio.Sistema;
import persistencia.ProjetoDAO;
import dados.Projeto;
import exceptions.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PainelProjeto {
    static Sistema sistema = new Sistema();

    private JPanel panelCadastrarProjeto;

    private JTextField textOrgaoProjeto;
    private JTextField textOrcamentoProjeto;
    private JTextField textDataInicioProjeto;
    private JTextField textDataFinalProjeto;
    private JTextField textCodProfessorGerente;
    private JTextField textProfessoresParticipantes;
    private JTextField textEstudantesAssistentes;

    private JTable tableProjetos = new JTable(null, null, null);
    private JScrollPane scrollProjetos = new JScrollPane(tableProjetos);

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    JTextField textDeletarProjeto = new JTextField();

    public void criarPainelCadastrarProjeto(JTabbedPane tabbedCadastrar) {
        panelCadastrarProjeto = new JPanel();
        panelCadastrarProjeto.setLayout(null);
        tabbedCadastrar.addTab("Projeto", null, panelCadastrarProjeto, null);
        
        JLabel lblOrgaoProjeto= new JLabel("Orgao:");
        lblOrgaoProjeto.setBounds(82, 31, 80, 22);
        panelCadastrarProjeto.add(lblOrgaoProjeto);
        
        textOrgaoProjeto = new JTextField();
        textOrgaoProjeto.setBounds(244, 31, 374, 22);
        textOrgaoProjeto.setColumns(10);
        panelCadastrarProjeto.add(textOrgaoProjeto);

        JLabel lblOrcamentoProjeto = new JLabel("Orcamento:");
        lblOrcamentoProjeto.setBounds(82, 84, 80, 22);
        panelCadastrarProjeto.add(lblOrcamentoProjeto);

        textOrcamentoProjeto = new JTextField();
        textOrcamentoProjeto.setBounds(244, 84, 374, 22);
        textOrcamentoProjeto.setColumns(10);
        panelCadastrarProjeto.add(textOrcamentoProjeto);

        JLabel lblDataInicioProjeto = new JLabel("Data de Inicio:");
        lblDataInicioProjeto.setBounds(82, 137, 80, 22);
        panelCadastrarProjeto.add(lblDataInicioProjeto);

        textDataInicioProjeto = new JTextField();
        textDataInicioProjeto.setBounds(244, 137, 374, 22);
        textDataInicioProjeto.setColumns(10);
        panelCadastrarProjeto.add(textDataInicioProjeto);

        JLabel lblDataFinalProjeto = new JLabel("Data Final:");
        lblDataFinalProjeto.setBounds(82, 190, 80, 22);
        panelCadastrarProjeto.add(lblDataFinalProjeto);
        
        textDataFinalProjeto = new JTextField();
        textDataFinalProjeto.setBounds(244, 190, 374, 22);
        textDataFinalProjeto.setColumns(10);
        panelCadastrarProjeto.add(textDataFinalProjeto);

        JLabel lblCodProfessorGerente = new JLabel("Codigo Professor Gerente:");
        lblCodProfessorGerente.setBounds(82, 243, 160, 22);
        panelCadastrarProjeto.add(lblCodProfessorGerente);
        
        textCodProfessorGerente = new JTextField();
        textCodProfessorGerente.setBounds(244, 243, 374, 22);
        textCodProfessorGerente.setColumns(10);
        panelCadastrarProjeto.add(textCodProfessorGerente);

        JLabel lblProfessoresParticipantes = new JLabel("Professores Participantes:");
        lblProfessoresParticipantes.setBounds(82, 296, 160, 22);
        panelCadastrarProjeto.add(lblProfessoresParticipantes);

        textProfessoresParticipantes = new JTextField();
        textProfessoresParticipantes.setBounds(244, 296, 374, 22);
        textProfessoresParticipantes.setColumns(10);
        panelCadastrarProjeto.add(textProfessoresParticipantes);

        JLabel lblEstudantesAssistentes = new JLabel("Estudantes Assistentes:");
        lblEstudantesAssistentes.setBounds(82, 349, 160, 22);
        panelCadastrarProjeto.add(lblEstudantesAssistentes);

        textEstudantesAssistentes = new JTextField();
        textEstudantesAssistentes.setBounds(244, 349, 374, 22);
        textEstudantesAssistentes.setColumns(10);
        panelCadastrarProjeto.add(textEstudantesAssistentes);
        
        JButton btnRealizarCadastroProjeto = new JButton("Cadastrar Projeto");
        btnRealizarCadastroProjeto.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    cadastrarProjeto();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnRealizarCadastroProjeto.setBounds(244, 400, 159, 22);
        panelCadastrarProjeto.add(btnRealizarCadastroProjeto);
    }
    
    public void cadastrarProjeto() throws ParseException {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        java.sql.Date sqlDataInicioProjeto = new java.sql.Date(dateFormat.parse(textDataInicioProjeto.getText()).getTime());
        java.sql.Date sqlDataFinalProjeto = new java.sql.Date(dateFormat.parse(textDataFinalProjeto.getText()).getTime());

        Projeto projeto = new Projeto();

        projeto.setOrgao(textOrgaoProjeto.getText());
        projeto.setOrcamento(Float.parseFloat(textOrcamentoProjeto.getText()));
        projeto.setDataInicio(sqlDataInicioProjeto);   
        projeto.setDataFinal(sqlDataFinalProjeto);
        projeto.setCodProfessorGerente(Integer.parseInt(textCodProfessorGerente.getText()));
        projeto.setProfessoresParticipantes(parseList(textProfessoresParticipantes.getText()));
        projeto.setEstudantesAssistentes(parseList(textEstudantesAssistentes.getText()));

        try {
            sistema.cadastrarProjeto(projeto);
            limparCadastrarProjeto();
            JOptionPane.showMessageDialog(null, "Cadastro do projeto efetuado com sucesso!");
        } catch (ClassNotFoundException e1) {
            System.err.println(e1.getMessage());
            System.err.println("Erro, projeto nao encontrado!");
            JOptionPane.showMessageDialog(null, "Erro, projeto nao encontrado!");
        } catch (InsertException e2) {
            System.err.println(e2.getMessage());
            System.err.println("Erro ao inserir projeto!");
            JOptionPane.showMessageDialog(null, "Erro ao inserir projeto!");
        } catch (SQLException e3) {
            System.err.println(e3.getMessage());
            System.err.println("Erro ao acessar o banco de dados!");
            JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
        }
    }

    public void limparCadastrarProjeto() {
        textOrgaoProjeto.setText("");
        textOrcamentoProjeto.setText("");
        textDataInicioProjeto.setText("");
        textDataFinalProjeto.setText("");
        textCodProfessorGerente.setText("");
        textProfessoresParticipantes.setText("");
        textEstudantesAssistentes.setText("");
    }

    public void criarPainelBuscarProjeto(JPanel panelBuscar, JLayeredPane layeredBuscar) {
        JButton btnListarProjetos = new JButton("Listar Projetos");
        JPanel panelBuscarProjeto = new JPanel();

        criaPainelTabelProjeto(panelBuscarProjeto, layeredBuscar, panelBuscar);

		btnListarProjetos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panelBuscar.setVisible(false);
				listarProjetos();
				panelBuscarProjeto.setVisible(true);
			}
		});
		btnListarProjetos.setBounds(293, 410, 182, 23);
		panelBuscar.add(btnListarProjetos);

    }

    public void criaPainelTabelProjeto(JPanel panelBuscarProjeto, JLayeredPane layeredBuscar,JPanel panelBuscar) {
		panelBuscarProjeto.setLayout(null);
		layeredBuscar.add(panelBuscarProjeto, "name_184619852570200");

		scrollProjetos.setBounds(0, 0, 768, 333);
		scrollProjetos.setViewportView(tableProjetos);
		panelBuscarProjeto.add(scrollProjetos);
		
		JButton btnVoltaBuscaProjetos = new JButton("Voltar");
		btnVoltaBuscaProjetos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panelBuscarProjeto.setVisible(false);
				panelBuscar.setVisible(true);
			}
		});
		btnVoltaBuscaProjetos.setBounds(164, 344, 89, 23);
		panelBuscarProjeto.add(btnVoltaBuscaProjetos);
		
		JButton bntAtualizarListaProjetos = new JButton("Atualizar Lista");
		bntAtualizarListaProjetos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				limpaTabela(tableProjetos);
				atualizarTabelaProjetos();
			}
		});
		bntAtualizarListaProjetos.setBounds(417, 344, 170, 23);
		panelBuscarProjeto.add(bntAtualizarListaProjetos);
	}

    public void listarProjetos() {
		tableProjetos.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableProjetos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Codigo", "Orgao", "Orcamento", "Data Inicio", "Data Final", "Professor Gerente", "Professores Participantes", "Estudantes Assistentes"
			}
		));
		tableProjetos.setBounds(0, 0, 740, 342);
		
		tableProjetos.getColumnModel().getColumn(0).setPreferredWidth(1);
        tableProjetos.getColumnModel().getColumn(1).setPreferredWidth(1);
		tableProjetos.getColumnModel().getColumn(2).setPreferredWidth(1);
		tableProjetos.getColumnModel().getColumn(3).setPreferredWidth(10);
		tableProjetos.getColumnModel().getColumn(4).setPreferredWidth(10);
		tableProjetos.getColumnModel().getColumn(5).setPreferredWidth(50);
		tableProjetos.getColumnModel().getColumn(6).setPreferredWidth(90);
        tableProjetos.getColumnModel().getColumn(7).setPreferredWidth(90);
			
		atualizarTabelaProjetos();	
	}

    public void atualizarTabelaProjetos() {;
        List<Projeto> projetos;
		String linha[] = new String[] {"", "", "", "", "", "", "", ""};
		DefaultTableModel dadosProjeto = (DefaultTableModel) tableProjetos.getModel();
		
		try {
			projetos = ProjetoDAO.getInstance().getAll();
			int pos = -1;
			for(Projeto projeto : projetos) {
				pos++;
				dadosProjeto.addRow(linha);
				dadosProjeto.setValueAt(projeto.getCodProjeto(), pos, 0);
				dadosProjeto.setValueAt(projeto.getOrgao(), pos, 1);
				dadosProjeto.setValueAt(projeto.getOrcamento(), pos, 2);
				dadosProjeto.setValueAt(projeto.getDataInicio(), pos, 3);
				dadosProjeto.setValueAt(projeto.getDataFinal(), pos, 4);
				dadosProjeto.setValueAt(projeto.getCodProfessorGerente(), pos, 5);
				dadosProjeto.setValueAt(projeto.getProfessoresParticipantes(), pos, 6);
                dadosProjeto.setValueAt(projeto.getEstudantesAssistentes(), pos, 7);
			}
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, projeto nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, projeto nao encontrado!");
		} catch (SelectException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao acessar projeto!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar projeto!");
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

    public void criarPainelDeletarProjeto(JPanel panelDeletar){

        JLabel lblDeletarProjeto = new JLabel();
        lblDeletarProjeto.setBackground(UIManager.getColor("Button.background"));
        lblDeletarProjeto.setText("Digite o codigo do projeto:");
        lblDeletarProjeto.setBounds(62, 390, 169, 23);
        panelDeletar.add(lblDeletarProjeto);
        
        textDeletarProjeto.setBounds(282, 390, 144, 23);
        textDeletarProjeto.setColumns(10);
        panelDeletar.add(textDeletarProjeto);
        
        JButton btnDeletarProjeto = new JButton("Deletar Projeto");
        btnDeletarProjeto.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                deletarProjeto();
            }
        });
        btnDeletarProjeto.setBounds(499, 390, 144, 23);
        panelDeletar.add(btnDeletarProjeto);
    }

    public void deletarProjeto() {
        try {
            sistema.removerProjeto(sistema.getProjeto(Integer.parseInt(textDeletarProjeto.getText())));
            limparDeletarProjeto();
            JOptionPane.showMessageDialog(null, "Projeto removido com sucesso!");
        } catch (ClassNotFoundException e1) {
            System.err.println(e1.getMessage());
            System.err.println("Erro, projeto nao encontrado!");
            JOptionPane.showMessageDialog(null, "Erro, projeto nao encontrado!");
        } catch (DeleteException e2) {
            System.err.println(e2.getMessage());
            System.err.println("Erro ao deletar projeto!");
            JOptionPane.showMessageDialog(null, "Erro ao deletar projeto!");
        } catch (SQLException e3) {
            System.err.println(e3.getMessage());
            System.err.println("Erro ao acessar o banco de dados!");
            JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
        } catch (NumberFormatException e4) {
            System.err.println(e4.getMessage());
            System.err.println("Erro no codigo do projeto informado!");
            JOptionPane.showMessageDialog(null, "Erro no codigo do projeto informado!");
        } catch (SelectException e5) {
            System.err.println(e5.getMessage());
            System.err.println("Erro ao acessar projeto!");
            JOptionPane.showMessageDialog(null, "Erro ao acessar projeto!");
        }
    }

    public void limparDeletarProjeto() {
        textDeletarProjeto.setText("");
    }

    private JLabel lblAlterarProjeto= new JLabel();
    private JPanel panelAlterarProjeto = new JPanel();
    private JTextField textCodigoDigitadoProjeto= new JTextField();

    public void criarPainelAlterarProjeto(JPanel panelAlterar, JLayeredPane layeredAlterar){
        criarPanelAlterarProjeto(panelAlterar, layeredAlterar);

        lblAlterarProjeto.setBackground(UIManager.getColor("Button.background"));
        lblAlterarProjeto.setText("Digite o codigo do projeto:");
        lblAlterarProjeto.setBounds(62, 375, 169, 23);

        panelAlterar.add(lblAlterarProjeto);

        textCodigoDigitadoProjeto.setBounds(282, 375, 144, 23);
        textCodigoDigitadoProjeto.setColumns(10);
        panelAlterar.add(textCodigoDigitadoProjeto);

        JButton  btnAlterarProjeto = new JButton ("Alterar Projeto");
        btnAlterarProjeto.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                panelAlterar.setVisible(false);
                panelAlterarProjeto.setVisible(true);
            }
        });
        btnAlterarProjeto.setBounds(499, 375, 144, 23);
        panelAlterar.add(btnAlterarProjeto);

    }

    private JTextField textAlterarOrgaoProjeto = new JTextField();
    private JTextField textAlterarOrcamentoProjeto = new JTextField();
    private JTextField textAlterarDataInicioProjeto = new JTextField();
    private JTextField textAlterarDataFinalProjeto = new JTextField();
    private JTextField textAlterarCodProfessorGerenteProjeto = new JTextField();
    private JTextField textAlterarProfessoresParticipantesProjeto = new JTextField();
    private JTextField textAlterarEstudantesAssistentesProjeto = new JTextField();

    public void criarPanelAlterarProjeto(JPanel panelAlterar,JLayeredPane layeredAlterar){
        panelAlterarProjeto.setLayout(null);
        panelAlterarProjeto.setVisible(false);
		layeredAlterar.add(panelAlterarProjeto, "name_185067340967200");

        JLabel lblMensagemAlterarProjeto = new JLabel();
        lblMensagemAlterarProjeto.setBackground(UIManager.getColor("Button.background"));
		lblMensagemAlterarProjeto.setText("Preencha os campos que deseja alterar:");
		lblMensagemAlterarProjeto.setBounds(248, 28, 266, 20);
		panelAlterarProjeto.add(lblMensagemAlterarProjeto);

        JLabel lblAlterarOrgaoProjeto = new JLabel("Orgao:");
        lblAlterarOrgaoProjeto.setBounds(84, 76, 80, 22);
        panelAlterarProjeto.add(lblAlterarOrgaoProjeto);

        textAlterarOrgaoProjeto.setColumns(10);
        textAlterarOrgaoProjeto.setBounds(248, 76, 374, 22);
        panelAlterarProjeto.add(textAlterarOrgaoProjeto);

        JLabel lblAlterarOrcamentoProjeto = new JLabel("Orcamento:");
        lblAlterarOrcamentoProjeto.setBounds(84, 126, 80, 22);
        panelAlterarProjeto.add(lblAlterarOrcamentoProjeto);

        textAlterarOrcamentoProjeto.setColumns(10);
        textAlterarOrcamentoProjeto.setBounds(248, 126, 374, 22);
        panelAlterarProjeto.add(textAlterarOrcamentoProjeto);

        JLabel lblAlterarDataInicioProjeto = new JLabel("Data de Inicio:");
        lblAlterarDataInicioProjeto.setBounds(84, 176, 80, 22);
        panelAlterarProjeto.add(lblAlterarDataInicioProjeto);

        textAlterarDataInicioProjeto.setColumns(10);
        textAlterarDataInicioProjeto.setBounds(248, 176, 374, 22);
        panelAlterarProjeto.add(textAlterarDataInicioProjeto);

        JLabel lblAlterarDataFinalProjeto = new JLabel("Data Final:");
        lblAlterarDataFinalProjeto.setBounds(84, 226, 80, 22);
        panelAlterarProjeto.add(lblAlterarDataFinalProjeto);

        textAlterarDataFinalProjeto.setColumns(10);
        textAlterarDataFinalProjeto.setBounds(248, 226, 374, 22);
        panelAlterarProjeto.add(textAlterarDataFinalProjeto);

        JLabel lblAlterarCodProfessorGerenteProjeto = new JLabel("Codigo Professor Gerente:");
        lblAlterarCodProfessorGerenteProjeto.setBounds(84, 276, 200, 22);
        panelAlterarProjeto.add(lblAlterarCodProfessorGerenteProjeto);

        textAlterarCodProfessorGerenteProjeto.setColumns(10);
        textAlterarCodProfessorGerenteProjeto.setBounds(248, 276, 374, 22);
        panelAlterarProjeto.add(textAlterarCodProfessorGerenteProjeto);

        JLabel lblAlterarProfessoresParticipantesProjeto = new JLabel("Professores Participantes:");
        lblAlterarProfessoresParticipantesProjeto.setBounds(84, 326, 200, 22);
        panelAlterarProjeto.add(lblAlterarProfessoresParticipantesProjeto);

        textAlterarProfessoresParticipantesProjeto.setColumns(10);
        textAlterarProfessoresParticipantesProjeto.setBounds(248, 326, 374, 22);
        panelAlterarProjeto.add(textAlterarProfessoresParticipantesProjeto);

        JLabel lblAlterarEstudantesAssistentesProjeto = new JLabel("Estudantes Assistentes:");
        lblAlterarEstudantesAssistentesProjeto.setBounds(84, 376, 150, 22);
        panelAlterarProjeto.add(lblAlterarEstudantesAssistentesProjeto);

        textAlterarEstudantesAssistentesProjeto.setColumns(10);
        textAlterarEstudantesAssistentesProjeto.setBounds(248, 376, 374, 22);
        panelAlterarProjeto.add(textAlterarEstudantesAssistentesProjeto);

        JButton btnAlterarProjeto = new JButton("Alterar Projeto");
        btnAlterarProjeto.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    alterarProjeto();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                panelAlterarProjeto.setVisible(false);
				panelAlterar.setVisible(true);
            }
        });
        btnAlterarProjeto.setBounds(248, 420, 144, 23);
        panelAlterarProjeto.add(btnAlterarProjeto);
    }

    public void alterarProjeto() throws ParseException {
		
		try {
			Projeto projeto = sistema.getProjeto(Integer.parseInt(textCodigoDigitadoProjeto.getText()));
        
            java.sql.Date sqlAlterarDataInicioProjeto = new java.sql.Date(dateFormat.parse(textAlterarDataInicioProjeto.getText()).getTime());
            java.sql.Date sqlAlterarDataFinalProjeto = new java.sql.Date(dateFormat.parse(textAlterarDataFinalProjeto.getText()).getTime());
        
			
            if(textAlterarOrgaoProjeto.getText().equalsIgnoreCase("") == false && projeto.getOrgao().equalsIgnoreCase(textAlterarOrgaoProjeto.getText()) == false)
                projeto.setOrgao(textAlterarOrgaoProjeto.getText());
            if(textAlterarOrcamentoProjeto.getText().equalsIgnoreCase("") == false && projeto.getOrcamento() != Float.parseFloat(textAlterarOrcamentoProjeto.getText()))
                projeto.setOrcamento(Float.parseFloat(textAlterarOrcamentoProjeto.getText()));
            if(textAlterarDataInicioProjeto.getText().equalsIgnoreCase("") == false)
              projeto.setDataInicio(sqlAlterarDataInicioProjeto);
            if(textAlterarDataFinalProjeto.getText().equalsIgnoreCase("") == false)
               projeto.setDataFinal(sqlAlterarDataFinalProjeto);
            if(textAlterarCodProfessorGerenteProjeto.getText().equalsIgnoreCase("") == false && projeto.getCodProfessorGerente() != Integer.parseInt(textAlterarCodProfessorGerenteProjeto.getText()))
                projeto.setCodProfessorGerente(Integer.parseInt(textAlterarCodProfessorGerenteProjeto.getText()));
            if(textAlterarProfessoresParticipantesProjeto.getText().equalsIgnoreCase("") == false) 
                projeto.setProfessoresParticipantes(parseList(textAlterarProfessoresParticipantesProjeto.getText()));
            if(textAlterarEstudantesAssistentesProjeto.getText().equalsIgnoreCase("") == false)
                projeto.setEstudantesAssistentes(parseList(textAlterarEstudantesAssistentesProjeto.getText()));
		
			sistema.alterarProjeto(projeto);
			limparAlterarProjeto();
			JOptionPane.showMessageDialog(null, "Projeto alterado!");	
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, projeto nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, projeto nao encontrado!");
		} catch (UpdateException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao atualizar projeto!");
			JOptionPane.showMessageDialog(null, "Erro ao atualizar projeto!");
		} catch (SQLException e3) {
			System.err.println(e3.getMessage());
			System.err.println("Erro ao acessar o banco de dados!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
		} catch (NumberFormatException e4) {
			System.err.println(e4.getMessage());
			System.err.println("Erro no codigo do projeto informado ou no formato dos dados inseridos!");
			JOptionPane.showMessageDialog(null, "Erro no codigo do projeto informado ou no formato dos dados inseridos!");
		} catch (SelectException e5) {
			System.err.println(e5.getMessage());
			System.err.println("Erro ao acessar projeto!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar projeto!");
		}
	}

    public void limparAlterarProjeto() {
		textCodigoDigitadoProjeto.setText("");
        textAlterarOrgaoProjeto.setText("");
        textAlterarOrcamentoProjeto.setText("");
        textAlterarDataInicioProjeto.setText("");
        textAlterarDataFinalProjeto.setText("");
        textAlterarCodProfessorGerenteProjeto.setText("");
        textAlterarProfessoresParticipantesProjeto.setText("");
        textAlterarEstudantesAssistentesProjeto.setText("");
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
