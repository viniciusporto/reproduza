package br.com.porto.backend.data.entity;

import br.com.porto.backend.data.entity.util.EntityFrontEnd;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@EntityFrontEnd(name="Paciente")
public class Paciente extends AbstractEntity {

	@Size(max = 11)
	@Column
	private String cpf;

	@NotBlank(message = "{bakery.name.required}")
	@Size(max = 255)
	private String nome;

	@Size(max = 255)
	private String endereco;

	@Size(max = 255)
	private String email;

	@Size(max = 1)
	@Column
	private String sexo;

	@Size(max = 20)
	@Column
	private String telefone;

	@Size(max = 20)
	@Column
	private String cartaoSUS;

	@Size(max = 20)
	@Column
	private String estadoCivil;

	@Size(max = 20)
	@Column
	private String escolaridade;

	@Column
	private LocalDate dataNascimento;

	// Real price * 100 as an int to avoid rounding errors
	@Min(value = 0, message = "{bakery.price.limits}")
	@Max(value = 100000, message = "{bakery.price.limits}")
	private Integer price;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getSexo() {
		return sexo;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getCartaoSUS() {
		return cartaoSUS;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public String getEscolaridade() {
		return escolaridade;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setCartaoSUS(String cartaoSUS) {
		this.cartaoSUS = cartaoSUS;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		Paciente that = (Paciente) o;
		return Objects.equals(nome, that.nome) &&
				Objects.equals(price, that.price);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), nome, price);
	}
}
