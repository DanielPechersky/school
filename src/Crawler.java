import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Crawler {
    private final String toSearch;

    public Crawler(String toSearch) {
        this.toSearch = toSearch;
    }

    public ArrayList<String> crawl(int count) {
        final LinkedList<String> toRead = new LinkedList<>();
        final ArrayList<String> hasRead = new ArrayList<>(count);

        String currentURLString = toSearch;
        try {
            for (int i=0; i<count; ++i) {
                boolean done=false;
                while (!done)
                    try {
                        toRead.addAll(0, crawlURL(currentURLString));
                        hasRead.add(currentURLString);
                        done=true;
                    } catch(IOException e) {
                    } finally {
                        currentURLString = toRead.pop();
                        while (hasRead.contains(currentURLString))
                            currentURLString = toRead.pop();
                    }
            }
            hasRead.add(toRead.pop());
        } catch(NoSuchElementException e) {}

        return hasRead;
    }

    private static LinkedList<String> crawlURL(String urlString) throws IOException {
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(new URL(urlString.concat("/robots.txt")).openStream()))) {
            // TODO: Read bots.txt
        }

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(new URL(urlString).openStream()))) {
            return br.lines()
                    .flatMap(Crawler::urlsFromLine)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
    }

    private static Stream<String> urlsFromLine(String line) {
        return Arrays.stream(line.split("href=\""))
                .skip(1)
                .map(section -> section.substring(0, section.indexOf('"')));
    }
}
