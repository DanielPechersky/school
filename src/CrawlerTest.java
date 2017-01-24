public class CrawlerTest {
    public static void main(String[] args) {
        Crawler crawler = new Crawler(args[0].split(","), args[1].split(","));
        System.out.println(crawler.crawl(Integer.parseInt(args[2])));
    }
}
