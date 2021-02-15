package br.com.porto.backend.data.entity;

import org.junit.Assert;
import org.junit.Test;

public class PacienteTest {

	@Test
	public void equalsTest() {
		Paciente o1 = new Paciente();
		o1.setNome("name");
		o1.setPrice(123);

		Paciente o2 = new Paciente();
		o2.setNome("anothername");
		o2.setPrice(123);

		Assert.assertNotEquals(o1, o2);

		o2.setNome("name");
		Assert.assertEquals(o1, o2);
	}
}
