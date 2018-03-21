package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(1000);
        assertEquals("saldo: 20.0", kortti.toString());  
    }
    
    @Test
    public void saldoVaheneeOikeinJosRahaaOnTarpeeksi() {
        kortti.otaRahaa(200);
        assertEquals("saldo: 8.0", kortti.toString());         
    }
    
    @Test
    public void saldoEiMuutuJosRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(1001);
        assertEquals("saldo: 10.0", kortti.toString()); 
    }
    
    @Test
    public void metodiPalauttaaTrueJosRahatRiittivatMuutenFalse() {
        assertFalse(kortti.otaRahaa(1001));  
        assertTrue(kortti.otaRahaa(1000));  
    }
    
    @Test
    public void kertookoOikeanSaldon() {
        assertEquals(1000, kortti.saldo());
    }
}
