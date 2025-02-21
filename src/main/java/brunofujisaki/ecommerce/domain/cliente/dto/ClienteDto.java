package brunofujisaki.ecommerce.domain.cliente.dto;

import brunofujisaki.ecommerce.domain.cliente.Cliente;

public record ClienteDto(
        String nome,
        String cpf
) {
    public ClienteDto(Cliente cliente) {
        this(cliente.getNome(), cliente.getCpf());
    }
}
