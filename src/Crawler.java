import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Crawler {
    private final List<String> fileTypesSeeking;
    private final LinkedList<URL> toRead;
    private final List<URL> hasRead;

    private final HashMap<String, Boolean> cachedRobotsTxt;

    public Crawler(String[] fileTypesSeeking, String... toRead) {
        this(Arrays.asList(fileTypesSeeking), Arrays.asList(toRead));
    }

    public Crawler(Collection<String> fileTypesSeeking, List<String> toRead) {
        this.fileTypesSeeking = new LinkedList<>(fileTypesSeeking);
        this.toRead = toRead.stream()
                .map(urlString -> {
                    try {
                        return new URL(urlString);
                    } catch(MalformedURLException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedList::new));
        hasRead = new LinkedList<>();
        cachedRobotsTxt = new HashMap<>();
    }

    public ArrayList<String> crawl(int count) {
        ArrayList<String> results = new ArrayList<>(count);
        try {
            while (hasRead.size() < count) {
                String result = crawlURL(toRead.pop());
                if (result != null)
                    results.add(result);
            }
        } catch(NoSuchElementException e) {} // toRead is empty
        return results;
    }

    private String crawlURL(URL url) {
        System.out.print(url);
        hasRead.add(url);
        if (url.getPath().contains(".") &&
                fileTypesSeeking.contains(url.getPath().substring(url.getPath().indexOf('.')+1))) {
            System.out.println(" +added");
            return url.toString();
        }

        System.out.println();

        try {
            if (isCrawlAllowed(url)) {
                try (final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                    toRead.addAll(Arrays.stream(
                            br.lines()
                                    .collect(Collectors.joining())
                                    .split("href=\"")
                    )
                            .skip(1)
                            .map(section -> section.substring(0, section.indexOf('"')))
                            .map(urlString -> {
                                try {
                                    return new URL(url, urlString);
                                } catch (MalformedURLException e) {
                                    return null;
                                }
                            })
                            .filter(Objects::nonNull)
                            .filter(this::shouldReadIfAllowed)
                            .collect(Collectors.toList()));
                }
            }

        } catch (IOException e) {}

        return null;
    }

    private boolean shouldReadIfAllowed(URL url) {
        return !hasRead.contains(url) && !toRead.contains(url);
    }

    private boolean isCrawlAllowed(URL url) {
        return isCrawlAllowed(url.getProtocol(), url.getHost());
    }

    private boolean isCrawlAllowed(String protocol, String host) {
        final Boolean isAllowed = cachedRobotsTxt.get(host);
        if (isAllowed == null) {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(new URL(protocol, host, "/robots.txt").openStream()))) {
                String line;
                while (!br.readLine().equalsIgnoreCase("user-agent: *"));

                while (!(line = br.readLine()).equals(""))
                    if (line.matches("(?i)disallow: \\S*")) {
                        cachedRobotsTxt.put(host, false);
                        return false;
                    }
            } catch (NullPointerException | IOException e) {}

            cachedRobotsTxt.put(host, true);
            return true;
        } else
            return isAllowed;
    }
}
