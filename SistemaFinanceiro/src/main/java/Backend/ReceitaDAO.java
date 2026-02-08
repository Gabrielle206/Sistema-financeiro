package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReceitaDAO {

	public boolean salvar (Receita r) {
		
		String sql = "INSERT INTO Receitas (id, valor, data, usuario_id) VALUES (?, ?, ?, ?)";
		
		try(Connection conn = DatabaseManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, r.getId());
			stmt.setDouble(2, r.getValor());
			stmt.setDate(3, java.sql.Date.valueOf(r.getData()));
			stmt.setString(4, r.getUsuarioId());
			
			stmt.executeQuery();
			return true;
		}
		
			catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}
	
	public List<Receita> listaMes (String usuarioId, int mes, int ano) {
		
		List<Receita> listaMes = new ArrayList<>();
		
		String sql = "SELECT * FROM Receitas WHERE usuario_id = ? AND strftime ('%m', data) = ? AND strftime ('%Y', data) = ?";
		
		try(Connection conn = DatabaseManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, usuarioId);
			stmt.setString(2, String.format("%02d", mes));
			stmt.setString(3, String.valueOf(ano));
			
			ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Receita r = new Receita();
	            r.setId(rs.getString("id"));
	            r.setValor(rs.getDouble("valor"));
	            r.setData(rs.getDate("data").toLocalDate());
	            r.setUsuarioId(rs.getString("usuario_id"));

	            listaMes.add(r);
	        }
	        
		} 
			catch (Exception e) {
				e.printStackTrace();
			}

		return listaMes;

	}

	public double totalMensal (String usuarioId, int mes, int ano) {
		
		String sql = "SELECT SUM(valor) FROM Receitas WHERE usuario_id = ? AND strftime ('%m', data) = ? AND strftime ('%Y', data) = ?";
		
		try(Connection conn = DatabaseManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, usuarioId);
			stmt.setString(2, String.format("%02d", mes));
			stmt.setString(3, String.valueOf(ano));
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return rs.getDouble("total");
			}
			
		}
		
			catch (Exception e) {
				e.printStackTrace();
			}
		
		return 0.0;
	}
	
}
	
