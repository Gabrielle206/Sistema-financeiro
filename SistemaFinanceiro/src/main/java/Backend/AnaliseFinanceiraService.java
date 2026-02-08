package Backend;

import java.time.YearMonth;

public class AnaliseFinanceiraService {

    private ReceitaDAO receitaDAO = new ReceitaDAO();
    private DespesaDAO despesaDAO = new DespesaDAO();
    private MetaDAO metaDAO = new MetaDAO();

    public double calcularSaldo(String usuarioId, int mes, int ano) {
        double totalReceitas = receitaDAO.totalMensal(usuarioId, mes, ano);
        double totalDespesas = despesaDAO.totalMensal(usuarioId, mes, ano);
        return totalReceitas - totalDespesas;
    }

    public double calcularSaldoMesAnterior(String usuarioId, int mes, int ano) {
        YearMonth atual = YearMonth.of(ano, mes);
        YearMonth anterior = atual.minusMonths(1);

        return calcularSaldo(usuarioId, anterior.getMonthValue(), anterior.getYear());
    }

    public double calcularEvolucaoPercentual(String usuarioId, int mes, int ano) {
        double saldoAtual = calcularSaldo(usuarioId, mes, ano);
        double saldoAnterior = calcularSaldoMesAnterior(usuarioId, mes, ano);

        if (saldoAnterior == 0) {
            return 0;
        }

        return ((saldoAtual - saldoAnterior) / Math.abs(saldoAnterior)) * 100;
    }
    
    public String gerarInsightSaldo(String usuarioId, int mes, int ano) {
        double percentual = calcularEvolucaoPercentual(usuarioId, mes, ano);

        if (percentual > 0) {
            return "Seu saldo aumentou " + String.format("%.1f", percentual) + "% em relação ao mês anterior.";
        } else if (percentual < 0) {
            return "Seu saldo caiu " + String.format("%.1f", Math.abs(percentual)) + "% em relação ao mês anterior.";
        } else {
            return "Seu saldo permaneceu igual ao mês anterior.";
        }
    }

    public boolean metaAtingida(String usuarioId, int mes, int ano) {
        Meta meta = MetaDAO.buscarMetaDoMes(usuarioId, YearMonth.of(ano, mes));

        if (meta == null) {
            return false;
        }

        double saldo = calcularSaldo(usuarioId, mes, ano);
        return saldo >= meta.getValorMeta();
    }
}
