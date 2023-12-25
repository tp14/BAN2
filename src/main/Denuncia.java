package main;

import java.util.List;

public class Denuncia {
    public int id;
    public int idUsuario;
    private int idDenuncia;
    private String descricaoDenuncia;

    public Denuncia(int id, int idUsuario,int idDenuncia, String descricaoDenuncia){
        this.id = id;
        this.idUsuario = idUsuario;
        this.descricaoDenuncia = descricaoDenuncia;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdDenuncia() {
		return idDenuncia;
	}

	public void setIdDenuncia(int idDenuncia) {
		this.idDenuncia = idDenuncia;
	}

	public String getDescricaoDenuncia() {
		return descricaoDenuncia;
	}

	public void setDescricaoDenuncia(String descricaoDenuncia) {
		this.descricaoDenuncia = descricaoDenuncia;
	}

	public List<Aula> deletarAula(List<Aula> aulas, Aula aula){
		aulas.remove(aula);
		return aulas;
	}

	public List<Avaliacao> deletarAvaliacao(List<Avaliacao> avaliacoes, Avaliacao avaliacao){
		avaliacoes.remove(avaliacao);
		return avaliacoes;
	}

	public List<Usuario> bloquearUsuario(List<Usuario> usuariosBloqueados, Usuario usuario){
		usuariosBloqueados.remove(usuario);
		return usuariosBloqueados;
	}
}
