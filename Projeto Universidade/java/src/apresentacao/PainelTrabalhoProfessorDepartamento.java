package apresentacao;

import negocio.Sistema;
import persistencia.TrabalhoProfessorDepartamentoDAO;
import dados.TrabalhoProfessorDepartamento;
import exceptions.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.SQLException;
import java.util.List;

public class PainelTrabalhoProfessorDepartamento {
    static Sistema sistema = new Sistema();

    private JPanel panelCadastarTraBalhoProfessorDepartamento;
    
    private JTextField textCodProfessorTrabalho;
    private JTextField textCodDepartamentoTrabalho;
    private JTextField textPerTempoTrabalho;
    
    private JTable tableTrabalhoProfessorDepartamentos = new JTable(null, null, null);
    private JScrollPane scrollTrabalhoProfessorDepartamentos = new JScrollPane(tableTrabalhoProfessorDepartamentos);

   JTextField textDeletarTrabalhoProfessorDepartamento = new JTextField();

    public void criarPanelCadastrarTrabalhoProfessorDepartamento(JTabbedPane tabbedCadastrar) {
        panelCadastarTraBalhoProfessorDepartamento = new JPanel();
        panelCadastarTraBalhoProfessorDepartamento.setLayout(null);
        tabbedCadastrar.addTab("Trabalho Professor Departamento", null, panelCadastarTraBalhoProfessorDepartamento, null);
        
        JLabel lblProfessorTrabalho = new JLabel("Codigo professor do trabalho:");
        lblProfessorTrabalho.setBounds(82, 84, 200, 22);
        panelCadastarTraBalhoProfessorDepartamento.add(lblProfessorTrabalho);
        
        textCodProfessorTrabalho = new JTextField();
        textCodProfessorTrabalho.setBounds(300, 84, 374, 22);
        textCodProfessorTrabalho.setColumns(10);
        panelCadastarTraBalhoProfessorDepartamento.add(textCodProfessorTrabalho);
        
        JLabel lblCodDepartamentoTrabalho = new JLabel("Codigo departamento do trabalho:");
        lblCodDepartamentoTrabalho.setBounds(82, 137, 200, 22);
        panelCadastarTraBalhoProfessorDepartamento.add(lblCodDepartamentoTrabalho);
        
        textCodDepartamentoTrabalho = new JTextField();
        textCodDepartamentoTrabalho.setBounds(300, 137, 374, 22);
        textCodDepartamentoTrabalho.setColumns(10);
        panelCadastarTraBalhoProfessorDepartamento.add(textCodDepartamentoTrabalho);

        JLabel lblPerTempoTrabalho = new JLabel("Percentagem de tempo do trabalho:");
        lblPerTempoTrabalho.setBounds(82, 190, 220, 22);
        panelCadastarTraBalhoProfessorDepartamento.add(lblPerTempoTrabalho);

        textPerTempoTrabalho = new JTextField();
        textPerTempoTrabalho.setBounds(300, 190, 374, 22);
        textPerTempoTrabalho.setColumns(10);
        panelCadastarTraBalhoProfessorDepartamento.add(textPerTempoTrabalho);
        
        JButton btnRealizarCadastroTrabalhoProfessorDepartamento  = new JButton("Cadastrar Trabalho Professor");
        btnRealizarCadastroTrabalhoProfessorDepartamento.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cadastrarTrabalhoProfessorDepartamento ();
            }
        });
        btnRealizarCadastroTrabalhoProfessorDepartamento.setBounds(300, 240, 220, 22);
        panelCadastarTraBalhoProfessorDepartamento.add(btnRealizarCadastroTrabalhoProfessorDepartamento);
    }
    
    public void cadastrarTrabalhoProfessorDepartamento () {
        TrabalhoProfessorDepartamento trabalhoProfessorDepartamento  = new TrabalhoProfessorDepartamento();
        
        trabalhoProfessorDepartamento.setCodProfessorTrabalho(Integer.parseInt(textCodProfessorTrabalho.getText()));		
		trabalhoProfessorDepartamento.setCodDepartamentoTrabalho(Integer.parseInt(textCodDepartamentoTrabalho.getText()));
        trabalhoProfessorDepartamento.setPerTempoTrabalho(Float.parseFloat(textPerTempoTrabalho.getText()));	
        
        try {
            sistema.cadastrarTrabalhoProfessorDepartamento (trabalhoProfessorDepartamento);
            limparCadastrarTrabalhoProfessorDepartamento ();
            JOptionPane.showMessageDialog(null, "Cadastro do trabalho do professor no departamento efetuado com sucesso!");
        } catch (ClassNotFoundException e1) {
            System.err.println(e1.getMessage());
            System.err.println("Erro, trabalho do professor no departamento nao encontrado!");
            JOptionPane.showMessageDialog(null, "Erro, trabalho do professor no departamento nao encontrado!");
        } catch (InsertException e2) {
            System.err.println(e2.getMessage());
            System.err.println("Erro ao inserir trabalho do professor no departamento!");
            JOptionPane.showMessageDialog(null, "Erro ao inserir trabalho do professor no departamento!");
        } catch (SQLException e3) {
            System.err.println(e3.getMessage());
            System.err.println("Erro ao acessar o banco de dados!");
            JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
        }
    }

    public void limparCadastrarTrabalhoProfessorDepartamento () {
        textCodProfessorTrabalho.setText("");
        textCodDepartamentoTrabalho.setText("");
        textPerTempoTrabalho.setText("");
    }

    public void criarPainelBuscarTrabalhoProfessorDepartamento(JPanel panelBuscar, JLayeredPane layeredBuscar) {
        JButton btnListarTrabalhoProfessorDepartamentos = new JButton("Listar Trabalho dos Professores");
        JPanel panelBuscarTrabalhoProfessorDepartamento = new JPanel();

        criaPainelTabelTrabalhoProfessorDepartamento(panelBuscarTrabalhoProfessorDepartamento, layeredBuscar, panelBuscar);

		btnListarTrabalhoProfessorDepartamentos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panelBuscar.setVisible(false);
				listarTrabalhoProfessorDepartamentos();
				panelBuscarTrabalhoProfessorDepartamento.setVisible(true);
			}
		});
		btnListarTrabalhoProfessorDepartamentos.setBounds(293, 350, 220, 23);
		panelBuscar.add(btnListarTrabalhoProfessorDepartamentos);
    }

    public void criaPainelTabelTrabalhoProfessorDepartamento(JPanel panelBuscarTrabalhoProfessorDepartamento, JLayeredPane layeredBuscar,JPanel panelBuscar) {
		panelBuscarTrabalhoProfessorDepartamento.setLayout(null);
		layeredBuscar.add(panelBuscarTrabalhoProfessorDepartamento, "name_184619852570200");

		scrollTrabalhoProfessorDepartamentos.setBounds(0, 0, 768, 333);
		scrollTrabalhoProfessorDepartamentos.setViewportView(tableTrabalhoProfessorDepartamentos);
		panelBuscarTrabalhoProfessorDepartamento.add(scrollTrabalhoProfessorDepartamentos);
		
		JButton btnVoltaBuscalistarTrabalhoProfessorDepartamentos = new JButton("Voltar");
		btnVoltaBuscalistarTrabalhoProfessorDepartamentos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panelBuscarTrabalhoProfessorDepartamento.setVisible(false);
				panelBuscar.setVisible(true);
			}
		});
		btnVoltaBuscalistarTrabalhoProfessorDepartamentos.setBounds(164, 344, 89, 23);
		panelBuscarTrabalhoProfessorDepartamento.add(btnVoltaBuscalistarTrabalhoProfessorDepartamentos);
		
		JButton bntAtualizarListaTrabalhoProfessorDepartamentos = new JButton("Atualizar Lista");
		bntAtualizarListaTrabalhoProfessorDepartamentos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				limpaTabela(tableTrabalhoProfessorDepartamentos);
				atualizarTabelaTrabalhoProfessorDepartamentos();
			}
		});
		bntAtualizarListaTrabalhoProfessorDepartamentos.setBounds(417, 344, 170, 23);
		panelBuscarTrabalhoProfessorDepartamento.add(bntAtualizarListaTrabalhoProfessorDepartamentos);
	}

    public void listarTrabalhoProfessorDepartamentos() {
		tableTrabalhoProfessorDepartamentos.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableTrabalhoProfessorDepartamentos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Codigo Trabalho Professor", "Codigo Professor", "Codigo Departamento", "Percentual de Tempo"
			}
		));
		tableTrabalhoProfessorDepartamentos.setBounds(0, 0, 740, 342);
		
		tableTrabalhoProfessorDepartamentos.getColumnModel().getColumn(0).setPreferredWidth(20);
        tableTrabalhoProfessorDepartamentos.getColumnModel().getColumn(1).setPreferredWidth(20);
		tableTrabalhoProfessorDepartamentos.getColumnModel().getColumn(2).setPreferredWidth(20);
        tableTrabalhoProfessorDepartamentos.getColumnModel().getColumn(3).setPreferredWidth(20);
        
		atualizarTabelaTrabalhoProfessorDepartamentos();	
	}

    public void atualizarTabelaTrabalhoProfessorDepartamentos() {;
        List<TrabalhoProfessorDepartamento> trabalhoProfessorDepartamentos;
		String linha[] = new String[] {"", "", "", ""};
		DefaultTableModel dadosTrabalhoProfessorDepartamento = (DefaultTableModel) tableTrabalhoProfessorDepartamentos.getModel();
		
		try {
			trabalhoProfessorDepartamentos = TrabalhoProfessorDepartamentoDAO.getInstance().getAll();
			int pos = -1;
			for(TrabalhoProfessorDepartamento trabalhoProfessorDepartamento : trabalhoProfessorDepartamentos) {
				pos++;
				dadosTrabalhoProfessorDepartamento.addRow(linha);
				dadosTrabalhoProfessorDepartamento.setValueAt(trabalhoProfessorDepartamento.getCodTrabalhoProfDep(), pos, 0);
				dadosTrabalhoProfessorDepartamento.setValueAt(trabalhoProfessorDepartamento.getCodProfessorTrabalho(), pos, 1);
				dadosTrabalhoProfessorDepartamento.setValueAt(trabalhoProfessorDepartamento.getCodDepartamentoTrabalho(), pos, 2);
                dadosTrabalhoProfessorDepartamento.setValueAt(trabalhoProfessorDepartamento.getPerTempoTrabalho(), pos, 3);
			}
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, trabalho do professor no departamento nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, trabalho do professor no departamento nao encontrado!");
		} catch (SelectException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao acessar trabalho do professor no departamentoo!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar trabalho do professor no departamento!");
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

    public void criarPainelDeletarTrabalhoProfessorDepartamento(JPanel panelDeletar) {
		JLabel lblDeletarTrabalhoProfessorDepartamento = new JLabel();
		lblDeletarTrabalhoProfessorDepartamento.setBackground(UIManager.getColor("Button.background"));
		lblDeletarTrabalhoProfessorDepartamento.setText("Digite o codigo do trabalho do professor no departamento:");
		lblDeletarTrabalhoProfessorDepartamento.setBounds(62, 320, 340, 23);
		panelDeletar.add(lblDeletarTrabalhoProfessorDepartamento);
		
		textDeletarTrabalhoProfessorDepartamento.setBounds(62, 345, 144, 23);
		textDeletarTrabalhoProfessorDepartamento.setColumns(10);
		panelDeletar.add(textDeletarTrabalhoProfessorDepartamento);
		
		JButton btnDeletarTrabalhoProfessorDepartamento = new JButton("Deletar Trabalho Professor");
		btnDeletarTrabalhoProfessorDepartamento.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				deletarTrabalhoProfessorDepartamento();
			}
		});
		btnDeletarTrabalhoProfessorDepartamento.setBounds(499, 320, 200, 23);
		panelDeletar.add(btnDeletarTrabalhoProfessorDepartamento);
	}

	public void deletarTrabalhoProfessorDepartamento(){
		try {
			sistema.removerTrabalhoProfessorDepartamento(sistema.getTrabalhoProfessorDepartamento(Integer.parseInt(textDeletarTrabalhoProfessorDepartamento.getText())));
			limparDeletarTrabalhoProfessorDepartamento();
			JOptionPane.showMessageDialog(null, "Trabalho do professor no departamento removido com sucesso!");
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, trabalho do professor no departamento nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, trabalho do professor no departamento nao encontrado!");
		} catch (DeleteException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao deletar trabalho do professor no departamento!");
			JOptionPane.showMessageDialog(null, "Erro ao deletar trabalho do professor no departamento!");
		} catch (SQLException e3) {
			System.err.println(e3.getMessage());
			System.err.println("Erro ao acessar o banco de dados!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
		} catch (NumberFormatException e4) {
			System.err.println(e4.getMessage());
			System.err.println("Erro no codigo do trabalho do professor no departamento informado!");
			JOptionPane.showMessageDialog(null, "Erro no codigo do trabalho do professor no departamento informado!");
		} catch (SelectException e5) {
			System.err.println(e5.getMessage());
			System.err.println("Erro ao acessar trabalho do professor no departamento!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar trabalho do professor no departamento!");
		}
	}

	public void limparDeletarTrabalhoProfessorDepartamento() {
		textDeletarTrabalhoProfessorDepartamento.setText("");
	}

    private JLabel lblAlterarTrabalhoProfessorDepartamento = new JLabel();
    private JPanel panelAlterarTrabalhoProfessorDepartamento = new JPanel();
    private JTextField textCodigoDigitadoTrabalhoProfessorDepartamento = new JTextField();

    public void criarPainelAlterarTrabalhoProfessorDepartamento(JPanel panelAlterar, JLayeredPane layeredAlterar){
        criarpanelAlterarTrabalhoProfessorDepartamento(panelAlterar, layeredAlterar);

        lblAlterarTrabalhoProfessorDepartamento.setBackground(UIManager.getColor("Button.background"));
        lblAlterarTrabalhoProfessorDepartamento.setText("Digite o codigo do trabalho do professor no departamento:");
        lblAlterarTrabalhoProfessorDepartamento.setBounds(62, 300, 350, 23);

        panelAlterar.add(lblAlterarTrabalhoProfessorDepartamento);

        textCodigoDigitadoTrabalhoProfessorDepartamento.setBounds(62, 325, 144, 23);
        textCodigoDigitadoTrabalhoProfessorDepartamento.setColumns(10);
        panelAlterar.add(textCodigoDigitadoTrabalhoProfessorDepartamento);

        JButton  btnAlterarProfessorDepartamento = new JButton ("Alterar Trabalho Professor");
        btnAlterarProfessorDepartamento.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                panelAlterar.setVisible(false);
                panelAlterarTrabalhoProfessorDepartamento.setVisible(true);
            }
        });
        btnAlterarProfessorDepartamento.setBounds(499, 300, 200, 23);
        panelAlterar.add(btnAlterarProfessorDepartamento);

    }

    private JTextField textAlterarCodProfessorTrabalho= new JTextField();
    private JTextField textAlterarCodDepartamentoTrabalho = new JTextField();
    private JTextField textAlterraPerTempoTrabalho = new JTextField();

    public void criarpanelAlterarTrabalhoProfessorDepartamento(JPanel panelAlterar,JLayeredPane layeredAlterar){
        panelAlterarTrabalhoProfessorDepartamento.setLayout(null);
        panelAlterarTrabalhoProfessorDepartamento.setVisible(false);
		layeredAlterar.add(panelAlterarTrabalhoProfessorDepartamento, "name_185067340967200");

        JLabel lblMensagemAlterarTrabalhoProfessorDepartamentos = new JLabel();
        lblMensagemAlterarTrabalhoProfessorDepartamentos.setBackground(UIManager.getColor("Button.background"));
		lblMensagemAlterarTrabalhoProfessorDepartamentos.setText("Preencha os campos que deseja alterar:");
		lblMensagemAlterarTrabalhoProfessorDepartamentos.setBounds(248, 28, 266, 20);
		panelAlterarTrabalhoProfessorDepartamento.add(lblMensagemAlterarTrabalhoProfessorDepartamentos);

        JLabel lblAlterarCodProfessorTrabalho = new JLabel("Codigo professor:");
        lblAlterarCodProfessorTrabalho.setBounds(84, 76, 150, 22);
		panelAlterarTrabalhoProfessorDepartamento.add(lblAlterarCodProfessorTrabalho);

		textAlterarCodProfessorTrabalho.setColumns(10);
		textAlterarCodProfessorTrabalho.setBounds(248, 76, 374, 22);
		panelAlterarTrabalhoProfessorDepartamento.add(textAlterarCodProfessorTrabalho);

        JLabel lblAlterarCodDepartamentoTrabalho= new JLabel("Codigo departamento:");
        lblAlterarCodDepartamentoTrabalho.setBounds(84, 126, 160, 22);
        panelAlterarTrabalhoProfessorDepartamento.add(lblAlterarCodDepartamentoTrabalho);

        textAlterarCodDepartamentoTrabalho.setColumns(10);
        textAlterarCodDepartamentoTrabalho.setBounds(248, 126, 374, 22);
        panelAlterarTrabalhoProfessorDepartamento.add(textAlterarCodDepartamentoTrabalho);

        JLabel lblAlterarPerTempoTrabalho = new JLabel("Percentagem de tempo:");
        lblAlterarPerTempoTrabalho.setBounds(84, 176, 160, 22);
        panelAlterarTrabalhoProfessorDepartamento.add(lblAlterarPerTempoTrabalho);

        textAlterraPerTempoTrabalho.setColumns(10);
        textAlterraPerTempoTrabalho.setBounds(248, 176, 374, 22);
        panelAlterarTrabalhoProfessorDepartamento.add(textAlterraPerTempoTrabalho);

        JButton btnAlterarProfessorDepartamento = new JButton("Alterar Trabalho Professor");
        btnAlterarProfessorDepartamento.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                alterarTrabalhoProfessorDepartamento();
                panelAlterarTrabalhoProfessorDepartamento.setVisible(false);
				panelAlterar.setVisible(true);
            }
        });
        btnAlterarProfessorDepartamento.setBounds(248, 230, 200, 23);
        panelAlterarTrabalhoProfessorDepartamento.add(btnAlterarProfessorDepartamento);
    }

    public void alterarTrabalhoProfessorDepartamento() {
		
		try {
			TrabalhoProfessorDepartamento trabalhoProfessorDepartamento = sistema.getTrabalhoProfessorDepartamento(Integer.parseInt(textCodigoDigitadoTrabalhoProfessorDepartamento.getText()));
			
			if(textAlterarCodProfessorTrabalho.getText().equalsIgnoreCase("") == false && trabalhoProfessorDepartamento.getCodProfessorTrabalho() != Integer.parseInt(textAlterarCodProfessorTrabalho.getText()))
			    trabalhoProfessorDepartamento.setCodProfessorTrabalho(Integer.parseInt(textAlterarCodProfessorTrabalho.getText()));		
			if(textAlterarCodDepartamentoTrabalho.getText().equalsIgnoreCase("") == false && trabalhoProfessorDepartamento.getCodDepartamentoTrabalho() != Long.parseLong(textAlterarCodDepartamentoTrabalho.getText()))
			    trabalhoProfessorDepartamento.setCodDepartamentoTrabalho(Integer.parseInt(textAlterarCodDepartamentoTrabalho.getText()));
            if(textAlterraPerTempoTrabalho.getText().equalsIgnoreCase("") == false && trabalhoProfessorDepartamento.getPerTempoTrabalho() != Float.parseFloat(textAlterraPerTempoTrabalho.getText()))
                trabalhoProfessorDepartamento.setPerTempoTrabalho(Float.parseFloat(textAlterraPerTempoTrabalho.getText()));
		
			sistema.alterarTrabalhoProfessorDepartamento(trabalhoProfessorDepartamento);
			limparAlterarTrabalhoProfessorDepartamento();
			JOptionPane.showMessageDialog(null, "Trabalho do professor no departamento alterado!");	
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, trabalho do professor no departamento nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, trabalho do professor no departamento nao encontrado!");
		} catch (UpdateException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao atualizar trabalho do professor no departamento!");
			JOptionPane.showMessageDialog(null, "Erro ao atualizar trabalho do professor no departamento!");
		} catch (SQLException e3) {
			System.err.println(e3.getMessage());
			System.err.println("Erro ao acessar o banco de dados!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
		} catch (NumberFormatException e4) {
			System.err.println(e4.getMessage());
			System.err.println("Erro no codigo do trabalho do professor no departamento informado ou no formato dos dados inseridos!");
			JOptionPane.showMessageDialog(null, "Erro no codigo do trabalho do professor no departamento informado ou no formato dos dados inseridos!");
		} catch (SelectException e5) {
			System.err.println(e5.getMessage());
			System.err.println("Erro ao acessar trabalho do professor no departamento!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar trabalho do professor no departamento!");
		}
	}

    public void limparAlterarTrabalhoProfessorDepartamento() {
		textCodigoDigitadoTrabalhoProfessorDepartamento.setText("");
		textAlterarCodProfessorTrabalho.setText("");
		textAlterarCodDepartamentoTrabalho.setText("");
        textAlterraPerTempoTrabalho.setText("");
	}
}
