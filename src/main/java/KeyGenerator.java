import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class KeyGenerator {

    public static KeyPair generateKeys(int maxPrime) {
        BigInteger prime1 = getRandomPrimeNumber(maxPrime);
        BigInteger prime2 = getRandomPrimeNumber(maxPrime);

        BigInteger modulus = prime1.multiply(prime2);
        BigInteger secret = (prime1.subtract(BigInteger.ONE)).multiply((prime2.subtract(BigInteger.ONE)));

        BigInteger relativePrime = getRelativePrime(secret);
        BigInteger privExp = relativePrime.modInverse(secret);

        Key publicKey = new Key(relativePrime, modulus);
        Key privateKey = new Key(privExp, modulus);

        return new KeyPair(publicKey, privateKey);
    }

    private static BigInteger getRandomPrimeNumber(int maxPrime) {
        BigInteger result = BigInteger.valueOf((int) (Math.random() * maxPrime)+1).abs();
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
