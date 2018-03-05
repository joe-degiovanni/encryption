import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class KeyGenerator {

    public static KeyPair generateKeys() {
        BigInteger prime1 = getRandomPrimeNumber();
        System.out.println("p" + prime1);

        BigInteger prime2 = getRandomPrimeNumber();
        System.out.println("q" + prime2);

        BigInteger modulus = prime1.multiply(prime2);
        BigInteger secret = (prime1.subtract(BigInteger.ONE)).multiply((prime2.subtract(BigInteger.ONE)));
        System.out.println("s"+secret);

        BigInteger relativePrime = getRelativePrime(secret);
        System.out.println("r"+relativePrime);

        Key publicKey = new Key(relativePrime, modulus);

        BigInteger privExp = relativePrime.modInverse(secret);

        Key privateKey = new Key(privExp, modulus);

        return new KeyPair(publicKey, privateKey);
    }

    private static BigInteger getRandomPrimeNumber() {
        BigInteger result = BigInteger.valueOf((int) (Math.random() * 40960)+1).abs();
        return result.nextProbablePrime();
    }

    private static BigInteger getRelativePrime(BigInteger secret) {
        List<BigInteger> relativePrimes = new ArrayList<>();
        BigInteger candiate = BigInteger.valueOf(65537);
        while (candiate.compareTo(BigInteger.ZERO) > 0) {
            if(secret.gcd(candiate).equals(BigInteger.ONE)) {
                relativePrimes.add(candiate);
                if(candiate.equals(BigInteger.valueOf(65537))) {
                    return BigInteger.valueOf(65537);
                }
            }
            candiate = candiate.subtract(BigInteger.ONE);
        }
        return relativePrimes.get((int)(Math.random()*relativePrimes.size()));
    }
}
