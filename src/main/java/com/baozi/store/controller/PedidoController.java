
package com.baozi.store.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.baozi.store.model.Pedido;
import com.baozi.store.repository.PedidoRepository;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoRepository repository;

    public PedidoController(PedidoRepository repository) {
        this.repository = repository;
    }

    // Cadastrar pedido
    @PostMapping
    public Pedido cadastrar(@RequestBody Pedido pedido) {
        return repository.save(pedido);
    }

    // Listar todos os pedidos
    @GetMapping
    public List<Pedido> listar() {
        return repository.findAll();
    }

    // Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(
            @PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id));
    }

    // Atualizar pedido
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(
            @PathVariable Long id,
            @RequestBody Pedido pedidoAtualizado) {

        return repository.findById(id)
                .map(pedido -> {
                    pedido.setClienteId(pedidoAtualizado.getClienteId());
                    pedido.setProdutoId(pedidoAtualizado.getProdutoId());
                    pedido.setQuantidade(pedidoAtualizado.getQuantidade());

                    Pedido atualizado = repository.save(pedido);

                    return ResponseEntity.ok(atualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Excluir pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
