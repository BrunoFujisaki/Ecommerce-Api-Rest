package brunofujisaki.ecommerce.controller;

import brunofujisaki.ecommerce.domain.cliente.Usuario;
import brunofujisaki.ecommerce.domain.cliente.dto.AtualizarUsuarioDto;
import brunofujisaki.ecommerce.domain.cliente.dto.DetalharUsuarioDto;
import brunofujisaki.ecommerce.domain.cliente.dto.UsuarioDto;
import brunofujisaki.ecommerce.domain.cliente.dto.UsuarioLoginDto;
import brunofujisaki.ecommerce.infra.exception.ValidacaoException;
import brunofujisaki.ecommerce.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    @Transactional
    @RequestMapping("/cadastro")
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid UsuarioDto usuarioDto, UriComponentsBuilder uriBuilder) {
        var senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDto.senha());
        Usuario usuario = new Usuario(usuarioDto, senhaCriptografada);
        usuarioRepository.save(usuario);

        var uri = uriBuilder.path("/clientes/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalharUsuarioDto(usuario));
    }

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity logarUsuario(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto) {
        var authToken = new UsernamePasswordAuthenticationToken(usuarioLoginDto.email(), usuarioLoginDto.senha());
        var auth = manager.authenticate(authToken);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioDto>> listarUsuarios(Pageable pageable) {
        var page = usuarioRepository.findAllByAtivoTrue(pageable).map(UsuarioDto::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarUsuario(@RequestBody @Valid AtualizarUsuarioDto atualizarUsuarioDto) {
        var usuario = usuarioRepository.findById(atualizarUsuarioDto.id()).orElseThrow(() -> new ValidacaoException("Usuario não encontrado."));
        usuario.atualizarInfo(atualizarUsuarioDto);
        return ResponseEntity.ok(new UsuarioDto(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desativarUsuario(@PathVariable Long id) {
        var usuario = usuarioRepository.findById(id).orElseThrow(() -> new ValidacaoException("Usuario não encontrado."));
        usuario.desativarCliente();
        return ResponseEntity.noContent().build();
    }
}
