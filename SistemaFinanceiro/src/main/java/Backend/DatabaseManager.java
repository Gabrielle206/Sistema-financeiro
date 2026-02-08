package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:sqlite:financeiro.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void initializeDatabase() {

        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "email TEXT UNIQUE NOT NULL,"
                + "nome_completo TEXT NOT NULL,"
                + "ocupacao TEXT NOT NULL,"
                + "renda_mensal REAL NOT NULL,"
                + "senha TEXT NOT NULL,"
                + "primeiro_login INTEGER DEFAULT 1"
                + ");";

        String sqlCategorias = "CREATE TABLE IF NOT EXISTS Categorias ("
                + "id TEXT PRIMARY KEY	,"
                + "nome TEXT NOT NULL,"
                + "padrao BOOLEAN NOT NULL,"
                + "usuario_id TEXT,"
                + "FOREIGN KEY (usuario_id) REFERENCES Usuarios(id)"
                + ");";

        String sqlDespesas = "CREATE TABLE IF NOT EXISTS Despesas ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "descricao TEXT NOT NULL,"
                + "valor REAL NOT NULL,"
                + "data DATE NOT NULL,"
                + "recorrente INTEGER DEFAULT 0,"
                + "usuario_id INTEGER NOT NULL,"
                + "categoria_id INTEGER NOT NULL,"
                + "FOREIGN KEY (usuario_id) REFERENCES Usuarios(id),"
                + "FOREIGN KEY (categoria_id) REFERENCES Categorias(id)"
                + ");";

        String sqlReceitas = "CREATE TABLE IF NOT EXISTS Receitas ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "valor REAL NOT NULL,"
                + "data DATE NOT NULL,"
                + "usuario_id INTEGER NOT NULL,"
                + "FOREIGN KEY (usuario_id) REFERENCES Usuarios(id)"
                + ");";

        String sqlMetas = "CREATE TABLE IF NOT EXISTS Metas ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "valor_meta REAL NOT NULL,"
                + "mes INTEGER NOT NULL,"
                + "ano INTEGER NOT NULL,"
                + "usuario_id INTEGER NOT NULL,"
                + "ativa INTEGER DEFAULT 1,"
                + "FOREIGN KEY (usuario_id) REFERENCES Usuarios(id)"
                + ");";

        String sqlHistorico = "CREATE TABLE IF NOT EXISTS HistoricoDecisoes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "descricao TEXT NOT NULL,"
                + "data_hora DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + "usuario_id INTEGER NOT NULL,"
                + "FOREIGN KEY (usuario_id) REFERENCES Usuarios(id)"
                + ");";

        String sqlSeedCategorias = 
                "INSERT OR IGNORE INTO Categorias (id, nome, usuario_id) VALUES "
                + "(1, 'Alimentação', NULL),"
                + "(2, 'Energia', NULL),"
                + "(3, 'Água', NULL),"
                + "(4, 'Internet', NULL),"
                + "(5, 'Celular', NULL),"
                + "(6, 'Cartão de Crédito', NULL);";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlUsuarios);
            stmt.execute(sqlCategorias);
            stmt.execute(sqlDespesas);
            stmt.execute(sqlReceitas);
            stmt.execute(sqlMetas);
            stmt.execute(sqlHistorico);
            stmt.execute(sqlSeedCategorias);

            System.out.println("Banco de dados financeiro inicializado com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao inicializar o banco: " + e.getMessage());
        }
    }
}