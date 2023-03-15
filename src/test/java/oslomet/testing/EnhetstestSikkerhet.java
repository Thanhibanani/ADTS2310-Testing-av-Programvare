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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
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

        HttpSession mockedSession = Mockito.mock(HttpSession.class);

        when(repository.sjekkLoggInn(personnummer, passord)).thenReturn("OK");

        // Act
        String resultat = sjekk.sjekkLoggInn(personnummer, passord);

        // Assert
        assertEquals("OK", resultat);
        assertEquals(personnummer, mockedSession.getAttribute("Innlogget"));
    }



}