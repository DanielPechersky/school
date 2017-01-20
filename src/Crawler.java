import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Crawler {
    private final String toSearch;

    public Crawler(String toSearch) {
        this.toSearch = toSearch;
    }

    public LinkedList<String> crawl(int count) {
        LinkedList<String> toRead = new LinkedList<>();
        toRead.add(toSearch);
        LinkedList<String> hasRead = new LinkedList<>();

        for (int i=0; toRead.isEmpty() || i<count; i++) {
            String currentURLString=toRead.pop();
            while (!hasRead.contains(currentURLString))
                currentURLString=toRead.pop();

            hasRead.add(currentURLString);
            toRead.addAll(crawlURL(currentURLString).collect(Collectors.toCollection(LinkedList::new)));
        }

        hasRead.add(toRead.pop());
        return hasRead;
    }

    private static Stream<String> crawlURL(String urlString) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(urlString).openStream()))) {
            return br.lines().flatMap(Crawler::urlStringsFromLine);
        } catch(IOException e) {
            return null;
        }
    }

    private static Stream<String> urlStringsFromLine(String line) {
        return Arrays.stream(line.split("href=\""))
                .map(section -> section.substring(0, section.indexOf('"')));
    }
}
