package com.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        HashSet<String> dictionary = new HashSet<>();

        dictionary.add("looked");
        dictionary.add("just");
        dictionary.add("like");
        dictionary.add("her");
        dictionary.add("brother");
        // test
        String sentence = "jesslookedjustliketimherbrother";
        reSpace(sentence, dictionary);

    }

    private static void reSpace(String input, HashSet<String> dictionary) {
        System.out.println("---------------------------------------");
        System.out.println("Входные параметры: " + input);
        System.out.println("Словарь: " + dictionary);
        Trie trie = new Trie(dictionary);
        char[] chars = input.toCharArray();
        Map<Character, Letter> letters = trie.letters;
        Letter lastLetter = null;
        StringBuilder sb = new StringBuilder();
        int unrecognizedCharsCount = 0;
        for (int i = 0; i < chars.length; i++) {
            int j = 0;
            while (i + j < chars.length && letters.containsKey(chars[i + j])) {
                lastLetter = letters.get(chars[i + j]);
                letters = lastLetter.children;
                j++;
            }

            if (lastLetter != null && lastLetter.wordEnd) {
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) != ' ') {
                    sb.append(' ');
                }
                sb.append(new String(Arrays.copyOfRange(chars, i, i + j)));
                sb.append(' ');
                i += (j - 1);
            } else {
                sb.append(chars[i]);
                unrecognizedCharsCount++;
            }
            lastLetter = null;
            letters = trie.letters;
        }

        System.out.println("Вывод: " + sb);
        System.out.println("Нераспознанных символов: " + unrecognizedCharsCount);
    }

    private static class Trie {
        private Map<Character, Letter> letters = new HashMap<>();

        Trie(HashSet<String> dictionary) {
            for (String word : dictionary) {
                Map<Character, Letter> candidates = letters;
                char[] charArray = word.toCharArray();
                for (int i = 0; i < charArray.length; i++) {
                    char c = charArray[i];
                    Letter l = new Letter(i == charArray.length - 1);
                    if (!candidates.containsKey(c)) {
                        candidates.put(c, l);
                        candidates = l.children;
                    } else {
                        candidates = candidates.get(c).children;
                    }

                }

            }

        }
    }

    private static class Letter {
        private final boolean wordEnd;
        private Map<Character, Letter> children = new HashMap<>();

        Letter(boolean wordEnd) {
            this.wordEnd = wordEnd;
        }
    }
}
