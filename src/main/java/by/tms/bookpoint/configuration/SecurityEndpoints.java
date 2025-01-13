package by.tms.bookpoint.configuration;


import java.util.Arrays;

public class SecurityEndpoints {

    public static String[] publicALL() {
        return new String[]{
                "/db/**", //db H2 for dev
//                "/account/**" // delete after test
        };
    }
    public static String[] publicGET() {
        return new String[]{
                "/api/health"
        };
    }

    public static String[] publicPOST() {
        return new String[]{
                "/account/**",
                "/auth/**"
        };
    }

    public static String[] adminDELETED() {
        return new String[]{
                "/admin/**"
        };
    }

//    public static String[] allPublicEndpoints() {
//        return Arrays.stream(new String[][]{
//                publicGET(),
//                publicPOST()
//        }).flatMap(Arrays::stream).toArray(String[]::new);
//    }
}
