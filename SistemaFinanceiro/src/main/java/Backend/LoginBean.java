package Backend;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import jakarta.inject.Inject;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String senha;
    private Usuario usuarioLogado;

    private LoginDAO loginDAO = new LoginDAO();

    public String efetuarLogin() {
        usuarioLogado = loginDAO.autenticar(email, senha);

        if (usuarioLogado != null) {
            return "dashboard?faces-redirect=true";
        }
        return "login?error=true";
    }

    public String login() {
        return efetuarLogin();
    }

    public String logout() {
        usuarioLogado = null;
        return "login?faces-redirect=true";
    }

    public boolean isLogado() {
        return usuarioLogado != null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}
