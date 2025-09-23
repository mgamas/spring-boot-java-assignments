package org.adaschool.api.service.product;

import org.adaschool.api.repository.product.Product;

import java.util.Optional;

public interface ProductsService {

    Product save(Product product);                    // guarda y retorna el producto (con id)
    Optional<Product> findById(String id);            // busca por id
    Product update(Product product, String id);       // actualiza y retorna; null si no existe
    void deleteById(String id);                       // elimina por id
}
