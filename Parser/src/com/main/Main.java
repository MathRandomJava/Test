package com.main;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {

        HashSet<String> dictionary = new HashSet<>();

        dictionary.add("looked");
        dictionary.add("just");
        dictionary.add("like");
        dictionary.add("her");
        dictionary.add("brother");

        String sentence = "jesslookedjustliketimherbrother";

        System.out.println(bestSplit(dictionary, sentence));
    }

    private static String bestSplit(HashSet<String> dictionary, String sentence) {
        ParseResult[] memo = new ParseResult[sentence.length()];
        ParseResult result = split(dictionary, sentence, 0, memo);
        return result.parsed;
    }

    private static ParseResult split(HashSet<String> dictionary, String sentence, int start, ParseResult[] memo) {
        if (start >= sentence.length()) {
            return new ParseResult(0, "");
        }

        if (memo[start] != null) {
            return memo[start];
        }

        int bestInvalid = Integer.MAX_VALUE;
        String bestParsing = null;
        String partial = "";
        for (int i = start; i < sentence.length(); i++) {
            char c = sentence.charAt(i);
            partial += c;
            int invalid = dictionary.contains(partial) ? 0 : partial.length();
            if (invalid < bestInvalid) {
                ParseResult result = split(dictionary, sentence, i+1, memo);
                if (invalid + result.invalid < bestInvalid) {
                    bestInvalid = invalid + result.invalid;
                    bestParsing = partial + " " + result.parsed;
                    if (bestInvalid == 0) {
                        break;
                    }
                }
            }
        }

        memo[start] = new ParseResult(bestInvalid, bestParsing);
        return memo[start];
    }

    public static class ParseResult {
        int invalid;
        String parsed;

        ParseResult(int invalid, String parsed) {
            this.invalid = invalid;
            this.parsed = parsed;
        }
    }

}
