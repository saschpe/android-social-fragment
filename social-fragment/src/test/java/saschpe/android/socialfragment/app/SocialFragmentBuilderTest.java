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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public final class SocialFragmentBuilderTest {
    private static final String TEST_APPLICATION_ID = "my.app";
    private static final String TEST_APPLICATION_NAME = "MyApp";
    private static final String TEST_CONTACT_EMAIL_ADDRESS = "joe@example.com";
    private static final String TEST_CONTACT_EMAIL_SUBJECT = "Support for MyApp";
    private static final String TEST_CONTACT_EMAIL_TEXT = "I like!";
    private static final String TEST_FACEBOOK_GROUP = "my_facebook_group";
    private static final String TEST_GOOGLE_PLUS_GROUP = "my_google_plus_group";
    private static final String TEST_RECOMMENDATION_SUBJECT = "Get it!";
    private static final String TEST_TWITTER_PROFILE = "my_twitter_profile";

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void build_throwsIfAppIdIsMissing() {
        // Arrange, act, assert
        expectedException.expect(IllegalStateException.class);
        new SocialFragment.Builder().build();
    }

    @Test
    public void build_minimalBundleArgsOnlyContainAppName() {
        // Arrange, act
        SocialFragment fragment = new SocialFragment.Builder()
                .setApplicationId(TEST_APPLICATION_ID)
                .build();

        Bundle args = fragment.getArguments();

        // Assert
        assertNotNull(fragment);
        assertTrue(args.containsKey(SocialFragment.ARG_APPLICATION_ID));
        assertEquals(TEST_APPLICATION_ID, args.getString(SocialFragment.ARG_APPLICATION_ID));
        assertFalse(args.containsKey(SocialFragment.ARG_APPLICATION_NAME));
        assertFalse(args.containsKey(SocialFragment.ARG_CONTACT_EMAIL_ADDRESS));
        assertFalse(args.containsKey(SocialFragment.ARG_CONTACT_EMAIL_SUBJECT));
        assertFalse(args.containsKey(SocialFragment.ARG_CONTACT_EMAIL_TEXT));
        assertFalse(args.containsKey(SocialFragment.ARG_FACEBOOK_PAGE));
        assertFalse(args.containsKey(SocialFragment.ARG_GOOGLE_PLUS_GROUP));
        assertFalse(args.containsKey(SocialFragment.ARG_RECOMMENDATION_SUBJECT));
        assertFalse(args.containsKey(SocialFragment.ARG_TWITTER_PROFILE));
    }

    @Test
    public void build_withAllOptionsFoundInBundleArgs() {
        // Arrange, act
        SocialFragment fragment = new SocialFragment.Builder()
                .setApplicationId(TEST_APPLICATION_ID)
                .setApplicationName(TEST_APPLICATION_NAME)
                .setContactEmailAddress(TEST_CONTACT_EMAIL_ADDRESS)
                .setContactEmailSubject(TEST_CONTACT_EMAIL_SUBJECT)
                .setContactEmailText(TEST_CONTACT_EMAIL_TEXT)
                .setFacebookGroup(TEST_FACEBOOK_GROUP)
                .setGooglePlusGroup(TEST_GOOGLE_PLUS_GROUP)
                .setRecommendationSubject(TEST_RECOMMENDATION_SUBJECT)
                .setTwitterProfile(TEST_TWITTER_PROFILE)
                .build();

        Bundle args = fragment.getArguments();

        // Assert
        assertNotNull(fragment);
        assertTrue(args.containsKey(SocialFragment.ARG_APPLICATION_ID));
        assertEquals(TEST_APPLICATION_ID, args.getString(SocialFragment.ARG_APPLICATION_ID));
        assertTrue(args.containsKey(SocialFragment.ARG_APPLICATION_NAME));
        assertEquals(TEST_APPLICATION_NAME, args.getString(SocialFragment.ARG_APPLICATION_NAME));
        assertTrue(args.containsKey(SocialFragment.ARG_CONTACT_EMAIL_ADDRESS));
        assertEquals(TEST_CONTACT_EMAIL_ADDRESS, args.getString(SocialFragment.ARG_CONTACT_EMAIL_ADDRESS));
        assertTrue(args.containsKey(SocialFragment.ARG_CONTACT_EMAIL_SUBJECT));
        assertEquals(TEST_CONTACT_EMAIL_SUBJECT, args.getString(SocialFragment.ARG_CONTACT_EMAIL_SUBJECT));
        assertTrue(args.containsKey(SocialFragment.ARG_CONTACT_EMAIL_TEXT));
        assertEquals(TEST_CONTACT_EMAIL_TEXT, args.getString(SocialFragment.ARG_CONTACT_EMAIL_TEXT));
        assertTrue(args.containsKey(SocialFragment.ARG_FACEBOOK_PAGE));
        assertEquals(TEST_FACEBOOK_GROUP, args.getString(SocialFragment.ARG_FACEBOOK_PAGE));
        assertTrue(args.containsKey(SocialFragment.ARG_GOOGLE_PLUS_GROUP));
        assertEquals(TEST_GOOGLE_PLUS_GROUP, args.getString(SocialFragment.ARG_GOOGLE_PLUS_GROUP));
        assertTrue(args.containsKey(SocialFragment.ARG_RECOMMENDATION_SUBJECT));
        assertEquals(TEST_RECOMMENDATION_SUBJECT, args.getString(SocialFragment.ARG_RECOMMENDATION_SUBJECT));
        assertTrue(args.containsKey(SocialFragment.ARG_TWITTER_PROFILE));
        assertEquals(TEST_TWITTER_PROFILE, args.getString(SocialFragment.ARG_TWITTER_PROFILE));
    }
}