package br.ifpr.agenda.bootstrap;

import br.ifpr.agenda.dominio.*;
import br.ifpr.agenda.repositories.ContatoRepository;
import br.ifpr.agenda.repositories.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Component
public class InicializarDados implements CommandLineRunner {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private UsuarioRepo userRepo;


    @Override
    public void run(String... args) throws Exception {

        Contato contato1 = Contato.builder().nome("João").build();
        contato1.setSobrenome("Silva");
        contato1.setEmail("joao.silva@gmail.com");
        contato1.setDataNascimento(LocalDate.of(1980, Month.OCTOBER, 1));

        Endereco endereco1 = new Endereco();
        endereco1.setEnderecoLinha1("Rua Brasil, 123. Cidade Alta");
        endereco1.setMunicipio("Curitiba");
        endereco1.setEstado("Paraná");
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco1);
        contato1.setEnderecos(enderecos);

        Telefone telefone1 = new Telefone();
        telefone1.setNumero("(44) 2222-2222");
        telefone1.setTipo(TipoTelefone.CASA);
        List<Telefone> telefones = new ArrayList<>();
        telefones.add(telefone1);
        contato1.setTelefones(telefones);

        var a = userRepo.findByNome("amogus");
        if (a.isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            var login = Usuario.builder().nome("amogus").senha(passwordEncoder.encode("amogus")).build();
            userRepo.save(login);
        }
        contatoRepository.save(contato1);
    }

}
