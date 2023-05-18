package com.zchalmers.stocks.highVolumeStocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class HighVolumeStocksApplication  {



	public static void main(String[] args) {
		SpringApplication.run(HighVolumeStocksApplication.class, args);
	}

}
