package brunofujisaki.ecommerce.controller;

import brunofujisaki.ecommerce.domain.usuario.Usuario;
import brunofujisaki.ecommerce.domain.usuario.dto.DetalharUsuarioDto;
import brunofujisaki.ecommerce.domain.usuario.dto.UsuarioDto;
import brunofujisaki.ecommerce.domain.usuario.dto.UsuarioLoginDto;
import brunofujisaki.ecommerce.infra.security.TokenJwtDto;
import brunofujisaki.ecommerce.infra.security.TokenService;
import brunofujisaki.ecommerce.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid UsuarioDto usuarioDto, UriComponentsBuilder uriBuilder) {
        var senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDto.senha());
        Usuario usuario = new Usuario(usuarioDto, senhaCriptografada);
        usuarioRepository.save(usuario);

        var uri = uriBuilder.path("/clientes/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalharUsuarioDto(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity logarUsuario(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto) {
        var authToken = new UsernamePasswordAuthenticationToken(usuarioLoginDto.email(), usuarioLoginDto.senha());
        var auth = manager.authenticate(authToken);
        var tokenJwt = tokenService.gerarToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new TokenJwtDto(tokenJwt));
    }
}
