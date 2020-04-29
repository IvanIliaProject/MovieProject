package validations;

import java.util.regex.Pattern;

public class Validation {

    public static boolean validPrice(Double price){
        String priceRegex = "^\\d+(.\\d{1,2})?$";
        Pattern pattern = Pattern.compile(priceRegex);

        if (price == null){
            return false;
        }
        return pattern.matcher(price.toString()).matches();
    }
    public static boolean validEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (email == null){
            return false;
        }
        return pattern.matcher(email).matches();
    }
    public static boolean isValidPassword(String password){
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pat = Pattern.compile(passwordRegex);
        if (password == null){
            return false;
        }else{
            return pat.matcher(password).matches();
        }
    }
    public static boolean isValidSeats(Integer seats){
        String priceRegex = "^\\d+(\\d{1,2})?$";
        Pattern pattern = Pattern.compile(priceRegex);

        if (seats == null){
            return false;
        }
        return pattern.matcher(seats.toString()).matches();
    }
}
