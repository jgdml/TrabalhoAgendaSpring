package br.ifpr.agenda.security;

import br.ifpr.agenda.dominio.Usuario;
import br.ifpr.agenda.repositories.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    public UsuarioRepo usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {

        Optional<Usuario> usuario = usuarioRepo.findByNome(nome);

        if (usuario.isEmpty()){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return new CustomUserDetails(usuario.get());
    }
}
