package brunofujisaki.ecommerce.domain.usuario.dto;

import brunofujisaki.ecommerce.domain.usuario.UserRole;
import brunofujisaki.ecommerce.domain.usuario.Usuario;

public record UsuarioDto(
        String nome,
        String email,
        String cpf,
        String senha,
        UserRole role
) {
    public UsuarioDto(Usuario usuario) {
        this(usuario.getNome(), usuario.getCpf(), usuario.getCpf(), usuario.getSenha(), usuario.getRole());
    }
}
