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
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;



    @Test
    public void hentAlleKonti_loggetInn() {

        // arrange

        List<Konto> kontoliste = new ArrayList<>();
        List<Transaksjon> transaksjon1 = new ArrayList<>();

        Konto konto1 = new Konto("123123123","2020202020", 300_000.50,"Driftskonto","NOK",transaksjon1);
        kontoliste.add(konto1);

        when(sjekk.loggetInn()).thenReturn("132123123");

        when(repository.hentAlleKonti()).thenReturn(kontoliste);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertEquals(kontoliste, resultat);
    }



    @Test
    public void hentAlleKonti_IkkeLoggetInn() {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertNull(resultat);
    }


    @Test
    public void registrerKonto_LoggetInn(){

        // arrange


        List<Konto> kontoliste = new ArrayList<>();
        List<Transaksjon> transaksjon1 = new ArrayList<>();

        Konto konto1 = new Konto("123123123","2020202020", 300_000.50,"Driftskonto","NOK",transaksjon1);
        kontoliste.add(konto1);


        when(sjekk.loggetInn()).thenReturn("132123123");

        when(repository.registrerKonto(konto1)).thenReturn("OK");

        // act
        String resultat = adminKontoController.registrerKonto(konto1);

        // assert
        assertEquals("OK", resultat);

    }

    @Test
    public void registrerKonto_Ikkeinnlogget(){

        // arrange
        List<Transaksjon> transaksjon1 = new ArrayList<>();
        Konto konto1 = new Konto("123123123","2020202020", 300_000.50,"Driftskonto","NOK",transaksjon1);
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKontoController.registrerKonto(konto1);

        // assert
        assertEquals("Ikke innlogget",resultat);
    }

    @Test
    public void endreKonto_LoggetInn(){

        // arrange
        List<Konto> kontoliste = new ArrayList<>();
        List<Transaksjon> transaksjon1 = new ArrayList<>();

        Konto konto1 = new Konto("123123123","2020202020", 300_000.50,"Driftskonto","NOK",transaksjon1);
        kontoliste.add(konto1);


        when(sjekk.loggetInn()).thenReturn("132123123");

        when(repository.endreKonto(konto1)).thenReturn("OK");

        // act
        String resultat = adminKontoController.endreKonto(konto1);

        // assert
        assertEquals("OK", resultat);

    }





}




