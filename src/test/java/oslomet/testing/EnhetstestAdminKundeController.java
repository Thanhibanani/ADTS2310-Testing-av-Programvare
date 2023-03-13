package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;


    @Test
    public void test_hentAlle() {

        // arrange
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        Kunde kunde2 = new Kunde("12345678901", "Per", "Hansen",
                "Osloveien 82", "1234", "Oslo", "12345678", "HeiHei");

        List<Kunde> kundeliste = new ArrayList<>();
        kundeliste.add(kunde1);
        kundeliste.add(kunde2);

        when(sjekk.loggetInn()).thenReturn(kunde1.getPersonnummer(), kunde2.getPersonnummer());

        // act
        when(repository.hentAlleKunder()).thenReturn(kundeliste);
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertEquals(kundeliste, resultat);

    }

    @Test
    public void test_hentAlleFeil() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);
    }


    @Test
    public void test_lagreKunde() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");


        when(sjekk.loggetInn()).thenReturn(enKunde.getPersonnummer());

        when(repository.registrerKunde(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = adminKundeController.lagreKunde(enKunde);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void test_lagreKundeFeil(){
        // arrange
        Kunde enKunde = new Kunde("01010110523", "Lene", "Jensen",
                "Askerveien 22", "3270", "Oslo", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.lagreKunde(enKunde);

        // assert
        assertEquals("Ikke logget inn", resultat);


    }

    @Test
    public void test_endreKunde(){
        // arrange
        Kunde enKunde = new Kunde("01010110523", "Lene", "Jensen",
                "Askerveien 22", "3270", "Oslo", "22224444", "HeiHei");


        Mockito.when(sjekk.loggetInn()).thenReturn(enKunde.getPersonnummer());

        Mockito.when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = adminKundeController.endre(enKunde);
        //assert
        assertEquals("OK", resultat);

    }

    @Test
    public void test_endreKundeFeil(){

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);
        // act
        String resultat = adminKundeController.endre(enKunde);

        // assert
        assertEquals(resultat, "Ikke logget inn", resultat);


    }

    @Test
    public void test_slettKunde(){

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");


        when(sjekk.loggetInn()).thenReturn(enKunde.getPersonnummer());

        when(repository.slettKunde(enKunde.getPersonnummer())).thenReturn("OK");

        // act
        String resultat = adminKundeController.slett(enKunde.getPersonnummer());

        //assert
        assertEquals("OK", resultat);

    }

    @Test
    public void test_slettKundeFeil(){

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);
        // act
        String resultat = adminKundeController.slett(enKunde.getPersonnummer());

        // assert
        assertEquals("Ikke logget inn",resultat);
    }


}


















