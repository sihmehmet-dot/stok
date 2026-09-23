
class Artan extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 20; i++) {
            System.out.println(i);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}

class Azal extends Thread {
    @Override
    public void run() {
        for (int i = 100; i >= 81; i--) {
            System.out.println(i);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}

public class artanazalan {
    public static void main(String[] args) {
        Artan t1 = new Artan();
        Azal t2 = new Azal();

        t1.start();
        t2.start();
    }
}
