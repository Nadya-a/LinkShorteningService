import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        LinkService ls = new LinkService();
        Scanner input = new Scanner(System.in);

        System.out.print("Введите ссылку, которую нужно сократить: ");
        String longLink = input.next();

        System.out.print("Введите максимальное кол-во переходов по сокращенной ссылке: ");
        String linkLimit = input.next();

        ShortLink sh = new ShortLink(longLink, linkLimit);
        //String result = shg.shortLinkGeneration();
        System.out.println(longLink + ": " + sh);
        ls.addToFile(sh);

        System.out.println(ls.readFromFile());
    }
}
