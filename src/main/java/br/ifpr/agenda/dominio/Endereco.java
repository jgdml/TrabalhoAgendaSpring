package br.ifpr.agenda.dominio;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.*;
import org.springframework.util.StringUtils;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, of = {"id"})
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String enderecoLinha1;
	private String enderecoLinha2;
	private String estado;
	private String municipio;
	
	@ManyToOne
	private Contato contato;

	@Override
	public String toString() {
		return "Endereco [enderecoLinha1=" + enderecoLinha1 + ", enderecoLinha2=" + enderecoLinha2 + ", estado="
				+ estado + ", municipio=" + municipio + "]";
	}

	public boolean isVazio() {
		return !StringUtils.hasText(enderecoLinha1)
				&&
				!StringUtils.hasText(enderecoLinha2)
				&&
				!StringUtils.hasText(estado)
				&&
				!StringUtils.hasText(municipio);
	}
}
