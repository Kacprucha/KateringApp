package com.kateringapp.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DummyIT {

	@Test
	void dummyIT() {

		//given
		double number = 1.5;

		//when
		double ceil = Math.ceil(number);

		//then
		Assertions.assertEquals(2.0, ceil);
	}

}
