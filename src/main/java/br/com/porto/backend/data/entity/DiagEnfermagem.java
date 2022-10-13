package br.com.porto.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class DiagEnfermagem extends AbstractEntity {

	@OneToOne
	@NotNull(message = "{bakery.pickup.product.required}")
	private Dominio dominio;

	@OneToOne
	@NotNull(message = "{bakery.pickup.product.required}")
	private Classe classe;

	@OneToOne
	@NotNull(message = "{bakery.pickup.product.required}")
	private Diagnostico diagnostico;

	@Min(1)
	@NotNull
	private Integer quantity = 1;

	@Size(max = 255)
	private String comment;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getTotalPrice() {
		return 0;
	}

	public Dominio getDominio() {
		return dominio;
	}

	public void setDominio(Dominio dominio) {
		this.dominio = dominio;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public Diagnostico getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(Diagnostico diagnostico) {
		this.diagnostico = diagnostico;
	}
}
