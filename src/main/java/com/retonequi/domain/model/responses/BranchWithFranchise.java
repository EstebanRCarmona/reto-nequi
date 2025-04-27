package com.retonequi.domain.model.responses;

import com.retonequi.domain.model.Branch;
import com.retonequi.domain.model.Franchise;

public class BranchWithFranchise {
    private final Branch branch;
    private final Franchise franchise;

    public BranchWithFranchise(Branch branch, Franchise franchise) {
        this.branch = branch;
        this.franchise = franchise;
    }
    public Branch getBranch() { return branch; }
    public Franchise getFranchise() { return franchise; }
}
