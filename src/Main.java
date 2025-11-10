public class Main {
    public static void main(String[] args) {
        Thread transaction1 = new Thread(new Transaction(1, 2));
        Thread transaction2 = new Thread(new Transaction(2, 1));
        Thread transaction3 = new Thread(new Transaction(1, 5));
        Thread transaction4 = new Thread(new Transaction(3, 3));

        transaction1.start();
        transaction2.start();
        transaction3.start();
        transaction4.start();
    }
}