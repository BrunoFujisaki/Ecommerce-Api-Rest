package brunofujisaki.ecommerce.domain.usuario.dto;

import brunofujisaki.ecommerce.domain.usuario.UserRole;
import brunofujisaki.ecommerce.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioDto(
        @NotBlank
        String nome,
        @NotBlank
        String email,
        @NotBlank
        String cpf,
        @NotBlank
        String senha,
        @NotNull
        UserRole role
) {
    public UsuarioDto(Usuario usuario) {
        this(usuario.getNome(), usuario.getCpf(), usuario.getCpf(), usuario.getSenha(), usuario.getRole());
    }
}
