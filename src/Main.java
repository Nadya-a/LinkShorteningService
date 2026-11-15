import java.awt.Desktop;
import java.net.URI;
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

        checkLink(inputLink, found, ls);
    }

    public static void checkLink (String inputLink, ShortLink found, LinkService ls) throws Exception {
        List<ShortLink> links = ls.readFromFile();

        if (!found.isExpired()) {
            System.out.println("Ссылка просрочена");

            //Удаление просроченной ссылки
            for (int i = 0; i < links.size(); i++) {
                if (links.get(i).getShortLink().equals(inputLink)) {
                    links.remove(i);
                    break;
                }
            }
            ls.saveToFile(links);
        }
        else if (found.isLimitExhausted()) {
            System.out.println("Лимит переходов по ссылке истек");
        }
        else {
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
        }
    }
}
