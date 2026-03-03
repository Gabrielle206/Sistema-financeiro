package Backend;

public class Categoria {
	
	private int id;
	private String nome;
	private boolean padrao; 
	private int usuarioId;
	
	public Categoria() {
	}
	
	// padrao do sistema
	public Categoria (int id, String nome) {
		this.id = id;
		this.nome = nome;
		this.padrao = true;
	}
	
	// criado pelo usuario
	public Categoria (int id, String nome, int usuarioId) {
		this.id = id;
		this.nome = nome;
		this.usuarioId = usuarioId;
		this.padrao = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isPadrao() {
		return padrao;
	}

	public void setPadrao(boolean padrao) {
		this.padrao = padrao;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}
