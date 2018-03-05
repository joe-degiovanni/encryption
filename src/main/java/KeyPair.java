public class KeyPair {

    final Key privateKey;
    final Key publicKey;

    public KeyPair(Key pub, Key priv){
        this.privateKey = priv;
        this.publicKey = pub;
    }

}
