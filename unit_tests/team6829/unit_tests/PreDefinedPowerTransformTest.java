package team6829.unit_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import team6829.common.transforms.PreDefinedPowerTransform;

class PreDefinedPowerTransformTest {

	@ParameterizedTest
	@CsvSource({"0.1, 0", "0.1, 1", "0.1, -1", "0.1, .5", "0.1, -.5", "0.1, 10","0.1, -10"})
	void TransformReturnsExpectedValues(double expectedVal, double inputVal) {
		PreDefinedPowerTransform predefinedPowerTransform = new PreDefinedPowerTransform();
		assertEquals(expectedVal, predefinedPowerTransform.transform(inputVal));
	}

}