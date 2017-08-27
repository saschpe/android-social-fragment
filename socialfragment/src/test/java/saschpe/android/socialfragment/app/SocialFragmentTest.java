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

package saschpe.android.socialfragment.app;import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import saschpe.android.socialfragment.app.SocialFragment;

@RunWith(RobolectricTestRunner.class)
public final class SocialFragmentTest  {
    private static final String TEST_EMAIL_ADDRESS = "joe@example.com";
    private static final String TEST_FACEBOOK_GROUP = "my_facebook_group";
    private static final String TEST_GOOGLE_PLUS_GROUP = "my_google_plus_group";
    private static final String TEST_TWITTER_PROFILE = "my_twitter_profile";
    public static final String TEST_APP_NAME = "app.name";

    private TestActivity activity;

    @Test
    public void setupActivity() {
        SocialFragment fragment = new SocialFragment.Builder()
                .setAppName(TEST_APP_NAME)
                .setContactEmailAddress(TEST_EMAIL_ADDRESS)
                .addFacebookGroup(TEST_FACEBOOK_GROUP)
                .addGooglePlusGroup(TEST_GOOGLE_PLUS_GROUP)
                .addTwitterProfile(TEST_TWITTER_PROFILE)
                .build();

        activity = Robolectric.setupActivity(TestActivity.class);
    }

    @Test
    public void bla() {
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