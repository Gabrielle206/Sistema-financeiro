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
		
		String sql = "INSERT INTO Despesas (valor, data, categoria_id, usuario_id, recorrente, descricao) VALUES (?, ?, ?, ?, ?, ?)";
		
		try(Connection conn = DatabaseManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setDouble(1, d.getValor());
			stmt.setDate(2, java.sql.Date.valueOf(d.getData()));
			stmt.setInt(3, d.getCategoriaId());
			stmt.setInt(4, d.getUsuarioId());
			stmt.setBoolean(5, d.isRecorrente());
			stmt.setString(6, d.getDescricao());

			stmt.executeUpdate();
			return true;
		}
			catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}

	public List<Despesa> listarMes (int usuarioId, int mes, int ano) {
		
		List<Despesa> listaMes = new ArrayList<>();
		
		String sql = "SELECT * FROM Despesas WHERE usuario_id = ? AND strftime ('%m', data) = ? AND strftime ('%Y', data) = ?";
		
		try(Connection conn = DatabaseManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, usuarioId);
			stmt.setString(2, String.format("%02d", mes));
			stmt.setString(3, String.valueOf(ano));
			
			ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Despesa d = new Despesa();
	            d.setId(rs.getInt("id"));
	            d.setValor(rs.getDouble("valor"));
	            d.setData(rs.getDate("data").toLocalDate());
	            d.setCategoriaId(rs.getInt("categoria_id"));
	            d.setUsuarioId(rs.getInt("usuario_id"));
	            d.setRecorrente(rs.getBoolean("recorrente"));
	            d.setDescricao(rs.getString("descricao"));

	            listaMes.add(d);
	        }
	        
		} 
			catch (Exception e) {
				e.printStackTrace();
			}

		return listaMes;
	}
	
	public double totalMensal (int usuarioId, int mes, int ano) {
		
		String sql = "SELECT SUM(valor) AS total FROM Despesas WHERE usuario_id = ? AND strftime ('%m', data) = ? AND strftime ('%Y', data) = ?";
		
		try(Connection conn = DatabaseManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, usuarioId);
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
	
	public List<Despesa> listarCategoria (int categoriaId) {
		
		List<Despesa> listaCategoria = new ArrayList<>();
		
		String sql = "SELECT * FROM Despesas WHERE categoria_id = ?";
		
		try(Connection conn = DatabaseManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, categoriaId);
			
			ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Despesa d = new Despesa();
                d.setId(rs.getInt("id"));
                d.setValor(rs.getDouble("valor"));
                d.setData(rs.getDate("data").toLocalDate());
                d.setCategoriaId(rs.getInt("categoria_id"));
                d.setUsuarioId(rs.getInt("usuario_id"));
                d.setRecorrente(rs.getBoolean("recorrente"));
                d.setDescricao(rs.getString("descricao"));

                listaCategoria.add(d);
            }

		}
		
			catch (Exception e) {
				e.printStackTrace();
			}
	
		return listaCategoria;
	}
	
	public List<Despesa> listarRecorrentes(int usuarioId) {
		
	    List<Despesa> lista = new ArrayList<>();

	    String sql = "SELECT * FROM Despesas WHERE usuario_id = ? AND recorrente = 1";

	    try (Connection conn = DatabaseManager.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, usuarioId);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Despesa d = new Despesa();
	            d.setId(rs.getInt("id"));
	            d.setValor(rs.getDouble("valor"));
	            d.setData(rs.getDate("data").toLocalDate());
	            d.setCategoriaId(rs.getInt("categoria_id"));
	            d.setUsuarioId(rs.getInt("usuario_id"));
	            d.setRecorrente(true);
	            d.setDescricao(rs.getString("descricao"));

	            lista.add(d);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return lista;
	}

	public void gerarRecorrentesProximoMes(int usuarioId, int mes, int ano) {
		
	    List<Despesa> recorrentes = listarRecorrentes(usuarioId);

	    for (Despesa d : recorrentes) {
	        Despesa nova = new Despesa();
	       
	        nova.setUsuarioId(usuarioId);
	        nova.setCategoriaId(d.getCategoriaId());
	        nova.setDescricao(d.getDescricao());
	        nova.setValor(d.getValor());
	        nova.setData(LocalDate.of(ano, mes, d.getData().getDayOfMonth()));
	        nova.setRecorrente(true);

	        salvar(nova);
	    }
	}
	
	public boolean cancelarRecorrencia (int despesaId) {
		
		String sql = "UPDATE Despesas SET recorrente = 0 WHERE id = ?";
		
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

	public boolean arquivarDespesa(int despesaId) {
		
        String sql = "UPDATE Despesas SET ativa = 0 WHERE id = ?";

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
