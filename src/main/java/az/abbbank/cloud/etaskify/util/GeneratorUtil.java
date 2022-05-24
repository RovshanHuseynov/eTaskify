package az.abbbank.cloud.etaskify.util;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneratorUtil {
    public static String generatePassword(int length){
        final String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        return IntStream.range(0, length)
                .map(i -> random.nextInt(text.length() - 1))
                .mapToObj(randomIndex -> String.valueOf(text.charAt(randomIndex)))
                .collect(Collectors.joining());
    }

    public static String generateCompanyUsername(String companyName){
        return String.join("","admin", companyName);
    }
}
