package apresentacao;

import negocio.Sistema;
import dados.TrabalhoEstudanteProjeto;
import exceptions.*;
import persistencia.TrabalhoEstudanteProjetoDAO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.SQLException;
import java.util.List;

public class PainelTrabalhoEstudanteProjeto {
    static Sistema sistema = new Sistema();

    private JPanel panelCadastrarTrabalhoEstudanteProjeto;

    private JTextField textEstudanteParticipante;
    private JTextField textCodProfessorSupervisor;

    private JTable tableTrabalhoEstudanteProjetos = new JTable(null, null, null);
    private JScrollPane scrollTrabalhoEstudanteProjetos = new JScrollPane(tableTrabalhoEstudanteProjetos);

   JTextField textDeletarTrabalhoEstudanteProjeto = new JTextField();

    public void criarPanelCadastrarTrabalhoEstudanteProjeto(JTabbedPane tabbedCadastrar) {
        panelCadastrarTrabalhoEstudanteProjeto = new JPanel();
        panelCadastrarTrabalhoEstudanteProjeto.setLayout(null);
        tabbedCadastrar.addTab("Trabalho Estudante Projeto", null, panelCadastrarTrabalhoEstudanteProjeto, null);
        
        JLabel lblEstudanteParticipante = new JLabel("Codigo estudante participante:");
        lblEstudanteParticipante.setBounds(82, 84, 300, 22);
        panelCadastrarTrabalhoEstudanteProjeto.add(lblEstudanteParticipante);
        
        textEstudanteParticipante = new JTextField();
        textEstudanteParticipante.setBounds(270, 84, 374, 22);
        textEstudanteParticipante.setColumns(10);
        panelCadastrarTrabalhoEstudanteProjeto.add(textEstudanteParticipante);
        
        JLabel lblCodProfessorSupervisor= new JLabel("Codigo professor supervisor:");
        lblCodProfessorSupervisor.setBounds(82, 137, 200, 22);
        panelCadastrarTrabalhoEstudanteProjeto.add(lblCodProfessorSupervisor);
        
        textCodProfessorSupervisor = new JTextField();
        textCodProfessorSupervisor.setBounds(270, 137, 374, 22);
        textCodProfessorSupervisor.setColumns(10);
        panelCadastrarTrabalhoEstudanteProjeto.add(textCodProfessorSupervisor);
        
        JButton btnRealizarCadastroTrabalhoEstudanteProjeto  = new JButton("Cadastrar Trabalho Estudante");
        btnRealizarCadastroTrabalhoEstudanteProjeto.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cadastrarTrabalhoEstudanteProjeto ();
            }
        });
        btnRealizarCadastroTrabalhoEstudanteProjeto.setBounds(270, 190, 220, 22);
        panelCadastrarTrabalhoEstudanteProjeto.add(btnRealizarCadastroTrabalhoEstudanteProjeto);
    }
    
    public void cadastrarTrabalhoEstudanteProjeto() {
        TrabalhoEstudanteProjeto trabalhoEstudanteProjeto = new TrabalhoEstudanteProjeto();
        
        trabalhoEstudanteProjeto.setCodEstudanteParticipante(Integer.parseInt(textEstudanteParticipante.getText()));		
        trabalhoEstudanteProjeto.setCodProfessorSupervisor(Integer.parseInt(textCodProfessorSupervisor.getText()));						
        
        try {
            sistema.cadastrarTrabalhoEstudanteProjeto(trabalhoEstudanteProjeto);
            limparCadastrarTrabalhoEstudanteProjeto();
            JOptionPane.showMessageDialog(null, "Cadastro do trabalho do estudante no projeto efetuado com sucesso!");
        } catch (ClassNotFoundException e1) {
            System.err.println(e1.getMessage());
            System.err.println("Erro, trabalho do estudante no projeto nao encontrado!");
            JOptionPane.showMessageDialog(null, "Erro, trabalho do estudante no projeto nao encontrado!");
        } catch (InsertException e2) {
            System.err.println(e2.getMessage());
            System.err.println("Erro ao inserir trabalho do estudante no projeto!");
            JOptionPane.showMessageDialog(null, "Erro ao inserir trabalho do estudante no projeto!");
        } catch (SQLException e3) {
            System.err.println(e3.getMessage());
            System.err.println("Erro ao acessar o banco de dados!");
            JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
        }
    }

    public void limparCadastrarTrabalhoEstudanteProjeto() {
        textEstudanteParticipante.setText("");
        textCodProfessorSupervisor.setText("");
    }

    public void criarPainelBuscarTrabalhoEstudanteProjeto(JPanel panelBuscar, JLayeredPane layeredBuscar) {
        JButton btnListarTrabalhoEstudanteProjetos = new JButton("Listar Trabalho dos Estudantes");
        JPanel panelBuscarTrabalhoEstudanteProjeto = new JPanel();

        criaPainelTabelTrabalhoEstudanteProjeto(panelBuscarTrabalhoEstudanteProjeto, layeredBuscar, panelBuscar);

		btnListarTrabalhoEstudanteProjetos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panelBuscar.setVisible(false);
				listarTrabalhoEstudanteProjetos();
				panelBuscarTrabalhoEstudanteProjeto.setVisible(true);
			}
		});
		btnListarTrabalhoEstudanteProjetos.setBounds(293, 285, 215, 23);
		panelBuscar.add(btnListarTrabalhoEstudanteProjetos);
    }

    public void criaPainelTabelTrabalhoEstudanteProjeto(JPanel panelBuscarTrabalhoEstudanteProjeto, JLayeredPane layeredBuscar,JPanel panelBuscar) {
		panelBuscarTrabalhoEstudanteProjeto.setLayout(null);
		layeredBuscar.add(panelBuscarTrabalhoEstudanteProjeto, "name_184619852570200");

		scrollTrabalhoEstudanteProjetos.setBounds(0, 0, 768, 333);
		scrollTrabalhoEstudanteProjetos.setViewportView(tableTrabalhoEstudanteProjetos);
		panelBuscarTrabalhoEstudanteProjeto.add(scrollTrabalhoEstudanteProjetos);
		
		JButton btnVoltaBuscalistarTrabalhoEstudanteProjetos = new JButton("Voltar");
		btnVoltaBuscalistarTrabalhoEstudanteProjetos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panelBuscarTrabalhoEstudanteProjeto.setVisible(false);
				panelBuscar.setVisible(true);
			}
		});
		btnVoltaBuscalistarTrabalhoEstudanteProjetos.setBounds(164, 344, 89, 23);
		panelBuscarTrabalhoEstudanteProjeto.add(btnVoltaBuscalistarTrabalhoEstudanteProjetos);
		
		JButton bntAtualizarListaTrabalhoEstudanteProjetos = new JButton("Atualizar Lista");
		bntAtualizarListaTrabalhoEstudanteProjetos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				limpaTabela(tableTrabalhoEstudanteProjetos);
				atualizarTabelaTrabalhoEstudanteProjetos();
			}
		});
		bntAtualizarListaTrabalhoEstudanteProjetos.setBounds(417, 344, 170, 23);
		panelBuscarTrabalhoEstudanteProjeto.add(bntAtualizarListaTrabalhoEstudanteProjetos);
	}

    public void listarTrabalhoEstudanteProjetos() {
		tableTrabalhoEstudanteProjetos.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableTrabalhoEstudanteProjetos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Codigo Trabalho Estudante", "Codigo Estudante Participante", "Codigo Professor Supervisor"
			}
		));
		tableTrabalhoEstudanteProjetos.setBounds(0, 0, 740, 342);
		
		tableTrabalhoEstudanteProjetos.getColumnModel().getColumn(0).setPreferredWidth(20);
        tableTrabalhoEstudanteProjetos.getColumnModel().getColumn(1).setPreferredWidth(20);
		tableTrabalhoEstudanteProjetos.getColumnModel().getColumn(2).setPreferredWidth(20);
			
		atualizarTabelaTrabalhoEstudanteProjetos();	
	}

    public void atualizarTabelaTrabalhoEstudanteProjetos() {;
        List<TrabalhoEstudanteProjeto> trabalhoEstudanteProjetos;
		String linha[] = new String[] {"", "", ""};
		DefaultTableModel dadosTrabalhoEstudanteProjeto = (DefaultTableModel) tableTrabalhoEstudanteProjetos.getModel();
		
		try {
			trabalhoEstudanteProjetos = TrabalhoEstudanteProjetoDAO.getInstance().getAll();
			int pos = -1;
			for(TrabalhoEstudanteProjeto trabalhoEstudanteProjeto : trabalhoEstudanteProjetos) {
				pos++;
				dadosTrabalhoEstudanteProjeto.addRow(linha);
				dadosTrabalhoEstudanteProjeto.setValueAt(trabalhoEstudanteProjeto.getCodTrabalhoEstudanteProjeto(), pos, 0);
				dadosTrabalhoEstudanteProjeto.setValueAt(trabalhoEstudanteProjeto.getCodEstudanteParticipante(), pos, 1);
				dadosTrabalhoEstudanteProjeto.setValueAt(trabalhoEstudanteProjeto.getCodProfessorSupervisor(), pos, 2);
			}
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, trabalho do estudante no projeto nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, trabalho do estudante no projeto nao encontrado!");
		} catch (SelectException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao acessar trabalho do estudante no projeto!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar trabalho do estudante no projeto!");
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

	public void criarPainelDeletarTrabalhoEstudanteProjeto(JPanel panelDeletar) {
		JLabel lblDeletarTrabalhoEstudanteProjeto= new JLabel();
		lblDeletarTrabalhoEstudanteProjeto.setBackground(UIManager.getColor("Button.background"));
		lblDeletarTrabalhoEstudanteProjeto.setText("Digite o codigo do trabalho do estudante no projeto:");
		lblDeletarTrabalhoEstudanteProjeto.setBounds(62, 260, 340, 23);
		panelDeletar.add(lblDeletarTrabalhoEstudanteProjeto);
		
		textDeletarTrabalhoEstudanteProjeto.setBounds(62, 285, 144, 23);
		textDeletarTrabalhoEstudanteProjeto.setColumns(10);
		panelDeletar.add(textDeletarTrabalhoEstudanteProjeto);
		
		JButton btnDeletarTrabalhoEstudanteProjeto = new JButton("Deletar Trabalho Estudante");
		btnDeletarTrabalhoEstudanteProjeto.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				deletarTrabalhoEstudanteProjeto();
			}
		});
		btnDeletarTrabalhoEstudanteProjeto.setBounds(499, 260, 200, 23);
		panelDeletar.add(btnDeletarTrabalhoEstudanteProjeto);
	}

	public void deletarTrabalhoEstudanteProjeto(){
		try {
			sistema.removerTrabalhoEstudanteProjeto(sistema.getTrabalhoEstudanteProjeto(Integer.parseInt(textDeletarTrabalhoEstudanteProjeto.getText())));
			limparDeletarTrabalhoEstudanteProjeto();
			JOptionPane.showMessageDialog(null, "Trabalho do estudante no projeto removido com sucesso!");
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, trabalho do estudante no projeto nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, trabalho do estudante no projeto nao encontrado!");
		} catch (DeleteException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao deletar trabalho do estudante no projeto!");
			JOptionPane.showMessageDialog(null, "Erro ao deletar trabalho do estudante no projeto!");
		} catch (SQLException e3) {
			System.err.println(e3.getMessage());
			System.err.println("Erro ao acessar o banco de dados!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
		} catch (NumberFormatException e4) {
			System.err.println(e4.getMessage());
			System.err.println("Erro no codigo do trabalho do estudante no projeto informado!");
			JOptionPane.showMessageDialog(null, "Erro no codigo do trabalho do estudante no projeto informado!");
		} catch (SelectException e5) {
			System.err.println(e5.getMessage());
			System.err.println("Erro ao acessar trabalho do estudante no projeto!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar trabalho do estudante no projeto!");
		}
	}

	public void limparDeletarTrabalhoEstudanteProjeto() {
		textDeletarTrabalhoEstudanteProjeto.setText("");
	}

	private JLabel lblAlterarTrabalhoEstudanteProjeto = new JLabel();
    private JPanel panelAlterarTrabalhoEstudanteProjeto = new JPanel();
    private JTextField textCodigoDigitadoTrabalhoEstudanteProjeto = new JTextField();

    public void criarPainelAlterarTrabalhoEstudanteProjeto(JPanel panelAlterar, JLayeredPane layeredAlterar){
        criarpanelAlterarTrabalhoEstudanteProjeto(panelAlterar, layeredAlterar);

        lblAlterarTrabalhoEstudanteProjeto.setBackground(UIManager.getColor("Button.background"));
        lblAlterarTrabalhoEstudanteProjeto.setText("Digite o codigo do trabalho do estudante no projeto:");
        lblAlterarTrabalhoEstudanteProjeto.setBounds(62, 230, 300, 23);

        panelAlterar.add(lblAlterarTrabalhoEstudanteProjeto);

        textCodigoDigitadoTrabalhoEstudanteProjeto.setBounds(62, 255, 144, 23);
        textCodigoDigitadoTrabalhoEstudanteProjeto.setColumns(10);
        panelAlterar.add(textCodigoDigitadoTrabalhoEstudanteProjeto);

        JButton  btnAlterarEstudanteProjeto = new JButton ("Alterar Trabalho Estudante");
        btnAlterarEstudanteProjeto.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                panelAlterar.setVisible(false);
                panelAlterarTrabalhoEstudanteProjeto.setVisible(true);
            }
        });
        btnAlterarEstudanteProjeto.setBounds(499, 230, 200, 23);
        panelAlterar.add(btnAlterarEstudanteProjeto);

    }

    private JTextField textAlterarCodEstudanteProjeto = new JTextField();
    private JTextField textAlterarCodProfessorSupervisor = new JTextField();

    public void criarpanelAlterarTrabalhoEstudanteProjeto(JPanel panelAlterar,JLayeredPane layeredAlterar){
        panelAlterarTrabalhoEstudanteProjeto.setLayout(null);
        panelAlterarTrabalhoEstudanteProjeto.setVisible(false);
		layeredAlterar.add(panelAlterarTrabalhoEstudanteProjeto, "name_185067340967200");

        JLabel lblMensagemAlterarTrabalhoEstudanteProjetos = new JLabel();
        lblMensagemAlterarTrabalhoEstudanteProjetos.setBackground(UIManager.getColor("Button.background"));
		lblMensagemAlterarTrabalhoEstudanteProjetos.setText("Preencha os campos que deseja alterar:");
		lblMensagemAlterarTrabalhoEstudanteProjetos.setBounds(248, 28, 266, 20);
		panelAlterarTrabalhoEstudanteProjeto.add(lblMensagemAlterarTrabalhoEstudanteProjetos);

        JLabel lblAlterarCodEstudanteProjeto  = new JLabel("Codigo estudante:");
        lblAlterarCodEstudanteProjeto.setBounds(84, 126, 200, 22);
		panelAlterarTrabalhoEstudanteProjeto.add(lblAlterarCodEstudanteProjeto);

		textAlterarCodEstudanteProjeto.setColumns(10);
		textAlterarCodEstudanteProjeto.setBounds(248, 126, 374, 22);
		panelAlterarTrabalhoEstudanteProjeto.add(textAlterarCodEstudanteProjeto);

        JLabel lblAlterarCodProfessorSupervisor = new JLabel("Codigo do professor lider:");
        lblAlterarCodProfessorSupervisor.setBounds(84, 176, 160, 22);
        panelAlterarTrabalhoEstudanteProjeto.add(lblAlterarCodProfessorSupervisor);

        textAlterarCodProfessorSupervisor.setColumns(10);
        textAlterarCodProfessorSupervisor.setBounds(248, 176, 374, 22);
        panelAlterarTrabalhoEstudanteProjeto.add(textAlterarCodProfessorSupervisor);

        JButton btnAlterarEstudanteProjeto = new JButton("Alterar Trabalho Estudante");
        btnAlterarEstudanteProjeto.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                alterarTrabalhoEstudanteProjeto();
                panelAlterarTrabalhoEstudanteProjeto.setVisible(false);
				panelAlterar.setVisible(true);
            }
        });
        btnAlterarEstudanteProjeto.setBounds(248, 240, 200, 23);
        panelAlterarTrabalhoEstudanteProjeto.add(btnAlterarEstudanteProjeto);
    }

    public void alterarTrabalhoEstudanteProjeto() {
		
		try {
			TrabalhoEstudanteProjeto trabalhoEstudanteProjeto = sistema.getTrabalhoEstudanteProjeto(Integer.parseInt(textCodigoDigitadoTrabalhoEstudanteProjeto.getText()));
			
			if(textAlterarCodEstudanteProjeto.getText().equalsIgnoreCase("") == false && trabalhoEstudanteProjeto.getCodEstudanteParticipante() != Integer.parseInt(textAlterarCodEstudanteProjeto.getText()))
			trabalhoEstudanteProjeto.setCodEstudanteParticipante(Integer.parseInt(textAlterarCodEstudanteProjeto.getText()));		
			if(textAlterarCodProfessorSupervisor.getText().equalsIgnoreCase("") == false && trabalhoEstudanteProjeto.getCodProfessorSupervisor() != Long.parseLong(textAlterarCodProfessorSupervisor.getText()))
			trabalhoEstudanteProjeto.setCodProfessorSupervisor(Integer.parseInt(textAlterarCodProfessorSupervisor.getText()));
		
			sistema.alterarTrabalhoEstudanteProjeto(trabalhoEstudanteProjeto);
			limparAlterarTrabalhoEstudanteProjeto();
			JOptionPane.showMessageDialog(null, "Trabalho do estudante no projeto alterado!");	
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, trabalho do estudante no projeto nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, trabalho do estudante no projeto nao encontrado!");
		} catch (UpdateException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao atualizar trabalho do estudante no projeto!");
			JOptionPane.showMessageDialog(null, "Erro ao atualizar trabalho do estudante no projeto!");
		} catch (SQLException e3) {
			System.err.println(e3.getMessage());
			System.err.println("Erro ao acessar o banco de dados!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
		} catch (NumberFormatException e4) {
			System.err.println(e4.getMessage());
			System.err.println("Erro no codigo do trabalho do estudante no projeto informado ou no formato dos dados inseridos!");
			JOptionPane.showMessageDialog(null, "Erro no codigo do trabalho do estudante no projeto informado ou no formato dos dados inseridos!");
		} catch (SelectException e5) {
			System.err.println(e5.getMessage());
			System.err.println("Erro ao acessar trabalho do estudante no projeto!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar trabalho do estudante no projeto!");
		}
	}

    public void limparAlterarTrabalhoEstudanteProjeto() {
		textCodigoDigitadoTrabalhoEstudanteProjeto.setText("");
		textAlterarCodEstudanteProjeto.setText("");
		textAlterarCodProfessorSupervisor.setText("");
	}
}
