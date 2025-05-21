package com.freitas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freitas.model.Produto;
import com.freitas.repo.ProductRepo;

import java.util.List;
@Service
public class ProductService {
        @Autowired
        ProductRepo productRepo;
        //create
        public void inserirProduto(Produto produto) {
            productRepo.save(produto);
        }
        //read
        public List<Produto> listarProdutos() {
            return productRepo.findAll();
        }
        //update
        public void atualizarProduto(Produto produto) {
            productRepo.save(produto);
        }
}
