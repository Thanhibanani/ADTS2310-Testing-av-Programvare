package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import javax.servlet.http.HttpSession;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {

    @InjectMocks
    // denne skal testes
    private Sikkerhet sjekk;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private HttpSession session;



    @Test
    public void sjekkLoggInn_korrektInput_girOK() {
        // Arrange
        String personnummer = "01010112345";
        String passord = "test123";

        when(repository.sjekkLoggInn(personnummer, passord)).thenReturn("OK");
        when(session.getAttribute("Innlogget")).thenReturn(personnummer);

        // Act
        String resultat = sjekk.sjekkLoggInn(personnummer, passord);

        // Assert
        assertEquals("OK", resultat);
    }

    @Test
    public void sjekkLoggInn_feilPersonnrRegex_girFeil() {
        // Arrange
        String personnummer = "A1010112345";
        String passord = "test123";

        when(repository.sjekkLoggInn(personnummer, passord)).thenReturn("Feil i personnummer");
        when(session.getAttribute("Innlogget")).thenReturn(personnummer);

        // Act
        String resultat = sjekk.sjekkLoggInn(personnummer, passord);

        // Assert
        assertEquals("Feil i personnummer", resultat);
    }

    @Test
    public void sjekkLoggInn_feilpassordRegex_girFeil() {
        // Arrange
        String personnummer = "01010112345";
        String passord = "123";

        when(repository.sjekkLoggInn(personnummer, passord)).thenReturn("Feil i passord");
        when(session.getAttribute("Innlogget")).thenReturn(personnummer);

        // Act
        String resultat = sjekk.sjekkLoggInn(personnummer, passord);

        // Assert
        assertEquals("Feil i passord", resultat);
    }

    @Test
    public void sjekkLoggInn() {
        // Arrange
        String personnummer = "11111111111";
        String passord = "12345678";

        when(repository.sjekkLoggInn(personnummer, passord)).thenReturn("Ikke OK");

        // Act
        String resultat = sjekk.sjekkLoggInn(personnummer, passord);

        // Assert
        assertEquals("Feil i personnummer eller passord", resultat);

    }

    @Test
    public void testLoggUt() {
        // Arrange
        when(session.getAttribute("Innlogget")).thenReturn("AltAnnetEnnInnlogget");

        // Act
        sjekk.loggUt();

        // Assert
        verify(session).setAttribute("Innlogget", null);
    }


    @Test
    public void adminInnlogging_OK() {
        // Arrange
        String bruker = "Admin";
        String passord = "Admin";

        // Act
        String resultat = sjekk.loggInnAdmin(bruker, passord);

        // Assert
        assertEquals("Logget inn", resultat);
        verify(session).setAttribute("Innlogget", "Admin");
    }

    @Test
    public void adminInnlogging_Feil() {
        // Arrange
        String bruker = "Imposter";
        String passord = "I_Am_The_Imposter";

        // Act
        String resultat = sjekk.loggInnAdmin(bruker, passord);

        // Assert
        assertEquals("Ikke logget inn", resultat);
        verify(session).setAttribute("Innlogget", null);
    }



    //Litt usikker på hvorfor/hvordan denne skulle testes
    @Test
    public void loggetInnIgjen() {
        // Arrange
        when(session.getAttribute("Innlogget")).thenReturn("Imposter");
        String forventetResultat = "Imposter";

        // Act
        String resultat = sjekk.loggetInn();

        // Assert
        assertEquals(forventetResultat, resultat);
    }

    @Test
    public void loggetInn_Feil() {
        // Arrange
        when(session.getAttribute("Innlogget")).thenReturn(null);

        // Act
        String resultat = sjekk.loggetInn();

        // Assert
        assertNull(resultat);
    }




}