package br.com.porto.ui.views.storefront.events;

import com.vaadin.flow.component.ComponentEvent;
import br.com.porto.backend.data.entity.Paciente;
import br.com.porto.ui.views.orderedit.OrderItemEditor;

public class ProductChangeEvent extends ComponentEvent<OrderItemEditor> {

	private final Paciente paciente;

	public ProductChangeEvent(OrderItemEditor component, Paciente paciente) {
		super(component, false);
		this.paciente = paciente;
	}

	public Paciente getProduct() {
		return paciente;
	}

}