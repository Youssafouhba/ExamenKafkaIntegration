package org.productms.controller;


import org.productms.exceptions.ProductNotFoundException;
import org.productms.model.Product;
import org.productms.repository.ProductDao;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class ProductController {

    private final ProductDao productDao;
    public ProductController(ProductDao productDao){
        this.productDao = productDao;
    }

    // Affiche la liste de tous les produits disponibles
    @GetMapping(value = "/Produits")
    public List<Product> listeDesProduits(){

        List<Product> products = productDao.findAll();
        if(products.isEmpty()) throw new ProductNotFoundException("Aucun produit n'est disponible à la vente");
        return products;
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product){
        return  productDao.save(product);
    }

    //Récuperer un produit par son id
    @GetMapping( value = "/Produits/{id}")
    public Optional<Product> recupererUnProduit(@PathVariable int id) {

        Optional<Product> product = productDao.findById(id);

        if(!product.isPresent())  throw new ProductNotFoundException("Le produit correspondant à l'id " + id + " n'existe pas");

        return product;
    }
}