import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TLSEmailTest {

    @Test
    @DisplayName("Envio de mail sin adjuntos")
    void send() {
        assertDoesNotThrow(() -> TLSEmail.getInstance().send("joaalsai@gmail.com","Prueba sin adjuntos","Test de prueba"));
    }

    @Test
    @DisplayName("Envio de mail con adjuntos")
    void testSend() {
        assertDoesNotThrow(() -> {
            List<File> files = new ArrayList<>();
            files.add(new File("app.properties"));
            files.add(new File("pom.xml"));

            TLSEmail.getInstance().send("joaalsai@gmail.com","Prueba con adjuntos","Test de prueba",files);
        });
    }
}