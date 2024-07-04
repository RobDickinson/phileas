/*
 *     Copyright 2024 Philterd, LLC @ https://www.philterd.ai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.philterd.test.phileas.model.policy;

import ai.philterd.phileas.model.policy.FPE;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FPETest {

    @Test
    public void test1() {

        final FPE crypto = new FPE("mykey", "myiv");

        assertEquals("mykey", crypto.getKey());
        assertEquals("myiv", crypto.getTweak());

    }

    @Test
    @SetEnvironmentVariable(key="mykey", value="value")
    public void test2() throws Exception {

        final FPE crypto = new FPE("env:mykey", "myiv");

        final String value = crypto.getKey();

        assertEquals("value", value);
        assertEquals("myiv", crypto.getTweak());

    }

    @Test
    @SetEnvironmentVariable(key="myiv", value="value")
    public void test3() {

        final FPE crypto = new FPE("mykey", "env:myiv");

        final String value = crypto.getTweak();

        assertEquals("mykey", crypto.getKey());
        assertEquals("value", value);

    }

}
