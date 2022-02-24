package br.com.mudi.mudi.controller;

import br.com.mudi.mudi.model.Pedido;
import br.com.mudi.mudi.model.StatusPedido;
import br.com.mudi.mudi.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public String home(Model model, Principal principal){
       List<Pedido> pedidos =  pedidoRepository.findAllByUsuario(principal.getName());
       model.addAttribute("pedidos", pedidos);
    return "home";
    }

    @GetMapping("/{status}")
    public String statusPedido(@PathVariable("status") String status, Model model){
       List<Pedido> pedidos =  pedidoRepository.findByStatus(StatusPedido.valueOf(status.toUpperCase(Locale.ROOT)));
       model.addAttribute("pedidos", pedidos);
       model.addAttribute("status", status);
    return "home";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String onError() {
        return "redirect:/home";
    }
}