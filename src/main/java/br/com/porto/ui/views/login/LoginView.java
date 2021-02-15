package br.com.porto.ui.views.login;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import br.com.porto.app.security.SecurityUtils;
import br.com.porto.ui.utils.BakeryConst;
import br.com.porto.ui.views.storefront.StorefrontView;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Route
@PageTitle("reproduza")
@JsModule("./styles/shared-styles.js")
@Viewport(BakeryConst.VIEWPORT)
public class LoginView extends LoginOverlay
	implements AfterNavigationObserver, BeforeEnterObserver {
 // "admin@vaadin.com + admin\n" + "barista@vaadin.com + barista"
	public LoginView() throws IOException {
		LoginI18n i18n = LoginI18n.createDefault();
		i18n.setHeader(new LoginI18n.Header());
		i18n.getHeader().setTitle("reproduza");

		i18n.setAdditionalInformation(null);
		i18n.setForm(new LoginI18n.Form());
		i18n.getForm().setSubmit("Entrar");
		i18n.getForm().setTitle("Login");
		i18n.getErrorMessage().setTitle("Usuário/senha inválidos");
		i18n.getErrorMessage()
				.setMessage("Confira seu usuário e senha e tente novamente.");

		i18n.getForm().setUsername("E-mail");
		i18n.getForm().setPassword("Senha");

		H1 h1 = new H1();
		byte[] imageBytes = FileUtils.readFileToByteArray(new File("src/main/resources/META-INF/resources/images/reproduza_logo_principal.png"));
		StreamResource resource = new StreamResource("logo.png", () -> new ByteArrayInputStream(imageBytes));
		Image appLogo = new Image(resource, "logo reproduza");
		appLogo.setHeight("64");
		appLogo.setWidth("143");
		h1.add(appLogo);


		setI18n(i18n);
		setTitle(h1);
		setForgotPasswordButtonVisible(false);
		setAction("login");
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (SecurityUtils.isUserLoggedIn()) {
			event.forwardTo(StorefrontView.class);
		} else {
			setOpened(true);
		}
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		setError(
			event.getLocation().getQueryParameters().getParameters().containsKey(
				"error"));
	}

}
