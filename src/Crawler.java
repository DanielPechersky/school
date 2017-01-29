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
    private final LinkedList<String> results;

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
        results = new LinkedList<>();
        cachedRobotsTxt = new HashMap<>();
    }

    public ArrayList<String> crawl(int count) {
        ArrayList<String> results = new ArrayList<>(count);
        try {
            while (hasRead.size() < count)
                crawlURL(toRead.pop(), count);
        } catch(NoSuchElementException e) {} // toRead is empty
        return results;
    }

    private void crawlURL(URL url, int count) {
        System.out.print(url);
        hasRead.add(url);
        if (url.getPath().contains(".") &&
                fileTypesSeeking.contains(url.getPath().substring(url.getPath().indexOf('.')+1))) {
            System.out.println(" +added");
            results.add(url.toString());
        }

        System.out.println();

        if (hasRead.size() < count)
            try {
                if (isCrawlAllowed(url)) {
                    try (final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                        String[] links = br.lines()
                                .collect(Collectors.joining())
                                .split("href=\"");

                        Arrays.stream(Arrays.copyOfRange(links, 1, Math.min(count-hasRead.size()+1, links.length)))
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
                                .forEach(url_ -> crawlURL(url_, count));
                    }
                }

            } catch (IOException e) {}
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
