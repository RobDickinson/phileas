package com.mtnfog.test.phileas.model.profile.filters.strategies.rules;

import com.mtnfog.phileas.model.profile.filters.strategies.AbstractFilterStrategy;
import com.mtnfog.phileas.model.profile.filters.strategies.rules.PhoneNumberExtensionFilterStrategy;
import com.mtnfog.test.phileas.model.profile.filters.strategies.AbstractFilterStrategyTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PhoneNumberExtensionFilterStrategyTest extends AbstractFilterStrategyTest {

    public AbstractFilterStrategy getFilterStrategy() {
        return new PhoneNumberExtensionFilterStrategy();
    }

    @Test
    public void evaluateCondition1() throws IOException {

        PhoneNumberExtensionFilterStrategy strategy = new PhoneNumberExtensionFilterStrategy();

        final boolean conditionSatisfied = strategy.evaluateCondition("context", "documentId", "90210", "token startswith \"902\"", 1.0, "");

        Assertions.assertTrue(conditionSatisfied);

    }

    @Test
    public void evaluateCondition2() throws IOException {

        PhoneNumberExtensionFilterStrategy strategy = new PhoneNumberExtensionFilterStrategy();

        final boolean conditionSatisfied = strategy.evaluateCondition("context", "documentId", "90210", "token == \"90210\"", 1.0, "");

        Assertions.assertTrue(conditionSatisfied);

    }

    @Test
    public void evaluateCondition3() throws IOException {

        PhoneNumberExtensionFilterStrategy strategy = new PhoneNumberExtensionFilterStrategy();

        final boolean conditionSatisfied = strategy.evaluateCondition("context", "documentId", "12345", "token == \"90210\"", 1.0, "");

        Assertions.assertFalse(conditionSatisfied);

    }

    @Test
    public void evaluateCondition4() {

        final AbstractFilterStrategy strategy = getFilterStrategy();

        final boolean conditionSatisfied = strategy.evaluateCondition("context", "documentId", "John Smith", "context == \"c1\"",  1.0, "");

        Assertions.assertFalse(conditionSatisfied);

    }

    @Test
    public void evaluateCondition5() {

        final AbstractFilterStrategy strategy = getFilterStrategy();

        final boolean conditionSatisfied = strategy.evaluateCondition("ctx", "documentId", "John Smith", "context == \"ctx\"",  1.0, "");

        Assertions.assertTrue(conditionSatisfied);

    }

    @Test
    public void evaluateCondition6() {

        final AbstractFilterStrategy strategy = getFilterStrategy();

        final boolean conditionSatisfied = strategy.evaluateCondition("ctx", "documentId", "John Smith", "confidence > 0.5",  1.0, "");

        Assertions.assertTrue(conditionSatisfied);

    }

    @Test
    public void evaluateCondition7() {

        final AbstractFilterStrategy strategy = getFilterStrategy();

        final boolean conditionSatisfied = strategy.evaluateCondition("ctx", "documentId", "John Smith", "confidence < 0.5",  1.0, "");

        Assertions.assertFalse(conditionSatisfied);

    }


}
