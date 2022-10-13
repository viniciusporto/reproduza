package br.com.porto.ui.views.orderedit;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.porto.backend.data.entity.*;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.internal.AbstractFieldSupport;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.shared.Registration;
import br.com.porto.ui.views.storefront.events.TotalPriceChangeEvent;

public class OrderItemsEditor extends Div implements HasValueAndElement<ComponentValueChangeEvent<OrderItemsEditor,List<DiagEnfermagem>>, List<DiagEnfermagem>> {

	private OrderItemEditor empty;

	private DataProvider<Dominio, String> dominioDataProvider;

	private DataProvider<Classe, String> classeDataProvider;

	private DataProvider<Diagnostico, String> diagnosticoDataProvider;

	private int totalPrice = 0;

	private boolean hasChanges = false;

	private final AbstractFieldSupport<OrderItemsEditor,List<DiagEnfermagem>> fieldSupport;
	
	public OrderItemsEditor(DataProvider<Dominio, String> dominioDataProvider, DataProvider<Classe, String> classeDataProvider, DataProvider<Diagnostico, String> diagnosticoDataProvider) {
		this.dominioDataProvider = dominioDataProvider;
		this.classeDataProvider = classeDataProvider;
		this.diagnosticoDataProvider = diagnosticoDataProvider;
		this.fieldSupport = new AbstractFieldSupport<>(this, Collections.emptyList(),
				Objects::equals, c ->  {}); 
	}

	@Override
	public void setValue(List<DiagEnfermagem> items) {
		fieldSupport.setValue(items);
		removeAll();
		totalPrice = 0;
		hasChanges = false;

		if (items != null) {
			items.forEach(this::createEditor);
		}
		createEmptyElement();
		setHasChanges(false);
	}

	private OrderItemEditor createEditor(DiagEnfermagem value) {
		OrderItemEditor editor = new OrderItemEditor(dominioDataProvider, classeDataProvider, diagnosticoDataProvider);
		getElement().appendChild(editor.getElement());
		editor.addPriceChangeListener(e -> updateTotalPriceOnItemPriceChange(e.getOldValue(), e.getNewValue()));
		editor.addProductChangeListener(e -> productChanged(e.getSource(), e.getDominio()));
		editor.addCommentChangeListener(e -> setHasChanges(true));
		editor.addDeleteListener(e -> {
			OrderItemEditor orderItemEditor = e.getSource();
			if (orderItemEditor != empty) {
				remove(orderItemEditor);
				DiagEnfermagem diagEnfermagem = orderItemEditor.getValue();
				setValue(getValue().stream().filter(element -> element != diagEnfermagem).collect(Collectors.toList()));
				updateTotalPriceOnItemPriceChange(diagEnfermagem.getTotalPrice(), 0);
				setHasChanges(true);
			}
		});

		editor.setValue(value);
		return editor;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		HasValueAndElement.super.setReadOnly(readOnly);
		getChildren().forEach(e -> ((OrderItemEditor) e).setReadOnly(readOnly));
	}

	@Override
	public List<DiagEnfermagem> getValue() {
		return fieldSupport.getValue();
	}

	private void productChanged(OrderItemEditor item, Dominio dominio) {
		setHasChanges(true);
		if (empty == item) {
			createEmptyElement();
			DiagEnfermagem diagEnfermagem = new DiagEnfermagem();
			//orderItem.setProduct(paciente);
			item.setValue(diagEnfermagem);
			setValue(Stream.concat(getValue().stream(),Stream.of(diagEnfermagem)).collect(Collectors.toList()));
		}
	}

	private void updateTotalPriceOnItemPriceChange(int oldItemPrice, int newItemPrice) {
		final int delta = newItemPrice - oldItemPrice;
		totalPrice += delta;
		setHasChanges(true);
		fireEvent(new TotalPriceChangeEvent(this, totalPrice));
	}

	private void createEmptyElement() {
		empty = createEditor(null);
	}

	public Registration addPriceChangeListener(ComponentEventListener<TotalPriceChangeEvent> listener) {
		return addListener(TotalPriceChangeEvent.class, listener);
	}

	public boolean hasChanges() {
		return hasChanges;
	}

	private void setHasChanges(boolean hasChanges) {
		this.hasChanges = hasChanges;
		if (hasChanges) {
			fireEvent(new br.com.porto.ui.views.storefront.events.ValueChangeEvent(this));
		}
	}

	public Stream<HasValue<?, ?>> validate() {
		return getChildren()
				.filter(component -> fieldSupport.getValue().size() == 0 || !component.equals(empty))
				.map(editor -> ((OrderItemEditor) editor).validate()).flatMap(stream -> stream);
	}

	@Override
	public Registration addValueChangeListener(
			ValueChangeListener<? super ComponentValueChangeEvent<OrderItemsEditor, List<DiagEnfermagem>>> listener) {
		return fieldSupport.addValueChangeListener(listener);
	}
}
