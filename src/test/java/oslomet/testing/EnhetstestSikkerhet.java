package oslomet.testing;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {

    @InjectMocks
    Sikkerhet sikkerhet;

    @Mock
    BankRepository repository;




    @Test
    public void testSjekkLoggInnWithInvalidPersonnummer() {
        // arrange
        String personnummer = "1234";
        String passord = "password123";

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // assert
        assertEquals("Feil i personnummer", resultat);
    }

    @Test
    public void testSjekkLoggInnWithInvalidPassord() {
        // arrange
        String personnummer = "12345678901";
        String passord = "pwd";

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // assert
        assertEquals("Feil i passord", resultat);
    }

    @Test
    public void testSjekkLoggInnWithInvalidCredentials() {
        // arrange
        String personnummer = "12345678901";
        String passord = "password123";
        when(repository.sjekkLoggInn(personnummer, passord)).thenReturn("Feil i personnummer eller passord");

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // assert
        assertEquals("Feil i personnummer eller passord", resultat);
    }

    @Test
    public void testSjekkLoggInnWithValidCredentials() {
        // arrange
        String personnummer = "12345678901";
        String passord = "password123";
        when(repository.sjekkLoggInn(personnummer, passord)).thenReturn("OK");

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // assert
        assertEquals("OK", resultat);
    }



}
