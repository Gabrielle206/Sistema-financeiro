package Backend;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.YearMonth;

@Named
@RequestScoped

public class MetaBean implements Serializable {

    @Inject
    
    private LoginBean loginBean;

    private Meta meta = new Meta();
    private MetaDAO metaDAO = new MetaDAO();

    public String salvar() {
        meta.setUsuarioId(loginBean.getUsuarioLogado().getId());
        meta.setMesReferencia(YearMonth.now());
        metaDAO.salvar(meta);
        return "dashboard?faces-redirect=true";
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
