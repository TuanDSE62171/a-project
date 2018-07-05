package portal_xml.portal_xml.crawler;

import portal_xml.portal_xml.entity.jaxb.capital.Capital;
import portal_xml.portal_xml.enumpage.CrawlPage;
import portal_xml.portal_xml.service.DBService;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static java.util.logging.Level.*;

public abstract class AbstractCrawler implements Callable {

    protected final Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());

    protected String crawlerName = String.format("%s", this.getClass().getSimpleName());

    protected String templateInit = String.format("Starting %s", crawlerName);

    protected String templateDestroy = String.format("Destroying %s", crawlerName);

    protected BlockingQueue<Capital> capitalBlockingQueue;

    public static final String TERMINATION_MESSAGE = "NO_MORE_CAPITAL";

    public static final Capital CAPITAL_QUEUE_TERMINATION_FLAG = ((Supplier<Capital>) (() -> {
        Capital poisonPill = new Capital();
        poisonPill.setName(TERMINATION_MESSAGE);
        return poisonPill;
    })).get();

    protected String templateStopped = String.format("%s stopped", crawlerName);

    protected String templateResumed = String.format("%s resumed", crawlerName);

    protected CrawlPage pageURL;

    protected DBService service;

    public double progress;

    protected boolean stop = true;

    protected String crawlHTML(Map<String, String> queryParameters) {
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
            LOGGER.log(INFO, "{0}: Crawl HTML done", crawlerName);
        } catch (IOException ioe) {
            LOGGER.log(WARNING, "{0}: Crawl HTML failed", crawlerName);
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

    protected String buildParameterString(Map<String, String> queryParameters) {
        if (queryParameters != null && !queryParameters.isEmpty()) {
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

    protected void waitForSignalToContinue() {
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean isTerminationFlag(Capital capital){
        return (capital.getName().equalsIgnoreCase(TERMINATION_MESSAGE));
    }
}
