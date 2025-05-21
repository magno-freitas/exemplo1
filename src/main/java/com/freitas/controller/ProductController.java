package com.freitas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.freitas.model.Produto;
import com.freitas.service.ProductService;
public class ProductController {
    @Autowired
     ProductService productService;
     
@PostMapping("/products")
    public void createProduct(@RequestBody Produto produto) {
        productService.inserirProduto(produto);
    }

    @GetMapping("/products")
    public List<Produto> getAllProducts() {
        return productService.listarProdutos();
    }
}
