package br.com.porto.ui.views.storefront.events;

import com.vaadin.flow.component.ComponentEvent;
import br.com.porto.ui.views.orderedit.OrderItemEditor;

public class DeleteEvent extends ComponentEvent<OrderItemEditor> {
	public DeleteEvent(OrderItemEditor component) {
		super(component, false);
	}
}