package com.mtnfog.test.phileas.model.profile.filters.strategies.dyamic;

import com.mtnfog.phileas.model.profile.filters.strategies.dynamic.HospitalFilterStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

public class HospitalFilterStrategyTest {

    private static final Logger LOGGER = LogManager.getLogger(HospitalFilterStrategyTest.class);

    @Test
    public void evaluateCondition1() throws IOException {

        HospitalFilterStrategy strategy = new HospitalFilterStrategy();

        final boolean conditionSatisfied = strategy.evaluateCondition("context", "documentId", "90210", "token startswith \"902\"", Collections.emptyMap());

        Assert.assertTrue(conditionSatisfied);

    }

    @Test
    public void evaluateCondition2() throws IOException {

        HospitalFilterStrategy strategy = new HospitalFilterStrategy();

        final boolean conditionSatisfied = strategy.evaluateCondition("context", "documentId", "90210", "token == \"90210\"", Collections.emptyMap());

        Assert.assertTrue(conditionSatisfied);

    }

    @Test
    public void evaluateCondition3() throws IOException {

        HospitalFilterStrategy strategy = new HospitalFilterStrategy();

        final boolean conditionSatisfied = strategy.evaluateCondition("context", "documentId", "12345", "token == \"90210\"", Collections.emptyMap());

        Assert.assertFalse(conditionSatisfied);

    }

}
