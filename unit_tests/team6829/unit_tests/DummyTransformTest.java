package team6829.unit_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import team6829.common.transforms.DummyTransform;

class DummyTransformTest{
	
	@ParameterizedTest
	@CsvSource({"1, 1", "-1, -1", "10, 10", "-10, -10"})
	void TransformReturnsExpectedValues(double expectedVal, double inputVal) {
		DummyTransform dummyTransform = new DummyTransform();
		assertEquals(expectedVal, dummyTransform.transform(inputVal));
	}
}