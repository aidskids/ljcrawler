package com.talkingdata.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.authentication.AuthInfo;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Created by gaohan on 2017/4/18.
 */
@Component
public class LjCrawlerController implements CommandLineRunner {

	public static final String crawlStorageFolder = "C:/Users/tend/Desktop/c4j";
	public static final int politenessDelay = 5000;
	public static final int maxDepthOfCrawling = 1;
	public static final int numberOfCrawlers = 1;

	public static final String[] seeds;
	public static final String district = "dongcheng";
	public static final int pageNum = 2;

	static {
		seeds = new String[pageNum];
		for (int i = 0; i < pageNum; i++) {
			seeds[i] = "http://bj.lianjia.com/xiaoqu/" + district + "/pg" + (i + 1) + "/";
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(LjCrawlerController.class);
	public static JdbcTemplate jdbcTemplate;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		LjCrawlerController.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void run(String... strings) throws Exception {

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);

		// 反爬处理
		BasicHeader cookie = new BasicHeader("Cookie",
				"select_city=110000; all-lj=fa25c352c963a53c37faa70f46a58187; _jzqx=1.1492501909.1492501909.1.jzqsr=captcha%2Elianjia%2Ecom|jzqct=/.-; _jzqckmp=1; UM_distinctid=15b800c330b58a-0d5de2c0111fe-396b4c0b-15f900-15b800c330c65f; lianjia_uuid=cdd012c5-4a56-e09c-e10a-870e1c31c003; _smt_uid=58f5c5ba.24afbbdb; CNZZDATA1253477573=108188624-1492497302-http%253A%252F%252Fcaptcha.lianjia.com%252F%7C1492497302; CNZZDATA1254525948=1704994468-1492498084-http%253A%252F%252Fcaptcha.lianjia.com%252F%7C1492498084; CNZZDATA1255633284=228674349-1492500734-http%253A%252F%252Fcaptcha.lianjia.com%252F%7C1492500734; CNZZDATA1255604082=1433859292-1492496787-http%253A%252F%252Fcaptcha.lianjia.com%252F%7C1492496787; _qzja=1.136279846.1492501908849.1492501908849.1492501908850.1492501954712.1492501970447.0.0.0.5.1; _qzjb=1.1492501908850.5.0.0.0; _qzjc=1; _qzjto=5.1.0; _jzqa=1.930648902131734400.1492501909.1492501909.1492501909.1; _jzqc=1; _jzqb=1.5.10.1492501909.1; _ga=GA1.2.1048924575.1492501804; lianjia_ssid=068d9ba2-dd2b-4a4d-9932-898e78d5a1d4");
		Collection<BasicHeader> defaultHeaders = config.getDefaultHeaders();
		defaultHeaders.add(cookie);
		config.setDefaultHeaders(defaultHeaders);
		config.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");

		config.setPolitenessDelay(politenessDelay);
		config.setMaxDepthOfCrawling(maxDepthOfCrawling);
		config.setMaxPagesToFetch(1000);
		config.setIncludeBinaryContentInCrawling(false);
		config.setResumableCrawling(false);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		robotstxtConfig.setEnabled(false);
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		for (String seed : seeds) {
			controller.addSeed(seed);
		}

		controller.start(LjCrawler.class, numberOfCrawlers);// 阻塞当前线程

//		controller.startNonBlocking(LjCrawler.class, numberOfCrawlers);//开启新线程
//		controller.waitUntilFinish();//start=startNonBlocking+waitUntilFinish
//		logger.info("finish");
	}

}
