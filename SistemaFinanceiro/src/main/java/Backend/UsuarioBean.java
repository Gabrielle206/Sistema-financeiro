package Backend;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped

public class UsuarioBean implements Serializable {

    private Usuario usuario = new Usuario();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public String salvar() {
        usuarioDAO.salvar(usuario);
        return "login?faces-redirect=true";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
