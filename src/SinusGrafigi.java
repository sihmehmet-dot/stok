import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

class SinusPaneli extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int genislik = getWidth();   // Panelin o anki dinamik genişliği
        int yukseklik = getHeight(); // Panelin o anki dinamik yüksekliği
        int merkezY = yukseklik / 2; // Y ekseninin sıfır noktası (Ekranın tam ortası)

        // 1. ADIM: Eksen Çizgilerini Çizme (X ve Y Ekseni)
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(0, merkezY, genislik, merkezY); // X Ekseni (Yatay)
        g.drawLine(50, 0, 50, yukseklik);          // Y Ekseni (Dikey - Kenardan 50 piksel içeride)

        // 2. ADIM: Sinüs Dalgasını Çizme
        g.setColor(Color.BLUE); // Dalgamız mavi renkli olsun

        // Grafik çizim parametreleri (Ölçekleme)
        double genlik = 80.0;    // Dalganın yukarı/aşağı yüksekliği (Piksel cinsinden)
        double frekans = 0.05;   // Dalganın sıklığı (X ekseninde ne kadar hızlı salınacağı)

        // İlk noktanın koordinatlarını başlangıç olarak belirliyoruz
        int oncekiX = 50;
        int oncekiY = merkezY - (int)(Math.sin(0) * genlik);

        // Ekranın solundan sağına doğru her piksel için sinüs değerini hesaplıyoruz
        for (int x = 50; x < genislik; x++) {

            // Matematiksel sinüs hesabı yapılıyor.
            // (x - 50) ifadesi dalganın dikey Y ekseninden (50. pikselden) başlamasını sağlar.
            double radyan = (x - 50) * frekans;
            double sinDegeri = Math.sin(radyan);

            // Java'da Y ekseni aşağı doğru arttığı için, sinüsün pozitif değerlerini
            // yukarıda göstermek amacıyla merkezY'den ÇIKARIYORUZ.
            int y = merkezY - (int)(sinDegeri * genlik);

            // Eski nokta ile yeni noktayı ince bir çizgiyle birleştiriyoruz
            g.drawLine(oncekiX, oncekiY, x, y);

            // Bir sonraki adım için mevcut noktayı "eski nokta" yapıyoruz
            oncekiX = x;
            oncekiY = y;
        }
    }
}

public class SinusGrafigi {
    public static void main(String[] args) {
        JFrame pencere = new JFrame();
        pencere.setTitle("Java Swing - Sinüs Eğrisi Grafiği");
        pencere.setSize(600, 400);
        pencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SinusPaneli panel = new SinusPaneli();
        pencere.add(panel);

        pencere.setVisible(true);
    }
}
