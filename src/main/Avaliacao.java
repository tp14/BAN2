package main;

public class Avaliacao {
    public int idAvaliacao;
    private double nota;
    private String comentario;

    public Avaliacao(int idAvaliacao, double nota, String comentario){
        this.idAvaliacao = idAvaliacao;
        this.nota = nota;
        this.comentario = comentario;
    }

	public int getIdAvaliacao() {
		return idAvaliacao;
	}

	public void setIdAvaliacao(int idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public void mudarComentario(String novoComentario){
		this.comentario = novoComentario;
	}  
}
