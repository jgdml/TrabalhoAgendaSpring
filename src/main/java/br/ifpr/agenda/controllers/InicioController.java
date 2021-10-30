package br.ifpr.agenda.controllers;

import br.ifpr.agenda.dominio.Usuario;
import br.ifpr.agenda.dominio.UsuarioDTO;
import br.ifpr.agenda.repositories.UsuarioRepo;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
public class InicioController {

    UsuarioRepo usuarioRepo;


    @GetMapping("/login")
    public String getLoginPage(){
        return "inicial/login";
    }


    @GetMapping("/cadastro")
    public String getCadastroPage(WebRequest req, Model model){

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        model.addAttribute("usuarioDto", usuarioDTO);


        return "inicial/cadastro";
    }


    @PostMapping("/cadastro")
    public ModelAndView cadastrarNovoUsuario(@ModelAttribute("usuarioDto") @Valid UsuarioDTO usuarioDTO){

        ModelAndView mv = new ModelAndView("inicial/cadastro");
        mv.addObject(usuarioDTO);

        if (!usuarioDTO.getSenha().equals(usuarioDTO.getSenha2())){
            mv.addObject("err", "As senhas digitadas são diferentes");
            return mv;
        }

        Usuario u = new Usuario();
        u.setNome(usuarioDTO.getNome());
        u.setSenha(new BCryptPasswordEncoder()
                        .encode(usuarioDTO.getSenha()));

        try{
            usuarioRepo.save(u);
        }
        catch (DataIntegrityViolationException err){
            mv.addObject("err", "Este nome de usuário já está sendo usado");

            return mv;
        }

        mv.addObject("ok", "Cadastrado com sucesso");
        return mv;
    }


}
