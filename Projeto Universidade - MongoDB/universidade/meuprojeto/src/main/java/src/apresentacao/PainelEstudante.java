package src.apresentacao;

import src.negocio.Sistema;
import src.persistencia.EstudanteDAO;
import src.dados.Estudante;
import src.exceptions.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;

public class PainelEstudante {
    static Sistema sistema = new Sistema();

    private JPanel panelCadastrarEstudante;

    private JTextField textNomeEstudante;
    private JTextField textIdadeEstudante;
    private JTextField textTipoCursoEstudante;
    private JTextField textCodDepartamento;
	private JTextField textCodAconselhador;

    private JTable tableEstudantes= new JTable(null, null, null);
    private JScrollPane scrollEstudantes = new JScrollPane(tableEstudantes);

    JTextField textDeletarEstudante = new JTextField();

    public void criarPainelCadastrarEstudante(JTabbedPane tabbedCadastrar) {
        panelCadastrarEstudante = new JPanel();
        panelCadastrarEstudante.setLayout(null);
        tabbedCadastrar.addTab("Estudante", null, panelCadastrarEstudante, null);
        
        JLabel lblNomeEstudante = new JLabel("Nome:");
        lblNomeEstudante.setToolTipText("");
        lblNomeEstudante.setBounds(82, 31, 80, 22);
        panelCadastrarEstudante.add(lblNomeEstudante);
        
        textNomeEstudante = new JTextField();
        textNomeEstudante.setBounds(244, 31, 374, 22);
        textNomeEstudante.setColumns(10);
        panelCadastrarEstudante.add(textNomeEstudante);
        
        JLabel lblIdadeEstudante= new JLabel("Idade:");
        lblIdadeEstudante.setBounds(82, 84, 46, 22);
        panelCadastrarEstudante.add(lblIdadeEstudante);
        
        textIdadeEstudante = new JTextField();
        textIdadeEstudante.setBounds(244, 84, 374, 22);
        textIdadeEstudante.setColumns(10);
        panelCadastrarEstudante.add(textIdadeEstudante);
        
        JLabel lblTipoCursoEstudante = new JLabel("Tipo de curso:");
        lblTipoCursoEstudante.setBounds(82, 137, 90, 22);
        panelCadastrarEstudante.add(lblTipoCursoEstudante);
        
        textTipoCursoEstudante = new JTextField();
        textTipoCursoEstudante.setBounds(244, 137, 374, 22);
        textTipoCursoEstudante.setColumns(10);
        panelCadastrarEstudante.add(textTipoCursoEstudante);
        
        JLabel lblCodDepartamentoCurso= new JLabel("Codigo do departamento:");
        lblCodDepartamentoCurso.setBounds(82, 190, 180, 22);
        panelCadastrarEstudante.add(lblCodDepartamentoCurso);
        
        textCodDepartamento = new JTextField();
        textCodDepartamento.setBounds(244, 190, 374, 22);
        textCodDepartamento.setColumns(10);
        panelCadastrarEstudante.add(textCodDepartamento);

		JLabel lblCOdAconselhador = new JLabel("Codigo aconselhador:");
        lblCOdAconselhador.setBounds(82, 243, 160, 22);
        panelCadastrarEstudante.add(lblCOdAconselhador);
        
        textCodAconselhador = new JTextField();
        textCodAconselhador.setBounds(244, 243, 374, 22);
        textCodAconselhador.setColumns(10);
        panelCadastrarEstudante.add(textCodAconselhador);
        
        JButton btnRealizarCadastroEstudante = new JButton("Cadastrar Estudante");
        btnRealizarCadastroEstudante.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cadastrarEstudante();
            }
        });
        btnRealizarCadastroEstudante.setBounds(244, 300, 159, 22);
        panelCadastrarEstudante.add(btnRealizarCadastroEstudante);
    }
    
    public void cadastrarEstudante() {
        Estudante estudante = new Estudante();
        
        estudante.setNome(textNomeEstudante.getText());
        estudante.setIdade(Integer.parseInt(textIdadeEstudante.getText()));		
        estudante.setTipoCurso(textTipoCursoEstudante.getText());						
        estudante.setCodDepartamentoCurso(Integer.parseInt(textCodDepartamento.getText()));
		estudante.setCodAconselhador(Integer.parseInt(textCodAconselhador.getText()));

        try {
            sistema.cadastrarEstudante(estudante);
            limparCadastrarEstudante();
            JOptionPane.showMessageDialog(null, "Cadastro do estudante efetuado com sucesso!");
        } catch (ClassNotFoundException e1) {
            System.err.println(e1.getMessage());
            System.err.println("Erro, estudante nao encontrado!");
            JOptionPane.showMessageDialog(null, "Erro, estudante nao encontrado!");
        } catch (InsertException e2) {
            System.err.println(e2.getMessage());
            System.err.println("Erro ao inserir estudante!");
            JOptionPane.showMessageDialog(null, "Erro ao inserir estudante!");
        } catch (Exception e3) {
            System.err.println(e3.getMessage());
            System.err.println("Erro ao acessar o banco de dados!");
            JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
        }
    }

    public void limparCadastrarEstudante() {
        textNomeEstudante.setText("");
        textIdadeEstudante.setText("");
        textTipoCursoEstudante.setText("");
        textCodDepartamento.setText("");
		textCodAconselhador.setText("");
    }

    public void criarPainelBuscarEstudante(JPanel panelBuscar, JLayeredPane layeredBuscar) {
        JButton btnListarEstudantes = new JButton("Listar Estudantes");
        final JPanel finalPanelBuscarEstudante = new JPanel();
		final JPanel finalPanelBusca = panelBuscar;

        criaPainelTabelEstudante(finalPanelBuscarEstudante, layeredBuscar, panelBuscar);

		btnListarEstudantes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				finalPanelBusca.setVisible(false);
				listarEstudantes();
				finalPanelBuscarEstudante.setVisible(true);
			}
		});
		btnListarEstudantes.setBounds(293, 220, 182, 23);
		panelBuscar.add(btnListarEstudantes);

    }

    public void criaPainelTabelEstudante(JPanel panelBuscarEstudante, JLayeredPane layeredBuscar,JPanel panelBuscar) {
		final JPanel finalPanelBusca = panelBuscar;
		final JPanel finalPanelBuscarEstudante = panelBuscarEstudante;
		finalPanelBuscarEstudante.setLayout(null);
		layeredBuscar.add(finalPanelBuscarEstudante, "name_184619852570200");

		scrollEstudantes.setBounds(0, 0, 768, 333);
		scrollEstudantes.setViewportView(tableEstudantes);
		finalPanelBuscarEstudante.add(scrollEstudantes);
		
		JButton btnVoltaBuscaEstudantes = new JButton("Voltar");
		btnVoltaBuscaEstudantes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				finalPanelBuscarEstudante.setVisible(false);
				finalPanelBusca.setVisible(true);
			}
		});
		btnVoltaBuscaEstudantes.setBounds(164, 344, 89, 23);
		finalPanelBuscarEstudante.add(btnVoltaBuscaEstudantes);
		
		JButton bntAtualizarListaEstudantes = new JButton("Atualizar Lista");
		bntAtualizarListaEstudantes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				limpaTabela(tableEstudantes);
				atualizarTabelaEstudantes();
			}
		});
		bntAtualizarListaEstudantes.setBounds(417, 344, 170, 23);
		finalPanelBuscarEstudante.add(bntAtualizarListaEstudantes);
	}

    public void listarEstudantes() {
		tableEstudantes.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableEstudantes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Codigo", "Nome", "Idade", "Tipo de Curso", "Codigo Departamento", "Codigo Aconselhador"
			}
		));
		tableEstudantes.setBounds(0, 0, 740, 342);
		
		tableEstudantes.getColumnModel().getColumn(0).setPreferredWidth(20);
        tableEstudantes.getColumnModel().getColumn(1).setPreferredWidth(10);
		tableEstudantes.getColumnModel().getColumn(2).setPreferredWidth(20);
		tableEstudantes.getColumnModel().getColumn(3).setPreferredWidth(20);
		tableEstudantes.getColumnModel().getColumn(4).setPreferredWidth(30);
		tableEstudantes.getColumnModel().getColumn(5).setPreferredWidth(20);
			
		atualizarTabelaEstudantes();	
	}

    public void atualizarTabelaEstudantes() {;
        List<Estudante> estudantes;
		String linha[] = new String[] {"", "", "", "", "", ""};
		DefaultTableModel dadosEstudante= (DefaultTableModel) tableEstudantes.getModel();
		
		try {
			estudantes = EstudanteDAO.getInstance().getAll();
			int pos = -1;
			for(Estudante estudante : estudantes) {
				pos++;
				dadosEstudante.addRow(linha);
				dadosEstudante.setValueAt(estudante.getCodEstudante(), pos, 0);
				dadosEstudante.setValueAt(estudante.getNome(), pos, 1);
				dadosEstudante.setValueAt(estudante.getIdade(), pos, 2);
				dadosEstudante.setValueAt(estudante.getTipoCurso(), pos, 3);
				dadosEstudante.setValueAt(estudante.getCodDepartamentoCurso(), pos, 4);
				dadosEstudante.setValueAt(estudante.getCodAconselhador(), pos, 5);
			}
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, estudante nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, estudante nao encontrado!");
		} catch (SelectException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao acessar estudante!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar estudante!");
		}
	}

    public void limpaTabela(JTable table) {
		DefaultTableModel limpa = (DefaultTableModel) table.getModel();
		while (limpa.getRowCount() > 0) {
			limpa.removeRow(0);
	    }
	}

    public void criarPainelDeletarEstudante(JPanel panelDeletar){
        JLabel lblDeletarEstudante = new JLabel();
		lblDeletarEstudante.setBackground(UIManager.getColor("Button.background"));
		lblDeletarEstudante.setText("Digite o codigo do estudante:");
		lblDeletarEstudante.setBounds(62, 200, 169, 23);
		panelDeletar.add(lblDeletarEstudante);
		
		textDeletarEstudante.setBounds(282, 200, 144, 23);
		textDeletarEstudante.setColumns(10);
		panelDeletar.add(textDeletarEstudante);
		
		JButton btnDeletarEstudante = new JButton("Deletar Estudante");
		btnDeletarEstudante.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				deletarEstudante();
			}
		});
		btnDeletarEstudante.setBounds(499, 200, 144, 23);
		panelDeletar.add(btnDeletarEstudante);
    }

    public void deletarEstudante() {
		try {
			sistema.removerEstudante(sistema.getEstudante(Integer.parseInt(textDeletarEstudante.getText())));
			limparDeletarEstudante();
			JOptionPane.showMessageDialog(null, "Estudante removido com sucesso!");
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, estudante nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, estudante nao encontrado!");
		} catch (DeleteException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao deletar estudante!");
			JOptionPane.showMessageDialog(null, "Erro ao deletar estudante!");
		} catch (Exception e3) {
			System.err.println(e3.getMessage());
			System.err.println("Erro ao acessar o banco de dados!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
		}
	}

    public void limparDeletarEstudante() {
		textDeletarEstudante.setText("");
	}

    private JLabel lblAlterarEstudante = new JLabel();
    private JPanel panelAlterarEstudante = new JPanel();
    private JTextField textCodigoDigitadoEstudnte = new JTextField();

    public void criarPainelAlterarEstudante(JPanel panelAlterar, JLayeredPane layeredAlterar){
		final JPanel finalPanelAlterar = panelAlterar;

        criarPanelAlterarEstudante(panelAlterar, layeredAlterar);

        lblAlterarEstudante.setBackground(UIManager.getColor("Button.background"));
        lblAlterarEstudante.setText("Digite o codigo do estudante:");
        lblAlterarEstudante.setBounds(62, 180, 169, 23);

        panelAlterar.add(lblAlterarEstudante);

        textCodigoDigitadoEstudnte.setBounds(282, 180, 144, 23);
        textCodigoDigitadoEstudnte.setColumns(10);
        panelAlterar.add(textCodigoDigitadoEstudnte);

        JButton  btnAlterarEstudante = new JButton ("Alterar Estudante");
        btnAlterarEstudante.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                finalPanelAlterar.setVisible(false);
                panelAlterarEstudante.setVisible(true);
            }
        });
        btnAlterarEstudante.setBounds(499, 180, 144, 23);
        panelAlterar.add(btnAlterarEstudante);

    }

    private JTextField textAlterarNomeEstudante = new JTextField();
    private JTextField textAlterarIdadeEstudante = new JTextField();
    private JTextField textAlterarTipoCursoEstudante = new JTextField();
    private JTextField textAlterarCodDepartamentoEstudante = new JTextField();
	private JTextField textAlterarCodAconselhador = new JTextField();

    public void criarPanelAlterarEstudante(JPanel panelAlterar,JLayeredPane layeredAlterar){
        final JPanel finalPanelAlterar = panelAlterar;

		panelAlterarEstudante.setLayout(null);
        panelAlterarEstudante.setVisible(false);
		layeredAlterar.add(panelAlterarEstudante, "name_185067340967200");

        JLabel lblMensagemAlterarEstudante = new JLabel();
        lblMensagemAlterarEstudante.setBackground(UIManager.getColor("Button.background"));
		lblMensagemAlterarEstudante.setText("Preencha os campos que deseja alterar:");
		lblMensagemAlterarEstudante.setBounds(248, 28, 266, 20);
		panelAlterarEstudante.add(lblMensagemAlterarEstudante);

        JLabel lblAlterarNomeEstudante = new JLabel("Nome:");
		lblAlterarNomeEstudante.setBounds(84, 76, 80, 22);
		panelAlterarEstudante.add(lblAlterarNomeEstudante);

        textAlterarNomeEstudante.setColumns(10);
		textAlterarNomeEstudante.setBounds(248, 76, 374, 22);
		panelAlterarEstudante.add(textAlterarNomeEstudante);

        JLabel lblAlterarIdadeEstudante= new JLabel("Idade:");
        lblAlterarIdadeEstudante.setBounds(84, 126, 46, 22);
		panelAlterarEstudante.add(lblAlterarIdadeEstudante);

		textAlterarIdadeEstudante.setColumns(10);
		textAlterarIdadeEstudante.setBounds(248, 126, 374, 22);
		panelAlterarEstudante.add(textAlterarIdadeEstudante);

        JLabel lblAlterarTipoCursoEstudante = new JLabel("Tipo de curso:");
        lblAlterarTipoCursoEstudante.setBounds(84, 176, 100, 22);
        panelAlterarEstudante.add(lblAlterarTipoCursoEstudante);

        textAlterarTipoCursoEstudante.setColumns(10);
        textAlterarTipoCursoEstudante.setBounds(248, 176, 374, 22);
        panelAlterarEstudante.add(textAlterarTipoCursoEstudante);

        JLabel lblAlterarCodDepartamentoEstudante = new JLabel("Código do departamento:");
        lblAlterarCodDepartamentoEstudante.setBounds(84, 226, 200, 22);
        panelAlterarEstudante.add(lblAlterarCodDepartamentoEstudante);
    
        textAlterarCodDepartamentoEstudante.setColumns(10);
        textAlterarCodDepartamentoEstudante.setBounds(248, 226, 374, 22);
        panelAlterarEstudante.add(textAlterarCodDepartamentoEstudante);

		JLabel lblAlterarProjetosGeridosEstudante = new JLabel("Codigo do aconselhador:");
        lblAlterarProjetosGeridosEstudante.setBounds(84, 276, 180, 22);
        panelAlterarEstudante.add(lblAlterarProjetosGeridosEstudante);
    
        textAlterarCodAconselhador.setColumns(10);
        textAlterarCodAconselhador.setBounds(248, 276, 374, 22);
        panelAlterarEstudante.add(textAlterarCodAconselhador);

        JButton btnAlterarEstudante = new JButton("Alterar Estudante");
        btnAlterarEstudante.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                alterarEstudante();
                panelAlterarEstudante.setVisible(false);
				finalPanelAlterar.setVisible(true);
            }
        });
        btnAlterarEstudante.setBounds(248, 330, 144, 23);
        panelAlterarEstudante.add(btnAlterarEstudante);
    }

    public void alterarEstudante() {
		
		try {
			Estudante estudante = sistema.getEstudante(Integer.parseInt(textCodigoDigitadoEstudnte.getText()));
			
			if(textAlterarNomeEstudante.getText().equalsIgnoreCase("") == false && estudante.getNome().equalsIgnoreCase(textAlterarNomeEstudante.getText()) == false)
                estudante.setNome(textAlterarNomeEstudante.getText());
			if(textAlterarIdadeEstudante.getText().equalsIgnoreCase("") == false && estudante.getIdade() != Integer.parseInt(textAlterarIdadeEstudante.getText()))
                estudante.setIdade(Integer.parseInt(textAlterarIdadeEstudante.getText()));		
			if(textAlterarTipoCursoEstudante.getText().equalsIgnoreCase("") == false && estudante.getTipoCurso().equalsIgnoreCase(textAlterarTipoCursoEstudante.getText()))
                estudante.setCodDepartamentoCurso(Integer.parseInt(textAlterarTipoCursoEstudante.getText()));
			if(textAlterarCodDepartamentoEstudante.getText().equalsIgnoreCase("") == false && estudante.getCodDepartamentoCurso() != Integer.parseInt(textAlterarCodDepartamentoEstudante.getText()) == false)
                estudante.setCodDepartamentoCurso(Integer.parseInt(textAlterarCodDepartamentoEstudante.getText()));
			if(textAlterarCodAconselhador.getText().equalsIgnoreCase("") == false && estudante.getCodAconselhador() != Integer.parseInt(textAlterarCodAconselhador.getText()) == false)
                estudante.setCodAconselhador(Integer.parseInt(textAlterarCodAconselhador.getText()));
		
			sistema.alterarEstudante(estudante);
			limparAlterarEstudante();
			JOptionPane.showMessageDialog(null, "Estudante alterado!");	
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, estudante nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, estudante nao encontrado!");
		} catch (UpdateException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao atualizar estudante!");
			JOptionPane.showMessageDialog(null, "Erro ao atualizar estudante!");
		} catch (Exception e3) {
			System.err.println(e3.getMessage());
			System.err.println("Erro ao acessar o banco de dados!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
		}
	}

    public void limparAlterarEstudante() {
		textCodigoDigitadoEstudnte.setText("");
		textAlterarNomeEstudante.setText("");
		textAlterarIdadeEstudante.setText("");
		textAlterarTipoCursoEstudante.setText("");
		textAlterarCodDepartamentoEstudante.setText("");
		textAlterarCodAconselhador.setText("");
	}
}
