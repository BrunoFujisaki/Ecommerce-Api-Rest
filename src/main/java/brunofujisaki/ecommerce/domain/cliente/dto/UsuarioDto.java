package brunofujisaki.ecommerce.domain.cliente.dto;

import brunofujisaki.ecommerce.domain.cliente.UserRole;
import brunofujisaki.ecommerce.domain.cliente.Usuario;

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
