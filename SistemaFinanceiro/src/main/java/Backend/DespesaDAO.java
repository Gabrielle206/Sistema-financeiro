package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DespesaDAO {

	public boolean salvar (Despesa d) {
		
		String sql = "INSERT INTO Despesas (id, valor, data, categoria_id, usuario_id, recorrente) VALUES (?, ?, ?, ?, ?, ?)";
		
		try(Connection conn = DatabaseManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, d.getId());
			stmt.setDouble(2, d.getValor());
			stmt.setDate(3, java.sql.Date.valueOf(d.getData()));
			stmt.setString(4, d.getCategoriaId());
			stmt.setString(5, d.getUsuarioId());
			stmt.setBoolean(6, d.isRecorrente());

			stmt.executeUpdate();
			return true;
		}
			catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}

	public List<Despesa> listarMes (String usuarioId, int mes, int ano) {
		
		List<Despesa> listaMes = new ArrayList<>();
		
		String sql = "SELECT * FROM Despesas WHERE usuario_id = ? AND strftime ('%m', data) = ? AND strftime ('%Y', data) = ?";
		
		try(Connection conn = DatabaseManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, usuarioId);
			stmt.setString(2, String.format("%02d", mes));
			stmt.setString(3, String.valueOf(ano));
			
			ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Despesa d = new Despesa();
	            d.setId(rs.getString("id"));
	            d.setValor(rs.getDouble("valor"));
	            d.setData(rs.getDate("data").toLocalDate());
	            d.setCategoriaId(rs.getString("categoria_id"));
	            d.setUsuarioId(rs.getString("usuario_id"));
	            d.setRecorrente(rs.getBoolean("recorrente"));

	            listaMes.add(d);
	        }
	        
		} 
			catch (Exception e) {
				e.printStackTrace();
			}

		return listaMes;
	}
	
	public double totalMensal (String usuarioId, int mes, int ano) {
		
		String sql = "SELECT SUM(valor) AS total FROM Despesas WHERE usuario_id = ? AND strftime ('%m', data) = ? AND strftime ('%Y', data) = ?";
		
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
	
	public List<Despesa> listarCategoria (String categoriaId) {
		
		List<Despesa> listaCategoria = new ArrayList<>();
		
		String sql = "SELECT * FROM Despesas WHERE categoria_id = ?";
		
		try(Connection conn = DatabaseManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, categoriaId);
			
			ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Despesa d = new Despesa();
                d.setId(rs.getString("id"));
                d.setValor(rs.getDouble("valor"));
                d.setData(rs.getDate("data").toLocalDate());
                d.setCategoriaId(rs.getString("categoria_id"));
                d.setUsuarioId(rs.getString("usuario_id"));
                d.setRecorrente(rs.getBoolean("recorrente"));

                listaCategoria.add(d);
            }

		}
		
			catch (Exception e) {
				e.printStackTrace();
			}
	
		return listaCategoria;
	}
	
	public List<Despesa> listarRecorrentes(String usuarioId) {
		
	    List<Despesa> lista = new ArrayList<>();

	    String sql = "SELECT * FROM Despesas WHERE usuario_id = ? AND recorrente = 1";

	    try (Connection conn = DatabaseManager.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, usuarioId);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Despesa d = new Despesa();
	            d.setId(rs.getString("id"));
	            d.setValor(rs.getDouble("valor"));
	            d.setData(rs.getDate("data").toLocalDate());
	            d.setCategoriaId(rs.getString("categoria_id"));
	            d.setUsuarioId(rs.getString("usuario_id"));
	            d.setRecorrente(true);

	            lista.add(d);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return lista;
	}

	public void gerarRecorrentesProximoMes(String usuarioId, int mes, int ano) {
		
	    List<Despesa> recorrentes = listarRecorrentes(usuarioId);

	    for (Despesa d : recorrentes) {
	        Despesa nova = new Despesa();
	        nova.setId(UUID.randomUUID().toString());
	        nova.setUsuarioId(usuarioId);
	        nova.setCategoriaId(d.getCategoriaId());
	        nova.setDescricao(d.getDescricao());
	        nova.setValor(d.getValor());
	        nova.setData(LocalDate.of(ano, mes, d.getData().getDayOfMonth()));
	        nova.setRecorrente(true);

	        salvar(nova);
	    }
	}
	
	public boolean cancelarRecorrencia (String despesaId) {
		
		String sql = "UPDATE Despesas SET recorrente = 0 WHERE id = ?";
		
		try (Connection conn = DatabaseManager.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setString(1, despesaId);
	            stmt.executeUpdate();
	            return true;

	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
		
	}

	public boolean arquivarDespesa(String despesaId) {
		
        String sql = "UPDATE Despesas SET ativa = 0 WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, despesaId);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	public boolean excluir(String despesaId) {
        String sql = "DELETE FROM Despesas WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, despesaId);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}