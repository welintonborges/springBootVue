package br.com.mudi.mudi.controller;

import br.com.mudi.mudi.dto.RequisicaoNovoPedido;
import br.com.mudi.mudi.model.Pedido;
import br.com.mudi.mudi.model.User;
import br.com.mudi.mudi.repository.PedidoRepository;
import br.com.mudi.mudi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("pedido")
public class PedidoController {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/formulario")
    public String formulario(RequisicaoNovoPedido requisicao) {
        return "pedido/formulario";
    }

    @PostMapping("novo")
    public String novo(@Valid RequisicaoNovoPedido requisicaoNovoPedido, BindingResult result) {
        if (result.hasErrors()) {
            return "pedido/formulario";
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User usuario = userRepository.findByUsername(username);
        Pedido pedido = requisicaoNovoPedido.toPedido();
        pedido.setUser(usuario);
        pedidoRepository.save(pedido);
        return "redirect:/home";
    }
}
