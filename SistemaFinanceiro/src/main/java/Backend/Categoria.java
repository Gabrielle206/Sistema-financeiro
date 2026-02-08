package Backend;

public class Categoria {
	
	private String id;
	private String nome;
	private boolean padrao; 
	private String usuarioId;
	
	public Categoria() {
	}
	
	// padrao do sistema
	public Categoria (String id, String nome) {
		this.id = id;
		this.nome = nome;
		this.padrao = true;
	}
	
	// criado pelo usuario
	public Categoria (String id, String nome, String usuarioId) {
		this.id = id;
		this.nome = nome;
		this.usuarioId = usuarioId;
		this.padrao = false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

}