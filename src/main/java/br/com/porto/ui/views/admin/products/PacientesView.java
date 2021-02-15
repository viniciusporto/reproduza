package br.com.porto.ui.views.admin.products;

import br.com.porto.backend.data.entity.Paciente;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import br.com.porto.app.security.CurrentUser;
import br.com.porto.backend.data.Role;
import br.com.porto.backend.service.PacienteService;
import br.com.porto.ui.MainView;
import br.com.porto.ui.crud.AbstractBakeryCrudView;
import br.com.porto.ui.utils.BakeryConst;
import br.com.porto.ui.utils.converters.CurrencyFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

import static br.com.porto.ui.utils.BakeryConst.PAGE_PACIENTES;

@Route(value = PAGE_PACIENTES, layout = MainView.class)
@PageTitle(BakeryConst.TITLE_PACIENTES)
@Secured(Role.ADMINISTRADOR)
public class PacientesView extends AbstractBakeryCrudView<Paciente> {

	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();

	@Autowired
	public PacientesView(PacienteService service, CurrentUser currentUser) {
		super(Paciente.class, service, new Grid<>(), createForm(), currentUser);
	}

	@Override
	protected void setupGrid(Grid<Paciente> grid) {
		grid.addColumn(Paciente::getNome).setHeader("Nome").setFlexGrow(10);
		grid.addColumn(Paciente::getEmail).setHeader("E-mail").setFlexGrow(10);
		grid.addColumn(Paciente::getTelefone).setHeader("Telefone").setFlexGrow(10);

	}

	@Override
	protected String getBasePage() {
		return PAGE_PACIENTES;
	}

	private static BinderCrudEditor<Paciente> createForm() {

		TextField nome = new TextField("Nome");
		nome.getElement().setAttribute("colspan", "2");
		TextField cpf = new TextField("CPF");
		cpf.getElement().setAttribute("colspan", "1");
		TextField price = new TextField("Preço Unitário");
		price.getElement().setAttribute("colspan", "2");
		TextField endereco = new TextField("Endereço");
		endereco.getElement().setAttribute("colspan", "3");
		DatePicker datePicker = new DatePicker();
		datePicker.setLabel("Data de Nascimento");
		datePicker.setPlaceholder("Data");
		datePicker.setLocale(new Locale("pt", "BR"));
		datePicker.setAutoOpen(false);
		datePicker.getElement().setAttribute("colspan", "1");
		datePicker.setI18n(new DatePicker.DatePickerI18n().setWeek("Semana")
				.setCalendar("Calendário").setClear("Limpar")
				.setToday("Hoje").setCancel("Cancelar").setFirstDayOfWeek(1)
				.setMonthNames(Arrays.asList("Janeiro", "Fevereiro", "Março",
						"Abril", "Maio", "Junho", "Julho", "Agosto",
						"Setembro", "Outubro", "Novembro", "Dezembro"))
				.setWeekdays(Arrays.asList("Domingo", "Segunda", "Terça",
						"Quarta", "Quinta", "Sexta", "Sábado"))
				.setWeekdaysShort(Arrays.asList("dom", "seg", "ter", "qua", "qui",
						"sex", "sa")));
		TextField email = new TextField("E-mail");
		email.getElement().setAttribute("colspan", "2");
		TextField telefone = new TextField("Telefone");
		telefone.getElement().setAttribute("colspan", "1");
		TextField carteiraSUS = new TextField("Cartão do SUS");
		carteiraSUS.getElement().setAttribute("colspan", "1");
		ComboBox<String> sexo = new ComboBox<>();
		sexo.setItems("Feminino", "Masculinho");
		sexo.setLabel("Sexo");
		sexo.getElement().setAttribute("colspan", "1");
		ComboBox<String> estadoCivil = new ComboBox<>();
		estadoCivil.setItems("Solteira", "Casada", "Divorciada", "Separada", "Viúva");
		estadoCivil.setLabel("Estado Civil");
		estadoCivil.getElement().setAttribute("colspan", "1");
		ComboBox<String> escolaridade = new ComboBox<>();
		escolaridade.setItems("Fundamental Incompleto,","Fundamental Completo" , "Ensino Médio Incompleto", "Ensino Médio Completo", "Superior incompleto", "Superior Completo", "Pós-graduação");
		escolaridade.setLabel("Escolaridade");
		escolaridade.getElement().setAttribute("colspan", "1");


		FormLayout form = new FormLayout(nome, cpf, endereco, datePicker, email, sexo, telefone, estadoCivil, escolaridade, carteiraSUS);
		form.setResponsiveSteps(
				new FormLayout.ResponsiveStep("25em", 1),
				new FormLayout.ResponsiveStep("32em", 2),
				new FormLayout.ResponsiveStep("40em", 3));


		BeanValidationBinder<Paciente> binder = new BeanValidationBinder<>(Paciente.class);

		binder.bind(nome, "nome");
		binder.bind(endereco, "endereco");
		//binder.forField(price).withConverter(new PriceConverter()).bind("price");
		price.setPattern("\\d+(\\.\\d?\\d?)?$");
		price.setPreventInvalidInput(true);

		String currencySymbol = Currency.getInstance(BakeryConst.APP_LOCALE).getSymbol();
		price.setPrefixComponent(new Span(currencySymbol));

		return new BinderCrudEditor<>(binder, form);
	}

}
