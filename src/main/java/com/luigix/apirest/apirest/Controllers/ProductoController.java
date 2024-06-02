package com.luigix.apirest.apirest.Controllers;

import com.luigix.apirest.apirest.Entities.Producto;
import com.luigix.apirest.apirest.Repositories.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> obtenerProductos() {
        logger.info("Obteniendo todos los productos");
        return productoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        logger.info("Recibido ID para buscar: {}", id);
        Producto producto = productoRepository.findById(id).orElse(null);

        if (producto != null) {
            logger.info("Producto encontrado: {}", producto);
            return ResponseEntity.ok(producto);
        } else {
            logger.warn("No se encontró el producto con el ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        logger.info("Creando nuevo producto: {}", producto);
        Producto nuevoProducto = productoRepository.save(producto);
        logger.info("Producto creado con ID: {}", nuevoProducto.getId());
        return ResponseEntity.status(201).body(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto detallesProducto) {
        logger.info("Actualizando producto con ID: {}", id);
        Producto producto = productoRepository.findById(id).orElse(null);

        if (producto != null) {
            producto.setNombre(detallesProducto.getNombre());
            producto.setPrecio(detallesProducto.getPrecio());
            Producto productoActualizado = productoRepository.save(producto);
            logger.info("Producto actualizado: {}", productoActualizado);
            return ResponseEntity.ok(productoActualizado);
        } else {
            logger.warn("No se encontró el producto con el ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarProducto(@PathVariable Long id) {
        logger.info("Borrando producto con ID: {}", id);
        Producto producto = productoRepository.findById(id).orElse(null);

        if (producto != null) {
            productoRepository.delete(producto);
            logger.info("Producto con ID: {} fue eliminado", id);
            return ResponseEntity.ok("El producto con el ID: " + id + " fue eliminado correctamente");
        } else {
            logger.warn("No se encontró el producto con el ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}