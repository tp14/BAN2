package apresentacao;

import negocio.Sistema;
import dados.Departamento;
import exceptions.*;
import persistencia.DepartamentoDAO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.SQLException;
import java.util.List;

public class PainelDepartamento {
    static Sistema sistema = new Sistema();

    private JPanel panelCadastrarDepartamento;

    private JTextField textNomeDepartamento;
    private JTextField textEscritorioDepartamento;
    private JTextField textCodProfessorLider;

    private JTable tableDepartamentos = new JTable(null, null, null);
    private JScrollPane scrollDepartamentos = new JScrollPane(tableDepartamentos);

   JTextField textDeletarDepartamento = new JTextField();

    public void criarPainelCadastrarDepartamento(JTabbedPane tabbedCadastrar) {
        panelCadastrarDepartamento = new JPanel();
        panelCadastrarDepartamento.setLayout(null);
        tabbedCadastrar.addTab("Departamento", null, panelCadastrarDepartamento, null);
        
        JLabel lblNomeDepartamento = new JLabel("Nome:");
        lblNomeDepartamento.setBounds(82, 31, 80, 22);
        panelCadastrarDepartamento.add(lblNomeDepartamento);
        
        textNomeDepartamento = new JTextField();
        textNomeDepartamento.setBounds(244, 31, 374, 22);
        textNomeDepartamento.setColumns(10);
        panelCadastrarDepartamento.add(textNomeDepartamento);
        
        JLabel lblEscritorioDepartamento = new JLabel("Escritorio:");
        lblEscritorioDepartamento.setBounds(82, 84, 100, 22);
        panelCadastrarDepartamento.add(lblEscritorioDepartamento);
        
        textEscritorioDepartamento = new JTextField();
        textEscritorioDepartamento.setBounds(244, 84, 374, 22);
        textEscritorioDepartamento.setColumns(10);
        panelCadastrarDepartamento.add(textEscritorioDepartamento);
        
        JLabel lblCodeProfessorLider = new JLabel("Código professor lider:");
        lblCodeProfessorLider.setBounds(82, 137, 200, 22);
        panelCadastrarDepartamento.add(lblCodeProfessorLider);
        
        textCodProfessorLider = new JTextField();
        textCodProfessorLider.setBounds(244, 137, 374, 22);
        textCodProfessorLider.setColumns(10);
        panelCadastrarDepartamento.add(textCodProfessorLider);
        
        JButton btnRealizarCadastroDepartamento= new JButton("Cadastrar Departamento");
        btnRealizarCadastroDepartamento.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cadastrarDepartamento();
            }
        });
        btnRealizarCadastroDepartamento.setBounds(244, 190, 180, 22);
        panelCadastrarDepartamento.add(btnRealizarCadastroDepartamento);
    }
    
    public void cadastrarDepartamento() {
        Departamento departamento = new Departamento();
        
        departamento.setNome(textNomeDepartamento.getText());
        departamento.setEscritorio(Integer.parseInt(textEscritorioDepartamento.getText()));		
        departamento.setCodProfessorLider(Integer.parseInt(textCodProfessorLider.getText()));						
        
        try {
            sistema.cadastrarDepartamento(departamento);
            limparCadastrarDepartamento();
            JOptionPane.showMessageDialog(null, "Cadastro do departamento efetuado com sucesso!");
        } catch (ClassNotFoundException e1) {
            System.err.println(e1.getMessage());
            System.err.println("Erro, departamento nao encontrado!");
            JOptionPane.showMessageDialog(null, "Erro, departamento nao encontrado!");
        } catch (InsertException e2) {
            System.err.println(e2.getMessage());
            System.err.println("Erro ao inserir departamento!");
            JOptionPane.showMessageDialog(null, "Erro ao inserir departamento!");
        } catch (SQLException e3) {
            System.err.println(e3.getMessage());
            System.err.println("Erro ao acessar o banco de dados!");
            JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
        }
    }

    public void limparCadastrarDepartamento() {
        textNomeDepartamento.setText("");
        textEscritorioDepartamento.setText("");
        textCodProfessorLider.setText("");
    }

    public void criarPainelBuscarDepartamento(JPanel panelBuscar, JLayeredPane layeredBuscar) {
        JButton btnListarDepartamentos = new JButton("Listar Departamentos");
        JPanel panelBuscarDepartamento = new JPanel();

        criaPainelTabelDepartamento(panelBuscarDepartamento, layeredBuscar, panelBuscar);

		btnListarDepartamentos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panelBuscar.setVisible(false);
				listarDepartamentos();
				panelBuscarDepartamento.setVisible(true);
			}
		});
		btnListarDepartamentos.setBounds(293, 150, 182, 23);
		panelBuscar.add(btnListarDepartamentos);

    }

    public void criaPainelTabelDepartamento(JPanel panelBuscarDepartamento, JLayeredPane layeredBuscar,JPanel panelBuscar) {
		panelBuscarDepartamento.setLayout(null);
		layeredBuscar.add(panelBuscarDepartamento, "name_184619852570200");

		scrollDepartamentos.setBounds(0, 0, 768, 333);
		scrollDepartamentos.setViewportView(tableDepartamentos);
		panelBuscarDepartamento.add(scrollDepartamentos);
		
		JButton btnVoltaBuscaDepartamentos = new JButton("Voltar");
		btnVoltaBuscaDepartamentos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panelBuscarDepartamento.setVisible(false);
				panelBuscar.setVisible(true);
			}
		});
		btnVoltaBuscaDepartamentos.setBounds(164, 344, 89, 23);
		panelBuscarDepartamento.add(btnVoltaBuscaDepartamentos);
		
		JButton bntAtualizarListaDepartamentos = new JButton("Atualizar Lista");
		bntAtualizarListaDepartamentos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				limpaTabela(tableDepartamentos);
				atualizarTabelaDepartamentos();
			}
		});
		bntAtualizarListaDepartamentos.setBounds(417, 344, 170, 23);
		panelBuscarDepartamento.add(bntAtualizarListaDepartamentos);
	}

    public void listarDepartamentos() {
		tableDepartamentos.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableDepartamentos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Codigo", "Nome", "Escritorio", "Código Professor Lider"
			}
		));
		tableDepartamentos.setBounds(0, 0, 740, 342);
		
		tableDepartamentos.getColumnModel().getColumn(0).setPreferredWidth(20);
        tableDepartamentos.getColumnModel().getColumn(1).setPreferredWidth(20);
		tableDepartamentos.getColumnModel().getColumn(2).setPreferredWidth(20);
		tableDepartamentos.getColumnModel().getColumn(3).setPreferredWidth(20);
			
		atualizarTabelaDepartamentos();	
	}

    public void atualizarTabelaDepartamentos() {;
        List<Departamento> departamentos;
		String linha[] = new String[] {"", "", "", ""};
		DefaultTableModel dadosDepartamento = (DefaultTableModel) tableDepartamentos.getModel();
		
		try {
			departamentos = DepartamentoDAO.getInstance().getAll();
			int pos = -1;
			for(Departamento departamento : departamentos) {
				pos++;
				dadosDepartamento.addRow(linha);
				dadosDepartamento.setValueAt(departamento.getCodDepartamento(), pos, 0);
				dadosDepartamento.setValueAt(departamento.getNome(), pos, 1);
				dadosDepartamento.setValueAt(departamento.getEscritorio(), pos, 2);
				dadosDepartamento.setValueAt(departamento.getCodProfessorLider(), pos, 3);
			}
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, departamento nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, departamento nao encontrado!");
		} catch (SelectException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao acessar departamento!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar departamento!");
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

    public void criarPainelDeletarDepartamento(JPanel panelDeletar){
        JLabel lblDeletarDepartamento = new JLabel();
		lblDeletarDepartamento.setBackground(UIManager.getColor("Button.background"));
		lblDeletarDepartamento.setText("Digite o codigo do departamento:");
		lblDeletarDepartamento.setBounds(62, 140, 200, 23);
		panelDeletar.add(lblDeletarDepartamento);
		
		textDeletarDepartamento.setBounds(282, 140, 144, 23);
		textDeletarDepartamento.setColumns(10);
		panelDeletar.add(textDeletarDepartamento);
		
		JButton btnDeletarDepartamento = new JButton("Deletar Departamento");
		btnDeletarDepartamento.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				deletarDepartamento();
			}
		});
		btnDeletarDepartamento.setBounds(499, 140, 160, 23);
		panelDeletar.add(btnDeletarDepartamento);
    }

    public void deletarDepartamento() {
		try {
			sistema.removerDepartamento(sistema.getDepartamento(Integer.parseInt(textDeletarDepartamento.getText())));
			limparDeletarDepartamento();
			JOptionPane.showMessageDialog(null, "Departamento removido com sucesso!");
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, departamento nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, departamento nao encontrado!");
		} catch (DeleteException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao deletar departamento!");
			JOptionPane.showMessageDialog(null, "Erro ao deletar departamento!");
		} catch (SQLException e3) {
			System.err.println(e3.getMessage());
			System.err.println("Erro ao acessar o banco de dados!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
		} catch (NumberFormatException e4) {
			System.err.println(e4.getMessage());
			System.err.println("Erro no codigo do departamento informado!");
			JOptionPane.showMessageDialog(null, "Erro no codigo do departamento informado!");
		} catch (SelectException e5) {
			System.err.println(e5.getMessage());
			System.err.println("Erro ao acessar departamento!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar departamento!");
		}
	}

    public void limparDeletarDepartamento() {
		textDeletarDepartamento.setText("");
	}

    private JLabel lblAlterarDepartamento = new JLabel();
    private JPanel panelAlterarDepartamento = new JPanel();
    private JTextField textCodigoDigitadoDepartamento = new JTextField();

    public void criarPainelAlterarDepartamento(JPanel panelAlterar, JLayeredPane layeredAlterar){
        criarPanelAlterarDepartamento(panelAlterar, layeredAlterar);

        lblAlterarDepartamento.setBackground(UIManager.getColor("Button.background"));
        lblAlterarDepartamento.setText("Digite o codigo do departamento:");
        lblAlterarDepartamento.setBounds(62, 130, 200, 23);

        panelAlterar.add(lblAlterarDepartamento);

        textCodigoDigitadoDepartamento.setBounds(282, 130, 144, 23);
        textCodigoDigitadoDepartamento.setColumns(10);
        panelAlterar.add(textCodigoDigitadoDepartamento);

        JButton  btnAlterarDepartamento = new JButton ("Alterar Departamento");
        btnAlterarDepartamento.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                panelAlterar.setVisible(false);
                panelAlterarDepartamento.setVisible(true);
            }
        });
        btnAlterarDepartamento.setBounds(499, 130, 160, 23);
        panelAlterar.add(btnAlterarDepartamento);

    }

    private JTextField textAlterarNomeDepartamento = new JTextField();
    private JTextField textAlterarEscritorioDepartamento = new JTextField();
    private JTextField textAlterarCodProfessorLider = new JTextField();

    public void criarPanelAlterarDepartamento(JPanel panelAlterar,JLayeredPane layeredAlterar){
        panelAlterarDepartamento.setLayout(null);
        panelAlterarDepartamento.setVisible(false);
		layeredAlterar.add(panelAlterarDepartamento, "name_185067340967200");

        JLabel lblMensagemAlterarDepartamentos = new JLabel();
        lblMensagemAlterarDepartamentos.setBackground(UIManager.getColor("Button.background"));
		lblMensagemAlterarDepartamentos.setText("Preencha os campos que deseja alterar:");
		lblMensagemAlterarDepartamentos.setBounds(248, 28, 266, 20);
		panelAlterarDepartamento.add(lblMensagemAlterarDepartamentos);

        JLabel lblAlterarNomeDepartamento = new JLabel("Nome:");
		lblAlterarNomeDepartamento.setBounds(84, 76, 80, 22);
		panelAlterarDepartamento.add(lblAlterarNomeDepartamento);

        textAlterarNomeDepartamento.setColumns(10);
		textAlterarNomeDepartamento.setBounds(248, 76, 374, 22);
		panelAlterarDepartamento.add(textAlterarNomeDepartamento);

        JLabel lblAlterarEscritorioDepartamento = new JLabel("Escritorio:");
        lblAlterarEscritorioDepartamento.setBounds(84, 126, 60, 22);
		panelAlterarDepartamento.add(lblAlterarEscritorioDepartamento);

		textAlterarEscritorioDepartamento.setColumns(10);
		textAlterarEscritorioDepartamento.setBounds(248, 126, 374, 22);
		panelAlterarDepartamento.add(textAlterarEscritorioDepartamento);

        JLabel lblAlterarCodProfessorLider = new JLabel("Codigo do professor lider:");
        lblAlterarCodProfessorLider.setBounds(84, 176, 160, 22);
        panelAlterarDepartamento.add(lblAlterarCodProfessorLider);

        textAlterarCodProfessorLider.setColumns(10);
        textAlterarCodProfessorLider.setBounds(248, 176, 374, 22);
        panelAlterarDepartamento.add(textAlterarCodProfessorLider);

        JButton btnAlterarDepartamento = new JButton("Alterar Departamento");
        btnAlterarDepartamento.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                alterarDepartamento();
                panelAlterarDepartamento.setVisible(false);
				panelAlterar.setVisible(true);
            }
        });
        btnAlterarDepartamento.setBounds(248, 240, 160, 23);
        panelAlterarDepartamento.add(btnAlterarDepartamento);
    }

    public void alterarDepartamento() {
		
		try {
			Departamento departamento = sistema.getDepartamento(Integer.parseInt(textCodigoDigitadoDepartamento.getText()));
			
			if(textAlterarNomeDepartamento.getText().equalsIgnoreCase("") == false && departamento.getNome().equalsIgnoreCase(textAlterarNomeDepartamento.getText()) == false)
                departamento.setNome(textAlterarNomeDepartamento.getText());
			if(textAlterarEscritorioDepartamento.getText().equalsIgnoreCase("") == false && departamento.getEscritorio() != Integer.parseInt(textAlterarEscritorioDepartamento.getText()))
                departamento.setEscritorio(Integer.parseInt(textAlterarEscritorioDepartamento.getText()));		
			if(textAlterarCodProfessorLider.getText().equalsIgnoreCase("") == false && departamento.getCodDepartamento() != Long.parseLong(textAlterarCodProfessorLider.getText()))
                departamento.setCodProfessorLider(Integer.parseInt(textAlterarCodProfessorLider.getText()));
		
			sistema.alterarDepartamento(departamento);
			limparAlterarDepartamento();
			JOptionPane.showMessageDialog(null, "Departamento alterado!");	
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getMessage());
			System.err.println("Erro, departamento nao encontrado!");
			JOptionPane.showMessageDialog(null, "Erro, departamento nao encontrado!");
		} catch (UpdateException e2) {
			System.err.println(e2.getMessage());
			System.err.println("Erro ao atualizar departamento!");
			JOptionPane.showMessageDialog(null, "Erro ao atualizar departamento!");
		} catch (SQLException e3) {
			System.err.println(e3.getMessage());
			System.err.println("Erro ao acessar o banco de dados!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados!");
		} catch (NumberFormatException e4) {
			System.err.println(e4.getMessage());
			System.err.println("Erro no codigo do departamento informado ou no formato dos dados inseridos!");
			JOptionPane.showMessageDialog(null, "Erro no codigo do departamento informado ou no formato dos dados inseridos!");
		} catch (SelectException e5) {
			System.err.println(e5.getMessage());
			System.err.println("Erro ao acessar departamento!");
			JOptionPane.showMessageDialog(null, "Erro ao acessar departamento!");
		}
	}

    public void limparAlterarDepartamento() {
		textCodigoDigitadoDepartamento.setText("");
		textAlterarNomeDepartamento.setText("");
		textAlterarEscritorioDepartamento.setText("");
		textAlterarCodProfessorLider.setText("");
	}
}
