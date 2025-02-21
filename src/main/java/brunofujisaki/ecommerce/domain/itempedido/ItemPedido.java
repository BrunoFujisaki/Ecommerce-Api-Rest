package brunofujisaki.ecommerce.domain.itempedido;

import brunofujisaki.ecommerce.domain.produto.Produto;
import brunofujisaki.ecommerce.domain.pedido.Pedido;
import brunofujisaki.ecommerce.domain.itempedido.dto.ItemPedidoDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itens_pedidos")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double precoUnitario;
    private Integer quantidade;
    @ManyToOne(fetch = FetchType.LAZY)
    private Produto produto;
    @ManyToOne(fetch = FetchType.LAZY)
    private Pedido pedido;

    public ItemPedido(ItemPedidoDto item, Produto produto) {
        this.precoUnitario = item.precoUnitario();
        this.quantidade = item.quantidade();
        this.produto = produto;
    }

    public void adicionarPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
