package br.ifpr.agenda.controllers;

import br.ifpr.agenda.dominio.*;
import br.ifpr.agenda.repositories.ContatoData;
import br.ifpr.agenda.repositories.ContatoRepository;
import br.ifpr.agenda.security.CustomUserDetails;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ContatoController {

    private ContatoRepository contatoRepository;
    private ContatoData contatoData;


    public ContatoController(ContatoRepository contatoRepository, ContatoData contatoData) {
        this.contatoRepository = contatoRepository;
        this.contatoData = contatoData;
    }


    private Usuario getCurrentUser() {
        var userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userDetails.getUsuario();
    }

    @RequestMapping("/contatos")
    public String getContatos(Model model) {

        model.addAttribute("contatos", contatoRepository.findAllByUsuario(getCurrentUser()));

        return "contatos/index";
    }

    @GetMapping("/contatos/novo")
    public String novoContato(Model model) {
        model.addAttribute("contato", Contato.builder().nome("").build());
        model.addAttribute("fieldToFocus", "nome");
        return "contatos/editar";
    }

    @GetMapping("/contatos/alterar/{id}")
    public String alterContato(@PathVariable("id") long id, Model model) {
        Contato contato = contatoRepository.findCompletoById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contato inválido"));

        model.addAttribute("contato", contato);
        model.addAttribute("fieldToFocus", "nome");
        return "contatos/editar";
    }

    @PostMapping("/contatos/excluir/{id}")
    public String excluirContato(@PathVariable("id") long id, Model model) {
        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contato inválido"));

        contatoRepository.delete(contato);

        return "redirect:/contatos";
    }

    @PostMapping("/contatos/salvar")
    public String salvarContato(@Valid Contato contato, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "contatos/editar";
        }

        contato.setUsuario(getCurrentUser());

        contato.corrigirEnderecosTelefones();

        contatoRepository.save(contato);

        return "redirect:/contatos";
    }

    @RequestMapping(value = "/contatos/salvar", params = {"addEndereco"})
    public String addEndereco(Contato contato, BindingResult bindingResult, Model model) {
        contato.addEndereco(new Endereco());
        String fieldId = "enderecos" + (contato.getEnderecos().size() - 1) + ".enderecoLinha1";
        model.addAttribute("fieldToFocus", fieldId);
        return "contatos/editar";
    }

    @RequestMapping(value = "/contatos/salvar", params = {"removeEndereco"})
    public String removeEndereco(Contato contato, BindingResult bindingResult, HttpServletRequest req) {
        final Integer enderecoIndex = Integer.valueOf(req.getParameter("removeEndereco"));

        contato.removeEndereco(enderecoIndex.intValue());
        return "contatos/editar";
    }

    @RequestMapping(value = "/contatos/salvar", params = {"addTelefone"})
    public String addTelefone(Contato contato, BindingResult bindingResult, Model model) {
        contato.addTelefone(new Telefone());

        String fieldId = "telefones" + (contato.getTelefones().size() - 1) + ".numero";
        model.addAttribute("fieldToFocus", fieldId);

        return "contatos/editar";
    }

    @RequestMapping(value = "/contatos/salvar", params = {"removeTelefone"})
    public String removeTelefone(Contato contato, BindingResult bindingResult, HttpServletRequest req) {
        final Integer telefoneIndex = Integer.valueOf(req.getParameter("removeTelefone"));

        contato.removeTelefone(telefoneIndex.intValue());
        return "contatos/editar";
    }

    @RequestMapping("/contatos/paged")
    public String getContatosPaginados(Model model,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       FilterContato filterContato) {

        Pageable pageable = PageRequest.of(page, size);
        contatoData.pageByFilter(pageable, filterContato);

        model.addAttribute("contatos", contatoData.pageByFilter(pageable, filterContato));

        return "contatos/paged";

    }
}
