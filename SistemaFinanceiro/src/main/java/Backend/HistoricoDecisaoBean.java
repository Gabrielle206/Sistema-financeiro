package Backend;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Named
@RequestScoped
public class HistoricoDecisaoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private LoginBean loginBean;

    private HistoricoDecisaoDAO historicoDAO = new HistoricoDecisaoDAO();

    public List<HistoricoDecisao> getListaHistorico() {
        if (loginBean == null || loginBean.getUsuarioLogado() == null) return null;
        return historicoDAO.listar(loginBean.getUsuarioLogado().getId());
    }

    public List<HistoricoDecisao> getHistorico() {
        return getListaHistorico();
    }

    public List<HistoricoDecisao> getHistoricoPeriodo(LocalDate inicio, LocalDate fim) {
        if (loginBean == null || loginBean.getUsuarioLogado() == null) return null;
        return historicoDAO.listarPeriodo(loginBean.getUsuarioLogado().getId(), inicio, fim);
    }
}
