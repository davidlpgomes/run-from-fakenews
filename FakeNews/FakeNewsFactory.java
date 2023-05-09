package FakeNews;

import java.util.Map;

public class FakeNewsFactory {
    public static FakeNews FakeNewsCreator(String type) {

        private static final Map<String, FakeNews> fakeNews = Map.ofEntries(
            entry("FN1", new FN1()),
            entry("FN2", new FN2()),
            entry("FN3", new FN3())
        );

        public static FakeNews createFakeNews(String type) {
            if (!fakeNews.containsKey(type)) {
                throw new IllegalArgumentException("Invalid type of fake news!");
            }
            return fakeNews.get(type);
        }
    }
}
