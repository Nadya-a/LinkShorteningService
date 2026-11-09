import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Введите ссылку, которую нужно сократить: ");
        String long_link = input.next();

        ShortLinkGenerator shg = new ShortLinkGenerator();
        String result = shg.shortLinkGeneration();
        System.out.println(long_link + ": " + result);
    }
}
