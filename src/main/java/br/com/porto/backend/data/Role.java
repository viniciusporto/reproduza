package br.com.porto.backend.data;

public class Role {
	public static final String ENFERMEIRO = "Enfermeiro";
	public static final String MEDICO = "Médico";
	// This role implicitly allows access to all views.
	public static final String ADMINISTRADOR = "Administrador";
	private static final String BIOLOGO = "Biólogo";
	private static final String TECNICO_ENFERMAGEM = "Técnico de Enfermagem";

	private Role() {
		// Static methods and fields only
	}

	public static String[] getAllRoles() {
		return new String[] {ENFERMEIRO, MEDICO, ADMINISTRADOR, BIOLOGO, TECNICO_ENFERMAGEM};
	}

}
