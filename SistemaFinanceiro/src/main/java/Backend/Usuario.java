package Backend;

public class Usuario {
	
	private String id;
	private String email;
	private String nomeCompleto;
	private String ocupacao;
	private double rendaMensal;
	private String senha;
	private boolean primeiroLogin;
	
	public Usuario() {
    }
	
	public Usuario (String id, String nomeCompleto, String ocupacao) {
		this.id = id;
		this.nomeCompleto = nomeCompleto;
		this.ocupacao = ocupacao;
		this.primeiroLogin = true;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getOcupacao() {
		return ocupacao;
	}
	public void setOcupacao(String ocupacao) {
		this.ocupacao = ocupacao;
	}
	public double getRendaMensal() {
		return rendaMensal;
	}
	public void setRendaMensal(double rendaMensal) {
		this.rendaMensal = rendaMensal;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean isPrimeiroLogin() {
		return primeiroLogin;
	}
	public void setPrimeiroLogin(boolean primeiroLogin) {
		this.primeiroLogin = primeiroLogin;
	}
	
}

