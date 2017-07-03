package com.animus.delta;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/products")
public class ProductRestController  {

	private ProductRepository productRepository;
	
	ProductRestController(ProductRepository productRepository){
		this.productRepository = productRepository;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add( @RequestBody Product input) {
		System.out.println("input.name=" + input.getName());
		System.out.println("input.description=" + input.getDescription());
		Product product = this.productRepository.save(new Product(input.getName(),input.getDescription()));
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(product.getId()).toUri();
		return ResponseEntity.created(location).build();
		
	}
	

	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable long id) {
		Product product = this.productRepository.findOne(id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable long id) {
		this.productRepository.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable long id,@RequestBody Product input) {
		Product product = this.productRepository.findOne(id);
		product .setName(input.getName());
		product.setDescription(input.getDescription());
		this.productRepository.save(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}	

	
	
	
	@RequestMapping(method = RequestMethod.GET)
	List<Product> readAllProducts() {
		return this.productRepository.findAll();
	}
	
	
	
	
}
