import java.time.LocalDate;

public class ShortLink {
    private String longLink;
    private String shortLink;
    private int limit;
    private String expiryDate; // храним как строку, так проще писать в JSON

    // пустой конструктор для Gson
    public ShortLink() {
    }

    // Конструктор создания новой ссылки
    public ShortLink(String longLink, String limit) {
        // Используем сеттеры, чтобы применить проверки сразу при создании
        setLongLink(longLink);
        setShortLink();
        setLimit(limit);
        setExpiryDate();
    }

    // Конструктор для чтения из файла
    public ShortLink(String longLink, String shortLink, String limit, String expiryDate) {
        // Используем сеттеры, чтобы применить проверки сразу при создании
        setLongLink(longLink);
        setShortLink(shortLink);
        setLimit(limit);
        setExpiryDate(expiryDate);
    }

    @Override
    public String toString() {
        return "ShortLink{" +
                "longLink='" + longLink + '\'' +
                ", shortLink='" + shortLink + '\'' +
                ", limit=" + limit +
                ", expiryDate=" + expiryDate +
                '}';
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

    public String getExpiryDate() {
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
        this.expiryDate = LocalDate.now().plusDays(3).toString();
    }

    // для чтения ссылки из файла
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
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
        return LocalDate.parse(expiryDate).isBefore(LocalDate.now());
    }
}
