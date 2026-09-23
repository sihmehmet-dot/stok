import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class Urun {
    private String id,isim;
    private int stokAdedi;
    private double fiyat;

    public Urun(String id, String isim, int stokAdedi, double fiyat) {
        this.id = id;
        this.isim = isim;
        this.stokAdedi = stokAdedi;
        this.fiyat = fiyat;
    }

    public String getId() {
        return id; }
    public void setId(String id) {
        this.id = id; }

    public String getIsim() {
        return isim; }
    public void setIsim(String isim) {            //buradki yazdıgımız get mototları ile private olan degişkenlere dışardan erişim izni verilir
        this.isim = isim; }                       //ve yazdıgımız diger metot olan set ise private olan bu degişkeneleri dışarıdan degişim hakkı veriri

    public int getStokAdedi() {
        return stokAdedi; }
    public void setStokAdedi(int stokAdedi) {
        this.stokAdedi = stokAdedi; }

    public double getFiyat() {
        return fiyat; }
    public void setFiyat(double fiyat) {
        this.fiyat = fiyat; }
}

// ==========================================
// 2. SINIF: DOSYA YONETIMI (Okuma - Yazma)
// ==========================================
class DosyaYonetimi {
    private static final String dosyaAdi = "urunler.txt";
    public static final int kapasite = 100; // Depolanabilecek maksimum ürün sayısı

    // Verileri dosyaya yazma işlemi (Urun[] dizisi ve eleman sayısı parametre olarak alınır)
    public static void verileriKaydet(Urun[] urunDizisi, int urunSayisi) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaAdi))) {
            // Sadece dizinin dolu olan elemanları kadar (urunSayisi) dönüyoruz
            for (int i = 0; i < urunSayisi; i++) {
                Urun urun = urunDizisi[i];          //ürün sınıfındaki boşluklara ürünlerimizi koy
                writer.write(urun.getId() + "," + urun.getIsim() + "," + urun.getStokAdedi() + "," + urun.getFiyat());
                writer.newLine();  //bu satır yazılan ürünle alaklı yazılcak olan şeyler bittigi an bir alt satıra geçirek bir sonraki ürünlerin yazılmasını saglar
            }
        } catch (IOException e) {
            System.out.println("Dosyaya yazılırken hata oluştu: " + e.getMessage());
        }
    }


    public static Urun[] verileriYukle() {
        Urun[] yuklenenUrunler = new Urun[kapasite];
        File dosya = new File(dosyaAdi);
        if (!dosya.exists()) {
            return yuklenenUrunler;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(dosyaAdi))) {
            String satir;
            int indeks = 0;
            while ((satir = reader.readLine()) != null && indeks < kapasite) {
                String[] veriler = satir.split(","); //split metodu parantez içine giridigimiz karakteri görünce ona kadar olan bölümü ayırır
                if (veriler.length == 4) {  //girilen ürünlerin bilgileri eksiksiz olarak girildiyse çalışır
                    String id = veriler[0];
                    String isim = veriler[1];
                    int stok = Integer.parseInt(veriler[2]);
                    double fiyat = Double.parseDouble(veriler[3]);

                    yuklenenUrunler[indeks] = new Urun(id, isim, stok, fiyat);  //bu satır uygulama yeniden açıldıgında dosyaya kaydedilen verileri yeni bir nesne üreterek ekranda tekraradan gösteririz
                    indeks++;
                }
            }
        } catch (IOException e) {
            System.out.println("Dosya okunurken hata oluştu: " + e.getMessage());
        }
        return yuklenenUrunler;
    }
}

class StokArayuz extends JFrame {
    private Urun[] urunDizisi;
    private int urunSayisi = 0; // Dizinin içinde şu an kaç gerçek ürün olduğunu tutar

    private JTable tablo;
    private DefaultTableModel tabloModeli;         //pencerede kullanacagımı buton ve label gibi araçları tanımlıyoruz
    private JTextField txtId, txtIsim, txtStok, txtFiyat;
    private JButton btnEkle, btnSil;

