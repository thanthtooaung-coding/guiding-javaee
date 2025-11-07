package com.springboot.productmanagement.service.impl;

import com.springboot.productmanagement.dto.ProductRequestDto;
import com.springboot.productmanagement.dto.ProductResponseDto;
import com.springboot.productmanagement.exception.EntityExistsException;
import com.springboot.productmanagement.exception.EntityNotFoundException;
import com.springboot.productmanagement.mapper.ProductMapper;
import com.springboot.productmanagement.model.Product;
import com.springboot.productmanagement.repository.ProductRepository;
import com.springboot.productmanagement.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createProduct(ProductRequestDto productRequest) {
        if(productRequest != null) {
            if(productRepository.existsByName(productRequest.getName())){
                throw new EntityExistsException("Product with name " + productRequest.getName() + " already exists");
            }

            /*
            Product product = new Product();
            product.setName(productRequest.name());
            product.setShortDescription(productRequest.shortDescription());
            product.setLongDescription(productRequest.longDescription());
            product.setPrice(productRequest.price());
             */

            Product product = modelMapper.map(productRequest, Product.class);
            product.setCreatedAt(LocalDateTime.now());

            this.productRepository.save(product);
        }
    }

    @Override
    public void updateProduct(Long id, ProductRequestDto productRequest) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id: " + id + " does not exist"));

        if (productRepository.existsByName(productRequest.getName())
                && existingProduct.getName().equals(productRequest.getName())) {
            throw new RuntimeException("Change the name or something bruh ;-;");
        }

        modelMapper.map(productRequest, existingProduct);
        /*existingProduct.setName(productRequest.name());
        existingProduct.setShortDescription(productRequest.shortDescription());
        existingProduct.setLongDescription(productRequest.longDescription());
        existingProduct.setPrice(productRequest.price());*/
        existingProduct.setUpdatedAt(LocalDateTime.now());

        productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if(!this.productRepository.existsById(id)){
            throw new EntityNotFoundException("product with id: " + id + " does not exist");
        }
        this.productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return this.productRepository.findAll()
                /*
                .stream().map(ProductMapper::toProductResponseDto).toList();
                 */
                /*
                .stream()
                .map(product -> ProductMapper.toProductResponseDto(product))
                .toList();
                 */
                .stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .toList();
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        if(!this.productRepository.existsById(id)){
            throw new EntityNotFoundException("product with id: " + id + " does not exist");
        }

        Optional<Product> productOptional = this.productRepository.findById(id);
        return productOptional.map(
                /*
                ProductMapper::toProductResponseDto
                 */
                /*
                product -> ProductMapper.toProductResponseDto(product)
                 */
                product -> modelMapper.map(product, ProductResponseDto.class)
        ).orElse(null);

    }
}
