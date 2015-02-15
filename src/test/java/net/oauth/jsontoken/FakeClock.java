/**
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package net.oauth.jsontoken;


import java.time.Instant;

/**
 * Created by steve on 12/09/14.
 */
public class FakeClock extends SystemClock {
    private Instant now = Instant.now();

    public FakeClock() {
        super(0);
    }

    public FakeClock(int acceptableClockSkewInMin) {
        super(acceptableClockSkewInMin);
  }

    public void setNow(Instant i) {
        now = i;
    }

    @Override
    public Instant now() {
        return now;
    }

}

