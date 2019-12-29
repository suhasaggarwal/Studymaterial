package com.rapidcassandra.Chapter04;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.rapidcassandra.Chapter04.model.Quote;
import com.rapidcassandra.Chapter04.repository.CassandraTraderDAO;
import com.rapidcassandra.Chapter04.repository.TradingSignal;
import com.rapidcassandra.Chapter04.util.CassandraTraderUtil;
import com.rapidcassandra.Chapter04.util.YahooFetcher;

public class CassandraTrader {

	static Logger log = Logger.getLogger(CassandraTrader.class.getName());

	// handle the collection of stock quote data over a date period
	// and process the newly collected stock quote data
	public void getData(final String symbol, final CassandraTraderDAO qp) {
		final Calendar cal = Calendar.getInstance();

		// today is the default end date for fetching stock quote data
		final Date today = new Date();
		cal.setTime(today);
		final int toDay = cal.get(Calendar.DAY_OF_MONTH);
		final int toMonth = cal.get(Calendar.MONTH);
		final int toYear = cal.get(Calendar.YEAR);

		// get the last date of stock quote data of a stock
		// if none is found, use the default 1-JAN-2000 as the start date
		// otherwise, use the next day of the last date in Cassandra
		Date lastQuoteDate = qp.lastQuoteDateBySymbol(symbol);
		int fromDay = 1;
		int fromMonth = 0;
		int fromYear = 2000;
		if (lastQuoteDate != null) {
			cal.setTime(lastQuoteDate);
			cal.add(Calendar.DATE, 1);
			fromDay = cal.get(Calendar.DAY_OF_MONTH);
			fromMonth = cal.get(Calendar.MONTH);
			fromYear = cal.get(Calendar.YEAR);
		}

		//retrieve stock name
		String stockName = YahooFetcher.getStockName(symbol);
		
		// retrieve stock quote data from Yahoo! Finance
		final BufferedReader br = YahooFetcher.getStock(symbol, fromMonth,
				fromDay, fromYear, toMonth, toDay, toYear);

		try {
			// process each line of stock quote data
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				String[] feed = line.split(",");
				// skip the header line
				if (!Pattern.matches("Date", feed[0])) {
					// extract each line into the Quote POJO
					Quote q = YahooFetcher.parseQuote(symbol, feed);
					// add the stock name
					q.setSymbol_name(stockName);
					// persist the Quote POJO into Cassandra
					qp.saveQuote(q);
				}
			}
		} catch (IOException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
	}

	// scan trading signal of a stock over a date period
	public void scanTradingSignal(final String symbol, final Date fromDate,
			final Date toDate, final CassandraTraderDAO qp) {

		// select the stock quote data of a symbol over a specified date
		// range from Cassandra
		List<Quote> quotes = qp.selectQuoteBySymbolAndDateRange(symbol,
				fromDate, toDate);

		// instantiate a TradingSingal
		final TradingSignal ts = new TradingSignal();

		// scan trading signal of close price crosses over 10-Day SMA
		ts.sma10BreakUp(quotes);
	}

	public static void main(String[] args) {
		// stock symbol to be processed
		final String symbol = "GOOG";
		
		// instantiate CassandraTraderDAO to manage persistence
		// final CassandraTraderDAO qp = new CassandraTraderDAO("localhost",
		// 9042);
		final CassandraTraderDAO qp = new CassandraTraderDAO("NY1", 9042,
				"ubtc01", "ubtc02");
		
		// instantiate CassandraTrader
		final CassandraTrader ct = new CassandraTrader();

		// retrieve stock quote data
		ct.getData(symbol, qp);

		// set the scan period from and to dates
		Date fromDate = CassandraTraderUtil.convertDate("2014-07-01");
		Date toDate = CassandraTraderUtil.convertDate("2014-09-30");

		// scan for trading signals
		ct.scanTradingSignal(symbol, fromDate, toDate, qp);

		// close CassandraTraderDAO
		qp.close();
	}

}
