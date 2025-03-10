import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorNomesBD implements GerenciadorNomes {

    private String url = "jdbc:postgresql://<Sua URL do Supabase>"; // ou "jdbc:h2:mem:testdb"
    private String user = "<Seu Usuário do Supabase>"; // ou "sa" para H2
    private String password = "<Sua Senha do Supabase>"; // ou "" para H2

    public GerenciadorNomesBD(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
        criarTabela();
    }

    public GerenciadorNomesBD(){
        this.url = "jdbc:h2:mem:testdb";
        this.user = "sa";
        this.password = "";
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
}