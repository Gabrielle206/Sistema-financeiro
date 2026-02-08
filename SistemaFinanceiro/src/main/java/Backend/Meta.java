package Backend;

import java.time.YearMonth;

public class Meta {

    private String id;
    private String usuarioId;

    private YearMonth mesReferencia;
    private double valorMeta;

    private boolean ativa;

    public Meta() {
        this.ativa = true;
    }

    public Meta(String id, String usuarioId, YearMonth mesReferencia, double valorMeta) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.mesReferencia = mesReferencia;
        this.valorMeta = valorMeta;
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

    public YearMonth getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(YearMonth mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public double getValorMeta() {
        return valorMeta;
    }

    public void setValorMeta(double valorMeta) {
        this.valorMeta = valorMeta;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}