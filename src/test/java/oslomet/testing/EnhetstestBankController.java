package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn() {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn() {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }


    //Prøvde meg på noe her, men ANER ikke hva jeg gjør...

    @Test
    public void hentTransaksjoner_loggetInn() {

        //arrange

        String fra = "";
        String til = "";
        List<Transaksjon> transaksjonList = new ArrayList<>();

        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", transaksjonList);

        Transaksjon transaksjon1 = new Transaksjon(2, "01010110523", 350.60, "2023-02-02", "test", "0", konto1.getKontonummer());
        Transaksjon transaksjon2 = new Transaksjon(3, "01010110523", 800, "2023-02-02", "test2", "0", konto1.getKontonummer());
        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        Mockito.when(repository.hentTransaksjoner(konto1.getKontonummer(), fra,til))
                .thenReturn(new Konto(konto1.getKontonummer(), konto1.getPersonnummer(),
                        konto1.getSaldo(), konto1.getType(), konto1.getValuta(), transaksjonList));

        //act
        Konto resultat = bankController.hentTransaksjoner(konto1.getKontonummer(), "","");


        //assert
        assertEquals(transaksjonList,resultat.getTransaksjoner());


    }

    @Test
    public void hentTransaksjoner_IkkeLoggetInn() {
        // arrange

        String fra = "";
        String til = "";
        List<Transaksjon> transaksjonList = new ArrayList<>();

        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", transaksjonList);

        Transaksjon transaksjon1 = new Transaksjon(2, "01010110523", 350.60, "2023-02-02", "test", "0", konto1.getKontonummer());
        Transaksjon transaksjon2 = new Transaksjon(3, "01010110523", 800, "2023-02-02", "test2", "0", konto1.getKontonummer());
        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        Konto resultat = bankController.hentTransaksjoner(konto1.getKontonummer(), "","");

        // assert
        assertNull(resultat);
    }


    @Test
    public void hentSaldi_loggetInn(){

        //arrange
        List<Transaksjon> transaksjonList = new ArrayList<>();

        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", transaksjonList);

        List<Konto> kontoListe = new ArrayList<>();
        kontoListe.add(konto1);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        Mockito.when(repository.hentSaldi(anyString())).thenReturn(kontoListe);

        //act
        List <Konto> resultat = bankController.hentSaldi();

        //assert
        assertEquals(kontoListe, resultat);

    }



    @Test
    public void hentSaldi_IkkeloggetInn() {

        //arrange
        List<Transaksjon> transaksjonList = new ArrayList<>();

        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", transaksjonList);

        List<Konto> kontoListe = new ArrayList<>();
        kontoListe.add(konto1);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();
        // assert
        assertNull(resultat);

    }


    @Test
    public void registrerBetaling_loggetInn(){

        //arrange

        List<Konto> kontoliste = new ArrayList<>();
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Konto konto1 = new Konto("123123123","2020202020", 300_000.50,"Driftskonto","NOK",transaksjoner);
        kontoliste.add(konto1);

        Transaksjon transaksjon1 = new Transaksjon(2, "01010110523", 350.60, "2023-02-02", "test", "0", konto1.getKontonummer());
        transaksjoner.add(transaksjon1);

        when(sjekk.loggetInn()).thenReturn("132123123");
        when(repository.registrerBetaling(transaksjon1)).thenReturn("OK");

        //act

        String resultat = bankController.registrerBetaling(transaksjon1);

        //assert

        assertEquals("OK", resultat);

    }


    @Test
    public void registrerBetaling_IkkeloggetInn(){

        //arrange

        List<Konto> kontoliste = new ArrayList<>();
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Konto konto1 = new Konto("123123123","2020202020", 300_000.50,"Driftskonto","NOK",transaksjoner);
        kontoliste.add(konto1);

        Transaksjon transaksjon1 = new Transaksjon(2, "01010110523", 350.60, "2023-02-02", "test", "0", konto1.getKontonummer());
        transaksjoner.add(transaksjon1);

        when(sjekk.loggetInn()).thenReturn(null);

        //act

        String resultat = bankController.registrerBetaling(transaksjon1);

        //assert

        assertNull(resultat);
//      assertEquals("Ikke innlogget", resultat); Usikker på hvorfor ikke denne returnerer "ikke innlogget".

    }


    @Test
    public void hentBetalinger_loggetInn(){

        //Arrange
        List<Transaksjon> transaksjonList = new ArrayList<>();

        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", transaksjonList);

        List<Konto> kontoListe = new ArrayList<>();
        kontoListe.add(konto1);



        when(sjekk.loggetInn()).thenReturn("105010123456");
        Mockito.when(repository.hentBetalinger(anyString())).thenReturn(transaksjonList);

        //Act

        List <Transaksjon> resultat = bankController.hentBetalinger();


        //Assert

        assertEquals(transaksjonList, resultat);

    }



/*
    @Test        //DENNE ER IKKE FERDIG.
    public void utforBetaling_loggetInn(){

        //arrange

        List<Konto> kontoliste = new ArrayList<>();
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Konto konto1 = new Konto("123123123","2020202020", 300_000.50,"Driftskonto","NOK",transaksjoner);
        kontoliste.add(konto1);

        Transaksjon transaksjon1 = new Transaksjon(2, "01010110523", 350.60, "2023-02-02", "test", "0", konto1.getKontonummer());
        transaksjoner.add(transaksjon1);

        when(sjekk.loggetInn()).thenReturn(konto1.getKontonummer());
        when(repository.utforBetaling(transaksjon1.getTxID())).thenReturn("OK");


        //act

      //  String resultat = bankController.utforBetaling(transaksjon1.getTxID());

        //assert

        // assertEquals("OK", resultat);

    }
*/

    @Test
    public void endreKundeInfo_loggetInn(){
        // arrange
        List<Kunde> kundeliste = new ArrayList<>();
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        kundeliste.add(kunde1);


        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(kunde1)).thenReturn("OK");

        // act
        String resultat = bankController.endre(kunde1);
        //assert
        assertEquals("OK", resultat);

    }

    @Test
    public void endreKunde_ikkeLoggetInn(){

        // arrange
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);
        // act
        String resultat = bankController.endre(kunde1);

        // assert
        assertEquals(null, resultat);

    }





}



