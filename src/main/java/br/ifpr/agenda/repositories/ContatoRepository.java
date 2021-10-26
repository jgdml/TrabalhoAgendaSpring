package br.ifpr.agenda.repositories;

import br.ifpr.agenda.dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ifpr.agenda.dominio.Contato;

import java.util.List;

public interface ContatoRepository extends JpaRepository<Contato, Long>, ContatoRepositoryCustom {
    List<Contato> findAllByUsuario(Usuario usuario);
}
