import spock.lang.Specification
import spock.lang.Unroll

class AsymmetricEncryptionSpec extends Specification {

    private KeyPair keys

    def setup() {
        keys = KeyGenerator.generateKeys(2048)
    }

    @Unroll
    def "Expect that encryption and decryption works -- message = \'#message\'"() {

        when:
        List<BigInteger> encrypted = AsymmetricEncryption.encrypt(message, keys.publicKey)

        then:
        encrypted.size() == message.size()
        encrypted != message

        when:
        String decrypted = AsymmetricEncryption.decrypt(encrypted, keys.privateKey)

        then:
        decrypted == message

        where:
        message << ["Hello, Encryption", "test2", ""]
    }
}
