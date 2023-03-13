package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {
    @InjectMocks
    // denne skal testes
    private AdminKontoController adminController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;



    @Test
    public void hentAlleKonti() {

        // arrange
        // Oppretter en List med en konti
        List<Transaksjon> transaksjonList = new ArrayList<>() {};
        Konto konto1 = new Konto("98426974158", "0123.45.67890", 20_000, "Brukskonto", "NOK", transaksjonList);
        List<Konto> kontoList =new ArrayList<>() {};
        kontoList.add(konto1);
        // Når metoden sjekk.loggetInn() blir kalt i hentAlleKonti(), returnerer den String
        String personnummer = "98426974158";
        when(sjekk.loggetInn()).thenReturn(personnummer);
        // Når metoden repository.hentAlleKonti() blir kalt, returneres "kontoList"
        when(repository.hentAlleKonti()).thenReturn(kontoList);

        // act
        // Kaller metoden for å bli testet og lagre resultatet
        List<Konto> resultat = adminController.hentAlleKonti();

        // assert
        // Sammenligner resultatet vi fikk med det forventede resultatet for å se om testen var korrekt
        assertEquals(resultat, kontoList);
    }



    @Test
    public void hentAlleKontiFeil() {
        // arrange
        // Når metoden sjekk.loggetInn() blir kalt vil vi ha resultatet null
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = adminController.hentAlleKonti();

        // assert
        assertNull(resultat);
    }


    @Test
    public void registrerKonto(){

        // arrange
        // Lager en transaksjonsliste og konto
        List<Transaksjon> transaksjonList = new ArrayList<>() {};
        Konto konto1 = new Konto("98426974158", "0123.45.67890", 20_000, "Brukskonto", "NOK", transaksjonList);

        String personnummer = "98426974158";
        when(sjekk.loggetInn()).thenReturn(personnummer);
        String retur = "OK";
        when(repository.registrerKonto(konto1)).thenReturn(retur);

        // act
        String result = adminController.registrerKonto(konto1);

        // assert
        assertEquals(retur, result);

    }

    @Test
    public void registrerKontoFeil(){

        // arrange
        List<Transaksjon> transaksjonList = new ArrayList<>() {};
        Konto konto1 = new Konto("98426974158","0123.45.67890", 20_000,"Brukskonto","NOK", transaksjonList);
        when(sjekk.loggetInn()).thenReturn(null);
        String retur = "Ikke innlogget";

        // act
        String resultat = adminController.registrerKonto(konto1);

        // assert
        assertEquals(retur, resultat);
    }

    @Test
    public void endreKonto(){

        // arrange
        List<Transaksjon> transaksjonList = new ArrayList<>() {};
        Konto konto1 = new Konto("98426974158", "0123.45.67890", 20_000, "Brukskonto", "NOK", transaksjonList);

        String personnummer = "98426974158";
        when(sjekk.loggetInn()).thenReturn(personnummer);
        String retur = "OK";
        when(repository.endreKonto(konto1)).thenReturn(retur);

        // act
        String resultat = adminController.endreKonto(konto1);

        // assert
        assertEquals(retur, resultat);

    }


    @Test
    public void endreKontoFeil(){

        // arrange
        List<Transaksjon> transaksjon1 = new ArrayList<>() {};
        Konto konto1 = new Konto("98426974158","0123.45.67890", 20_000,"Brukskonto","NOK",transaksjon1);
        when(sjekk.loggetInn()).thenReturn(null);
        String retur = "Ikke innlogget";

        // act
        String resultat = adminController.endreKonto(konto1);

        // assert
        assertEquals(retur,resultat);
    }

    @Test
    public void slettKonto(){

        // arrange
        String kontonummer = "0123.45.67890";
        String personnummer = "98426974158";
        when(sjekk.loggetInn()).thenReturn(personnummer);
        String retur = "OK";
        when(repository.slettKonto(kontonummer)).thenReturn(retur);

        // act
        String resultat = adminController.slettKonto(kontonummer);

        // assert
        assertEquals(retur, resultat);

    }


    @Test
    public void slettKontoFeil(){

        // arrange
        String kontonummer = "0123.45.67890";
        when(sjekk.loggetInn()).thenReturn(null);
        String retur = "Ikke innlogget";

        // act
        String resultat = adminController.slettKonto(kontonummer);

        // assert
        assertEquals(retur,resultat);
    }



}




