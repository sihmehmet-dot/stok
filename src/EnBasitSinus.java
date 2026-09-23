import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

class BasitSinusPaneli extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Ekranın tam ortasına düz bir çizgi çekiyoruz (X Ekseni)
        g.setColor(Color.GRAY);
        g.drawLine(0, 200, 600, 200);

        // Çizim rengimizi kırmızı yapalım
        g.setColor(Color.RED);

        // Döngümüz her piksel için değil, 5'er 5'er ilerlesin (Kod daha da kısalsın)
        for (int x = 0; x < 600; x += 5) {

            // 1. Dereceyi Radyana çeviriyoruz (Math.sin sadece radyan anlar)
            double radyan = Math.toRadians(x);

            // 2. Sinüs değerini hesaplayıp 100 ile çarpıyoruz (görünür olsun diye)
            int yEkseni = (int) (Math.sin(radyan) * 100);

            // 3. Noktayı ekrana çiziyoruz.
            // Merkezimiz Y=200 olduğu için 200'den çıkarıyoruz.
            g.fillOval(x, 200 - yEkseni, 4, 4);
        }
    }
}

public class EnBasitSinus {
    public static void main(String[] args) {
        JFrame pencere = new JFrame();
        pencere.setSize(600, 400); // Sabit 600x400 piksel bir pencere
        pencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pencere.add(new BasitSinusPaneli());
        pencere.setVisible(true);
    }
}
