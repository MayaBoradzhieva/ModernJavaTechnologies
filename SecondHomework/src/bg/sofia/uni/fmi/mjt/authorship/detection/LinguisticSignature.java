package bg.sofia.uni.fmi.mjt.authorship.detection;

import java.util.HashMap;
import java.util.Map;

public class LinguisticSignature {

    private Map<FeatureType, Double> features;

    public LinguisticSignature(Map<FeatureType, Double> features) {
        this.features = new HashMap<>();
        this.features.putAll(features);
    }

    public Map<FeatureType, Double> getFeatures() {
        return features;

    }
}
