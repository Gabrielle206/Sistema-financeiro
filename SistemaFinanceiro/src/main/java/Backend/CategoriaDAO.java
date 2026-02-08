package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public boolean salvar(Categoria c) {
        String sql = "INSERT INTO Categorias (id, nome, padrao, usuario_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getId());
            stmt.setString(2, c.getNome());
            stmt.setBoolean(3, c.isPadrao());
            stmt.setString(4, c.getUsuarioId());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Categoria> listarTodas(String usuarioId) {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM Categorias WHERE padrao = 1 OR usuario_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getString("id"));
                c.setNome(rs.getString("nome"));
                c.setPadrao(rs.getBoolean("padrao"));
                c.setUsuarioId(rs.getString("usuario_id"));
                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean atualizar(Categoria c) {
        String sql = "UPDATE Categorias SET nome = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getId());
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(String id) {
        String sql = "DELETE FROM Categorias WHERE id = ? AND padrao = 0";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existe(String nome, String usuarioId) {
        String sql = "SELECT 1 FROM Categorias WHERE nome = ? AND (usuario_id = ? OR padrao = 1)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, usuarioId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}