package com.williansmartins.pubsub.domain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Informe {

    private String nome;
    private String documento;

    @JsonCreator
    public Informe(@JsonProperty("nome") String nome, String documento) {
        this.nome = nome;
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	@Override
    public String toString() {
        return "Informe{" +
                    "nome='" + nome + '\'' +
                    "documento='" + documento + '\'' +
                '}';
    }
}
