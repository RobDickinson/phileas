package com.mtnfog.test.phileas.services.filters;

import com.mtnfog.phileas.model.enums.FilterType;
import com.mtnfog.phileas.model.objects.FilterResult;
import com.mtnfog.phileas.model.objects.Span;
import com.mtnfog.phileas.model.profile.Crypto;
import com.mtnfog.phileas.model.profile.filters.strategies.rules.StateAbbreviationFilterStrategy;
import com.mtnfog.phileas.model.services.AlertService;
import com.mtnfog.phileas.services.anonymization.StateAbbreviationAnonymizationService;
import com.mtnfog.phileas.services.anonymization.cache.LocalAnonymizationCacheService;
import com.mtnfog.phileas.services.filters.regex.StateAbbreviationFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StateAbbreviationFilterTest extends AbstractFilterTest {

    private static final Logger LOGGER = LogManager.getLogger(StateAbbreviationFilterTest.class);

    private AlertService alertService = Mockito.mock(AlertService.class);

    @Test
    public void filter1() throws Exception {

        final List<StateAbbreviationFilterStrategy> strategies = Arrays.asList(new StateAbbreviationFilterStrategy());
        final StateAbbreviationFilter filter = new StateAbbreviationFilter(strategies, new StateAbbreviationAnonymizationService(new LocalAnonymizationCacheService()), alertService, Collections.emptySet(), new Crypto(), windowSize);

        final String input = "The patient is from WV.";
        final FilterResult filterResult = filter.filter(getFilterProfile(), "context", "docid", 0, input);

        Assertions.assertEquals(1, filterResult.getSpans().size());
        Assertions.assertEquals(20, filterResult.getSpans().get(0).getCharacterStart());
        Assertions.assertEquals(22, filterResult.getSpans().get(0).getCharacterEnd());
        Assertions.assertEquals(FilterType.STATE_ABBREVIATION, filterResult.getSpans().get(0).getFilterType());
        Assertions.assertEquals("WV", filterResult.getSpans().get(0).getText());

    }

    @Test
    public void filter2() throws Exception {

        final List<StateAbbreviationFilterStrategy> strategies = Arrays.asList(new StateAbbreviationFilterStrategy());
        final StateAbbreviationFilter filter = new StateAbbreviationFilter(strategies, new StateAbbreviationAnonymizationService(new LocalAnonymizationCacheService()), alertService, Collections.emptySet(), new Crypto(), windowSize);

        final String input = "The patient is from wv.";
        final FilterResult filterResult = filter.filter(getFilterProfile(), "context", "docid", 0, input);

        Assertions.assertEquals(1, filterResult.getSpans().size());
        Assertions.assertEquals(20, filterResult.getSpans().get(0).getCharacterStart());
        Assertions.assertEquals(22, filterResult.getSpans().get(0).getCharacterEnd());
        Assertions.assertEquals(FilterType.STATE_ABBREVIATION, filterResult.getSpans().get(0).getFilterType());

    }

    @Test
    public void filter3() throws Exception {

        final List<StateAbbreviationFilterStrategy> strategies = Arrays.asList(new StateAbbreviationFilterStrategy());
        final StateAbbreviationFilter filter = new StateAbbreviationFilter(strategies, new StateAbbreviationAnonymizationService(new LocalAnonymizationCacheService()), alertService, Collections.emptySet(), new Crypto(), windowSize);

        final String input = "Patients from WV and MD.";
        final FilterResult filterResult = filter.filter(getFilterProfile(), "context", "docid", 0, input);

        showSpans(filterResult.getSpans());

        Assertions.assertEquals(2, filterResult.getSpans().size());

        Assertions.assertEquals(21, filterResult.getSpans().get(0).getCharacterStart());
        Assertions.assertEquals(23, filterResult.getSpans().get(0).getCharacterEnd());
        Assertions.assertEquals(FilterType.STATE_ABBREVIATION, filterResult.getSpans().get(0).getFilterType());
        Assertions.assertEquals("MD", filterResult.getSpans().get(0).getText());

        Assertions.assertEquals(14, filterResult.getSpans().get(1).getCharacterStart());
        Assertions.assertEquals(16, filterResult.getSpans().get(1).getCharacterEnd());
        Assertions.assertEquals(FilterType.STATE_ABBREVIATION, filterResult.getSpans().get(1).getFilterType());
        Assertions.assertEquals("WV", filterResult.getSpans().get(1).getText());

    }

}
