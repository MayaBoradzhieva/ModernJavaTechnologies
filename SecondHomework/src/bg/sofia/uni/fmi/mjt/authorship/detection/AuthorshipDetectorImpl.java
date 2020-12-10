package bg.sofia.uni.fmi.mjt.authorship.detection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class AuthorshipDetectorImpl implements AuthorshipDetector {

    private final static int NUMBER_OF_FEATURES = 5;
    
    private double[] weights;
    private Map<String, LinguisticSignature> knownSignatures;
    private Map<FeatureType, Double> features;

    public AuthorshipDetectorImpl(InputStream signaturesDataset, double[] weights) {
        knownSignatures = new HashMap<>();
        features = new HashMap<>();

        try (BufferedReader signaturesBufferedReader = 
                new BufferedReader(new InputStreamReader(signaturesDataset))) {
            String line;
            while ((line = signaturesBufferedReader.readLine()) != null) {

                String newLine = line.strip();

                String[] tokens = newLine.split(", ");
                String authorName = tokens[0];

                for (int i = 0; i < NUMBER_OF_FEATURES; i++) {
                    FeatureType featureTypeValue = FeatureType.values()[i];
                    Double feature = Double.parseDouble(tokens[i + 1]); // skipping the author's name
                    features.put(featureTypeValue, feature);

                }

                knownSignatures.put(authorName, new LinguisticSignature(features));

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        this.weights = new double[NUMBER_OF_FEATURES];
        System.arraycopy(weights, 0, this.weights, 0, NUMBER_OF_FEATURES);
    }

    public static String cleanUp(String word) {
        return word.toLowerCase().replaceAll(
                "^[!.,:;\\-?<>#\\*\'\"\\[\\(\\]\\n\\t\\\\]+|[!.,:;\\-?<>#\\*\'\"\\[\\(\\]\\n\\t\\\\]+$", "");
    }

    @Override
    public LinguisticSignature calculateSignature(InputStream mysteryText) {

        if (mysteryText == null) {
            throw new IllegalArgumentException();
        }

        long symbolsInWord = 0;
        long numberOfWords = 0;
        long sentenceCount = 0;
        long phrasesCount = 0;
        Set<String> uniqueWords = new HashSet<>();
        Map<String, Integer> wordFrequence = new HashMap<>();
        
        try (BufferedReader mysteryTextBufferedReader =
                new BufferedReader(new InputStreamReader(mysteryText))) {
            String line;
            while ((line = mysteryTextBufferedReader.readLine()) != null) {

                String newLine = cleanUp(line);

                String[] words = newLine.split(" "); // words
                numberOfWords += words.length;

                for (int i = 0; i < words.length; i++) {
                    symbolsInWord += words[i].length();
                }

                for (int i = 0; i < words.length; i++) {
                    uniqueWords.add(words[i]);
                }

                for (int i = 0; i < words.length; i++) {

                    if (wordFrequence.containsKey(words[i])) {
                        wordFrequence.replace(words[i], wordFrequence.get(words[i]), wordFrequence.get(words[i]) + 1);
                    } else {
                        wordFrequence.put(words[i], 1);
                    }
                }

                String strippedLine = line.strip();
                String terminals = "?!.";

                for (int i = 0; i < strippedLine.length(); i++) {
                    if (terminals.indexOf(strippedLine.charAt(i)) != -1) { // If the terminals string contains the
                        // character
                        sentenceCount++;
                    }
                }

                String delimiters = ",:;";

                for (int i = 0; i < strippedLine.length(); i++) {
                    if (delimiters.indexOf(strippedLine.charAt(i)) != -1) { // If the delimiters string contains the
                        // character
                        phrasesCount++;
                    }
                }

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        Set<String> meetsOnceWord = new HashSet<>();
        for (Map.Entry<String, Integer> entry : wordFrequence.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            if (value == 1) {
                meetsOnceWord.add(key);
            }
        }

        double averageWordLength = (double) symbolsInWord / numberOfWords;
        double typeTokenRatio = (double) uniqueWords.size() / numberOfWords;
        double hapaxLegomenaRatio = (double) meetsOnceWord.size() / numberOfWords;
        double averageSentenceLength = (double) numberOfWords / sentenceCount;
        double averageSentenceComplexity = (double) phrasesCount / sentenceCount;

        Map<FeatureType, Double> features = new HashMap<>();
        features.put(FeatureType.AVERAGE_WORD_LENGTH, averageWordLength);
        features.put(FeatureType.TYPE_TOKEN_RATIO, typeTokenRatio);
        features.put(FeatureType.HAPAX_LEGOMENA_RATIO, hapaxLegomenaRatio);
        features.put(FeatureType.AVERAGE_SENTENCE_LENGTH, averageSentenceLength);
        features.put(FeatureType.AVERAGE_SENTENCE_COMPLEXITY, averageSentenceComplexity);

        LinguisticSignature signature = new LinguisticSignature(features);

        return signature;

    }

    @Override
    public double calculateSimilarity(LinguisticSignature firstSignature, LinguisticSignature secondSignature) {

        if (firstSignature == null || secondSignature == null) {
            throw new IllegalArgumentException();
        }

        double similarity = 0.0;

        for (int i = 0; i < NUMBER_OF_FEATURES; i++) {
            double firstSignatureIthValue = firstSignature.getFeatures().get(FeatureType.values()[i]);
            double secondSignatureIthValue = secondSignature.getFeatures().get(FeatureType.values()[i]);
            similarity += Math.abs(firstSignatureIthValue - secondSignatureIthValue) * weights[i];
        }
        
        return similarity;
    }

    @Override
    public String findAuthor(InputStream mysteryText) {

        if (mysteryText == null) {
            throw new IllegalArgumentException();
        }

        LinguisticSignature mysteryTextSignature = calculateSignature(mysteryText);
        double result = Double.MAX_VALUE;
        double similarity = 0.0;

        StringBuilder searchedAuthor = new StringBuilder();

        for (Entry<String, LinguisticSignature> entry : knownSignatures.entrySet()) {
            String authorName = entry.getKey();
            LinguisticSignature authorSignature = entry.getValue();

            similarity = calculateSimilarity(authorSignature, mysteryTextSignature);

            if (similarity < result) {
                result = similarity;
                searchedAuthor.replace(0, searchedAuthor.length(), authorName);
            }
        }
        
        return searchedAuthor.toString();
    }
}