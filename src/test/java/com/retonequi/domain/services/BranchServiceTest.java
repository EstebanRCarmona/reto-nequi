package com.retonequi.domain.services;

import com.retonequi.domain.interfaces.IBranchPersistence;
import com.retonequi.domain.interfaces.IFranchisePersistence;
import com.retonequi.domain.model.Branch;
import com.retonequi.domain.model.Franchise;
import com.retonequi.domain.model.PageResponse;
import com.retonequi.domain.model.responses.BranchWithFranchise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class BranchServiceTest {
    private IBranchPersistence branchPersistence;
    private IFranchisePersistence franchisePersistence;
    private BranchService branchService;

    @BeforeEach
    void setUp() {
        branchPersistence = Mockito.mock(IBranchPersistence.class);
        franchisePersistence = Mockito.mock(IFranchisePersistence.class);
        branchService = new BranchService(branchPersistence, franchisePersistence);
    }

    @Test
    void testCreateBranch() {
        Branch mockBranch = new Branch(1L, "Main Branch", 2L);
        when(branchPersistence.save(any(Branch.class))).thenReturn(Mono.just(mockBranch));
        when(branchPersistence.findByNameIgnoreCase(anyString())).thenReturn(Mono.empty());
        when(franchisePersistence.findById(anyLong())).thenReturn(Mono.just(new Franchise(2L, "Franchise")));
        Mono<Branch> result = branchService.createBranch("Main Branch", 2L);
        Branch branch = result.block();
        assertNotNull(branch);
        assertEquals("Main Branch", branch.getName());
    }

    @Test
    void testGetAllBranchesWithFranchisePaged() {
        when(branchPersistence.findAll()).thenReturn(Flux.just(new Branch(1L, "Branch1", 2L)));
        when(franchisePersistence.findById(anyLong())).thenReturn(Mono.just(new Franchise(2L, "Franchise")));
        Mono<PageResponse<BranchWithFranchise>> result = branchService.getAllBranchesWithFranchisePaged(0, 10);
        PageResponse<BranchWithFranchise> page = result.block();
        assertNotNull(page);
        assertEquals(1, page.getContent().size());
    }


    @Test
    void testCreateBranch_FranchiseNotFound() {
        when(branchPersistence.findByNameIgnoreCase(anyString())).thenReturn(Mono.empty());
        when(franchisePersistence.findById(anyLong())).thenReturn(Mono.empty());
        Mono<Branch> result = branchService.createBranch("Sucursal", 99L);
        Exception ex = assertThrows(com.retonequi.domain.exception.ErrorNotFound.class, result::block);
        assertTrue(ex.getMessage().contains(com.retonequi.domain.util.ConstantsDomain.ERR_FRANCHISE_NOT_FOUND + "99"));
    }

    @Test
    void testGetAllBranchesWithFranchisePaged_LessThanPageSize() {
        when(branchPersistence.findAll()).thenReturn(Flux.just(new Branch(1L, "Branch1", 2L)));
        when(franchisePersistence.findById(anyLong())).thenReturn(Mono.just(new Franchise(2L, "Franchise")));
        Mono<PageResponse<BranchWithFranchise>> result = branchService.getAllBranchesWithFranchisePaged(0, 10);
        PageResponse<BranchWithFranchise> page = result.block();
        assertNotNull(page);
        assertEquals(1, page.getContent().size());
    }

    @Test
    void testGetAllBranchesWithFranchisePaged_FranchiseNotFoundForBranch() {
        when(branchPersistence.findAll()).thenReturn(Flux.just(new Branch(1L, "Branch1", 2L)));
        when(franchisePersistence.findById(anyLong())).thenReturn(Mono.empty());
        Mono<PageResponse<BranchWithFranchise>> result = branchService.getAllBranchesWithFranchisePaged(0, 10);
        PageResponse<BranchWithFranchise> page = result.block();
        assertNotNull(page);
        assertEquals(0, page.getContent().size()); // No debe incluir branches sin franquicia
    }
}
