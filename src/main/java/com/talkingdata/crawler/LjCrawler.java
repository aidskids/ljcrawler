package com.talkingdata.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by gaohan on 2017/4/18.
 */
public class LjCrawler extends WebCrawler {

	private JdbcTemplate jdbcTemplate = LjCrawlerController.jdbcTemplate;
	public static final String DETAIL_REGEX = "http://bj.lianjia.com/xiaoqu/\\d+/";
	public static final String SOURCE_REGEX = "http://bj.lianjia.com/xiaoqu/\\w+/pg\\d+/";
	public static final String DETAIL_PREFIX = "http://bj.lianjia.com/xiaoqu/";

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String url1 = referringPage.getWebURL().getURL();
		if (!url1.matches(SOURCE_REGEX)) throw new RuntimeException("深度必须是1");
		String href = url.getURL().toLowerCase();
		return href.matches(DETAIL_REGEX);
	}

	@Override
	@Transactional
	public void visit(Page page) {

		String href = page.getWebURL().getURL().toLowerCase();
		if (!href.matches(DETAIL_REGEX)) return;// 排除seedURL

		ParseData parseData = page.getParseData();
		if (!(parseData instanceof HtmlParseData)) throw new RuntimeException("DETAIL_REGEX存在问题");
		HtmlParseData htmlParseData = (HtmlParseData) parseData;
		String html = htmlParseData.getHtml();
		Element body = Jsoup.parseBodyFragment(html).body();

		Object[] args = new Object[13];
		String[] infoLabels = {"建筑年代", "建筑类型", "物业费用", "物业公司", "开发商", "楼栋总数", "房屋总数", "附近门店"};

		int j = 0;
		args[j++] = href.substring(DETAIL_PREFIX.length(), href.length() - 1);
		args[j++] = body.select(".detailTitle").first().text();
		args[j++] = body.select(".detailDesc").first().text();
		args[j++] = Integer.valueOf(body.select("span.xiaoquUnitPrice").first().text());
		args[j++] = body.select("span.xiaoquUnitPriceDesc").first().text();

		for (int i = 0; i < infoLabels.length; i++) {
			String infoLabel = infoLabels[i];
			Optional<String> optional = body.select("div.xiaoquInfoItem").stream()
					.filter(e -> e.select("span.xiaoquInfoLabel").first().text().contains(infoLabel))
					.map(e -> e.select("span.xiaoquInfoContent").first().text())
					.findFirst();
			String infoContent = optional.isPresent() ? optional.get() : null;
			args[i + args.length - infoLabels.length] = infoContent;
		}

		String sql = "INSERT INTO lj_crawler(id,name,addr,unit_price,unit_price_desc,year,type,pro_cost,pro_company,developer,building_num,house_num,store_around) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, args);
	}
}
