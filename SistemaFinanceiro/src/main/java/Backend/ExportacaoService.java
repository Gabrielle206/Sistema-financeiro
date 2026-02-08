package Backend;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.util.List;

public class ExportacaoService {

    private ReceitaDAO receitaDAO = new ReceitaDAO();
    private DespesaDAO despesaDAO = new DespesaDAO();

    public void exportarRelatorioMensal(String usuarioId, int mes, int ano) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response =
                    (HttpServletResponse) context.getExternalContext().getResponse();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                    "attachment;filename=relatorio-" + mes + "-" + ano + ".pdf");

            OutputStream out = response.getOutputStream();

            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            document.add(new Paragraph("Relatório Financeiro"));
            document.add(new Paragraph("Mês: " + mes + "/" + ano));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Receitas:"));
            List<Receita> receitas = receitaDAO.listaMes(usuarioId, mes, ano);
            for (Receita r : receitas) {
                document.add(new Paragraph(
                        r.getData() + " - " +
                        r.getDescricao() + " - R$ " +
                        r.getValor()
                ));
            }

            document.add(new Paragraph(" "));

            document.add(new Paragraph("Despesas:"));
            List<Despesa> despesas = despesaDAO.listarMes(usuarioId, mes, ano);
            for (Despesa d : despesas) {
                document.add(new Paragraph(
                        d.getData() + " - " +
                        d.getDescricao() + " - R$ " +
                        d.getValor()
                ));
            }

            document.add(new Paragraph(" "));

            double totalReceitas = receitaDAO.totalMensal(usuarioId, mes, ano);
            double totalDespesas = despesaDAO.totalMensal(usuarioId, mes, ano);
            double saldo = totalReceitas - totalDespesas;

            document.add(new Paragraph("Total receitas: R$ " + totalReceitas));
            document.add(new Paragraph("Total despesas: R$ " + totalDespesas));
            document.add(new Paragraph("Saldo: R$ " + saldo));

            document.close();
            out.close();

            context.responseComplete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
