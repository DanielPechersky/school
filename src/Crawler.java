import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Crawler {
    private final List<String> filetypesSeeking;
    private final LinkedList<URL> toRead;
    private final List<URL> hasRead;

    private final HashMap<String, Boolean> cachedRobotsTxt;

    public Crawler(String[] filetypesSeeking, String... toRead) {
        this(Arrays.asList(filetypesSeeking), Arrays.asList(toRead));

    }

    public Crawler(Collection<String> filetypesSeeking, List<String> toRead) {
        this.filetypesSeeking = new LinkedList<>(filetypesSeeking);
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
            while (results.size() < count) {
                String result = crawlURL(toRead.pop());
                if (result != null)
                    results.add(result);
            }
        } catch(NoSuchElementException e) {} // toRead is empty
        return results;
    }

    private String crawlURL(URL url) {
        System.out.println(url);
        hasRead.add(url);
        if (url.getPath().contains(".") &&
                filetypesSeeking.contains(url.getPath().substring(url.getPath().indexOf('.')+1)))
            return url.toString();

        try {
            if (isCrawlAllowed(url)) {
                try (final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                    toRead.addAll(
                            Arrays.stream(
                            br.lines()
                            .collect(Collectors.joining())
                            .split("href=\"")
                            )
                            .skip(1)
                            .map(section -> section.substring(0, section.indexOf('"')))
                            .map(urlString -> {
                                try {
                                    return new URL(url, urlString);
                                } catch(MalformedURLException e) {
                                    return null;
                                }
                            })
                            .filter(Objects::nonNull)
                            .filter(url_ -> !hasRead.contains(url_) && !toRead.contains(url_))
                            .collect(Collectors.toList()));
                }
            }

        } catch(IOException e) {}

        return null;
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
}
