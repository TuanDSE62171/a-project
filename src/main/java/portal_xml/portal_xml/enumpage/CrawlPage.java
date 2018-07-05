package portal_xml.portal_xml.enumpage;

public enum CrawlPage {

    NEWS_PAGE("https://tintuc.vn/tin-quoc-te"),
    CAPITAL_PAGE("http://www.sport-histoire.fr/en/Geography/ISO_codes_countries.php"),
    WEATHER_PAGE("https://www.timeanddate.com/weather/%s/%s/ext"),
    IMAGE_PAGE("https://unsplash.com/search/photos/%s-%s");

    String url;

    CrawlPage(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
