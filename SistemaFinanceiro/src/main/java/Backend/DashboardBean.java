package Backend;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.YearMonth;

@Named
@RequestScoped
public class DashboardBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private LoginBean loginBean;

    private ReceitaDAO receitaDAO = new ReceitaDAO();
    private DespesaDAO despesaDAO = new DespesaDAO();
    private MetaDAO metaDAO = new MetaDAO();
    private AnaliseFinanceiraService analiseService = new AnaliseFinanceiraService();

    public double getSaldoTotal() {
        if (loginBean == null || loginBean.getUsuarioLogado() == null) return 0.0;
        int id = loginBean.getUsuarioLogado().getId();
        YearMonth ym = YearMonth.now();
        return analiseService.calcularSaldo(id, ym.getMonthValue(), ym.getYear());
    }

    public double getReceitasTotal() {
        if (loginBean == null || loginBean.getUsuarioLogado() == null) return 0.0;
        int id = loginBean.getUsuarioLogado().getId();
        YearMonth ym = YearMonth.now();
        return receitaDAO.totalMensal(id, ym.getMonthValue(), ym.getYear());
    }

    public double getDespesasTotal() {
        if (loginBean == null || loginBean.getUsuarioLogado() == null) return 0.0;
        int id = loginBean.getUsuarioLogado().getId();
        YearMonth ym = YearMonth.now();
        return despesaDAO.totalMensal(id, ym.getMonthValue(), ym.getYear());
    }

    public double getEvolucaoPercentual() {
        if (loginBean == null || loginBean.getUsuarioLogado() == null) return 0.0;
        int id = loginBean.getUsuarioLogado().getId();
        YearMonth ym = YearMonth.now();
        return analiseService.calcularEvolucaoPercentual(id, ym.getMonthValue(), ym.getYear());
    }

    public boolean isMetaAtingida() {
        if (loginBean == null || loginBean.getUsuarioLogado() == null) return false;
        int id = loginBean.getUsuarioLogado().getId();
        YearMonth ym = YearMonth.now();
        return analiseService.metaAtingida(id, ym.getMonthValue(), ym.getYear());
    }
}
