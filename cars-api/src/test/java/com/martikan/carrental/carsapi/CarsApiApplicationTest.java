package com.martikan.carrental.carsapi;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Random;

@ActiveProfiles("test")
@SpringBootTest(classes = CarsApiApplication.class)
public abstract class CarsApiApplicationTest {

    private final Random random = new Random();

    /**
     * Generates a random string with a length of 10.
     * @return random string
     */
    protected String getRandomString() {
        return getRandomString(10);
    }

    /**
     * Generates a random string
     * @param targetStringLength length of the generated string
     * @return random string
     */
    protected String getRandomString(int targetStringLength) {
        final int leftLimit = 97; // letter 'a'

        final int rightLimit = 122; // letter 'z'

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Generates a random number between {@literal 0} and {@literal 100}.
     * @return random number
     */
    protected int getRandomNumber() {
        return getRandomNumber(100);
    }

    /**
     * Generates a random number between {@literal 0} and `bound`
     * @param bound bound
     * @return random number
     */
    protected int getRandomNumber(int bound) {
        return random.nextInt(bound);
    }

}
