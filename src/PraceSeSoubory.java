import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PraceSeSoubory {
    private static final String ODDELOVAC = ";";
    private static final String SOUBOR = "deskovky.txt";

    public List<Deskovka> ziskejSeznamDeskovekZeSouboru(){
        try{
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(SOUBOR)));
            List<Deskovka> seznamDeskovek = new ArrayList<>();
            while (scanner.hasNext()){
                String radek = scanner.nextLine();
                String[] castiRadku = radek.split(ODDELOVAC);
                for(int i = 0; i < castiRadku.length; i++){
                    castiRadku[i] = castiRadku[i].trim();
                }
                String nazev = castiRadku[0];
                boolean zakoupeno = Boolean.parseBoolean(castiRadku[1]);
                int oblibenost = Integer.parseInt(castiRadku[2]);
                seznamDeskovek.add(new Deskovka(nazev, zakoupeno, oblibenost));

            }
            scanner.close();
            return seznamDeskovek;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}



