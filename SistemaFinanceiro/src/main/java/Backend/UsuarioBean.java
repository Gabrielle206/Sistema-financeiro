package Backend;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import jakarta.inject.Inject;

@Named
@RequestScoped

public class UsuarioBean implements Serializable {

    private Usuario usuario = new Usuario();
    
    @Inject
    private UsuarioDAO usuarioDAO;

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
