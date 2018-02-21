package team6829.unit_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import team6829.common.transforms.SlowTransform;

class SlowTransformTest {

	@ParameterizedTest
	@CsvSource({"0, 0", "0.75, 1", "-0.75, -1", "0.375, .5", "-0.375, -.5", "7.5, 10","-7.5, -10"})
	void TransformReturnsExpectedValues(double expectedVal, double inputVal) {
		SlowTransform slowTransform = new SlowTransform();
		assertEquals(expectedVal, slowTransform.transform(inputVal));
	}

}