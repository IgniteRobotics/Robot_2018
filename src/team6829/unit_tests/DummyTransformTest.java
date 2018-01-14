package team6829.unit_tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import team6829.common.transforms.DummyTransform;

class DummyTransformTest {

	@Test
	void test() {
		DummyTransform transform = new DummyTransform();
		
		double max_in = 1.0;
		double ex_max_out = 0;
		double max_out = transform.transform(max_in);
		assertEquals(ex_max_out, max_out);
		
		double min_in = -1.0;
		double ex_min_out = 0;
		double min_out = transform.transform(min_in);
		assertEquals(ex_min_out, min_out);
		
		double great_in = 10.0;
		double ex_great_out = 0;
		double great_out = transform.transform(great_in);
		assertEquals(ex_great_out, great_out);
		
		double less_in = -10.0;
		double ex_less_out= 0;
		double less_out = transform.transform(less_in);
		assertEquals(ex_less_out, less_out);
	}

}
