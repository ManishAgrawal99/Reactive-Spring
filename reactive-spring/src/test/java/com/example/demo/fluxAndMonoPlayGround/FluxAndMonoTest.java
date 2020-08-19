package com.example.demo.fluxAndMonoPlayGround;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {
	
//	@Test
//	public void fluxTest() {
//		
//		Flux<String> stringFlux = Flux.just("Spring" ,"Spring Boot", "Reactive Spring")
//									  //.concatWith(Flux.error(new RuntimeException("Exception Occured")))
//									  .concatWith(Flux.just("After Error"))
//									  .log();
//		
//		stringFlux.subscribe(System.out::println,
//				(e) -> System.err.println(e),
//				() -> System.out.println("Completed"));
//		
//	}
	 
	@Test
	public void fluxTestElements_WithoutErrors() {
		Flux<String> stringFlux = Flux.just("Spring" ,"Spring Boot", "Reactive Spring")
									.log();
		
		StepVerifier.create(stringFlux)
					.expectNext("Spring")
					.expectNext("Spring Boot")
					.expectNext("Reactive Spring")
					.verifyComplete();
		
	}
	
	
	@Test
	public void fluxTestElements_WithErrors() {
		Flux<String> stringFlux = Flux.just("Spring" ,"Spring Boot", "Reactive Spring")
									.concatWith(Flux.error(new RuntimeException("Exception Occured")))
									.log();
		
		StepVerifier.create(stringFlux)
					.expectNext("Spring")
					.expectNext("Spring Boot")
					.expectNext("Reactive Spring")
					.expectError(RuntimeException.class)
//					.expectErrorMessage("Exception Occured")
					.verify();
//					.verifyComplete();
		
	}
	
	
	@Test
	public void fluxTestElements_Count() {
		Flux<String> stringFlux = Flux.just("Spring" ,"Spring Boot", "Reactive Spring")
									.concatWith(Flux.error(new RuntimeException("Exception Occured")))
									.log();
		
		StepVerifier.create(stringFlux)
					.expectNextCount(3)
					.expectErrorMessage("Exception Occured")
					.verify();
		
	}
	
	
	@Test
	public void monoTest() {
		
		Mono<String> stringMono = Mono.just("spring");
		
		StepVerifier.create(stringMono.log())
					.expectNext("spring")
					.verifyComplete();
		
		
	}
	
	
	
	@Test
	public void monoTest_Error() {
		
		Mono<String> stringMono = Mono.just("spring");
		
		StepVerifier.create(Mono.error(new RuntimeException("Exception Occured")))
					.expectError(RuntimeException.class)
					.verify();
		
		
	}
}