    public StokArayuz() {
        this.urunDizisi = DosyaYonetimi.verileriYukle(); //pencere açıldıgında ilk bu satır çalışacak ve çalışır çalışmaz dosyay kaydedilen verileri açar açmaz pencereye yazmamızı saglar


        this.urunSayisi = 0;
        for (int i = 0; i < urunDizisi.length; i++) {
            if (urunDizisi[i] != null) {
                this.urunSayisi++;   //dosyayı okuyup kaç adte ürün oldugunu hesaplar
            } else {
                break;
            }
        }

        setTitle("E-Ticaret Stok Yönetim Sistemi (Dizi Versiyonu)");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); //bu kısım açılan pencerenin düzeninin belirler ve 5 parçaya ayırır kuzey bölgesine yani en üst bölgeye ürün biligilerini girdigimiz kısım tablo olan kısmı ise center bölgesi aşagıda buton olan kısımlar ise günay en alt kısımdır

        JPanel girisPaneli = new JPanel(new GridLayout(2, 4, 5, 5));
        girisPaneli.setBorder(BorderFactory.createTitledBorder("Ürün Bilgileri"));  //buraya kadarki yazıdıgımız pencere kısımlarını ince bir çizgiyle çerçeveler ve ve sol üstüne ürün bilgileri yazar

        girisPaneli.add(new JLabel(" Ürün ID:"));
        txtId = new JTextField();
        girisPaneli.add(txtId);

        girisPaneli.add(new JLabel(" Ürün Adı:"));
        txtIsim = new JTextField();
        girisPaneli.add(txtIsim);

        girisPaneli.add(new JLabel(" Stok Adedi:"));
        txtStok = new JTextField();
        girisPaneli.add(txtStok);

        girisPaneli.add(new JLabel(" Fiyat (TL):"));
        txtFiyat = new JTextField();
        girisPaneli.add(txtFiyat);

        add(girisPaneli, BorderLayout.NORTH);

        String[] kolonlar = {"Ürün ID", "Ürün Adı", "Stok Adedi", "Fiyat"}; // burada kolonların başlıklarını belirliyoruz yani biz buarada diyrouz ki benim tablom 4 sütundan oluşacak
        tabloModeli = new DefaultTableModel(kolonlar, 0);  // buradaki sıfır tablo açıldıgında kaç satır olacıgını belirliyor ani sıfırın yerine kaç yazarsak yazalım ilk açıldıgında o kladar satır lacak sıfır yazıldıgında ise sıfır tane olacak
        tablo = new JTable(tabloModeli);
        add(new JScrollPane(tablo), BorderLayout.CENTER); // virgülün sol tarafı oluşturulan tabloyu aşagı yukarı haraket ettirip girilen veriyi görmemizi saglar sag tarafı ise oluşan tabloyu pencerenin center yani merkez ksımına konumlandırmamızı saglıyor

        JPanel butonPaneli = new JPanel();
        btnEkle = new JButton("Ürün Ekle / Güncelle");
        btnSil = new JButton("Seçili Ürünü Sil");
        butonPaneli.add(btnEkle);
        butonPaneli.add(btnSil);
        add(butonPaneli, BorderLayout.SOUTH);

        tabloyuYenile(); // butonlara bastıgımızda silme yada ekleme yaparak gücelledigimiz veri dosyasını güncel haliyle bize sunar

        btnEkle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = txtId.getText().trim();
                    String isim = txtIsim.getText().trim();                    // sonlarına yazıdıgımı .trim() bir temizlik metodur gereksiz boşlukları temizler
                    int stok = Integer.parseInt(txtStok.getText().trim());
                    double fiyat = Double.parseDouble(txtFiyat.getText().trim());

                    if (id.isEmpty() || isim.isEmpty() ) {
                        JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurun.");
                        return;
                    }

                    boolean guncellendi = false;
                    for (int i = 0; i < urunSayisi; i++) {
                        if (urunDizisi[i].getId().equals(id)) {
                            urunDizisi[i].setIsim(isim);              //depoda olan ürünlerin bilgilerini günceledikmi güncelemedikmi diye kontrol ediyor
                            urunDizisi[i].setStokAdedi(stok);
                            urunDizisi[i].setFiyat(fiyat);
                            guncellendi = true;
                            break;
                        }
                    }

