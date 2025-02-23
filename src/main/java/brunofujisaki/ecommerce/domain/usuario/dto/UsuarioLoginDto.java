package brunofujisaki.ecommerce.domain.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginDto(
        @NotBlank
        String email,
        @NotBlank
        String senha
) {
}
