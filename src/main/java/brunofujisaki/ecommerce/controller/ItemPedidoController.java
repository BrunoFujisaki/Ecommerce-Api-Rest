package brunofujisaki.ecommerce.controller;

import brunofujisaki.ecommerce.domain.itempedido.dto.DetalharItemPedidoDto;
import brunofujisaki.ecommerce.repository.ItemPedidoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("itens")
@SecurityRequirement(name = "bearer-key")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @GetMapping
    public ResponseEntity<Page<DetalharItemPedidoDto>> listarItensDosPedidos(Pageable pageable) {
        var page = itemPedidoRepository.findAll(pageable).map(DetalharItemPedidoDto::new);
        return ResponseEntity.ok(page);
    }
}
