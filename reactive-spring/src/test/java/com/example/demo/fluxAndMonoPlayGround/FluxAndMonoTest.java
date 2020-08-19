package com.example.demo.fluxAndMonoPlayGround;

import org.junit.Test;

import reactor.core.publisher.Flux;

public class FluxAndMonoTest {
	
	@Test
	public void fluxTest() {
		
		Flux<String> stringFlux = Flux.just("Spring" ,"Spring Boot", "Reactive Spring")
									  .concatWith(Flux.error(new RuntimeException("Exception Occured")));
		
		stringFlux.subscribe(System.out::println,
				(e) -> System.err.println(e));
		
		
		
	}
}
