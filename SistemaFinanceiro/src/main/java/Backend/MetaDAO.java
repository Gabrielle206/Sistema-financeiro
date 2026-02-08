package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.YearMonth;

public class MetaDAO {

    public boolean salvar(Meta m) {

        String sql = "INSERT INTO Metas (id, usuario_id, mes, ano, valor_meta, ativa) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getId());
            stmt.setString(2, m.getUsuarioId());
            stmt.setInt(3, m.getMesReferencia().getMonthValue());
            stmt.setInt(4, m.getMesReferencia().getYear());
            stmt.setDouble(5, m.getValorMeta());
            stmt.setBoolean(6, m.isAtiva());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Meta buscarMetaDoMes(String usuarioId, YearMonth mes) {

        String sql = "SELECT * FROM Metas WHERE usuario_id = ? AND mes = ? AND ano = ? AND ativa = 1";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuarioId);
            stmt.setInt(2, mes.getMonthValue());
            stmt.setInt(3, mes.getYear());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Meta meta = new Meta();
                meta.setId(rs.getString("id"));
                meta.setUsuarioId(rs.getString("usuario_id"));
                meta.setMesReferencia(YearMonth.of(rs.getInt("ano"), rs.getInt("mes")));
                meta.setValorMeta(rs.getDouble("valor_meta"));
                meta.setAtiva(rs.getBoolean("ativa"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }

    public boolean desativarMeta(String metaId) {

        String sql = "UPDATE Metas SET ativa = 0 WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, metaId);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}