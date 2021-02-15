package br.com.porto.ui;

import static br.com.porto.ui.utils.BakeryConst.TITLE_DASHBOARD;
import static br.com.porto.ui.utils.BakeryConst.TITLE_LOGOUT;
import static br.com.porto.ui.utils.BakeryConst.TITLE_PACIENTES;
import static br.com.porto.ui.utils.BakeryConst.TITLE_AGENDA;
import static br.com.porto.ui.utils.BakeryConst.TITLE_USERS;
import static br.com.porto.ui.utils.BakeryConst.VIEWPORT;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinServlet;
import br.com.porto.app.security.SecurityUtils;
import br.com.porto.ui.views.HasConfirmation;
import br.com.porto.ui.views.admin.products.PacientesView;
import br.com.porto.ui.views.admin.users.UsersView;
import br.com.porto.ui.views.dashboard.DashboardView;
import br.com.porto.ui.views.storefront.StorefrontView;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Viewport(VIEWPORT)
@PWA(name = "Bakery App Starter", shortName = "reproduza",
		startPath = "login",
		backgroundColor = "#227aef", themeColor = "#227aef",
		offlinePath = "offline-page.html",
		offlineResources = {"images/offline-login-banner.jpg"},
		enableInstallPrompt = false)
@CssImport(value = "./styles/vaadin-app-layout-styles.css", themeFor = "vaadin-app-layout")
public class MainView extends AppLayout {

	private final ConfirmDialog confirmDialog = new ConfirmDialog();
	private final Tabs menu;

	public MainView() throws IOException {
		confirmDialog.setCancelable(true);
		confirmDialog.setConfirmButtonTheme("raised tertiary error");
		confirmDialog.setCancelButtonTheme("raised tertiary");

		this.setDrawerOpened(false);
		byte[] imageBytes = FileUtils.readFileToByteArray(new File("src/main/resources/META-INF/resources/images/reproduza-logo.png"));
		StreamResource resource = new StreamResource("logo.png", () -> new ByteArrayInputStream(imageBytes));
		Image appLogo = new Image(resource, "logo reproduza");
		appLogo.addClassName("hide-on-mobile");
		appLogo.setHeight("64");
		appLogo.setWidth("143");
		menu = createMenuTabs();

		this.addToNavbar(appLogo);
		this.addToNavbar(true, menu);
		this.getElement().appendChild(confirmDialog.getElement());

		getElement().addEventListener("search-focus", e -> {
			getElement().getClassList().add("hide-navbar");
		});

		getElement().addEventListener("search-blur", e -> {
			getElement().getClassList().remove("hide-navbar");
		});
	}

	@Override
	protected void afterNavigation() {
		super.afterNavigation();
		confirmDialog.setOpened(false);
		if (getContent() instanceof HasConfirmation) {
			((HasConfirmation) getContent()).setConfirmDialog(confirmDialog);
		}
		RouteConfiguration configuration = RouteConfiguration.forSessionScope();
		if (configuration.isRouteRegistered(this.getContent().getClass())) {
			String target = configuration.getUrl(this.getContent().getClass());
			Optional < Component > tabToSelect = menu.getChildren().filter(tab -> {
				Component child = tab.getChildren().findFirst().get();
				return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
			}).findFirst();
			tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
		} else {
			menu.setSelectedTab(null);
		}
	}

	private static Tabs createMenuTabs() {
		final Tabs tabs = new Tabs();
		tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
		tabs.add(getAvailableTabs());
		return tabs;
	}

	private static Tab[] getAvailableTabs() {
		final List<Tab> tabs = new ArrayList<>(4);
		tabs.add(createTab(VaadinIcon.CALENDAR, TITLE_AGENDA,
						StorefrontView.class));
		tabs.add(createTab(VaadinIcon.DASHBOARD,TITLE_DASHBOARD, DashboardView.class));
		if (SecurityUtils.isAccessGranted(UsersView.class)) {
			tabs.add(createTab(VaadinIcon.NURSE,TITLE_USERS, UsersView.class));
		}
		if (SecurityUtils.isAccessGranted(PacientesView.class)) {
			tabs.add(createTab(VaadinIcon.USER, TITLE_PACIENTES, PacientesView.class));
		}
		final String contextPath = VaadinServlet.getCurrent().getServletContext().getContextPath();
		final Tab logoutTab = createTab(createLogoutLink(contextPath));
		tabs.add(logoutTab);
		return tabs.toArray(new Tab[tabs.size()]);
	}

	private static Tab createTab(VaadinIcon icon, String title, Class<? extends Component> viewClass) {
		return createTab(populateLink(new RouterLink(null, viewClass), icon, title));
	}

	private static Tab createTab(Component content) {
		final Tab tab = new Tab();
		tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		tab.add(content);
		return tab;
	}

	private static Anchor createLogoutLink(String contextPath) {
		final Anchor a = populateLink(new Anchor(), VaadinIcon.ARROW_RIGHT, TITLE_LOGOUT);
		a.setHref(contextPath + "/logout");
		return a;
	}

	private static <T extends HasComponents> T populateLink(T a, VaadinIcon icon, String title) {
		a.add(icon.create());
		a.add(title);
		return a;
	}
}