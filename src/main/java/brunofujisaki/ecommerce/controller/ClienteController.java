package brunofujisaki.ecommerce.controller;

import brunofujisaki.ecommerce.domain.cliente.dto.AtualizarClienteDto;
import brunofujisaki.ecommerce.domain.cliente.dto.ClienteDto;
import brunofujisaki.ecommerce.infra.exception.ValidacaoException;
import brunofujisaki.ecommerce.domain.cliente.Cliente;
import brunofujisaki.ecommerce.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarCliente(@RequestBody @Valid ClienteDto clienteDto, UriComponentsBuilder uriBuilder) {
        Cliente cliente = new Cliente(clienteDto);
        clienteRepository.save(cliente);

        var uri = uriBuilder.path("/clientes/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(cliente);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDto>> listarClientes(Pageable pageable) {
        var page = clienteRepository.findAllByAtivoTrue(pageable).map(ClienteDto::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarCliente(@RequestBody @Valid AtualizarClienteDto atualizarClienteDto) {
        var cliente = clienteRepository.findById(atualizarClienteDto.id()).orElseThrow(() -> new ValidacaoException("Cliente não encontrado."));
        cliente.atualizarInfo(atualizarClienteDto);
        return ResponseEntity.ok(new ClienteDto(cliente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desativarUsuario(@PathVariable Long id) {
        var cliente = clienteRepository.findById(id).orElseThrow(() -> new ValidacaoException("Cliente não encontrado."));
        cliente.desativarCliente();
        return ResponseEntity.noContent().build();
    }
}