                    if (!guncellendi) {
                        if (urunSayisi >= DosyaYonetimi.kapasite) {
                            JOptionPane.showMessageDialog(null, "Hata: Maksimum ürün kapasitesine ulaşıldı!");
                            return;
                        }
                        urunDizisi[urunSayisi] = new Urun(id, isim, stok, fiyat);
                        urunSayisi++;
                    }

                    DosyaYonetimi.verileriKaydet(urunDizisi, urunSayisi);
                    tabloyuYenile();         //en güncel haliyle tabloyu yeninden bize sunar bu 3 satur
                    alanlariTemizle();          // bu satır ise ise ürünleri eklenkenki yazıdgımız şeyleri butona bastıklatan sonra siler ve kutucukları boş bırakır

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Stok ve Fiyat alanlarına geçerli sayılar giriniz!");
                }
            }
        });

        btnSil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seciliSatir = tablo.getSelectedRow(); // fareyle seçtigimiz satırın indeksini veririr eger bir satır seçmediysek -1 döndürür
                if (seciliSatir >= 0) {
                    String silinecekId = tabloModeli.getValueAt(seciliSatir, 0).toString();

                    int silinecekIndeks = -1;
                    for (int i = 0; i < urunSayisi; i++) {
                        if (urunDizisi[i].getId().equals(silinecekId)) {  // burada silinecek indexi buluyoruz
                            silinecekIndeks = i;
                            break;
                        }
                    }

                    if (silinecekIndeks != -1) {
                        // Silinen elemandan sonra gelen tüm elemanları 1 adım sola kaydırıyoruz bunun nedeni arada bir ürün sildigimizde 4 ten 6 ya atalamaması için yani idlerin düzgün sıralanması içiin
                        for (int i = silinecekIndeks; i < urunSayisi - 1; i++) {
                            urunDizisi[i] = urunDizisi[i + 1];
                        }
                        urunDizisi[urunSayisi - 1] = null;
                        urunSayisi--;

                        DosyaYonetimi.verileriKaydet(urunDizisi, urunSayisi);
                        tabloyuYenile();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen silmek istediğiniz ürünü tablodan seçin.");
                }
            }
        });
    }

    private void tabloyuYenile() {
        tabloModeli.setRowCount(0); // Tabloyu komple silmeye yarar komple sileriz çünki en güncel halini düzgün bir şekilde yazabilmek için
        for (int i = 0; i < urunSayisi; i++) {
            Urun urun = urunDizisi[i];
            Object[] satir = {urun.getId(), urun.getIsim(), urun.getStokAdedi(), urun.getFiyat()};
            //Object[] satir: Java'da farklı türdeki verileri (String, int, double) aynı sepetin içine koymak istiyorsan, hepsinin atası olan Object dizisini kullanırsın. Bu satırda ürünün ID'sini, adını, stok sayısını ve fiyatını yan yana dizerek tek bir satırlık "veri şeridi" oluşturuyoruz.
            tabloModeli.addRow(satir);  // hazırlanan yeni satırı bir sonraki satıra ekler ve böylece hazırlanan satırlar bitene kadar devam eder
        }
    }

    private void alanlariTemizle() {
        txtId.setText("");
        txtIsim.setText("");
        txtStok.setText("");
        txtFiyat.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {    //önce arayüzü açar ve sonrasındada çalıştırır
            @Override
            public void run() {
                new StokArayuz().setVisible(true); // noktanın sol tarafı bütün verileri en anlamlı şekilde hazır eder ve bekler noktanın sag tarafı ise bu verileri bize görünür kılar
            }
        });
    }
}

//invokeLater = daha nsonra çalıştır demektir
//SwingUtilities = işleri organize eden yapıdır SwingUtilities, aradaki bu güvenliği sağlayan, arka plandaki o işçiler ile ekrandaki görsel pencereler arasında köprü olan ana yöneticidir.
//thread iş parçacıklarına iyi çalış main metodu bu sayede yazdın