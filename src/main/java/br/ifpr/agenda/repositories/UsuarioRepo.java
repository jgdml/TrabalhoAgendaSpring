package br.ifpr.agenda.repositories;

import br.ifpr.agenda.dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNome(String nome);

}
