import java.io.File;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args){
        TLSEmail tlsEmail = new TLSEmail();
        tlsEmail.send("joaalsai@gmail.com","prueba","<h4>Esto es una prueba</h4><h1>prueba</h1>",new ArrayList<File>());
    }
}
