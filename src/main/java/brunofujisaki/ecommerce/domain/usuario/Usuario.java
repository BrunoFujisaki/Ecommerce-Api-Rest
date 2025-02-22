package brunofujisaki.ecommerce.domain.usuario;

import brunofujisaki.ecommerce.domain.usuario.dto.AtualizarUsuarioDto;
import brunofujisaki.ecommerce.domain.usuario.dto.UsuarioDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String senha;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private Boolean ativo;

    public Usuario(UsuarioDto usuarioDto, String senhaCriptografada) {
        this.nome = usuarioDto.nome();
        this.email = usuarioDto.email();
        this.cpf = usuarioDto.cpf();
        this.senha = senhaCriptografada;
        this.role = usuarioDto.role();
        this.ativo = true;
    }

    public void atualizarInfo(AtualizarUsuarioDto atualizarClienteDto) {
        if (atualizarClienteDto.nome() != null)
            this.nome = atualizarClienteDto.nome();

        if (atualizarClienteDto.cpf() != null)
            this.cpf = atualizarClienteDto.cpf();
    }

    public void desativarCliente() {
        this.ativo = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
