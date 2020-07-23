package com.lighthouse.kalah;

import com.lighthouse.kalah.game.KalahImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KalahApplicationTests {

	@Autowired
	KalahImpl kalah;

	@Test
	void contextLoads() {
	}

	@Test
	public void initGame() {
		//long id = 10;
		//kalah.getGameById(10).getStatus();
	}

	@Test
	public void movePlayer() {

	}
}
