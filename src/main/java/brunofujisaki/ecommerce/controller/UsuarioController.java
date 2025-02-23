package brunofujisaki.ecommerce.controller;

import brunofujisaki.ecommerce.domain.usuario.dto.AtualizarUsuarioDto;
import brunofujisaki.ecommerce.domain.usuario.dto.DetalharUsuarioDto;
import brunofujisaki.ecommerce.domain.usuario.dto.UsuarioDto;
import brunofujisaki.ecommerce.infra.exception.ValidacaoException;
import brunofujisaki.ecommerce.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<Page<DetalharUsuarioDto>> listarUsuarios(@PageableDefault(size = 10, sort={"id"}) Pageable pageable) {
        var page = usuarioRepository.findAllByAtivoTrue(pageable).map(DetalharUsuarioDto::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarUsuario(@RequestBody @Valid AtualizarUsuarioDto atualizarUsuarioDto) {
        var usuario = usuarioRepository.findById(atualizarUsuarioDto.id()).orElseThrow(() -> new ValidacaoException("Usuario não encontrado."));
        usuario.atualizarInfo(atualizarUsuarioDto);
        return ResponseEntity.ok(new DetalharUsuarioDto(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desativarUsuario(@PathVariable Long id) {
        var usuario = usuarioRepository.findById(id).orElseThrow(() -> new ValidacaoException("Usuario não encontrado."));
        usuario.desativarCliente();
        return ResponseEntity.noContent().build();
    }
}
