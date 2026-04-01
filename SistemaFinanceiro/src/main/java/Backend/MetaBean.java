package Backend;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.List;

@Named
@RequestScoped
public class MetaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private LoginBean loginBean;

    private MetaDAO metaDAO = new MetaDAO();

    private Meta meta = new Meta();

    public String salvar() {
        meta.setUsuarioId(loginBean.getUsuarioLogado().getId());
        meta.setMesReferencia(YearMonth.now());
        metaDAO.salvar(meta);
        meta = new Meta(); // Reset for next entry
        return "metas?faces-redirect=true";
    }

    public List<Meta> getListaMetas() {
        if (loginBean == null || loginBean.getUsuarioLogado() == null) return null;
        return metaDAO.listarTodas(loginBean.getUsuarioLogado().getId());
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
