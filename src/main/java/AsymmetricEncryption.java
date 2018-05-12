import java.math.BigInteger;
import java.util.AbstractList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Demonstrate basic Asymmetric Encryption
 */
public class AsymmetricEncryption {

    /**
     * encrypt a message with the given key
     */
    public static List<BigInteger> encrypt(String unencryptedMessage, Key pubKey) {
        return getCharacterStream(unencryptedMessage)
                .map(character -> encrypt(character, pubKey))
                .collect(Collectors.toList());
    }

    /**
     * encrypt a single character with the given key
     */
    private static BigInteger encrypt(Character character, Key pubKey) {
        return BigInteger.valueOf(character).pow(pubKey.exp.intValueExact()).mod(pubKey.modulus);
    }

    /**
     * decrypt a message with the given key
     */
    public static String decrypt(List<BigInteger> encryptedMessage, Key privKey) {
        return encryptedMessage.parallelStream()
                .map(value -> decrypt(value, privKey))
                .reduce(String::concat)
                .orElse("");
    }

    /**
     * decrypt a single character with the given key
     */
    private static String decrypt(BigInteger value, Key privKey) {
        Character decrypted = (char) value.pow(privKey.exp.intValueExact()).mod(privKey.modulus).byteValueExact();
        return decrypted.toString();
    }

    private static Stream<Character> getCharacterStream(String unencryptedMessage) {
        return asList(unencryptedMessage).parallelStream();
    }

    private static List<Character> asList(final String string) {
        return new AbstractList<Character>() {
            public int size() {
                return string.length();
            }

            public Character get(int index) {
                return string.charAt(index);
            }
        };
    }


}
