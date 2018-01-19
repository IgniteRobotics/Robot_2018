package team6829.unit_tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import team6829.common.transforms.ZeroTransform;

class ZeroTransformTest {

	@ParameterizedTest
	@CsvSource({"0, 1", "0, -1", "0, 10", "0, -10"})
	void TransformReturnsExpectedValues(double expectedVal, double inputVal) {
		ZeroTransform zeroTransform = new ZeroTransform();
		assertEquals(expectedVal, zeroTransform.transform(inputVal));
	}

}
