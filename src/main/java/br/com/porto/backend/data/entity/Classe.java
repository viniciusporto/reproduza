package br.com.porto.backend.data.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Classe extends AbstractEntity{

    @NotBlank
    @Size(max = 255)
    private String nome;
}
