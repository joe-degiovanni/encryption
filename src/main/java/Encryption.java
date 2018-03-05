import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.AbstractList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Encryption {

    private static List<BigInteger> encrypt(String unencryptedMessage, Key pubKey) {
        return getCharacterStream(unencryptedMessage)
                .map(character -> encrypt(character, pubKey))
                .collect(Collectors.toList());
    }

    private static BigInteger encrypt(Character character, Key pubKey) {
        return BigInteger.valueOf(character).pow(pubKey.exp.intValueExact()).mod(pubKey.modulus);
    }

    private static String decrypt(List<BigInteger> encryptedMessage, Key privKey) {
        return encryptedMessage.parallelStream()
                .map(value -> (char) value.pow(privKey.exp.intValueExact()).mod(privKey.modulus).byteValueExact())
                .map(character -> character.toString())
                .reduce((c1, c2) -> c1 + c2)
                .orElse("");
    }

    private static Stream<Character> getCharacterStream(String unencryptedMessage) {
        return asList(unencryptedMessage).parallelStream();
    }

    private static List<Character> asList(final String string) {
        return new AbstractList<Character>() {
            public int size() { return string.length(); }
            public Character get(int index) { return string.charAt(index); }
        };
    }

    public static void main (String[] args) {
        KeyPair keys = KeyGenerator.generateKeys();

        System.out.println("public key "+keys.publicKey);
        System.out.println("private key "+keys.privateKey);

        String message = "Hello, Encryption";
        System.out.println("original - "+message);

        Instant start = Instant.now();
        List<BigInteger> encrypted = encrypt(message, keys.publicKey);
        System.out.println("Encryption took "+ Duration.between(start, Instant.now()).toMillis() + " milliseconds");
        System.out.println("encrypted - "+toString(encrypted));
        String decrypted = decrypt(encrypted, keys.privateKey);
        System.out.println("Decryption took "+ Duration.between(start, Instant.now()).toMillis() + " milliseconds");
        System.out.println("decrypted - "+decrypted);

    }

    private static String toString(List<BigInteger> array){
        return array.stream()
                .map(bigInteger -> toHexString(bigInteger))
                .reduce((s1, s2) -> s1 + "," + s2)
                .orElse("");
    }

    private static String toHexString(BigInteger bigInteger) {
        return bigInteger.toString(16);
    }


}
