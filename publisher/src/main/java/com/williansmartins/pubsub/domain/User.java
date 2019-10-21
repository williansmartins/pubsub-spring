package com.williansmartins.pubsub.domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private String nome;
    private int idade;

    @JsonCreator
    public User(@JsonProperty("nome") String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	@Override
    public String toString() {
        return "User{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
