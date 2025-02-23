package brunofujisaki.ecommerce.controller;

import brunofujisaki.ecommerce.domain.produto.Produto;
import brunofujisaki.ecommerce.domain.produto.dto.AtualizarProdutoDto;
import brunofujisaki.ecommerce.domain.produto.dto.DetalharProdutoDto;
import brunofujisaki.ecommerce.domain.produto.dto.ProdutoDto;
import brunofujisaki.ecommerce.repository.ProdutoRepository;
import brunofujisaki.ecommerce.service.ProdutoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("produtos")
@SecurityRequirement(name = "bearer-key")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity cadastrarProduto(@RequestBody @Valid ProdutoDto produtoDto, UriComponentsBuilder uriBuilder) {
        var produto = new Produto(produtoDto);
        produtoRepository.save(produto);
        var uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(produto);
    }

    @GetMapping
    public ResponseEntity<Page<DetalharProdutoDto>> listarProdutos(@PageableDefault(size = 10, sort={"id"}) Pageable pageable, Authentication authentication) {
        var page = produtoService.verificar(pageable, authentication);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarProduto(@RequestBody @Valid AtualizarProdutoDto produtoDto) {
        var produto = produtoRepository.findById(produtoDto.id()).orElseThrow(() -> new ValidationException("Produto não encontrado."));
        produto.atualizarProduto(produtoDto);
        return ResponseEntity.ok(new DetalharProdutoDto(produto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desativarProduto(@PathVariable Long id) {
        produtoService.desativarProduto(id);
        return ResponseEntity.noContent().build();
    }

}
