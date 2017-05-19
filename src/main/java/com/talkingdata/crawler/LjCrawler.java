package com.talkingdata.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gaohan on 2017/4/18.
 */
public class LjCrawler extends WebCrawler {

	public static AtomicInteger visitCount = new AtomicInteger(0);
	private static final Logger logger = LoggerFactory.getLogger(LjCrawler.class);
	private static final String DETAIL_REGEX = "http://bj.lianjia.com/xiaoqu/\\d+/";
	private static final String SOURCE_REGEX = "http://bj.lianjia.com/xiaoqu/\\w+/pg\\d+/";
	private static final String DETAIL_PREFIX = "http://bj.lianjia.com/xiaoqu/";

	private Map crawlerData;
	private LjCrawlerLauncher launcher;
	private int crawlerNum;

	public LjCrawler(Object data) {
		Map crawlerData = (Map) data;
		this.crawlerData = crawlerData;
		this.launcher = (LjCrawlerLauncher) crawlerData.get("launcher");
		this.crawlerNum = Integer.valueOf(crawlerData.get("crawlerNum").toString());
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		String parentUrl = url.getParentUrl();
		String refUrl = referringPage.getWebURL().getURL();
		logger.debug("ＤＯ　ＶＩＳＩＴ？．．．．．．爬虫编号：{}，访问：{}，来源：{}，refUrl：{}", crawlerNum, href, parentUrl == null ? "SEED" : parentUrl, refUrl);
		if (parentUrl != null && !parentUrl.equals(refUrl)) logger.warn("referringPage和parentURL不一致，可能被重定向");
		if (parentUrl != null && !parentUrl.matches(SOURCE_REGEX)) logger.warn("深度不是1");
		if (href.contains("captcha")) launcher.relaunch(crawlerData);
		return href.matches(DETAIL_REGEX);
	}

	@Override
	public void visit(Page page) {

		String href = page.getWebURL().getURL().toLowerCase();
		logger.info("ＶＩＳＩＴＩＮＧ．．．．．．．爬虫编号：{}，访问：{}，来源：{}，累计访问次数：{}", crawlerNum, href, href.matches(DETAIL_REGEX) ? page.getWebURL().getParentUrl() : "SEED", visitCount.incrementAndGet());
		if (!href.matches(DETAIL_REGEX)) return;
		ParseData parseData = page.getParseData();
		if (!(parseData instanceof HtmlParseData)) throw new RuntimeException("DETAIL_REGEX存在问题");
		HtmlParseData htmlParseData = (HtmlParseData) parseData;
		String html = htmlParseData.getHtml();
		Element body = Jsoup.parseBodyFragment(html).body();

		List<String> args = new ArrayList<>();
		args.add(href.substring(DETAIL_PREFIX.length(), href.length() - 1));
		args.add(body.select(".detailTitle").first().text());
		args.add(body.select(".detailDesc").first().text());
		String xiaoquPrice = body.select("div.xiaoquPrice").select("div.fl").first().text();
		if (xiaoquPrice.contains("暂无参考均价")) {
			args.add("暂无参考均价");
			args.add("暂无参考均价");
		} else {
			args.add(body.select("span.xiaoquUnitPrice").first().text());
			args.add(body.select("span.xiaoquUnitPriceDesc").first().text());
		}
		String[] infoLabels = {"建筑年代", "建筑类型", "物业费用", "物业公司", "开发商", "楼栋总数", "房屋总数", "附近门店"};
		for (String infoLabel : infoLabels) {
			Optional<String> optional = body.select("div.xiaoquInfoItem").stream()
					.filter(e -> e.select("span.xiaoquInfoLabel").first().text().contains(infoLabel))
					.map(e -> e.select("span.xiaoquInfoContent").first().text())
					.findFirst();
			args.add(optional.isPresent() ? optional.get() : null);
		}
		launcher.insert(args);
	}
}
