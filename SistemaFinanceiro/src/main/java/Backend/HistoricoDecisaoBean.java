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

    @Inject
    
    private LoginBean loginBean;

    private HistoricoDecisaoDAO historicoDAO = new HistoricoDecisaoDAO();

    public List<HistoricoDecisao> getHistorico() {
        return historicoDAO.listar(
            loginBean.getUsuarioLogado().getId()
        );
    }

    public List<HistoricoDecisao> getHistoricoPeriodo(LocalDate inicio, LocalDate fim) {
        return historicoDAO.listarPeriodo(
            loginBean.getUsuarioLogado().getId(),
            inicio,
            fim
        );
    }
}
