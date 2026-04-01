package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DespesaDAO {

    public boolean salvar(Despesa d) {
        String sql = "INSERT INTO Despesas (descricao, valor, data, usuario_id, categoria_id, recorrente) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getDescricao());
            stmt.setDouble(2, d.getValor());
            stmt.setDate(3, java.sql.Date.valueOf(d.getData()));
            stmt.setInt(4, d.getUsuarioId());
            stmt.setInt(5, d.getCategoriaId());
            stmt.setBoolean(6, d.isRecorrente());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Despesa> listarMes(int usuarioId, int mes, int ano) {
        List<Despesa> listaMes = new ArrayList<>();
        String sql = "SELECT d.*, c.nome as categoria_nome FROM Despesas d JOIN Categorias c ON d.categoria_id = c.id WHERE d.usuario_id = ? AND strftime('%m', d.data) = ? AND strftime('%Y', d.data) = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setString(2, String.format("%02d", mes));
            stmt.setString(3, String.valueOf(ano));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Despesa despesa = new Despesa();
                despesa.setId(rs.getInt("id"));
                despesa.setDescricao(rs.getString("descricao"));
                despesa.setValor(rs.getDouble("valor"));
                despesa.setData(rs.getDate("data").toLocalDate());
                despesa.setUsuarioId(rs.getInt("usuario_id"));
                despesa.setCategoriaId(rs.getInt("categoria_id"));
                despesa.setRecorrente(rs.getBoolean("recorrente"));
                // Categoria é um objeto, vamos apenas setar o nome por enquanto
                // Para uma solução completa, precisaríamos de um objeto Categoria em Despesa
                // e um CategoriaDAO para buscar a categoria completa.
                // Por simplicidade, vamos adicionar um campo transiente em Despesa.
                despesa.setCategoriaNome(rs.getString("categoria_nome"));
                listaMes.add(despesa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaMes;
    }

    public double totalMensal(int usuarioId, int mes, int ano) {
        String sql = "SELECT SUM(valor) AS total FROM Despesas WHERE usuario_id = ? AND strftime('%m', data) = ? AND strftime('%Y', data) = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setString(2, String.format("%02d", mes));
            stmt.setString(3, String.valueOf(ano));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public boolean excluir(int despesaId) {
        String sql = "DELETE FROM Despesas WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, despesaId);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
