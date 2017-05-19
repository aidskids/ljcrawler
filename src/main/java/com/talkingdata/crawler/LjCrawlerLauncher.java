package com.talkingdata.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gaohan on 2017/4/18.
 */
@Component
public class LjCrawlerLauncher implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(LjCrawlerLauncher.class);
	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Value("${crawler.folder}")
	private String folder;
	@Value("${crawler.district}")
	private String district;
	@Value("${crawler.init-page}")
	private int initPage;//代表已经爬过的页数，0开始。如想从第4页开始爬，则值应为3
	@Value("${crawler.total-page}")
	private int totalPage;
	@Value("${crawler.page-per-robot}")
	private int pagePerRobot;
	@Value("${crawler.delay}")
	private int delay;
	private String[] agents;
	private List<String> cookies;
	private String[] seeds;
	private AtomicInteger crawlerNum = new AtomicInteger(0);
	private Set<Integer> failedStartPages = new TreeSet<>();

	@Value("${crawler.agents}")
	public void setAgents(String agents) {
		this.agents = agents.split("|||");
	}

	@Value("${crawler.cookies}")
	public void setCookies(String cookies) {
		this.cookies = new ArrayList<>(Arrays.asList(cookies.split(",")));
	}

	@Override
	public void run(String... strings) throws Exception {
		logger.warn("ＢＯＯＴＳＴＲＡＰ．．．．．．城区：{}，总页数：{}", district, totalPage);
		seeds = new String[totalPage - initPage];
		for (int i = 0; i < totalPage - initPage; i++)
			seeds[i] = "http://bj.lianjia.com/xiaoqu/" + district + "/pg" + (i + 1 + initPage) + "/";
		int currentPage = initPage;
		while (cookies.size() > 0 && currentPage < totalPage) {
			int pageNum = Math.min(pagePerRobot, totalPage - currentPage);
			launch(crawlerNum.getAndIncrement(), currentPage, pageNum);
			currentPage += pageNum;
		}
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			if (cookies.size() != 0) {
				logger.warn("★★★抓取完毕★★★");
				logger.warn("剩余Cookie数量：{}，具体Cookie：{}", cookies.size(), cookies.toString());
			} else {
				logger.warn("★★★Cookie耗尽★★★");
				logger.warn("失败的起始页：{}", failedStartPages.toString());
			}
		}));
	}

	private synchronized void launch(int crawlerNum, int startPage, int pageNum) throws Exception {
		launch(crawlerNum, startPage, pageNum, false);
	}

	private synchronized void launch(int crawlerNum, int startPage, int pageNum, boolean relaunch) throws Exception {
		if (!relaunch) logger.warn("ＬＡＵＮＣＨＩＮＧ．．．．．．爬虫编号：{}，起始页：{}，页数：{}", crawlerNum, startPage, pageNum);
		if (relaunch) logger.warn("ＲＥＬＡＵＮＣＨＩＮＧ．．．．爬虫编号：{}，起始页：{}，页数：{}", crawlerNum, startPage, pageNum);

		String cookie = cookies.get(crawlerNum % cookies.size());
		String agent = agents[crawlerNum % agents.length];

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(folder + File.separator + crawlerNum);
		config.setPolitenessDelay(delay);
		config.setUserAgentString(agent);
		Collection<BasicHeader> defaultHeaders = config.getDefaultHeaders();
		defaultHeaders.add(new BasicHeader("Cookie", cookie));
		defaultHeaders.add(new BasicHeader("Host", "bj.lianjia.com"));
		defaultHeaders.add(new BasicHeader("Connection", "keep-alive"));
		defaultHeaders.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"));
		defaultHeaders.add(new BasicHeader("Accept-Encoding", "gzip,deflate,sdch"));
		defaultHeaders.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8"));
		defaultHeaders.add(new BasicHeader("Referer", "bj.lianjia.com"));
		config.setDefaultHeaders(defaultHeaders);
		// TODO Proxy
		config.setMaxDepthOfCrawling(1);
		config.setMaxPagesToFetch(1000);
		config.setIncludeBinaryContentInCrawling(false);
		config.setResumableCrawling(false);
		config.setThreadMonitoringDelaySeconds(1);//开始后
		config.setThreadShutdownDelaySeconds(1);//结束前
		config.setCleanupDelaySeconds(1);//结束后
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		robotstxtConfig.setEnabled(false);
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		Arrays.asList(seeds).subList(startPage - initPage, startPage - initPage + pageNum).forEach(controller::addSeed);
		Map<String, Object> data = new HashMap<>();
		data.put("launcher", this);
		data.put("crawlerNum", crawlerNum);
		data.put("startPage", startPage);
		data.put("pageNum", pageNum);
		data.put("cookie", cookie);
		controller.startNonBlocking(new LjCrawlerFactory<>(data), 1);
//		controller.waitUntilFinish();//同步化
	}

	@Transactional
	public void insert(List<String> args) {
		String sql = "INSERT INTO lj_crawler(xiaoqu_id,name,addr,unit_price,unit_price_desc,year,type,pro_cost,pro_company,developer,building_num,house_num,store_around) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			jdbcTemplate.update(sql, args.toArray());
		} catch (DataAccessException e) {
			if (e instanceof DuplicateKeyException) {
				logger.warn("数据冲突：" + sql.replaceAll("\\?", "{}"), args.toArray());
			} else {
				throw new RuntimeException(e);
			}
		}
	}

	public synchronized void relaunch(Map crawlerData) {
		int crawlerNum = Integer.valueOf(crawlerData.get("crawlerNum").toString());
		int startPage = Integer.valueOf(crawlerData.get("startPage").toString());
		int pageNum = Integer.valueOf(crawlerData.get("pageNum").toString());
		String cookie = crawlerData.get("cookie").toString();
		logger.error("ＲＯＢＯＴ　ＤＥＡＤ．．．．．爬虫编号：{}，起始页：{}，页数：{}", crawlerNum, startPage, pageNum);
		if (cookies.remove(cookie))
			logger.error("ＣＯＯＫＩＥ　ＤＥＡＤ．．．．剩余Cookie：{}，死亡Cookie：{}", cookies.size(), cookie);
		if (cookies.size() > 0) {
			try {
				launch(this.crawlerNum.getAndIncrement(), startPage, pageNum, true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			logger.error("ＲＥＬＡＵＮＣＨ　ＦＡＩＬ．．Cookie全部死亡，起始页：{}，页数：{}", startPage, pageNum);
			failedStartPages.add(startPage);
		}
	}
}
