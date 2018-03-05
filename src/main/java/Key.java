import java.math.BigInteger;

public class Key {
        final BigInteger exp;
        final BigInteger modulus;

        Key(BigInteger exp, BigInteger modulus) {
            this.exp = exp;
            this.modulus = modulus;
        }

        @Override
        public String toString() {
            return "{exponent:"+exp+", modulus:"+modulus.toString(16)+"}";
        }
}
