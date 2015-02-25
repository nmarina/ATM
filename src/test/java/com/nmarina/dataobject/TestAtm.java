package com.nmarina.dataobject;


import org.junit.Assert;
import org.junit.Test;


public class TestAtm {

    @Test
    public void testGetAtmBalance() {
        Atm atm = Atm.getAtmInstance();
        Assert.assertNotNull(atm.getAtmBalance());
    }

    @Test
    public void testGetCashFromAtm() {
        Atm atm = Atm.getAtmInstance();
        Assert.assertEquals(-1, atm.getCashFromAtm(null));
        Assert.assertEquals(-1, atm.getCashFromAtm(70));
    }

    @Test
    public void testAddCashToAtm() {
        Atm atm = Atm.getAtmInstance();
        Assert.assertFalse(atm.addCashToAtm(null));
        Assert.assertFalse(atm.addCashToAtm(-1));
        Assert.assertFalse(atm.addCashToAtm(10));
    }
}
