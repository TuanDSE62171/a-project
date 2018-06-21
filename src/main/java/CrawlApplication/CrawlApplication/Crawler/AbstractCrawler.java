package CrawlApplication.CrawlApplication.Crawler;

import CrawlApplication.CrawlApplication.EnumPage.CrawlPage;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

public abstract class AbstractCrawler {

    public CrawlPage pageURL;

    public String crawlHTML(Map<String, String> queryParameters) {
        BufferedReader br = null;
        StringBuilder result = new StringBuilder("");
        try {
            String queryString = buildParameterString(queryParameters);
            URL url = new URL(this.pageURL.getUrl() + "?" + queryString);
            URLConnection con = url.openConnection();
            con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            con.addRequestProperty("Accept-Charset", "UTF-8");
            InputStream is = con.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                result = result.append(inputLine + "\n");
            }
            System.out.println("Crawl HTML done");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    protected String buildParameterString(Map<String, String> queryParameters){
        if(queryParameters != null && !queryParameters.isEmpty()) {
            try {
                StringBuilder queryString = new StringBuilder("");
                int numOfKeyVal = 0;
                String keyVal = "";
                for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
                    keyVal = String.format("%s=%s", URLEncoder.encode(entry.getKey(), "UTF-8"), URLEncoder.encode(entry.getValue(), "UTF-8"));
                    if (numOfKeyVal != queryParameters.size()) {
                        queryString = queryString.append(keyVal).append("&");
                    } else {
                        queryString = queryString.append(keyVal);
                    }
                    numOfKeyVal++;
                }
                return queryString.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
