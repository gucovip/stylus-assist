public class Utils {
    public static String formatClassWithShortLine(String str) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = str.length() - 1; i > 0; i--) {
            if (Character.isUpperCase(str.charAt(i))) {
                sb.insert(i, "-");
            }
        }
        return sb.toString().toLowerCase();
    }

    public static String formatClass(String str) {
        return str;
    }
}
