package br.com.porto.backend.data.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.com.porto.backend.data.OrderState;

public interface OrderSummary {
	Long getId();

	OrderState getState();

	Paciente getCustomer();

	List<DiagEnfermagem> getItems();

	LocalDate getDueDate();

	LocalTime getDueTime();

	PickupLocation getPickupLocation();

	Integer getTotalPrice();
}
