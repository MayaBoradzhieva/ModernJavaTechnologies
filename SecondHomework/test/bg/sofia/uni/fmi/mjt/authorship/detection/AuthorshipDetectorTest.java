package bg.sofia.uni.fmi.mjt.authorship.detection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class AuthorshipDetectorTest {

    private static final double DELTA = 0.0001;
    private static final int NUMBER_OF_FEATURES = 5;
    private static final int INITIAL_WEIGHT = 1;

    AuthorshipDetectorImpl authorshipDetector;
    InputStream knownSignaturesStream;
    InputStream mysteryTextStream;
    InputStream mysteryTextStream1;

    double[] weights;

    @Before
    public void setUp() {
        weights = new double[NUMBER_OF_FEATURES];
        Arrays.fill(weights, INITIAL_WEIGHT);

        knownSignaturesStream = TextStreamInitializer.initKnownSignaturesStream();
        mysteryTextStream = TextStreamInitializer.initMysteryTextStream();
        mysteryTextStream1 = TextStreamInitializer.initMysteryText1Stream();
        authorshipDetector = new AuthorshipDetectorImpl(knownSignaturesStream, weights);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testSignatureWhenNull() {
        authorshipDetector.calculateSignature(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSimilarityWhenBothNullSignatures() {
        authorshipDetector.calculateSimilarity(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAuthorWhenNullInputStream() {
        authorshipDetector.findAuthor(null);
    }

    @Test
    public void testCalculateSimilarity() {
        Map<FeatureType, Double> first = new HashMap<>();

        final double averageWordLengthFirst = 4.4;
        final double typeTokenRatioFirst = 0.1;
        final double hapaxLegomenaRatioFirst = 0.05;
        final double averageSentenceLengthFirst = 10.0;
        final double averageSentenceComplexityFirst = 2.0;

        first.put(FeatureType.AVERAGE_WORD_LENGTH, averageWordLengthFirst);
        first.put(FeatureType.TYPE_TOKEN_RATIO, typeTokenRatioFirst);
        first.put(FeatureType.HAPAX_LEGOMENA_RATIO, hapaxLegomenaRatioFirst);
        first.put(FeatureType.AVERAGE_SENTENCE_LENGTH, averageSentenceLengthFirst);
        first.put(FeatureType.AVERAGE_SENTENCE_COMPLEXITY, averageSentenceComplexityFirst);

        LinguisticSignature firstSignature = new LinguisticSignature(first);

        final double averageWordLengthSecond = 4.3;
        final double typeTokenRatioSecond = 0.1;
        final double hapaxLegomenaRatioSecond = 0.04;
        final double averageSentenceLengthSecond = 16.0;
        final double averageSentenceComplexitySecond = 4.0;

        Map<FeatureType, Double> second = new HashMap<>();
        second.put(FeatureType.AVERAGE_WORD_LENGTH, averageWordLengthSecond);
        second.put(FeatureType.TYPE_TOKEN_RATIO, typeTokenRatioSecond);
        second.put(FeatureType.HAPAX_LEGOMENA_RATIO, hapaxLegomenaRatioSecond);
        second.put(FeatureType.AVERAGE_SENTENCE_LENGTH, averageSentenceLengthSecond);
        second.put(FeatureType.AVERAGE_SENTENCE_COMPLEXITY, averageSentenceComplexitySecond);

        LinguisticSignature secondSignature = new LinguisticSignature(second);

        double actual = authorshipDetector.calculateSimilarity(firstSignature, secondSignature);
        final double expected = 8.11;

        assertEquals(expected, actual, DELTA);
    }

    @Test
    public void testCalculateSignature() {
        LinguisticSignature actual = authorshipDetector.calculateSignature(mysteryTextStream1);

        Map<FeatureType, Double> expSignature = new HashMap<>();
        final double averageWordLength = 4.2894736842105265;
        final double typeTokenRatio = 0.6929824561403509;
        final double hapaxLegomenaRatio = 0.5614035087719298;
        final double averageSentenceLength = 11.4;
        final double averageSentenceComplexity = 0.1;

        expSignature.put(FeatureType.AVERAGE_WORD_LENGTH, averageWordLength);
        expSignature.put(FeatureType.TYPE_TOKEN_RATIO, typeTokenRatio);
        expSignature.put(FeatureType.HAPAX_LEGOMENA_RATIO, hapaxLegomenaRatio);
        expSignature.put(FeatureType.AVERAGE_SENTENCE_LENGTH, averageSentenceLength);
        expSignature.put(FeatureType.AVERAGE_SENTENCE_COMPLEXITY, averageSentenceComplexity);

        LinguisticSignature expected = new LinguisticSignature(expSignature);

        boolean flag = true;
        for (int i = 0; i < NUMBER_OF_FEATURES; i++) {
            double first = expected.getFeatures().get(FeatureType.values()[i]);
            double second = actual.getFeatures().get(FeatureType.values()[i]);

            if (Math.abs(first - second) > DELTA) {
                flag = false;
            }
        }

        assertTrue(flag);
    }

    @Test
    public void testFindAuthor() {

        String actual = authorshipDetector.findAuthor(mysteryTextStream);
        String expected = "Brothers Grim";

        assertEquals(expected, actual);

    }

}
