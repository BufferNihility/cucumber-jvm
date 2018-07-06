package cucumber.runtime.junit;

import cucumber.runtime.ThreadLocalRunnerSupplier;
import cucumber.runtime.junit.PickleRunners.PickleRunner;
import cucumber.runtime.model.CucumberFeature;
import io.cucumber.messages.Messages.Pickle;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class PickleRunnerWithNoStepDescriptionsTest {

    @Test
    public void shouldUseScenarioNameWithFeatureNameAsClassNameForDisplayName() throws Exception {
        List<Pickle> pickles = CucumberFeature.fromSourceForTest("featurePath", "" +
            "Feature: fromSourceForTest name\n" +
            "  Scenario: scenario name\n" +
            "    Then it works\n").getPickles();

        PickleRunner runner = PickleRunners.withNoStepDescriptions(
            "fromSourceForTest name",
            mock(ThreadLocalRunnerSupplier.class),
            pickles.get(0),
            createJUnitOptions()
        );

        assertEquals("scenario name(fromSourceForTest name)", runner.getDescription().getDisplayName());
    }

    @Test
    public void shouldConvertTextFromFeatureFileForNamesWithFilenameCompatibleNameOption() throws Exception {
        List<Pickle> pickles = CucumberFeature.fromSourceForTest("featurePath", "" +
            "Feature: feature name\n" +
            "  Scenario: scenario name\n" +
            "    Then it works\n").getPickles();

        PickleRunner runner = PickleRunners.withNoStepDescriptions(
            "feature name",
            mock(ThreadLocalRunnerSupplier.class),
            pickles.get(0),
            createJUnitOptions("--filename-compatible-names")
        );

        assertEquals("scenario_name(feature_name)", runner.getDescription().getDisplayName());
    }

    private JUnitOptions createJUnitOptions() {
        return new JUnitOptions(true, Collections.<String>emptyList());
    }

    private JUnitOptions createJUnitOptions(String option) {
        return new JUnitOptions(true, Arrays.asList(option));
    }
}
