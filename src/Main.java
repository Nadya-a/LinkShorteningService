import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        LinkService ls = new LinkService();
        Scanner input = new Scanner(System.in);

        System.out.print("Введите ссылку, которую нужно сократить: ");
        String longLink = input.next();

        System.out.print("Введите максимальное кол-во переходов по сокращенной ссылке: ");
        String linkLimit = input.next();

        String uuid = ls.getOrCreateUuid();

        ShortLink sh = new ShortLink(uuid, longLink, linkLimit);
        //System.out.println(longLink + ": " + sh);

        boolean added = ls.addToFile(sh);
        if (added) {
            System.out.println("Ссылка успешно добавлена! Короткая ссылка: " + sh.getShortLink());
        } else {
            System.out.println("Такая ссылка уже есть в файле!");
        }

        //System.out.println(ls.readFromFile());
    }
}
