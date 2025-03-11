import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorNomesBD implements GerenciadorNomes {

    private String url = "jdbc:postgresql://db.juatxdltwmiuocbytvwt.supabase.co:5432/postgres";
    private String user = "postgres";
    private String password = "iKNeZC@3TP_bCSC";

    public GerenciadorNomesBD(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
        criarTabela();
    }

    public GerenciadorNomesBD(){
        this.url = "jdbc:postgresql://db.juatxdltwmiuocbytvwt.supabase.co:5432/postgres";
        this.user = "postgres";
        this.password = "iKNeZC@3TP_bCSC";
        criarTabela();
    }

    private void criarTabela(){
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS nomes (nome VARCHAR(255))";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> obterNomes() {
        List<String> nomes = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nome FROM nomes")) {

            while (rs.next()) {
                nomes.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nomes;
    }

    @Override
    public void adicionarNome(String nome) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO nomes (nome) VALUES (?)")) {

            pstmt.setString(1, nome);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removerNome(String nome) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM nomes WHERE nome = ?")) {

            pstmt.setString(1, nome);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizarNome(String nomeAntigo, String nomeNovo) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE nomes SET nome = ? WHERE nome = ?")) {

            pstmt.setString(1, nomeNovo);
            pstmt.setString(2, nomeAntigo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}