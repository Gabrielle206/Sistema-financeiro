package Backend;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.List;

@Named
@RequestScoped

public class ReceitaBean implements Serializable {

    @Inject
    
    private LoginBean loginBean;

    private Receita receita = new Receita();
    private ReceitaDAO receitaDAO = new ReceitaDAO();

    public String salvar() {
        receita.setUsuarioId(loginBean.getUsuarioLogado().getId());
        receitaDAO.salvar(receita);
        return "receitas?faces-redirect=true";
    }

    public List<Receita> getReceitasMes() {
        return receitaDAO.listaMes(loginBean.getUsuarioLogado().getId(), YearMonth.now());
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }
}
