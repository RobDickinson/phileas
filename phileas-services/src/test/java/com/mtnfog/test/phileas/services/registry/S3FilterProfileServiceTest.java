package com.mtnfog.test.phileas.services.registry;

import com.google.gson.Gson;
import com.mtnfog.phileas.configuration.PhileasConfiguration;
import com.mtnfog.phileas.model.profile.FilterProfile;
import com.mtnfog.phileas.model.profile.Identifiers;
import com.mtnfog.phileas.model.profile.filters.Age;
import com.mtnfog.phileas.model.profile.filters.CreditCard;
import com.mtnfog.phileas.model.profile.filters.strategies.rules.AgeFilterStrategy;
import com.mtnfog.phileas.model.profile.filters.strategies.rules.CreditCardFilterStrategy;
import com.mtnfog.phileas.model.services.FilterProfileService;
import com.mtnfog.phileas.services.registry.S3FilterProfileService;
import io.findify.s3mock.S3Mock;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class S3FilterProfileServiceTest {

    private static final Logger LOGGER = LogManager.getLogger(S3FilterProfileServiceTest.class);

    private Gson gson = new Gson();
    private RedisServer redisServer;
    private S3Mock api;

    private static boolean isExternalRedis = false;

    private PhileasConfiguration getConfiguration() {

        final Properties properties = new Properties();

        final String redisHost = System.getenv("PHILTER_REDIS_HOST");
        final String redisPort = System.getenv("PHILTER_REDIS_PORT");
        final String redisSsl = System.getenv("PHILTER_REDIS_SSL");
        final String redisToken = System.getenv("PHILTER_REDIS_AUTH_TOKEN");
        final String redisClustered = System.getenv("PHILTER_REDIS_CLUSTERED");

        properties.setProperty("filter.profiles", "s3");
        properties.setProperty("filter.profiles.s3.bucket", "profiles");
        properties.setProperty("filter.profiles.s3.prefix", "/");
        properties.setProperty("cache.redis.enabled", "true");

        if(StringUtils.isNotEmpty(redisHost)) {

            LOGGER.info("Using redis host: {}", redisHost);

            properties.setProperty("cache.redis.host", redisHost);
            properties.setProperty("cache.redis.port", redisPort);
            properties.setProperty("cache.redis.ssl", redisSsl);
            properties.setProperty("cache.redis.auth.token", redisToken);
            properties.setProperty("cache.redis.cluster", "true");
            properties.setProperty("cache.redis.cluster", redisClustered);

        } else {

            LOGGER.info("Using local redis host.");

            properties.setProperty("cache.redis.host", "localhost");
            properties.setProperty("cache.redis.port", "31000");
            properties.setProperty("cache.redis.ssl", "false");
            properties.setProperty("cache.redis.auth.token", "");
            properties.setProperty("cache.redis.cluster", "false");

        }

        return ConfigFactory.create(PhileasConfiguration.class, properties);

    }

    @BeforeClass
    public static void beforeClass() {

        Assume.assumeFalse(System.getProperty("os.name").toLowerCase().startsWith("win"));

        final String redisHost = System.getenv("PHILTER_REDIS_HOST");

        if(StringUtils.isNotEmpty(redisHost)) {
            isExternalRedis = true;
        }

    }

    @Before
    public void before() throws IOException {

        LOGGER.info("Starting S3 emulator.");

        final Path temporaryDirectory = Files.createTempDirectory("s3mock");

        api = new S3Mock.Builder().withPort(8001).withFileBackend(temporaryDirectory.toFile().getAbsolutePath()).build();
        api.start();

        if(!isExternalRedis) {
            redisServer = RedisServer.builder().port(31000).build();
            redisServer.start();
        }

    }

    @After
    public void after() {

        api.shutdown();

        if(!isExternalRedis) {
            redisServer.stop();
        }

    }

    @Test
    public void list() throws IOException {

        final FilterProfileService filterProfileService = new S3FilterProfileService(getConfiguration(), true);

        filterProfileService.save(gson.toJson(getFilterProfile("name1")));
        filterProfileService.save(gson.toJson(getFilterProfile("name2")));
        final List<String> names = filterProfileService.get();

        LOGGER.info("Found {} filter profiles", names.size());

        Assert.assertTrue(names.size() == 2);
        Assert.assertTrue(names.contains("name1"));
        Assert.assertTrue(names.contains("name2"));

    }

    @Test
    public void getAll() throws IOException {

        final FilterProfileService filterProfileService = new S3FilterProfileService(getConfiguration(), true);

        filterProfileService.save(gson.toJson(getFilterProfile("name1")));
        filterProfileService.save(gson.toJson(getFilterProfile("name2")));

        final Map<String, String> all = filterProfileService.getAll();

        LOGGER.info("Found {} profiles", all.size());

        Assert.assertEquals(2, all.size());
        Assert.assertTrue(all.keySet().contains("name1"));
        Assert.assertTrue(all.keySet().contains("name2"));

    }

    @Test
    public void save() throws IOException {

        final String name = "default";

        final String profile = gson.toJson(getFilterProfile(name));

        final FilterProfileService filterProfileService = new S3FilterProfileService(getConfiguration(), true);

        filterProfileService.save(profile);

    }

    @Test
    public void get() throws IOException {

        final String name = "default";

        final String profile = gson.toJson(getFilterProfile(name));

        final FilterProfileService filterProfileService = new S3FilterProfileService(getConfiguration(), true);

        filterProfileService.save(profile);

        final String filterProfileJson = filterProfileService.get(name);

        Assert.assertEquals(profile, filterProfileJson);

    }

    @Test
    public void delete() throws IOException {

        final String name = "default";
        final String profile = gson.toJson(getFilterProfile(name));

        final FilterProfileService filterProfileService = new S3FilterProfileService(getConfiguration(), true);

        filterProfileService.save(profile);

        filterProfileService.delete(name);

        Assert.assertFalse(filterProfileService.getAll().containsKey(name));

    }

    private FilterProfile getFilterProfile(String name) {

        AgeFilterStrategy ageFilterStrategy = new AgeFilterStrategy();

        Age age = new Age();
        age.setAgeFilterStrategies(Arrays.asList(ageFilterStrategy));

        CreditCardFilterStrategy creditCardFilterStrategy = new CreditCardFilterStrategy();

        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardFilterStrategies(Arrays.asList(creditCardFilterStrategy));

        Identifiers identifiers = new Identifiers();

        identifiers.setAge(age);

        FilterProfile filterProfile = new FilterProfile();
        filterProfile.setName(name);
        filterProfile.setIdentifiers(identifiers);

        return filterProfile;

    }

}
