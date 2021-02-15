package br.com.porto.app.security;

import br.com.porto.backend.data.entity.User;

@FunctionalInterface
public interface CurrentUser {

	User getUser();
}
