package com.retonequi.domain.services;

import com.retonequi.domain.exception.ErrorNotFound;
import com.retonequi.domain.interfaces.IFranchisePersistence;
import com.retonequi.domain.interfaces.IPaginator;
import com.retonequi.domain.model.Franchise;
import com.retonequi.domain.model.PageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FranchiseServiceTest {
    private IFranchisePersistence franchisePersistence;
    private IPaginator<Franchise> paginator;
    private FranchiseService franchiseService;

    @BeforeEach
    void setUp() {
        franchisePersistence = Mockito.mock(IFranchisePersistence.class);
        paginator = Mockito.mock(IPaginator.class);
        franchiseService = new FranchiseService(franchisePersistence, paginator);
    }

    @Test
    void testCreateFranchise_Success() {
        when(franchisePersistence.findByName(anyString())).thenReturn(Mono.empty());
        when(franchisePersistence.save(any(Franchise.class))).thenReturn(Mono.just(new Franchise(1L, "Franchise1")));
        Mono<Franchise> result = franchiseService.createFranchise("Franchise1");
        StepVerifier.create(result)
            .expectNextMatches(franchise -> franchise.getName().equals("Franchise1"))
            .verifyComplete();
    }


    @Test
    void testUpdateFranchiseName_Success() {
        Franchise existing = new Franchise(1L, "OldName");
        when(franchisePersistence.findById(1L)).thenReturn(Mono.just(existing));
        when(franchisePersistence.updateName(1L, "NewName")).thenReturn(Mono.just(new Franchise(1L, "NewName")));
        Mono<Franchise> result = franchiseService.updateFranchiseName(1L, "NewName");
        StepVerifier.create(result)
            .expectNextMatches(franchise -> franchise.getName().equals("NewName"))
            .verifyComplete();
    }

    @Test
    void testUpdateFranchiseName_NotFound() {
        when(franchisePersistence.findById(anyLong())).thenReturn(Mono.empty());
        Mono<Franchise> result = franchiseService.updateFranchiseName(1L, "NewName");
        StepVerifier.create(result)
            .expectErrorSatisfies(ex -> {
                assertTrue(ex instanceof ErrorNotFound);
                assertTrue(ex.getMessage().contains("not found"));
            })
            .verify();
    }

    @Test
    void testGetAllFranchisesPaged() {
        when(paginator.count()).thenReturn(Mono.just(1L));
        when(paginator.findPage(anyInt(), anyInt())).thenReturn(Flux.just(new Franchise(1L, "FranchiseA")));
        Mono<PageResponse<Franchise>> result = franchiseService.getAllFranchisesPaged(0, 10);
        StepVerifier.create(result)
            .expectNextMatches(page -> page.getContent().size() == 1 && page.getContent().get(0).getName().equals("FranchiseA"))
            .verifyComplete();
    }

    @Test
    void testGetAllFranchisesPaged_Empty() {
        when(paginator.count()).thenReturn(Mono.just(0L));
        when(paginator.findPage(anyInt(), anyInt())).thenReturn(Flux.empty());
        Mono<PageResponse<Franchise>> result = franchiseService.getAllFranchisesPaged(0, 10);
        StepVerifier.create(result)
            .expectNextMatches(page -> page.getContent().isEmpty() && page.getTotalElements() == 0)
            .verifyComplete();
    }

    @Test
    void testGetAllFranchisesPaged_MultiplePages() {
        when(paginator.count()).thenReturn(Mono.just(3L));
        when(paginator.findPage(0, 2)).thenReturn(Flux.just(
            new Franchise(1L, "F1"),
            new Franchise(2L, "F2")
        ));
        when(paginator.findPage(1, 2)).thenReturn(Flux.just(
            new Franchise(3L, "F3")
        ));
        
        Mono<PageResponse<Franchise>> page0 = franchiseService.getAllFranchisesPaged(0, 2);
        StepVerifier.create(page0)
            .expectNextMatches(page -> page.getContent().size() == 2 && page.getTotalElements() == 3)
            .verifyComplete();
        
        Mono<PageResponse<Franchise>> page1 = franchiseService.getAllFranchisesPaged(1, 2);
        StepVerifier.create(page1)
            .expectNextMatches(page -> page.getContent().size() == 1 && page.getTotalElements() == 3)
            .verifyComplete();
    }
}
