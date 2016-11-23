package br.com.up.upark.upark;

public class Usuario {
    public String Nome;
    public String Senha;
    public String DataNasc;
    public String Email;
    public int CPF;
    public int UsuarioId;

    public String getNome() {
        return Nome;
    }

    public String getSenha() {
        return Senha;
    }

    public String getDataNasc() {
        return DataNasc;
    }

    public String getEmail() {
        return Email;
    }

    public int getCPF() {
        return CPF;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public void setCPF(int CPF) {
        this.CPF = CPF;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setDataNasc(String dataNasc) {
        DataNasc = dataNasc;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public void setSenha(String senha) {
        this.Senha = senha;
    }
}
