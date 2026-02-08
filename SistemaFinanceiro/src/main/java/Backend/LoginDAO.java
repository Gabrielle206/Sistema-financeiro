package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

    public boolean emailExiste(String email) {
        String sql = "SELECT 1 FROM Usuarios WHERE email = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); 

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cadastrar(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (id, email, nome_completo, ocupacao, renda_mensal, senha, primeiro_login) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getId());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getNomeCompleto());
            stmt.setString(4, usuario.getOcupacao());
            stmt.setDouble(5, usuario.getRendaMensal());
            stmt.setString(6, usuario.getSenha());
            stmt.setBoolean(7, usuario.isPrimeiroLogin());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario autenticar(String email, String senha) {
        String sql = "SELECT * FROM Usuarios WHERE email = ? AND senha = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getString("id"));
                u.setEmail(rs.getString("email"));
                u.setNomeCompleto(rs.getString("nome_completo"));
                u.setOcupacao(rs.getString("ocupacao"));
                u.setRendaMensal(rs.getDouble("renda_mensal"));
                u.setSenha(rs.getString("senha"));
                u.setPrimeiroLogin(rs.getBoolean("primeiro_login"));

                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void atualizarPrimeiroLogin(String usuarioId) {
        String sql = "UPDATE Usuarios SET primeiro_login = 0 WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuarioId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}