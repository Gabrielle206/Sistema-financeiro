package Backend;

import java.time.LocalDate;

public class Despesa {
	
	private int id;
	private int usuarioId;
	private int categoriaId;
	
	private String descricao;
	private double valor;
	
	private LocalDate data;
	private boolean recorrente;
	private boolean apenasNotificar;
	
	private boolean ativa;
    private String categoriaNome; // Campo para exibição
	
	public Despesa() {
		this.ativa = true;
	}
	
	public Despesa (int id, int usuarioId, int categoriaId, String descricao, double valor, LocalDate data) {
		this.id = id;
		this.usuarioId = usuarioId;
		this.categoriaId = categoriaId;
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
		this.ativa = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(int categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public boolean isRecorrente() {
		return recorrente;
	}

	public void setRecorrente(boolean recorrente) {
		this.recorrente = recorrente;
	}

	public boolean isApenasNotificar() {
		return apenasNotificar;
	}

	public void setApenasNotificar(boolean apenasNotificar) {
		this.apenasNotificar = apenasNotificar;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }
}
