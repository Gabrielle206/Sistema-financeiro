package Backend;

import java.time.LocalDateTime;

public class HistoricoDecisao {

	private String id;
	private String descricao;
	private String usuarioId;
	private LocalDateTime data;
	
	public HistoricoDecisao () {
	}
	
	public HistoricoDecisao (String id, String descricao, String usuarioId, LocalDateTime data) {
		
		this.id = id;
		this.descricao = descricao;
		this.usuarioId = usuarioId;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime localDateTime) {
		this.data = localDateTime;
	}
	
	
}