package brunofujisaki.ecommerce.controller;

import brunofujisaki.ecommerce.domain.produto.dto.AtualizarProdutoDto;
import brunofujisaki.ecommerce.domain.produto.dto.DetalharProdutoDto;
import brunofujisaki.ecommerce.domain.produto.dto.ProdutoDto;
import brunofujisaki.ecommerce.infra.exception.ValidacaoException;
import brunofujisaki.ecommerce.domain.produto.Produto;
import brunofujisaki.ecommerce.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    public ResponseEntity cadastrarProduto(@RequestBody @Valid ProdutoDto produtoDto, UriComponentsBuilder uriBuilder) {
        var produto = new Produto(produtoDto);
        produtoRepository.save(produto);
        var uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(produto);
    }

    @GetMapping
    public ResponseEntity<Page<DetalharProdutoDto>> listarProdutos(Pageable pageable) {
        var page = produtoRepository.findAll(pageable).map(DetalharProdutoDto::new);
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
        var produto = produtoRepository.findById(id).orElseThrow(() -> new ValidacaoException("Produto não encontrado"));
        produto.desativarProduto();
        return ResponseEntity.noContent().build();
    }

}
