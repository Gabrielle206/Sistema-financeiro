package Backend;

import java.time.LocalDateTime;

public class HistoricoDecisao {

	private int id;
	private String descricao;
	private int usuarioId;
	private LocalDateTime data;
	
	public HistoricoDecisao () {
	}
	
	public HistoricoDecisao (int id, String descricao, int usuarioId, LocalDateTime data) {
		
		this.id = id;
		this.descricao = descricao;
		this.usuarioId = usuarioId;
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime localDateTime) {
		this.data = localDateTime;
	}
	
	
}
