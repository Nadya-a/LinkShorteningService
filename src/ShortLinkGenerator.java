import java.math.BigInteger;
import java.security.SecureRandom;


public class ShortLinkGenerator {
    private SecureRandom random = new SecureRandom();

    private static final String prefix = "clck.ru/";

    public String shortLinkGeneration()
    {
        String shortPart = new BigInteger(40, random).toString(32);
        String finalLink = prefix + shortPart;
        return finalLink;
    }
}
