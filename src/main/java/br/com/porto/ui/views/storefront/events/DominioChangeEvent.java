package br.com.porto.ui.views.storefront.events;

import br.com.porto.backend.data.entity.Dominio;
import com.vaadin.flow.component.ComponentEvent;
import br.com.porto.backend.data.entity.Paciente;
import br.com.porto.ui.views.orderedit.OrderItemEditor;

public class DominioChangeEvent extends ComponentEvent<OrderItemEditor> {

	private final Dominio dominio;

	public DominioChangeEvent(OrderItemEditor component, Dominio paciente) {
		super(component, false);
		this.dominio = paciente;
	}

	public Dominio getDominio() {
		return dominio;
	}

}