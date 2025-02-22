package brunofujisaki.ecommerce.domain.cliente.dto;

import brunofujisaki.ecommerce.domain.cliente.UserRole;
import brunofujisaki.ecommerce.domain.cliente.Usuario;

public record DetalharUsuarioDto(
        Long id,
        String nome,
        String email,
        String cpf,
        String senha,
        UserRole role,
        Boolean ativo
) {
    public DetalharUsuarioDto(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getCpf(), usuario.getSenha(), usuario.getRole(), usuario.getAtivo());
    }
}
