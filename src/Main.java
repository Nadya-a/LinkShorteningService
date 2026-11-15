import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        LinkService ls = new LinkService();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("1. Добавить новую ссылку");
            System.out.println("2. Перейти по короткой ссылке");
            System.out.println("0. Выход");
            System.out.println("Введите номер действия, которое хотите совершить: ");

            String choice = input.nextLine().trim();

            if (choice.equals("1")) {
                addNewLink(ls, input);
            } else if (choice.equals("2")) {
                openShortLink(ls, input);
            } else if (choice.equals("0")) {
                System.out.println("Программа заканчивает свою работу...");
                break;
            } else {
                System.out.println("Введите номер из пункта меню");
            }
        }
    }

    public static void addNewLink(LinkService ls, Scanner input) throws Exception {
        System.out.print("Введите ссылку, которую нужно сократить: ");
        String longLink = input.nextLine().trim();

        System.out.print("Введите максимальное кол-во переходов по сокращенной ссылке: ");
        String linkLimit = input.nextLine().trim();

        String uuid = ls.getOrCreateUuid();
        ShortLink sh = new ShortLink(uuid, longLink, linkLimit);

        if (ls.addToFile(sh)) {
            System.out.println("Ссылка успешно добавлена! Короткая ссылка: " + sh.getShortLink());
        } else {
            System.out.println("Такая ссылка уже есть в файле!");
        }
    }

    public static void openShortLink(LinkService ls, Scanner input) throws Exception {
        System.out.print("Введите ссылку, по которой хотите перейти: ");
        String inputLink = input.nextLine().trim();

        ShortLink found = null;
        for (ShortLink link : ls.readFromFile()) {
            if (link.getShortLink().equals(inputLink)) {
                found = link;
                break;
            }
        }

        if (found == null) {
            System.out.println("Такой короткой ссылки не существует");
            return;
        }

//        List<ShortLink> links = ls.readFromFile();
//        for (ShortLink link : links) {
//            if (link.getShortLink().equals(inputLink)) {
//                // проверки isExpired / isLimitExhausted
//                link.linkRedirection();
//                ls.saveToFile(links);
//                Desktop.getDesktop().browse(new URI(link.getLongLink()));
//                return;
//            }
//        }

        checkLink(inputLink, found, ls);
    }

    public static void checkLink (String inputLink, ShortLink found, LinkService ls) throws Exception {
        if (found.isExpired()) {
            System.out.println("Ссылка просрочена");
        }
        else if (found.isLimitExhausted()) {
            System.out.println("Лимит переходов по ссылке истек");
        }
        else {
            List<ShortLink> links = ls.readFromFile();
            for (ShortLink link : links) {
                if (link.getShortLink().equals(inputLink)) {
                    // проверки isExpired / isLimitExhausted
                    link.linkRedirection();
                    ls.saveToFile(links);

                    System.out.println("Происходит редирект... ");
                    Desktop.getDesktop().browse(new URI(link.getLongLink()));
                    return;
                }
            }

//            found.linkRedirection();
//            //ls.saveToFile(found);
//            System.out.println("Происходит редирект... ");
//            Desktop.getDesktop().browse(new URI(found.getLongLink()));
        }
    }
}
