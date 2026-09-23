import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

// 1. ADIM: Çizim yapacağımız paneli (JPanel) oluşturuyoruz.
class CizimPaneli extends JPanel {

    // 2. ADIM: paintComponent metodunu eziyoruz (override).
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Panelin arka planını temizler ve hazırlar.

        // Çizgi Çizme: (x1, y1) noktasından (x2, y2) noktasına
        g.setColor(Color.RED); // Çizim rengini kırmızı yap
        g.drawLine(20, 30, 200, 30);

        // Dikdörtgen Çizme: (x, y, genişlik, yükseklik)
        g.setColor(Color.BLUE); // Rengi maviye çevir
        g.drawRect(20, 60, 150, 80); // İçi boş dikdörtgen

        // İçi Dolu Daire/Oval Çizme: (x, y, genişlik, yükseklik)
        g.setColor(Color.GREEN); // Rengi yeşile çevir
        g.fillOval(20, 160, 100, 100); // Genişlik ve yükseklik eşitse tam daire olur

        // Ekrana Yazı Yazma: ("Metin", x, y)
        g.setColor(Color.BLACK);
        g.drawString("Sınavda Başarılar!", 200, 200);
    }
}

// 3. ADIM: Pencereyi (JFrame) oluşturup içine paneli ekliyoruz.
public class GrafikSinavSorus {
    public static void main(String[] args) {
        JFrame pencere = new JFrame(); // Boş bir pencere nesnesi oluştur

        pencere.setTitle("Grafik Çizim Ekranı"); // Pencere başlığı
        pencere.setSize(400, 400); // Pencere boyutları (Genişlik, Yükseklik)
        pencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kapatılınca programı durdur

        CizimPaneli panel = new CizimPaneli(); // Çizim yaptığımız panelden nesne üret
        pencere.add(panel); // Paneli pencerenin içine yerleştir

        pencere.setVisible(true); // Pencereyi görünür yap (En sonda olmalı)
    }
}
