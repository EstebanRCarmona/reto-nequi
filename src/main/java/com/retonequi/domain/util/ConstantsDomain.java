package com.retonequi.domain.util;

public final  class ConstantsDomain {
    
    private ConstantsDomain(){
        throw new IllegalStateException("Utility class");
    }

    public static final String ERR_NAME_AND_FRANCHISE_ID_NULL = "Name and franchiseId cannot be null";
    public static final String ERR_BRANCH_ALREADY_EXISTS = "Branch name already exists";
    public static final String ERR_FRANCHISE_NOT_FOUND = "Franchise not found: ";
    public static final String ERR_NAME_NULL = "Name cannot be null";
    public static final String ERR_FRANCHISE_ALREADY_EXISTS = "Franchise already exists";
    public static final String ERR_FRANCHISE_NOT_FOUND_ID = "Franchise not found: ";

}
