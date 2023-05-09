package validator;

//import static java.lang.Character.;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.joining;
import static validator.Flaw.NODIGIT;
import static validator.Flaw.NOLOWER;
import static validator.Flaw.NOUPPER;
import static validator.Flaw.TOO_SHORT;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Password validator using lambdas and maps.
 *
 * @author Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}
 */
public class Validator {

//    List<Character> convertStringToCharList(String password) {
//        List<Character> chars = password.chars()
//                .mapToObj(e -> (char) e)
//                .collect(Collectors.toList());
//        return chars;
//    }
//    List<String> convertPasswordToList(String password) {
//        String[] sSplit = password.split("");
//        List<String> list = new ArrayList<>(Arrays.asList(sSplit));
//        return list;
//    }
    void validate(String password) {
        EnumSet<Flaw> flaws = EnumSet.allOf(Flaw.class);
        Pattern patternDigit = Pattern.compile("\\d");
        Pattern patternUpper = Pattern.compile("[A-Z]");
        Pattern patternLower = Pattern.compile("[a-z]");
        String specialChars = SpecialChars.getSpecialChars().toString();
        Pattern patternSpecial = Pattern.compile("[" + specialChars.substring(1, specialChars.length() - 1).replaceAll("[, ]", "").replace("\\", "\\\\").replace("[", "\\[").replace("]", "\\]") + "]");
        
        for (Flaw flaw : flaws) {
            switch (flaw) {
                case TOO_SHORT:
                    if (password.length() >= 10) {
                        flaws.remove(flaw);
                    }
                    break;
                case NOUPPER:
                    if (patternUpper.matcher(password).find()) {
                        flaws.remove(flaw);
                    }
                    break;
                case NOLOWER:
                    if (patternLower.matcher(password).find()) {
                        flaws.remove(flaw);
                    }
                    break;
                case NODIGIT:
                    if (patternDigit.matcher(password).find()) {
                        flaws.remove(flaw);
                    }
                    break;
                case NOSPECIAL:
                    if (patternSpecial.matcher(password).find()) {
                        flaws.remove(flaw);
                    }
                    break;
            }
        }

        if (!flaws.isEmpty()) {
            StringBuilder error = new StringBuilder();
            for (Flaw flaw : flaws) {
                error.append(flaw.getDescription()).append(" ");
            }
            
            throw new InvalidPasswordException(error.toString());
        }
//        String flawString = "";
//        List<Character> chars = convertStringToCharList(password);
//        
//        // Too short
//        if (password.length() < 10) {
//            flawString += Flaw.TOO_SHORT;
//        }
//        
//        // Upper case
//        boolean upperCase = false;
//        for (int i = 0; i < password.length(); i++) {
//            if (Character.isUpperCase(password.charAt(i))) {
//                upperCase = true;
//            }
//        }
//        if (upperCase == false) {
//            flawString += Flaw.NOUPPER;
//        }
//        
//        // Lower case
//        boolean lowerCase = false;
//        for (int i = 0; i < password.length(); i++) {
//            if (Character.isLowerCase(password.charAt(i))) {
//                lowerCase = true;
//            }
//        }
//        if (lowerCase == false) {
//            flawString += Flaw.NOLOWER;
//        }
//        
//        // Digits
//        if (!password.matches(".*[0-9].*")) {
//            flawString += Flaw.NODIGIT;
//        }
//        
//        // Special characters ???????????
//        
//        if (!flawString.isEmpty()) {
//            throw new InvalidPasswordException(flawString);
//        }
    }
}
