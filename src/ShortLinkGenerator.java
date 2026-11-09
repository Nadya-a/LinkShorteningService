import java.math.BigInteger;
import java.security.SecureRandom;


public class ShortLinkGenerator {
    private SecureRandom random = new SecureRandom();

    public String shortLinkGeneration()
    {
        return new BigInteger(40, random).toString(32);
    }
}
