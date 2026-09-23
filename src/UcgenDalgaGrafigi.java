import javax.swing.*;
import java.awt.*;

public class UcgenDalgaGrafigi extends JFrame {

    public UcgenDalgaGrafigi() {
        setTitle("JLabel ile Üçgen Dalga Grafiği");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        DalgaLabel dalgaLabel = new DalgaLabel();
        add(dalgaLabel);
    }

    // Üçgen dalgayı çizecek özel JLabel sınıfı
    static class DalgaLabel extends JLabel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Çizimin daha pürüzsüz görünmesi için Anti-aliasing aktif ediliyor
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int genislik = getWidth();
            int yukseklik = getHeight();
            int merkezY = yukseklik / 2;

            // Arka planı temizle
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, genislik, yukseklik);

            // Eksen çizgilerini çiz
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawLine(0, merkezY, genislik, merkezY); // X ekseni

            // Dalga parametreleri
            double periyot = 200.0; // İki tepe noktası arasındaki piksel mesafesi
            double genlik = 100.0;  // Dalganın maksimum yüksekliği (piksel)

            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(2f)); // Çizgi kalınlığı

            // Grafiği pikselleri tarayarak çiziyoruz
            for (int x = 0; x < genislik - 1; x++) {
                int y1 = ucgenDalgaYHesapla(x, periyot, genlik, merkezY);
                int y2 = ucgenDalgaYHesapla(x + 1, periyot, genlik, merkezY);

                g2d.drawLine(x, y1, x + 1, y2);
            }
        }

        /**
         * Verilen X koordinatı için Üçgen Dalga Y değerini hesaplar.
         */
        private int ucgenDalgaYHesapla(double x, double periyot, double genlik, int merkezY) {
            // Matematiksel üçgen dalga formülü:
            // y = (4 * genlik / periyot) * |((x - periyot/4) % periyot) - periyot/2| - genlik

            double baskan = (x - periyot / 4.0) % periyot;
            if (baskan < 0) {
                baskan += periyot; // Negatif modülasyonu engellemek için
            }

            double y = (4.0 * genlik / periyot) * Math.abs(baskan - periyot / 2.0) - genlik;

            // Java ekran koordinat matrisinde Y aşağı doğru arttığı için merkezY'den çıkarıyoruz
            return (int) (merkezY - y);
        }
    }

    public static void main(String[] args) {
        // Swing arayüzünü güvenli thread üzerinde çalıştırıyoruz
        SwingUtilities.invokeLater(() -> {
            new UcgenDalgaGrafigi().setVisible(true);
        });
    }
}

//üçgen dalga gtrafigi çizimi bu şekilde kare dalga grafigi çizimide yaptık sinüs dalga grafigi çizimide yaptık kare dalga grafigini sinüs dalga grafigini degiştirerek yani bir sınıftan bir metot yasnımlayarak yaptık