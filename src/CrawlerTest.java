public class CrawlerTest {
    public static void main(String[] args) {
        String[] fileTypesSeeking = {"png","jpg","jpeg","gif","ico"};
        String[] toRead = {"http://conwaylife.com/wiki/Main_Page"};
        int count=500;

        Crawler crawler = new Crawler(fileTypesSeeking, toRead);
        System.out.println(crawler.crawl(count));
    }
}
