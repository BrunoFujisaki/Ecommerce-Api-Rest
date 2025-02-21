package brunofujisaki.ecommerce.domain.pedido;

import brunofujisaki.ecommerce.domain.itempedido.ItemPedido;
import brunofujisaki.ecommerce.domain.cliente.Cliente;
import brunofujisaki.ecommerce.domain.itempedido.dto.ItemPedidoDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    @ManyToOne
    private Cliente cliente;
    private Double valorTotal = 0.0;
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido(Cliente cliente) {
        this.data = LocalDate.now();
        this.cliente = cliente;
        this.status = StatusPedido.PENDENTE;
    }

    public void calcPrecoTotal(List<ItemPedidoDto> itens) {
        itens.forEach(i -> this.valorTotal += i.precoUnitario() * i.quantidade());
    }

    public void adicionarItem(ItemPedido item) {
        item.adicionarPedido(this);
        this.itens.add(item);
    }

    public void atualizarStatus(StatusPedido status) {
        this.status = status;
    }
}
