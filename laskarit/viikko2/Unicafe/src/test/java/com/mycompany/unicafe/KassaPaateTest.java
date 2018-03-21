/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hdheli
 */
public class KassaPaateTest {
    
        Kassapaate paate;
    
    public KassaPaateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        paate= new Kassapaate();
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void luodunPaatteenRahaOikein() {
        assertEquals(100000, paate.kassassaRahaa());
    }
    
     @Test
    public void luodunPaatteenMyydytOikein() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void josMaksuRiittavaEdullisenVaihtoRahaOk() {
        assertEquals(60, paate.syoEdullisesti(300));  
    }
    
    @Test
    public void josMaksuRiittavaMaukkaanVaihtoRahaOk() {
        assertEquals(100, paate.syoMaukkaasti(500));  
    }
    
    @Test
    public void rahanLatausKortilleOnnistuu() {
        Maksukortti kortti = new Maksukortti(10000);  
        paate.lataaRahaaKortille(kortti, 100);
        assertEquals(100100, paate.kassassaRahaa());
    }
    
    @Test
    public void maksuEiRiitaEdullinenKassanSummaEiMuutu () {
        paate.syoEdullisesti(200);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
     @Test
    public void maksuEiRiitaMaukasKassanSummaEiMuutu () {
        paate.syoMaukkaasti(200);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
     @Test
    public void maksuEiRiitaEdullinenKaikkiRahatTakas () {
        assertEquals(200, paate.syoEdullisesti(200));
    }
    
    @Test
    public void maksuEiRiitaMaukasKaikkiRahatTakas () {
        assertEquals(200, paate.syoMaukkaasti(200));
    }
    
    @Test
    public void maksuEiRiitaEdullinenMyytyjenMaaraSailyy() {
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maksuEiRiitaMaukasMyytyjenMaaraSailyy() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void josKorttiSaldoOkEdullinenVeloitetaanKortilta(){
        Maksukortti kortti = new Maksukortti(10000); 
        paate.syoEdullisesti(kortti);
        assertEquals(9760, kortti.saldo());
    }
    
 
    @Test
    public void josKorttiSaldoRiittavaEdullinenKassaOk() {
        Maksukortti kortti = new Maksukortti(10000);  
        paate.syoEdullisesti(kortti);
        assertEquals(100000, paate.kassassaRahaa());  
    }
    
    @Test
    public void josKorttiSaldoRiittavaMaukasKassaOk() {
        Maksukortti kortti = new Maksukortti(10000);  
        paate.syoMaukkaasti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
        @Test
    public void josKorttiSaldoRiittavaEdullinenMyynnitOikein () {
       Maksukortti kortti = new Maksukortti(10000);  
       paate.syoEdullisesti(kortti);
       assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
     @Test
    public void josKorttiSaldoOkMaukasMyynnitOikein () {
       Maksukortti kortti = new Maksukortti(10000);  
       paate.syoMaukkaasti(kortti);
       assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void saldoEiRiitaKortinSaldoPysyyEdullinen() {
        Maksukortti kortti = new Maksukortti(100);  
        paate.syoEdullisesti(kortti);
        assertEquals(100, kortti.saldo());
    }
    
     @Test
    public void saldoEiRiitaKortinSaldoPysyyMaukas() {
        Maksukortti kortti = new Maksukortti(100);  
        paate.syoMaukkaasti(kortti);
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void yritetaanLadataNegatiivinenSummaKortille () {
        Maksukortti kortti = new Maksukortti(100); 
        paate.lataaRahaaKortille(kortti, -1);
        assertEquals(100, kortti.saldo());   
    }
    
    
    
}
