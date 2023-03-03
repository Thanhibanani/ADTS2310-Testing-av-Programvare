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

        String fra = "01.03.2023";
        String til = "05.03.2023";
        List<Transaksjon> transaksjonList = new ArrayList<>();

        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", transaksjonList);

        Transaksjon transaksjon1 = new Transaksjon(2, konto1.getKontonummer(), 350.60, "03.03.23", "test", "avventer", konto1.getKontonummer());
        Transaksjon transaksjon2 = new Transaksjon(3, konto1.getKontonummer(), 800, "04.03.23", "test2", "avventer", konto1.getKontonummer());


        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        when(repository.hentTransaksjoner(konto1.getKontonummer(), fra,til))
                .thenReturn(new Konto(konto1.getKontonummer(), konto1.getPersonnummer(),
                        konto1.getSaldo(), konto1.getType(), konto1.getValuta(), transaksjonList));

        //act
        Konto resultat = bankController.hentTransaksjoner(konto1.getKontonummer(), fra, til);


        //assert
        assertEquals(transaksjonList, ((Konto) resultat).getTransaksjoner());


    }

}





