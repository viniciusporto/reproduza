package br.com.porto.ui.views.orderedit;

import java.util.Objects;
import java.util.stream.Stream;

import br.com.porto.backend.data.entity.Classe;
import br.com.porto.backend.data.entity.Diagnostico;
import br.com.porto.backend.data.entity.Dominio;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.internal.AbstractFieldSupport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;
import br.com.porto.backend.data.entity.DiagEnfermagem;
import br.com.porto.ui.views.storefront.events.CommentChangeEvent;
import br.com.porto.ui.views.storefront.events.DeleteEvent;
import br.com.porto.ui.views.storefront.events.PriceChangeEvent;
import br.com.porto.ui.views.storefront.events.DominioChangeEvent;

@Tag("order-item-editor")
@JsModule("./src/views/orderedit/order-item-editor.js")
public class OrderItemEditor extends PolymerTemplate<TemplateModel> implements HasValueAndElement<ComponentValueChangeEvent<OrderItemEditor, DiagEnfermagem>, DiagEnfermagem> {

	@Id("dominios")
	private ComboBox<Dominio> dominios;

	@Id("delete")
	private Button delete;

	@Id("classes")
	private ComboBox<Classe> classes;

	@Id("diagnosticos")
	private ComboBox<Diagnostico> diagnosticos;

	@Id("comment")
	private TextField comment;

	private int totalPrice;
	
    private final AbstractFieldSupport<OrderItemEditor, DiagEnfermagem> fieldSupport;

	private BeanValidationBinder<DiagEnfermagem> binder = new BeanValidationBinder<>(DiagEnfermagem.class);
	public OrderItemEditor(DataProvider<Dominio, String> dominioDataProvider, DataProvider<Classe, String> classeDataProvider, DataProvider<Diagnostico, String> diagnosticoDataProvider) {
		this.fieldSupport =  new AbstractFieldSupport<>(this, null,
				Objects::equals, c ->  {});
		dominios.setDataProvider(dominioDataProvider);
		classes.setDataProvider(classeDataProvider);
		diagnosticos.setDataProvider(diagnosticoDataProvider);
		dominios.addValueChangeListener(e -> {
			//setPrice();
			fireEvent(new DominioChangeEvent(this, e.getValue()));
		});
		//amount.addValueChangeListener(e -> setPrice());
		comment.addValueChangeListener(e -> fireEvent(new CommentChangeEvent(this, e.getValue())));

		binder.forField(dominios).bind("dominio");
		binder.forField(classes).bind("classe");
		binder.forField(diagnosticos).bind("diagnostico");
		//amount.setRequiredIndicatorVisible(true);
		binder.forField(comment).bind("comment");
		//binder.forField(products).bind("product");
		dominios.setRequired(true);
		classes.setRequired(true);
		diagnosticos.setRequired(true);

		delete.addClickListener(e -> fireEvent(new DeleteEvent(this)));
		//setPrice();
	}
	
//	private void setPrice() {
//		int oldValue = totalPrice;
//		Integer selectedAmount = amount.getValue();
//		Paciente paciente = products.getValue();
//		totalPrice = 0;
//		if (selectedAmount != null && paciente != null) {
//			totalPrice = selectedAmount * paciente.getPrice();
//		}
//		price.setText(FormattingUtils.formatAsCurrency(totalPrice));
//		if (oldValue != totalPrice) {
//			fireEvent(new PriceChangeEvent(this, oldValue, totalPrice));
//		}
//	}

	@Override
	public void setValue(DiagEnfermagem value) {
		fieldSupport.setValue(value);
		binder.setBean(value);
		boolean noProductSelected = value == null;
		//amount.setEnabled(!noProductSelected);
		delete.setEnabled(!noProductSelected);
		comment.setEnabled(!noProductSelected);
		//setPrice();
	}

	@Override
	public DiagEnfermagem getValue() {
		return fieldSupport.getValue();
	}

	public Stream<HasValue<?, ?>> validate() {
		return binder.validate().getFieldValidationErrors().stream().map(BindingValidationStatus::getField);
	}

	public Registration addPriceChangeListener(ComponentEventListener<PriceChangeEvent> listener) {
		return addListener(PriceChangeEvent.class, listener);
	}

	public Registration addProductChangeListener(ComponentEventListener<DominioChangeEvent> listener) {
		return addListener(DominioChangeEvent.class, listener);
	}

	public Registration addCommentChangeListener(ComponentEventListener<CommentChangeEvent> listener) {
		return addListener(CommentChangeEvent.class, listener);
	}

	public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
		return addListener(DeleteEvent.class, listener);
	}

	@Override
	public Registration addValueChangeListener(
			ValueChangeListener<? super ComponentValueChangeEvent<OrderItemEditor, DiagEnfermagem>> listener) {
		return fieldSupport.addValueChangeListener(listener);
	}

}
