package br.ifpr.agenda.dominio;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import lombok.*;
import org.springframework.util.StringUtils;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, of = {"id"})
public class Telefone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private TipoTelefone tipo;
	
	private String numero;
	
	@ManyToOne
	private Contato contato;

	@Override
	public String toString() {
		return "Telefone [numero=" + numero + "]";
	}

	
	public boolean isVazio() {
		return !StringUtils.hasText(this.numero);
	}
}
