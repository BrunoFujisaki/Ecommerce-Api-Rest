package brunofujisaki.ecommerce.domain.cliente.dto;

import jakarta.validation.constraints.NotNull;

public record AtualizarClienteDto(
        @NotNull
        Long id,
        String nome,
        String cpf
) {
}
