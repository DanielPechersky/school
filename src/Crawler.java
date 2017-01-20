import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Crawler {
    private final LinkedList<String> toRead;
    private final Deque<String> hasRead;

    private final HashMap<String, Boolean> cachedRobotsTxt;

    public Crawler(List<String> toRead) {
        this.toRead = new LinkedList<>(toRead);
        hasRead = new LinkedList<>();

        cachedRobotsTxt = new HashMap<>();
    }

    public void crawl(int count) {
        try {
            for (int i=0; i<count; ++i) {
                while (true)
                    try {
                        crawlURL(toRead.pop());
                        break;
                    } catch(IOException e) {}
            }
        } catch(NoSuchElementException e) {}
    }

    private void crawlURL(String urlString) throws IOException {
        hasRead.add(urlString);
        final URL url = new URL(urlString);

        if (isCrawlAllowed(url))
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                toRead.addAll(br.lines()
                        .flatMap(this::urlsFromLine)
                        .filter(urlString_ -> !hasRead.contains(urlString_) && !toRead.contains(urlString_))
                        .collect(Collectors.toCollection(LinkedList::new)));
            }
        else
            throw new CrawlNotAllowedException(urlString);
    }

    private Stream<String> urlsFromLine(String line) {
        return Arrays.stream(line.split("href=\""))
                .skip(1)
                .map(section -> section.substring(0, section.indexOf('"')));
    }

    private boolean isCrawlAllowed(URL url) {
        return isCrawlAllowed(url.getProtocol(), url.getHost());
    }

    private boolean isCrawlAllowed(String protocol, String host) {
        final Boolean isAllowed = cachedRobotsTxt.get(host);
        if (isAllowed == null) {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(new URL(protocol, host, "robots.txt").openStream()))) {
                String line;
                while (!br.readLine().equalsIgnoreCase("user-agent: *"));

                while (!(line = br.readLine()).equals(""))
                    if (line.matches("disallow: \\S*")) {
                        cachedRobotsTxt.put(host, false);
                        return false;
                    }
            } catch (NullPointerException | IOException e) {}

            cachedRobotsTxt.put(host, true);
            return true;
        } else
            return isAllowed;
    }

    private class CrawlNotAllowedException extends IOException {
        CrawlNotAllowedException(String host) {
            super("Crawling not allowed for host: "+host);
        }
    }
}
