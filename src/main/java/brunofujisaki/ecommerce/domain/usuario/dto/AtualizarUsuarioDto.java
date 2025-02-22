package brunofujisaki.ecommerce.domain.usuario.dto;

import jakarta.validation.constraints.NotNull;

public record AtualizarUsuarioDto(
        @NotNull
        Long id,
        String nome,
        String cpf
) {
}
