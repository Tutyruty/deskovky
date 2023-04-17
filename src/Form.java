
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Form extends JFrame {
    private JCheckBox zakoupenoAno;
    private JTextArea textArea1;
    private JButton dalsiButton;
    private JButton predchoziButton;
    private JCheckBox zakoupenoNe;
    private JPanel panel;
    private JSlider slider;
    private JButton tabulkaButton;
    private ButtonGroup skupina;
    private int indexSeznamu = 0;

    public Form() {

        this.skupina = new ButtonGroup();
        this.skupina.add(this.zakoupenoAno);
        this.skupina.add(this.zakoupenoNe);
        this.nactiData(this.indexSeznamu);
        this.dalsiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ++Form.this.indexSeznamu;
                Form.this.nactiData(Form.this.indexSeznamu);
            }
        });
        this.predchoziButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                --Form.this.indexSeznamu;
                Form.this.nactiData(Form.this.indexSeznamu);
            }
        });
        tabulkaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabulka();
            }
        });
    }

    public void nactiData(int index) {
        PraceSeSoubory praceSeSoubory = new PraceSeSoubory();
        List<Deskovka> seznam = praceSeSoubory.ziskejSeznamDeskovekZeSouboru();
        if (this.indexSeznamu == 0) {
            this.predchoziButton.setEnabled(false);
            this.dalsiButton.setEnabled(true);
        } else if (this.indexSeznamu == seznam.size() - 1) {
            this.predchoziButton.setEnabled(true);
            this.dalsiButton.setEnabled(false);
        } else {
            this.predchoziButton.setEnabled(true);
            this.dalsiButton.setEnabled(true);
        }

        Deskovka aktualniDeskovka = (Deskovka)seznam.get(index);
        this.textArea1.setText(aktualniDeskovka.getNazev());
        if (aktualniDeskovka.isZakoupeno()) {
            this.zakoupenoAno.setSelected(true);
        } else {
            this.zakoupenoNe.setSelected(true);
        }

        this.slider.setValue(aktualniDeskovka.getOblibenost());
    }

    public void tabulka(){
        List<Deskovka> hry = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("deskovky.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String nazev = parts[0];
                boolean koupena = Boolean.parseBoolean(parts[1]);
                int oblibenost = Integer.parseInt(parts[2]);
                hry.add(new Deskovka(nazev, koupena, oblibenost));

            }
        } catch (IOException e) {
            System.err.println("Chyba při načítání souboru");
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Deskové hry");
        JPanel panel = new JPanel();
        String[] ramecek = {"Název", "Zakoupeno", "Oblíbenost"};
        Object[][] data = new Object[hry.size()][3];
        for (int i = 0; i < hry.size(); i++) {
            Deskovka hra = hry.get(i);
            data[i][0] = hra.getNazev();
            data[i][1] = hra.isZakoupeno();
            data[i][2] = hra.getOblibenost();
        }
        JTable table = new JTable(data, ramecek);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }



    public static void main(String[] args) {
        Form h = new Form();
        h.setContentPane(h.panel);
        h.setSize(700, 500);
        h.setDefaultCloseOperation(3);
        h.setVisible(true);
    }
}
