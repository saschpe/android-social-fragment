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

public final class SocialFragment extends Fragment {
    // TODO: Kill all!
    private static final String[] SUPPORT_EMAIL_ADDRESS = new String[]{"sascha+gp@peilicke.de"};
    private static final String TWITTER_NAME = "saschpe";
    private static final String APP_NAME = "app";

    private TextView openFacebookGroup;
    private TextView joinGoogleGroup;
    private TextView followTwitter;
    private TextView rateOnPlayStore;
    private TextView provideFeedback;
    private TextView recommendToFriend;
    private String packageName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packageName = getContext().getPackageName().replace(".debug", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        openFacebookGroup = view.findViewById(R.id.open_facebook_group);
        joinGoogleGroup = view.findViewById(R.id.join_google_plus_group);
        provideFeedback = view.findViewById(R.id.provide_feedback);
        followTwitter = view.findViewById(R.id.follow_twitter);
        rateOnPlayStore = view.findViewById(R.id.rate_play_store);
        recommendToFriend = view.findViewById(R.id.recommend_to_friend);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        openFacebookGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getContext().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/466079123741258")));
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/alphaplusplayer/")));
                }
            }
        });
        joinGoogleGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/communities/116602691405798233571"))
                            .setPackage("com.google.android.apps.plus");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/communities/116602691405798233571")));
                }
            }
        });
        followTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + TWITTER_NAME)));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + TWITTER_NAME)));
                }
            }
        });
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
        recommendToFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = getString(R.string.get_app_template, APP_NAME);
                String body = Uri.parse("http://play.google.com/store/apps/details?id=" + packageName).toString();

                Intent sharingIntent = new Intent(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_SUBJECT, subject)
                        .putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(sharingIntent, view.getContext().getString(R.string.share_via)));
            }
        });
        provideFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = getString(R.string.feedback_mail_subject_template, APP_NAME);

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", SUPPORT_EMAIL_ADDRESS[0], null))
                        .putExtra(Intent.EXTRA_SUBJECT, subject)
                        .putExtra(Intent.EXTRA_TEXT, "")
                        .putExtra(Intent.EXTRA_EMAIL, SUPPORT_EMAIL_ADDRESS);
                startActivity(Intent.createChooser(emailIntent, view.getContext().getString(R.string.send_email)));
            }
        });
    }

    public static final class Builder {
        private String appName;
        private String twitterProfile;
        private String emailAddress;
        private String googlePlusGroup;
        private String facebookGroup;

        public Builder setAppName(final String appName) {
            this.appName = appName;
            return this;
        }

        public Builder setContactEmailAddress(final String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public Builder addTwitterProfile(final String twitterProfile) {
            this.twitterProfile = twitterProfile;
            return this;
        }

        public Builder addGooglePlusGroup(final String googlePlusGroup) {
            this.googlePlusGroup = googlePlusGroup;
            return this;
        }

        public Builder addFacebookGroup(final String facebookGroup) {
            this.facebookGroup = facebookGroup;
            return this;
        }

        public SocialFragment build() {
            SocialFragment fragment = new SocialFragment();

            return fragment;
        }
    }
}