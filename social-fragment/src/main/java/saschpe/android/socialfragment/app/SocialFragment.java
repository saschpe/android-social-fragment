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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import saschpe.android.socialfragment.R;

/**
 * Fragment to display links to the Play Store and social networks.
 * <p>
 * Tries to open links in respective app or falls back to web links or other
 * means if the user doesn't have the apps installed. Currently provides support
 * for Facebook, Google+ and Twitter. Provides links into the Play Store for
 * recommendation and sharing with friends.
 */
public final class SocialFragment extends Fragment {
    public static final String ARG_APPLICATION_NAME = "app_name";
    public static final String ARG_CONTACT_EMAIL_ADDRESS = "contact_email_address";
    public static final String ARG_CONTACT_EMAIL_SUBJECT = "contact_email_subject";
    public static final String ARG_CONTACT_EMAIL_TEXT = "contact_email_text";
    public static final String ARG_FACEBOOK_PAGE = "facebook_page";
    public static final String ARG_GOOGLE_PLUS_GROUP = "google_plus_group";
    public static final String ARG_RECOMMENDATION_SUBJECT = "recommendation_subject";
    public static final String ARG_TWITTER_PROFILE = "twitter_profile";

    private TextView followTwitter;
    private TextView joinGoogleGroup;
    private TextView openFacebookGroup;
    public String packageName; // TODO!!!!
    private TextView provideFeedback;
    private TextView rateOnPlayStore;
    private TextView recommendToFriend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packageName = getContext().getPackageName().replace(".debug", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        followTwitter = view.findViewById(R.id.follow_twitter);
        joinGoogleGroup = view.findViewById(R.id.join_google_plus_group);
        openFacebookGroup = view.findViewById(R.id.open_facebook_group);
        provideFeedback = view.findViewById(R.id.provide_feedback);
        rateOnPlayStore = view.findViewById(R.id.rate_play_store);
        recommendToFriend = view.findViewById(R.id.recommend_to_friend);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle args = getArguments();

        if (args.containsKey(ARG_FACEBOOK_PAGE)) {
            openFacebookGroup.setVisibility(View.VISIBLE);
            openFacebookGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        getContext().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + args.get(ARG_FACEBOOK_PAGE))));
                    } catch (Exception e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + args.get(ARG_FACEBOOK_PAGE))));
                    }
                }
            });
        }

        if (args.containsKey(ARG_GOOGLE_PLUS_GROUP)) {
            joinGoogleGroup.setVisibility(View.VISIBLE);
            joinGoogleGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/communities/" + args.get(ARG_GOOGLE_PLUS_GROUP)))
                                .setPackage("com.google.android.apps.plus");
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/communities/" + args.get(ARG_GOOGLE_PLUS_GROUP))));
                    }
                }
            });
        }

        if (args.containsKey(ARG_TWITTER_PROFILE)) {
            followTwitter.setVisibility(View.VISIBLE);
            followTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + args.get(ARG_TWITTER_PROFILE))));
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + args.get(ARG_TWITTER_PROFILE))));
                    }
                }
            });
        }

        rateOnPlayStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // To count with Play market back stack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
                if (Build.VERSION.SDK_INT >= 21) {
                    flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
                } else {
                    //noinspection deprecation
                    flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
                }
                Intent goToMarket = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + packageName))
                        .addFlags(flags);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
                }
            }
        });

        if (args.containsKey(ARG_APPLICATION_NAME)) {
            final String subject;
            if (args.containsKey(ARG_RECOMMENDATION_SUBJECT)) {
                subject = args.getString(ARG_RECOMMENDATION_SUBJECT);
            } else if (args.containsKey(ARG_APPLICATION_NAME)) {
                subject = getString(R.string.get_the_app_template, args.getString(ARG_APPLICATION_NAME));
            } else  {
                subject = getString(R.string.get_the_app);
            }

            recommendToFriend.setVisibility(View.VISIBLE);
            recommendToFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String text = Uri.parse("http://play.google.com/store/apps/details?id=" + packageName).toString();

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND)
                            .setType("text/plain")
                            .putExtra(Intent.EXTRA_SUBJECT, subject)
                            .putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(sharingIntent, view.getContext().getString(R.string.share_via)));
                }
            });
        }

        if (args.containsKey(ARG_CONTACT_EMAIL_ADDRESS)) {
            final String subject;
            if (args.containsKey(ARG_CONTACT_EMAIL_SUBJECT)) {
                subject = args.getString(ARG_CONTACT_EMAIL_SUBJECT);
            } else if (args.containsKey(ARG_APPLICATION_NAME)) {
                subject = getString(R.string.feedback_mail_subject_template, args.getString(ARG_APPLICATION_NAME));
            } else {
                subject = getString(R.string.feedback);
            }
            final String text;
            if (args.containsKey(ARG_CONTACT_EMAIL_TEXT)) {
                text = args.getString(ARG_CONTACT_EMAIL_TEXT);
            } else {
                text = getString(R.string.i_love_your_app);
            }

            provideFeedback.setVisibility(View.VISIBLE);
            provideFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", args.getString(ARG_CONTACT_EMAIL_ADDRESS), null))
                            .putExtra(Intent.EXTRA_SUBJECT, subject)
                            .putExtra(Intent.EXTRA_TEXT, text)
                            .putExtra(Intent.EXTRA_EMAIL, args.getString(ARG_CONTACT_EMAIL_ADDRESS));
                    startActivity(Intent.createChooser(emailIntent, view.getContext().getString(R.string.send_email)));
                }
            });
        }
    }

    /**
     * Builder to create {@link SocialFragment} instances.
     */
    public static final class Builder {
        private final Bundle args = new Bundle();

        /**
         * Set the application name used for links to the Play Store and for
         * recommendations to friends.
         *
         * @param appName The app name
         * @return The builder
         */
        public Builder setApplicationName(final String appName) {
            args.putString(ARG_APPLICATION_NAME, appName);
            return this;
        }

        public Builder setContactEmailAddress(final String emailAddress) {
            args.putString(ARG_CONTACT_EMAIL_ADDRESS, emailAddress);
            return this;
        }

        public Builder setContactEmailSubject(final String emailSubject) {
            args.putString(ARG_CONTACT_EMAIL_SUBJECT, emailSubject);
            return this;
        }

        public Builder setContactEmailText(final String emailText) {
            args.putString(ARG_CONTACT_EMAIL_TEXT, emailText);
            return this;
        }

        public Builder setFacebookGroup(final String facebookPage) {
            args.putString(ARG_FACEBOOK_PAGE, facebookPage);
            return this;
        }

        public Builder setGooglePlusGroup(final String googlePlusGroup) {
            args.putString(ARG_GOOGLE_PLUS_GROUP, googlePlusGroup);
            return this;
        }

        public Builder setRecommendationSubject(final String subject) {
            args.putString(ARG_RECOMMENDATION_SUBJECT, subject);
            return this;
        }

        public Builder setTwitterProfile(final String twitterProfile) {
            args.putString(ARG_TWITTER_PROFILE, twitterProfile);
            return this;
        }

        public SocialFragment build() {
            SocialFragment fragment = new SocialFragment();
            fragment.setArguments(args);
            return fragment;
        }
    }
}