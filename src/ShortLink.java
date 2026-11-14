import java.time.LocalDate;

public class ShortLink {
    private String longLink;
    private String shortLink;
    private int limit;
    private LocalDate expiryDate;

    // Конструктор создания новой ссылки
    public ShortLink(String longLink, String limit) {
        // Используем сеттеры, чтобы применить проверки сразу при создании
        setLongLink(longLink);
        setShortLink();
        setLimit(limit);
        setExpiryDate();
    }

    // Конструктор для чтения из файла
    public ShortLink(String longLink, String shortLink, String limit, LocalDate expiryDate) {
        // Используем сеттеры, чтобы применить проверки сразу при создании
        setLongLink(longLink);
        setShortLink(shortLink);
        setLimit(limit);
        setExpiryDate(expiryDate);
    }

    // Геттеры (чтение)
    public String getLongLink() {
        return longLink;
    }

    public String getShortLink() {
        return shortLink;
    }

    public int getLimit() {
        return limit;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    // Сеттеры (редактирование) с проверками
    public void setLongLink(String longLink) {
        if (longLink == null || longLink.trim().isEmpty()) {
            throw new IllegalArgumentException("Ошибка конвертации ссылки. Длинная ссылка не может быть пустой!");
        }
        this.longLink = longLink;
    }

    public void setShortLink(String shortLink) {
        if (shortLink == null || shortLink.trim().isEmpty()) {
            throw new IllegalArgumentException("Ошибка сокращения ссылки. Сокращенная ссылка не может быть пустой!");
        }
        this.shortLink = shortLink;
    }

    private void setShortLink(){
        ShortLinkGenerator shg = new ShortLinkGenerator();
        String result = shg.shortLinkGeneration();

        if (result == null || result.trim().isEmpty()) {
            throw new IllegalArgumentException("Ошибка сокращения ссылки. Сокращенная ссылка не может быть пустой!");
        }
        this.shortLink = result;
    }

    public void setLimit(String limit) {
        int number = 3;
        if (limit != null && !limit.trim().isEmpty()) {
            try {
                number = Integer.parseInt(limit.trim());
            } catch (NumberFormatException e) {
                System.out.println("Некорректное число: '" + limit + "'. Установлен лимит по умолчанию = 3.");
                //number = 3;
            }
        }

        if (number <= 0) {
            System.out.println("Ошибка установки лимита. Лимит не может быть отрицательным или равным нулю!");
            number = 3;
        }
        this.limit = number;
    }

    //создание новой ссылки
    private void setExpiryDate() {
        LocalDate currentDate = LocalDate.now();
        this.expiryDate = currentDate.plusDays(3);
    }

    // для чтения ссылки из файла
    public void setExpiryDate(LocalDate expirationDate) {
        if (expirationDate == null) {
            throw new IllegalArgumentException("Дата истечения не может быть null");
        }
        this.expiryDate = expirationDate;
    }

    //уменьшение лимита
    public void linkRedirection() {
        if (isExpired() || isLimitExhausted()) {
            throw new IllegalStateException("Ссылка недоступна");
        }
        this.limit--;
    }

    //проверка истечения лимита
    public boolean isLimitExhausted() {
        return limit <= 0;
//        if (limit > 0)
//            return false;
//        else return true;
    }

    //Проверка истечения даты. True если срок уже истек
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }
}
