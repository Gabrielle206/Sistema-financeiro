package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReceitaDAO {

    public boolean salvar(Receita r) {
        String sql = "INSERT INTO Receitas (descricao, valor, data, usuario_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, r.getDescricao());
            stmt.setDouble(2, r.getValor());
            stmt.setDate(3, java.sql.Date.valueOf(r.getData()));
            stmt.setInt(4, r.getUsuarioId());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Receita> listaMes(int usuarioId, int mes, int ano) {
        List<Receita> listaMes = new ArrayList<>();
        String sql = "SELECT * FROM Receitas WHERE usuario_id = ? AND strftime('%m', data) = ? AND strftime('%Y', data) = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setString(2, String.format("%02d", mes));
            stmt.setString(3, String.valueOf(ano));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Receita r = new Receita();
                r.setId(rs.getInt("id"));
                r.setDescricao(rs.getString("descricao"));
                r.setValor(rs.getDouble("valor"));
                r.setData(rs.getDate("data").toLocalDate());
                r.setUsuarioId(rs.getInt("usuario_id"));
                listaMes.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaMes;
    }

    public double totalMensal(int usuarioId, int mes, int ano) {
        String sql = "SELECT SUM(valor) AS total FROM Receitas WHERE usuario_id = ? AND strftime('%m', data) = ? AND strftime('%Y', data) = ?";
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

    public void excluir(int id) {
        String sql = "DELETE FROM Receitas WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
