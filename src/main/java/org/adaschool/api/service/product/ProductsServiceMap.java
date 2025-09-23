package org.adaschool.api.service.product;

import org.adaschool.api.repository.product.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

    @Service
    public class ProductsServiceMap implements ProductsService {

        private final Map<String, Product> store = new HashMap<>();

    @Override
    public Product save(Product product) {
        if (product.getId() == null || product.getId().isBlank()) {
            product.setId(UUID.randomUUID().toString());
        }
        store.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Product update(Product product, String id) {
        if (!store.containsKey(id)) {
            return null;
        }
        product.setId(id);           // conservamos el id del recurso
        store.put(id, product);
        return product;
    }

    @Override
    public void deleteById(String id) {
        store.remove(id);
    }
}
