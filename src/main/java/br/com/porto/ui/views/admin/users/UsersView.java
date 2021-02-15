package br.com.porto.ui.views.admin.users;

import static br.com.porto.ui.utils.BakeryConst.PAGE_USERS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import br.com.porto.app.security.CurrentUser;
import br.com.porto.backend.data.Role;
import br.com.porto.backend.data.entity.User;
import br.com.porto.backend.service.UserService;
import br.com.porto.ui.MainView;
import br.com.porto.ui.crud.AbstractBakeryCrudView;
import br.com.porto.ui.utils.BakeryConst;

@Route(value = PAGE_USERS, layout = MainView.class)
@PageTitle(BakeryConst.TITLE_USERS)
@Secured(Role.ADMINISTRADOR)
public class UsersView extends AbstractBakeryCrudView<User> {

	@Autowired
	public UsersView(UserService service, CurrentUser currentUser, PasswordEncoder passwordEncoder) {
		super(User.class, service, new Grid<>(), createForm(passwordEncoder), currentUser);
	}

	@Override
	public void setupGrid(Grid<User> grid) {

		grid.addColumn(u -> u.getFirstName() + " " + u.getLastName()).setHeader("Nome").setWidth("200px").setFlexGrow(5);
		grid.addColumn(User::getEmail).setWidth("270px").setHeader("E-mail").setFlexGrow(5);
		grid.addColumn(User::getRole).setHeader("Função").setWidth("150px");
	}

	@Override
	protected String getBasePage() {
		return PAGE_USERS;
	}

	private static BinderCrudEditor<User> createForm(PasswordEncoder passwordEncoder) {
		EmailField email = new EmailField("E-mail (login)");
		email.getElement().setAttribute("colspan", "2");
		TextField first = new TextField("Primeiro nome");
		TextField last = new TextField("Sobrenome");
		PasswordField password = new PasswordField("Senha");
		password.getElement().setAttribute("colspan", "2");
		ComboBox<String> role = new ComboBox<>();
		role.getElement().setAttribute("colspan", "2");
		role.setLabel("Função");

		FormLayout form = new FormLayout(email, first, last, password, role);

		BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);

		ListDataProvider<String> roleProvider = DataProvider.ofItems(Role.getAllRoles());
		role.setItemLabelGenerator(s -> s != null ? s : "");
		role.setDataProvider(roleProvider);

		binder.bind(first, "firstName");
		binder.bind(last, "lastName");
		binder.bind(email, "email");
		binder.bind(role, "role");

		binder.forField(password)
				.withValidator(pass -> pass.matches("^(|(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,})$"),
				"A senha deve ter 6 ou mais caracteres, envolvendo numeros, letras maíúsculas e minúsculas")
				.bind(user -> password.getEmptyValue(), (user, pass) -> {
					if (!password.getEmptyValue().equals(pass)) {
						user.setPasswordHash(passwordEncoder.encode(pass));
					}
				});

		return new BinderCrudEditor<User>(binder, form);
	}
}
