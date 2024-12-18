package ma.enset.aspects;

import org.springframework.stereotype.Component;

@Component
public class SecurityContext
{
    private static String username = "";
    private static String password = "";
    private static String[] roles = {};
    public static boolean isAuthenticated() {
        return !username.isEmpty() && !password.isEmpty();
    }


    public static void authenticate(String username, String password, String[] roles) {
        System.out.println("user name: " + username +" try to authenticate");
        if(username.equals("admin") && password.equals("1234")) {
            SecurityContext.username = username;
            SecurityContext.password = password;
            SecurityContext.roles = roles;
        } else {
            throw new RuntimeException("Bad credentials");
        }
    }

    public static boolean hasRole(String role) {
        for(String r : roles) {
            if(r.equals(role)) return true;
        }
        return false;
    }


}
