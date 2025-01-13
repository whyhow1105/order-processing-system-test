package com.test.orderprocessingsystem.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.test.orderprocessingsystem.constant.Currency;
import com.test.orderprocessingsystem.constant.CustomerType;
import com.test.orderprocessingsystem.model.Product;
import com.test.orderprocessingsystem.usecase.ProductUseCase;

@Component
public class ApplicationRunner implements CommandLineRunner {
	
	private final ProductUseCase productUseCase;

	public ApplicationRunner(ProductUseCase productUseCase) {
		this.productUseCase = productUseCase;
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		Product product = new Product();
		
		product.setType("ABC");
		product.setAllowSpecificCurrency(true);
		product.setAllowCurrency(Currency.USD.name());
		product.setAllowSpecificCustomerType(true);
		product.setAllowCustomerType(CustomerType.VIP.name());
		product.setDisallowSpecificCurrency(true);
		product.setDisallowCurrency(Currency.MYR.name());
		
		productUseCase.addProduct(product);
		
		Product product2 = new Product();
		product2.setType("XYZ");
		product2.setAllowSpecificCurrency(false);
		product2.setAllowSpecificCustomerType(false);
		product2.setDisallowSpecificCurrency(true);
		product2.setDisallowCurrency(Currency.USD.name());
		
		productUseCase.addProduct(product2);
		
		Product product3 = new Product();
		product3.setType("XYZ");
		product3.setAllowSpecificCurrency(false);
		product3.setAllowSpecificCustomerType(false);
		product3.setDisallowSpecificCurrency(true);
		product3.setDisallowCurrency(Currency.JPY.name());
		
		productUseCase.addProduct(product3);
		
		Product product4 = new Product();
		product4.setType("AAA");
		product4.setAllowSpecificCurrency(false);
		product4.setAllowSpecificCustomerType(false);
		product4.setDisallowSpecificCurrency(false);
		
		productUseCase.addProduct(product4);
		
	}

}
