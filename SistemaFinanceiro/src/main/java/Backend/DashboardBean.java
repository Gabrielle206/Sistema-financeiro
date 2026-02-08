package Backend;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.YearMonth;

@Named
@RequestScoped

public class DashboardBean implements Serializable {

    private String usuarioId;
    private int mes;
    private int ano;

    private double saldo;
    private double evolucaoPercentual;
    private boolean metaAtingida;

    private AnaliseFinanceiraService analiseService = new AnaliseFinanceiraService();

    public DashboardBean() {
        YearMonth agora = YearMonth.now();
        this.mes = agora.getMonthValue();
        this.ano = agora.getYear();
    }

    public void carregarDashboard(LoginBean loginBean) {
        this.usuarioId = loginBean.getUsuarioLogado().getId();

        saldo = analiseService.calcularSaldo(usuarioId, mes, ano);
        evolucaoPercentual = analiseService.calcularEvolucaoPercentual(usuarioId, mes, ano);
        metaAtingida = analiseService.metaAtingida(usuarioId, mes, ano);
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getEvolucaoPercentual() {
        return evolucaoPercentual;
    }

    public boolean isMetaAtingida() {
        return metaAtingida;
    }
}
