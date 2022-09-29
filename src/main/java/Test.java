import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args){
        TLSEmail tlsEmail = new TLSEmail();
        List<File> fileList = new ArrayList<>();
        fileList.add(new File("abc.txt"));
        fileList.add(new File("cdc.txt"));

        tlsEmail.send("joaalsai@gmail.com","prueba","<h4>Esto es una prueba</h4><h1>prueba</h1>",fileList);
    }
}
