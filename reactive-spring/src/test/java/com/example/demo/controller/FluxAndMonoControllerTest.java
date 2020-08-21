package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

//@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxAndMonoControllerTest {

	
	@Autowired
	WebTestClient webTestClient; 
	
	@Test
	public void flux_approach1() {
		
		@SuppressWarnings("deprecation")
		Flux<Integer> integerFlux = webTestClient.get().uri("/flux")
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.exchange()
					.expectStatus().isOk()
					.returnResult(Integer.class)
					.getResponseBody();
		
		StepVerifier.create(integerFlux)
					.expectSubscription()
					.expectNext(1)
					.expectNext(2)
					.expectNext(3)
					.expectNext(4)
					.verifyComplete();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void flux_approach2() {
		
		
		webTestClient.get().uri("/flux")
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.exchange()
					.expectStatus().isOk()
					.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
					.expectBodyList(Integer.class)
					.hasSize(4);
		
		
	}
	
	
	@Test
	public void flux_approach3() {
		
		
		List<Integer> expectedIntegerList = Arrays.asList(1,2,3,4);

        @SuppressWarnings("deprecation")
		EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient
                .get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();

        assertEquals(expectedIntegerList,entityExchangeResult.getResponseBody());
		
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
    public void flux_approach4(){

        List<Integer> expectedIntegerList = Arrays.asList(1,2,3,4);

         webTestClient
                .get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .consumeWith((response) -> {
                    assertEquals(expectedIntegerList, response.getResponseBody());
                });

    }
	
	
	@Test
    public void fluxStream(){

        Flux<Long> longStreamFlux = webTestClient.get().uri("/fluxstream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();


        StepVerifier.create(longStreamFlux)
                .expectNext(0l)
                .expectNext(1l)
                .expectNext(2l)
                .thenCancel()
                .verify();


    }
	
	
}
