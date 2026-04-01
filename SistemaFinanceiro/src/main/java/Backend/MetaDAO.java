package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class MetaDAO {

    public boolean salvar(Meta m) {
        String sql = "INSERT INTO Metas (usuario_id, mes, ano, valor_meta, ativa) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, m.getUsuarioId());
            stmt.setInt(2, m.getMesReferencia().getMonthValue());
            stmt.setInt(3, m.getMesReferencia().getYear());
            stmt.setDouble(4, m.getValorMeta());
            stmt.setBoolean(5, m.isAtiva());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Meta buscarMetaMes(int usuarioId, int mes, int ano) {
        String sql = "SELECT * FROM Metas WHERE usuario_id = ? AND mes = ? AND ano = ? AND ativa = 1 LIMIT 1";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, mes);
            stmt.setInt(3, ano);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Meta meta = new Meta();
                meta.setId(rs.getInt("id"));
                meta.setUsuarioId(rs.getInt("usuario_id"));
                meta.setMesReferencia(YearMonth.of(rs.getInt("ano"), rs.getInt("mes")));
                meta.setValorMeta(rs.getDouble("valor_meta"));
                meta.setAtiva(rs.getBoolean("ativa"));
                return meta;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Meta> listarTodas(int usuarioId) {
        List<Meta> lista = new ArrayList<>();
        String sql = "SELECT * FROM Metas WHERE usuario_id = ? ORDER BY ano DESC, mes DESC";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Meta meta = new Meta();
                meta.setId(rs.getInt("id"));
                meta.setUsuarioId(rs.getInt("usuario_id"));
                meta.setMesReferencia(YearMonth.of(rs.getInt("ano"), rs.getInt("mes")));
                meta.setValorMeta(rs.getDouble("valor_meta"));
                meta.setAtiva(rs.getBoolean("ativa"));
                lista.add(meta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
