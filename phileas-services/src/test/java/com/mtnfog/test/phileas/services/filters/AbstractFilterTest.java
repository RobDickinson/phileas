package com.mtnfog.test.phileas.services.filters;

import com.mtnfog.phileas.model.enums.FilterType;
import com.mtnfog.phileas.model.enums.SensitivityLevel;
import com.mtnfog.phileas.model.objects.Span;
import com.mtnfog.phileas.model.profile.FilterProfile;
import com.mtnfog.phileas.model.profile.Identifiers;
import com.mtnfog.phileas.model.profile.filters.*;
import com.mtnfog.phileas.model.profile.filters.strategies.dynamic.*;
import com.mtnfog.phileas.model.profile.filters.strategies.rules.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFilterTest {

    protected static final Logger LOGGER = LogManager.getLogger(AbstractFilterTest.class);

    /**
     * Gets a {@link FilterProfile} where all non-deterministic filters use a MEDIUM {@link SensitivityLevel}.
     * @return A {@link FilterProfile} where all non-deterministic filters use a MEDIUM {@link SensitivityLevel}.
     */
    public FilterProfile getFilterProfile() throws IOException {
        return getFilterProfile(SensitivityLevel.MEDIUM);
    }

    /**
     * Gets a {@link FilterProfile} where all non-deterministic filters use the given {@link SensitivityLevel}.
     * @param sensitivityLevel The {@link SensitivityLevel} for all non-deterministic filters.
     * @return A {@link FilterProfile} where all non-deterministic filters use the given {@link SensitivityLevel}.
     */
    public FilterProfile getFilterProfile(SensitivityLevel sensitivityLevel) throws IOException {

        AgeFilterStrategy ageFilterStrategy = new AgeFilterStrategy();

        Age age = new Age();
        age.setAgeFilterStrategies(Arrays.asList(ageFilterStrategy));

        CreditCardFilterStrategy creditCardFilterStrategy = new CreditCardFilterStrategy();

        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardFilterStrategies(Arrays.asList(creditCardFilterStrategy));

        DateFilterStrategy dateFilterStrategy = new DateFilterStrategy();

        Date date = new Date();
        date.setDateFilterStrategies(Arrays.asList(dateFilterStrategy));

        EmailAddressFilterStrategy emailAddressFilterStrategy = new EmailAddressFilterStrategy();

        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setEmailAddressFilterStrategies(Arrays.asList(emailAddressFilterStrategy));

        IdentifierFilterStrategy identifierFilterStrategy = new IdentifierFilterStrategy();

        Identifier identifier = new Identifier();
        identifier.setIdentifierFilterStrategies(Arrays.asList(identifierFilterStrategy));

        IpAddressFilterStrategy ipAddressFilterStrategy = new IpAddressFilterStrategy();

        IpAddress ipAddress = new IpAddress();
        ipAddress.setIpAddressFilterStrategies(Arrays.asList(ipAddressFilterStrategy));

        PhoneNumberFilterStrategy phoneNumberFilterStrategy = new PhoneNumberFilterStrategy();

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPhoneNumberFilterStrategies(Arrays.asList(phoneNumberFilterStrategy));

        PhoneNumberExtensionFilterStrategy phoneNumberExtensionFilterStrategy = new PhoneNumberExtensionFilterStrategy();

        PhoneNumberExtension phoneNumberExtension = new PhoneNumberExtension();
        phoneNumberExtension.setPhoneNumberExtensionFilterStrategies(Arrays.asList(phoneNumberExtensionFilterStrategy));

        SsnFilterStrategy ssnFilterStrategy = new SsnFilterStrategy();

        Ssn ssn = new Ssn();
        ssn.setSsnFilterStrategies(Arrays.asList(ssnFilterStrategy));

        StateAbbreviationsFilterStrategy stateAbbreviationsFilterStrategy = new StateAbbreviationsFilterStrategy();

        StateAbbreviation stateAbbreviation = new StateAbbreviation();
        stateAbbreviation.setStateAbbreviationsFilterStrategies(Arrays.asList(stateAbbreviationsFilterStrategy));

        UrlFilterStrategy urlFilterStrategy = new UrlFilterStrategy();

        Url url = new Url();
        url.setUrlFilterStrategies(Arrays.asList(urlFilterStrategy));

        VinFilterStrategy vinFilterStrategy = new VinFilterStrategy();

        Vin vin = new Vin();
        vin.setVinFilterStrategies(Arrays.asList(vinFilterStrategy));

        ZipCodeFilterStrategy zipCodeFilterStrategy = new ZipCodeFilterStrategy();
        zipCodeFilterStrategy.setTruncateDigits(2);

        ZipCode zipCode = new ZipCode();
        zipCode.setZipCodeFilterStrategies(Arrays.asList(zipCodeFilterStrategy));

        // ----------------------------------------------------------------------------------

        CityFilterStrategy cityFilterStrategy = new CityFilterStrategy();
        cityFilterStrategy.setSensitivityLevel(sensitivityLevel.getName());

        City city = new City();
        city.setCityFilterStrategies(Arrays.asList(cityFilterStrategy));

        CountyFilterStrategy countyFilterStrategy = new CountyFilterStrategy();
        countyFilterStrategy.setSensitivityLevel(sensitivityLevel.getName());

        County county = new County();
        county.setCountyFilterStrategies(Arrays.asList(countyFilterStrategy));

        FirstNameFilterStrategy firstNameFilterStrategy = new FirstNameFilterStrategy();
        firstNameFilterStrategy.setSensitivityLevel(sensitivityLevel.getName());

        FirstName firstName = new FirstName();
        firstName.setFirstNameFilterStrategies(Arrays.asList(firstNameFilterStrategy));

        HospitalAbbreviationFilterStrategy hospitalAbbreviationFilterStrategy = new HospitalAbbreviationFilterStrategy();
        hospitalAbbreviationFilterStrategy.setSensitivityLevel(sensitivityLevel.getName());

        HospitalAbbreviation hospitalAbbreviation = new HospitalAbbreviation();
        hospitalAbbreviation.setHospitalAbbreviationFilterStrategies(Arrays.asList(hospitalAbbreviationFilterStrategy));

        HospitalFilterStrategy hospitalFilterStrategy = new HospitalFilterStrategy();
        hospitalAbbreviationFilterStrategy.setSensitivityLevel(sensitivityLevel.getName());

        Hospital hospital = new Hospital();
        hospital.setHospitalFilterStrategies(Arrays.asList(hospitalFilterStrategy));

        StateFilterStrategy stateFilterStrategy = new StateFilterStrategy();
        stateAbbreviationsFilterStrategy.setSensitivityLevel(sensitivityLevel.getName());

        State state = new State();
        state.setStateFilterStrategies(Arrays.asList(stateFilterStrategy));

        SurnameFilterStrategy surnameFilterStrategy = new SurnameFilterStrategy();
        surnameFilterStrategy.setSensitivityLevel(sensitivityLevel.getName());

        Surname surname = new Surname();
        surname.setSurnameFilterStrategies(Arrays.asList(surnameFilterStrategy));

        // ----------------------------------------------------------------------------------

        Identifiers identifiers = new Identifiers();

        identifiers.setAge(age);
        identifiers.setCreditCard(creditCard);
        identifiers.setDate(date);
        identifiers.setEmailAddress(emailAddress);
        identifiers.setIdentifier(identifier);
        identifiers.setIpAddress(ipAddress);
        identifiers.setPhoneNumber(phoneNumber);
        identifiers.setPhoneNumberExtension(phoneNumberExtension);
        identifiers.setSsn(ssn);
        identifiers.setStateAbbreviation(stateAbbreviation);
        identifiers.setUrl(url);
        identifiers.setVin(vin);
        identifiers.setZipCode(zipCode);

        identifiers.setCity(city);
        identifiers.setCounty(county);
        identifiers.setFirstName(firstName);
        identifiers.setHospital(hospital);
        identifiers.setHospitalAbbreviation(hospitalAbbreviation);
        identifiers.setState(state);
        identifiers.setSurname(surname);

        FilterProfile filterProfile = new FilterProfile();
        filterProfile.setName("default");
        filterProfile.setIdentifiers(identifiers);

        return filterProfile;

    }

    public String getIndexDirectory(String indexName) {

        final String baseDir = System.getenv("PHILTER_INDEX_DIR");

        if(!StringUtils.isEmpty(baseDir)) {

            final String indexDirectory = baseDir + "/data/indexes/" + indexName;

            LOGGER.info("Using index directory: " + indexDirectory);

            return indexDirectory;

        } else {

            LOGGER.warn("Environment variable PHILTER_INDEX_DIR is not set for Lucene index test.");

            return "/mtnfog/code/bitbucket/philter/philter/data/indexes/" + indexName;

        }

    }

    public void showSpans(List<Span> spans) {

        for(Span span : spans) {
            LOGGER.info(span.toString());
        }

    }

    public boolean checkSpan(Span span, int characterStart, int characterEnd, FilterType filterType) {

        LOGGER.info("Checking span: {}", span.toString());

        return (span.getCharacterStart() == characterStart
                && span.getCharacterEnd() == characterEnd
                && span.getFilterType() == filterType);

    }

}
