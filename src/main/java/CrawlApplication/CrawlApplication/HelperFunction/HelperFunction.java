package CrawlApplication.CrawlApplication.HelperFunction;

import javafx.util.Pair;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperFunction {

    private final static BiFunction<String, String, UnaryOperator<String>> subStringRegEx = (begin, end) -> {
        UnaryOperator<String> subStringFunc = (result) -> {
            if (begin != null && end != null && !begin.isEmpty() && !end.isEmpty()) {
                result = result.substring(result.indexOf(begin));
                Pattern pattern = Pattern.compile(end);
                Matcher matcher = pattern.matcher(result);
                int endIndex = (matcher.find()) ? matcher.start() : 0;
                result = result.substring(0, endIndex + ((endIndex == 0) ? 0 : matcher.group().length()));
            }
            return result;
        };
        return subStringFunc;
    };

    private final static BiFunction<String, String, UnaryOperator<String>> subString = (begin, end) -> {
        UnaryOperator<String> subStringFunc = (result) -> {
            if (begin != null && end != null && !begin.isEmpty() && !end.isEmpty()) {
                result = result.substring(result.indexOf(begin));
                result = result.substring(0, result.indexOf(end));
            }
            return result;
        };
        return subStringFunc;
    };

    private final static Function<Pair<String, String>[], UnaryOperator<String>> replaceAllInvalidTokens = (invalidTokens) -> {
        UnaryOperator<String> replaceAllFunc = (result) -> {
            if (invalidTokens != null && invalidTokens.length > 0) {
                for (int i = 0; i < invalidTokens.length; i++) {
                    result = result.replaceAll(invalidTokens[i].getKey(), invalidTokens[i].getValue());
                }
            }
            return result;
        };
        return replaceAllFunc;
    };

    public static UnaryOperator<String> getReplaceAllInvalidTokens(Pair<String, String>[] pairs) {
        return replaceAllInvalidTokens.apply(pairs);
    }

    public static UnaryOperator<String> getSubStringRegEx(String begin, String end) {
        return subStringRegEx.apply(begin, end);
    }

    public static UnaryOperator<String> getSubString(String begin, String end){
        return subString.apply(begin, end);
    }
}
