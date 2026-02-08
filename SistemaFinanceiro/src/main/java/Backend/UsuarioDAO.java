package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
	
	public Usuario buscarPorId (String id) {
		
		String sql = "SELECT * FROM Usuarios WHERE id = ?";	
		
		try(Connection conn = DatabaseManager.getConnection(); 
			PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, id);
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
            
		 } 
			catch (Exception e) {
	            e.printStackTrace();
	        }

	        return null;
	    }
		
		public void atualizar (Usuario usuario) {
			
			String sql = "UPDATE Usuarios SET email = ?, nome_completo = ?, ocupacao = ?, renda_mensal = ?";
			
			try(Connection conn = DatabaseManager.getConnection(); 
				PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				stmt.setString(1, usuario.getEmail());
				stmt.setString(2, usuario.getNomeCompleto());
				stmt.setString(3, usuario.getOcupacao());
				stmt.setDouble(4, usuario.getRendaMensal());
				
				stmt.executeUpdate();
				
			 } 
				catch (Exception e) {
		            e.printStackTrace();
		        }
				
			}
		
		public void alterarSenha (String usuarioId, String novaSenha) {
			
			String sql = "UPDATE Usuarios SET senha = ? WHERE id = ?";
			
			try(Connection conn = DatabaseManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				stmt.setString(1, novaSenha);
				stmt.setString(2, usuarioId);
				
				stmt.executeUpdate();
			}
			
			 	catch (Exception e) {
		            e.printStackTrace();
		        }
			
		}
		
		public List<Usuario> listarTodos() {
			
			List<Usuario> usuarios = new ArrayList<>();
			String sql = "SELECT * FROM Usuarios";
			
			try(Connection conn = DatabaseManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
				
				while (rs.next()) {
	                Usuario u = new Usuario();
	                u.setId(rs.getString("id"));
	                u.setEmail(rs.getString("email"));
	                u.setNomeCompleto(rs.getString("nome_completo"));
	                u.setOcupacao(rs.getString("ocupacao"));
	                u.setRendaMensal(rs.getDouble("renda_mensal"));
	                u.setPrimeiroLogin(rs.getBoolean("primeiro_login"));
	                usuarios.add(u);
	            }
			}
				catch (Exception e) {
					e.printStackTrace();
				}
			
			return usuarios;
		}

		public void salvar(Usuario usuario) {
			// TODO Auto-generated method stub
			
		}
}
	