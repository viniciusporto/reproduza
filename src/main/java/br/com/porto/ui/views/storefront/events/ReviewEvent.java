package br.com.porto.ui.views.storefront.events;

import com.vaadin.flow.component.ComponentEvent;
import br.com.porto.ui.views.orderedit.OrderEditor;

public class ReviewEvent extends ComponentEvent<OrderEditor> {

	public ReviewEvent(OrderEditor component) {
		super(component, false);
	}
}