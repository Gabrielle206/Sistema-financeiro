package Backend;

import java.time.LocalDate;

public class Receita {

    private String id;
    private String usuarioId;

    private String descricao;
    private double valor;

    private LocalDate data;

    private boolean recorrente;
    private boolean ativa;

    public Receita() {
        this.ativa = true;
    }

    public Receita(String id, String usuarioId, String descricao, double valor, LocalDate data) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.ativa = true;
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
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

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}