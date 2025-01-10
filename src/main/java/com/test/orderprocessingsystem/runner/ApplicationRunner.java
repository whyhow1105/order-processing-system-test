package com.test.orderprocessingsystem.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.test.orderprocessingsystem.constant.Currency;
import com.test.orderprocessingsystem.constant.CustomerType;
import com.test.orderprocessingsystem.entity.Product;
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
		
	}

}
