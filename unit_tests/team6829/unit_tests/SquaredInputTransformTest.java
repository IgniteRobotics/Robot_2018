package team6829.unit_tests;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import team6829.common.transforms.SquaredInputTransform;

class SquaredInputTransformTest {
	
	@ParameterizedTest
	@CsvSource({"0, 0", "1, 1", "-1, -1", ".25, .5", "-.25, -.5", "100, 10","-100, -10"})
	void TransformReturnsExpectedValues(double expectedVal, double inputVal) {
		SquaredInputTransform squaredInputTransform = new SquaredInputTransform();
		assertEquals(expectedVal, squaredInputTransform.transform(inputVal));
	}

}