package br.com.mudi.mudi.api;

import br.com.mudi.mudi.dto.RequisicaoNovaOferta;
import br.com.mudi.mudi.dto.RequisicaoNovoPedido;
import br.com.mudi.mudi.model.Oferta;
import br.com.mudi.mudi.model.Pedido;
import br.com.mudi.mudi.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaRest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping
    public Oferta criarOferta(@Valid  @RequestBody RequisicaoNovaOferta requisicaoNovaOferta) {
        Optional<Pedido> pedidoBuscado = pedidoRepository.findById(requisicaoNovaOferta.getPedidoId());
        if (!pedidoBuscado.isPresent()){
            return  null;
        }
        Pedido pedido =  pedidoBuscado.get();

        Oferta nova = requisicaoNovaOferta.toOferta();
        nova.setPedido(pedido);
        pedido.getOfertas().add(nova);
        pedidoRepository.save(pedido);

        return nova;

    }
}
