package brunofujisaki.ecommerce.domain.cliente;

import brunofujisaki.ecommerce.domain.cliente.dto.AtualizarClienteDto;
import brunofujisaki.ecommerce.domain.cliente.dto.ClienteDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private Boolean ativo;

    public Cliente(ClienteDto clienteDto) {
        this.nome = clienteDto.nome();
        this.cpf = clienteDto.cpf();
        this.ativo = true;
    }

    public void atualizarInfo(AtualizarClienteDto atualizarClienteDto) {
        if (atualizarClienteDto.nome() != null)
            this.nome = atualizarClienteDto.nome();

        if (atualizarClienteDto.cpf() != null)
            this.cpf = atualizarClienteDto.cpf();
    }

    public void desativarCliente() {
        this.ativo = false;
    }
}
