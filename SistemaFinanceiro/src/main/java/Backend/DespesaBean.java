package Backend;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Named
@SessionScoped
public class DespesaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private LoginBean loginBean;

    private DespesaDAO despesaDAO = new DespesaDAO();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    private Despesa despesa = new Despesa();

    public String salvar() {
        if (despesa.getData() == null) {
            despesa.setData(LocalDate.now());
        }
        despesa.setUsuarioId(loginBean.getUsuarioLogado().getId());
        despesaDAO.salvar(despesa);
        despesa = new Despesa();
        return "despesas?faces-redirect=true";
    }

    public void excluir(Despesa d) {
        despesaDAO.excluir(d.getId());
    }

    public List<Despesa> getListaDespesas() {
        if (loginBean == null || loginBean.getUsuarioLogado() == null) return null;
        YearMonth ym = YearMonth.now();
        return despesaDAO.listarMes(
            loginBean.getUsuarioLogado().getId(),
            ym.getMonthValue(),
            ym.getYear()
        );
    }

    public List<Categoria> getCategorias() {
        if (loginBean == null || loginBean.getUsuarioLogado() == null) return null;
        return categoriaDAO.listar(loginBean.getUsuarioLogado().getId());
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }
}
