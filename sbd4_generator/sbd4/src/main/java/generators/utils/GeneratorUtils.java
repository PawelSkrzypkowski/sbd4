package generators.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class GeneratorUtils {
    public static Date date2017;

    static {
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date2017 = dtf.parse("2017-01-01");
        } catch (ParseException e) {
            date2017 = null;
        }
    }

    public static String generateString(){
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static String generatePesel(){
        return RandomStringUtils.randomNumeric(11);
    }

    public static Character generateSex() {
        return new Random().nextInt(2) == 0 ? 'M' : 'K';
    }

    public static String generateEmail() {
        return generateString() + '@' + generateString() + ".pl";
    }

    public static String generatePhone() {
        return RandomStringUtils.randomNumeric(9);
    }

    public static int generateFrom1To10() {
        return new Random().nextInt(10) + 1;
    }

    public static String generateNip() {
        return RandomStringUtils.randomNumeric(10);
    }

    public static Integer generateSalary() {
        return new Random().nextInt(8000) + 2000;
    }

    public static Date generateDate() {
        return new Date(date2017.getTime() + RandomUtils.nextLong(0, 3L * 365 * 24 * 60 * 60 * 1000));
    }

    public static Boolean generateBoolean() {
        return new Random().nextInt(2) == 0;
    }
}
