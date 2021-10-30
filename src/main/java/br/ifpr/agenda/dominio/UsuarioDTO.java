package br.ifpr.agenda.dominio;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UsuarioDTO {

    @NotNull
    @NotEmpty
    private String nome;

    @NotNull
    @NotEmpty
    private String senha;

    @NotNull
    @NotEmpty
    private String senha2;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha2() {
        return senha2;
    }

    public void setSenha2(String senha2) {
        this.senha2 = senha2;
    }
}
