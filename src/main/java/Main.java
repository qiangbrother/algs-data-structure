public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        int a = -6;
        for (int i = 0; i < 32; i++) {
            int t = (a & 0x80000000 >>> i) >>> (31 - i);
            System.out.print(t);
        }

    }
}
