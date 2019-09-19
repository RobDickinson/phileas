package com.mtnfog.phileas.services.filters.regex;

import com.mtnfog.phileas.model.enums.FilterType;
import com.mtnfog.phileas.model.filter.rules.regex.RegexFilter;
import com.mtnfog.phileas.model.objects.Span;
import com.mtnfog.phileas.model.profile.FilterProfile;
import com.mtnfog.phileas.model.services.AnonymizationService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

public class ZipCodeFilter extends RegexFilter implements Serializable {

    private static final Pattern ZIP_CODE_REGEX = Pattern.compile("\\b[0-9]{5}(?:-[0-9]{4})?\\b");

    public ZipCodeFilter(AnonymizationService anonymizationService) {
        super(FilterType.ZIP_CODE, anonymizationService);
    }

    @Override
    public List<Span> filter(FilterProfile filterProfile, String context, String documentId, String input) throws IOException {

        return findSpans(filterProfile, ZIP_CODE_REGEX, input, context, documentId);

    }

}
