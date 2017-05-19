package com.talkingdata.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;

/**
 * Created by gaohan on 2017/4/20.
 */
public class LjCrawlerFactory<T> implements CrawlController.WebCrawlerFactory {

	private T data;

	public LjCrawlerFactory(T data) {
		this.data = data;
	}

	@Override
	public WebCrawler newInstance() throws Exception {
		return new LjCrawler(data);
	}
}
