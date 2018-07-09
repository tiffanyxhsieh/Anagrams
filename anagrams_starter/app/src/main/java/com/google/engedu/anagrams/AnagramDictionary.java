/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList = new ArrayList<>();
    private HashMap <String, ArrayList> lettersToWord = new HashMap<>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<Integer, String> sizeToWords = new HashMap<>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;

        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (!lettersToWord.containsKey(sortLetters(word))) {
                lettersToWord.put(sortLetters(word), new ArrayList());
            }
            wordSet.add(word);
            wordList.add(word);

        }
    }

    public boolean isGoodWord(String word, String base) {
        if (word.contains(base)) {
            return false;
        }

        if (wordSet.contains(word)){
            return true;
        } else {
            return false;
        }

    }

    public String sortLetters(String word) {
        char[] temp = word.toCharArray();
        Arrays.sort(temp);
        return Arrays.toString(temp);



    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String alphTargetWord = sortLetters(targetWord);
        for (String s: wordList) {
            if (s.length() == targetWord.length() && sortLetters(targetWord).equals(sortLetters(s))) {
                result.add(s);
            }
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        List<String> originalAnagrams = getAnagrams(word);
        ArrayList<String> result = new ArrayList<String>();
        for (String s: originalAnagrams) {
            int length = s.length();
            StringBuilder anagram = new StringBuilder(s);
            for (int i = 0; i < length; i++) {
                for (char letter = 'a'; letter <= 'z'; letter++) {
                    String anagramWithLetter = anagram.insert(i, letter).toString();
                    if (wordList.contains(anagramWithLetter)) {
                        result.add(anagramWithLetter);
                    }
                    anagram = new StringBuilder((s));

                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {

        int r = (int)(Math.random() * wordList.size());
        String startingWord = wordList.get(r);

        Log.e("*********initial starting word",startingWord);
        while (getAnagramsWithOneMoreLetter(startingWord).size() < MIN_NUM_ANAGRAMS) {
            startingWord = wordList.get((int)(Math.random() * wordList.size()));
        }

        Log.e("********FINAL STARTING WORD", startingWord);

        return startingWord;
    }
}
