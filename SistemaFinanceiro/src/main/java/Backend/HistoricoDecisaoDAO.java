package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoricoDecisaoDAO {

    public void registrar(HistoricoDecisao h) {

        String sql = "INSERT INTO HistoricoDecisoes (id, usuario_id, descricao, data_hora) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, h.getId());
            stmt.setString(2, h.getUsuarioId());
            stmt.setString(3, h.getDescricao());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(h.getData()));

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<HistoricoDecisao> listar(String usuarioId) {

        List<HistoricoDecisao> lista = new ArrayList<>();

        String sql = "SELECT * FROM HistoricoDecisoes "
                   + "WHERE usuario_id = ? "
                   + "ORDER BY data_hora DESC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HistoricoDecisao h = new HistoricoDecisao();
                h.setId(rs.getString("id"));
                h.setUsuarioId(rs.getString("usuario_id"));
                h.setDescricao(rs.getString("descricao"));
                h.setData(rs.getTimestamp("data_hora").toLocalDateTime());

                lista.add(h);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<HistoricoDecisao> listarPeriodo(
            String usuarioId,
            LocalDate dataInicio,
            LocalDate dataFim) {

        List<HistoricoDecisao> lista = new ArrayList<>();

        String sql = "SELECT * FROM HistoricoDecisoes "
                   + "WHERE usuario_id = ? "
                   + "AND date(data_hora) BETWEEN ? AND ? "
                   + "ORDER BY data_hora DESC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuarioId);
            stmt.setDate(2, java.sql.Date.valueOf(dataInicio));
            stmt.setDate(3, java.sql.Date.valueOf(dataFim));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HistoricoDecisao h = new HistoricoDecisao();
                h.setId(rs.getString("id"));
                h.setUsuarioId(rs.getString("usuario_id"));
                h.setDescricao(rs.getString("descricao"));
                h.setData(rs.getTimestamp("data_hora").toLocalDateTime());

                lista.add(h);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}