/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/18/2023 - 11:59 PM
 */


package mockproject.backend.utils.tools;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "_#?!@$%^&*-";

    public static void main(String[] args) {
        String password = generatePassword(8, 12);
        System.out.println("Generated Password: " + password);
    }

    public static String generateRamdonURL() {
        int minLength = 40;
        int maxLength = 50;
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();
        String allCharacters = UPPER_CASE + LOWER_CASE + NUMBERS;

        // Ensure at least one character from each character set
        password.append(getRandomChar(UPPER_CASE, random));
        password.append(getRandomChar(LOWER_CASE, random));
        password.append(getRandomChar(NUMBERS, random));

        // Ensure the remaining characters meet the requirements
        int length = minLength + random.nextInt(maxLength - minLength + 1);
        for (int i = 4; i < length; i++) {
            password.append(getRandomChar(allCharacters, random));
        }

        // Shuffle the password characters
        char[] passwordArray = password.toString().toCharArray();
        for (int i = passwordArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = passwordArray[index];
            passwordArray[index] = passwordArray[i];
            passwordArray[i] = temp;
        }
        return new String(passwordArray);
    }


    public static String generatePassword(int minLength, int maxLength) {
        if (minLength < 8 || maxLength > 12 || minLength > maxLength) {
            throw new IllegalArgumentException("Invalid length range");
        }
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();
        String allCharacters = UPPER_CASE + LOWER_CASE + NUMBERS + SPECIAL_CHARACTERS;

        // Ensure at least one character from each character set
        password.append(getRandomChar(UPPER_CASE, random));
        password.append(getRandomChar(LOWER_CASE, random));
        password.append(getRandomChar(NUMBERS, random));
        password.append(getRandomChar(SPECIAL_CHARACTERS, random));

        // Ensure the remaining characters meet the requirements
        int length = minLength + random.nextInt(maxLength - minLength + 1);
        for (int i = 4; i < length; i++) {
            password.append(getRandomChar(allCharacters, random));
        }

        // Shuffle the password characters
        char[] passwordArray = password.toString().toCharArray();
        for (int i = passwordArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = passwordArray[index];
            passwordArray[index] = passwordArray[i];
            passwordArray[i] = temp;
        }
        return new String(passwordArray);
    }

    private static char getRandomChar(String characterSet, SecureRandom random) {
        int index = random.nextInt(characterSet.length());
        return characterSet.charAt(index);
    }

}
