/*
 * Copyright 2017 Sascha Peilicke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package saschpe.android.socialfragment.app;

import android.os.Bundle;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@Ignore
public final class SocialFragmentTest {
    private static final String TEST_APPLICATION_ID = "my.app";
    private static final String TEST_APPLICATION_NAME = "MyApp";
    private static final String TEST_EMAIL_ADDRESS = "joe@example.com";
    private static final String TEST_FACEBOOK_GROUP = "my_facebook_group";
    private static final String TEST_GOOGLE_PLUS_GROUP = "my_google_plus_group";
    private static final String TEST_TWITTER_PROFILE = "my_twitter_profile";

    private TestActivity activity;

    @Test
    public void setupActivity() {
        activity = Robolectric.setupActivity(TestActivity.class);
    }

    @Test
    public void bla() {
        SocialFragment fragment = new SocialFragment.Builder()
                .setApplicationId(TEST_APPLICATION_ID)
                .setApplicationName(TEST_APPLICATION_NAME)
                .setContactEmailAddress(TEST_EMAIL_ADDRESS)
                .setFacebookGroup(TEST_FACEBOOK_GROUP)
                .setGooglePlusGroup(TEST_GOOGLE_PLUS_GROUP)
                .setTwitterProfile(TEST_TWITTER_PROFILE)
                .build();
        // TODO!
    }

    private static final class TestActivity extends AppCompatActivity {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // TODO: Inflate fragment!
        }
    }
}