package ua.edu.cdu.vu.exchangeprocessor.encryption;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EnDecryptionJob {

    private static final String KEY = "JASYPT_ENCRYPTOR_PASSWORD";
    private static final String ALGORITHM = "PBEWITHHMACSHA512ANDAES_256";
    private static final StandardPBEStringEncryptor ENCRYPTOR = new StandardPBEStringEncryptor();

    @BeforeAll
    static void init() {
        ENCRYPTOR.setPassword(System.getenv(KEY));
        ENCRYPTOR.setAlgorithm(ALGORITHM);
        ENCRYPTOR.setKeyObtentionIterations(1000);
        ENCRYPTOR.setIvGenerator(new RandomIvGenerator());
        ENCRYPTOR.setSaltGenerator(new RandomSaltGenerator());
    }

    @ParameterizedTest
    @ValueSource(strings = "")
    void encrypt(String value) {
        System.out.println(ENCRYPTOR.encrypt(value));
    }

    @ParameterizedTest
    @ValueSource(strings = "sYK9iyxD5+gbhxraLq78JoERGXbwEyQmH0YS6dP+fmUYVfIWzAH5VKIEkDii2NUDPImLkdiHT0hIVyqOBNV06gGOu4T6HGtPCOPZyndmOfo=")
    void decrypt(String value) {
        System.out.println(ENCRYPTOR.decrypt(value));
    }
}
