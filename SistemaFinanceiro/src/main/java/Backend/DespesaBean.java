package Backend;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.List;

@Named
@RequestScoped

public class DespesaBean implements Serializable {

    @Inject
    
    private LoginBean loginBean;

    private Despesa despesa = new Despesa();
    
    @Inject
    private DespesaDAO despesaDAO;

    public String salvar() {
        despesa.setUsuarioId(loginBean.getUsuarioLogado().getId());
        despesaDAO.salvar(despesa);
        return "despesas?faces-redirect=true";
    }

    public void excluir(int id) {
        despesaDAO.excluir(id);
    }

    public List<Despesa> getDespesasMes() {
    	YearMonth ym = YearMonth.now();
    	
        return despesaDAO.listarMes(loginBean.getUsuarioLogado().getId(),  ym.getMonthValue(), ym.getYear());
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }
}
