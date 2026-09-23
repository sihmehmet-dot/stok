import javax.swing.*;
import java.awt.*;

public class IsimMerdiveni {
    public static void main(String[] args) {
        JFrame pencere = new JFrame("İsim Merdiveni");
        pencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pencere.setSize(400, 400);

        JTextArea metinAlani = new JTextArea();
        metinAlani.setFont(new Font("Monospaced", Font.BOLD, 20)); // Harflerin hizalı durması için
        metinAlani.setEditable(false); // Kullanıcı metni değiştiremesin

        String isim = "ŞIHMEHMETTELLO";
        StringBuilder sonuc = new StringBuilder();

        for (int i = 1; i <= isim.length(); i++) {
            sonuc.append(isim.substring(0, i)).append("\n");
        }

        metinAlani.setText(sonuc.toString());

        pencere.add(new JScrollPane(metinAlani));
        //pencere.setLocationRelativeTo(null); // Pencereyi ekranın ortasında açar
        pencere.setVisible(true);
    }
}

//Java'da append, mevcut bir veri grubunun (genellikle bir metin veya liste) en sonuna yeni bir veri eklemek
// anlamına gelir. Programlamada "üzerine yazmak" yerine "ucuna eklemek" mantığıyla çalışır.