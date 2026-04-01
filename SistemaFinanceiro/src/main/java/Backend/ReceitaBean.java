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
public class ReceitaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private LoginBean loginBean;

    private ReceitaDAO receitaDAO = new ReceitaDAO();

    private Receita receita = new Receita();

    public String salvar() {
        if (receita.getData() == null) {
            receita.setData(LocalDate.now());
        }
        receita.setUsuarioId(loginBean.getUsuarioLogado().getId());
        receitaDAO.salvar(receita);
        receita = new Receita(); // Limpa para o próximo
        return "receitas?faces-redirect=true";
    }

    public void excluir(Receita r) {
        receitaDAO.excluir(r.getId());
    }

    public List<Receita> getListaReceitas() {
        YearMonth ym = YearMonth.now();
        return receitaDAO.listaMes(
            loginBean.getUsuarioLogado().getId(),
            ym.getMonthValue(),
            ym.getYear()
        );
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }
}
