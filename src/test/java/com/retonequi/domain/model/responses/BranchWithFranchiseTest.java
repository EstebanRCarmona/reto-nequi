package com.retonequi.domain.model.responses;

import com.retonequi.domain.model.Branch;
import com.retonequi.domain.model.Franchise;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BranchWithFranchiseTest {
    @Test
    void testConstructorAndGetters() {
        Branch branch = new Branch(1L, "Branch1", 2L);
        Franchise franchise = new Franchise(2L, "Franchise1");
        BranchWithFranchise bwf = new BranchWithFranchise(branch, franchise);
        assertEquals(branch, bwf.getBranch());
        assertEquals(franchise, bwf.getFranchise());
    }
}
