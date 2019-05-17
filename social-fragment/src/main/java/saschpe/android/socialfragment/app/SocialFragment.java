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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
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
    public static final String ARG_APPLICATION_ID = "app_id";
    public static final String ARG_APPLICATION_NAME = "app_name";
    public static final String ARG_CONTACT_EMAIL_ADDRESS = "contact_email_address";
    public static final String ARG_CONTACT_EMAIL_SUBJECT = "contact_email_subject";
    public static final String ARG_CONTACT_EMAIL_TEXT = "contact_email_text";
    public static final String ARG_FACEBOOK_PAGE = "facebook_page";
    public static final String ARG_ICON_TINT = "drawable_tint";
    public static final String ARG_RECOMMENDATION_SUBJECT = "recommendation_subject";
    public static final String ARG_TWITTER_PROFILE = "twitter_profile";
    public static final String ARG_HEADER_TEXT_APPEARANCE = "header_text_appearance";
    public static final String ARG_HEADER_TEXT_COLOR = "header_text_color";
    public static final String ARG_GITHUB_PROJECT = "github_project";

    // Social networks links
    private TextView followTitle;
    private TextView followTwitter;
    private TextView openFacebookGroup;
    // Recommendation links
    private TextView supportTitle;
    private TextView rateOnPlayStore;
    private TextView recommendToFriend;
    // Feedback links
    private TextView contactTitle;
    private TextView provideFeedback;
    // Open Source links
    private TextView openSourceTitle;
    private TextView forkOnGithub;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Support vector drawable support for pre-Lollipop devices
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        followTitle = view.findViewById(R.id.follow_title);
        followTwitter = view.findViewById(R.id.follow_twitter);
        openFacebookGroup = view.findViewById(R.id.open_facebook_group);

        supportTitle = view.findViewById(R.id.support_title);
        rateOnPlayStore = view.findViewById(R.id.rate_play_store);
        recommendToFriend = view.findViewById(R.id.recommend_to_friend);

        contactTitle = view.findViewById(R.id.contact_title);
        provideFeedback = view.findViewById(R.id.provide_feedback);

        openSourceTitle = view.findViewById(R.id.open_source_title);
        forkOnGithub = view.findViewById(R.id.fork_on_github);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle args = getArguments();

        if (args == null) {
            return;
        }

        if (args.containsKey(ARG_FACEBOOK_PAGE) || args.containsKey(ARG_TWITTER_PROFILE)) {
            followTitle.setVisibility(View.VISIBLE);
            applyTextArgs(args, followTitle);
        }

        if (args.containsKey(ARG_FACEBOOK_PAGE)) {
            openFacebookGroup.setVisibility(View.VISIBLE);
            applyIconTint(args, openFacebookGroup, R.drawable.ic_facebook);
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

        if (args.containsKey(ARG_TWITTER_PROFILE)) {
            followTwitter.setVisibility(View.VISIBLE);
            applyIconTint(args, followTwitter, R.drawable.ic_twitter);
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

        applyTextArgs(args, supportTitle);

        applyIconTint(args, rateOnPlayStore, R.drawable.ic_star);
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
                        Uri.parse("market://details?id=" + args.getString(ARG_APPLICATION_ID)))
                        .addFlags(flags);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + args.getString(ARG_APPLICATION_ID))));
                }
            }
        });

        final String recommendSubject;
        if (args.containsKey(ARG_RECOMMENDATION_SUBJECT)) {
            recommendSubject = args.getString(ARG_RECOMMENDATION_SUBJECT);
        } else if (args.containsKey(ARG_APPLICATION_NAME)) {
            recommendSubject = getString(R.string.get_the_app_template, args.getString(ARG_APPLICATION_NAME));
        } else {
            recommendSubject = getString(R.string.get_the_app);
        }

        applyIconTint(args, recommendToFriend, R.drawable.ic_share);
        recommendToFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = Uri.parse("http://play.google.com/store/apps/details?id=" + args.getString(ARG_APPLICATION_ID)).toString();

                Intent sharingIntent = new Intent(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_SUBJECT, recommendSubject)
                        .putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(sharingIntent, view.getContext().getString(R.string.share_via)));
            }
        });

        if (args.containsKey(ARG_CONTACT_EMAIL_ADDRESS)) {
            contactTitle.setVisibility(View.VISIBLE);
            applyTextArgs(args, contactTitle);

            final String emailSubject;
            if (args.containsKey(ARG_CONTACT_EMAIL_SUBJECT)) {
                emailSubject = args.getString(ARG_CONTACT_EMAIL_SUBJECT);
            } else if (args.containsKey(ARG_APPLICATION_NAME)) {
                emailSubject = getString(R.string.feedback_mail_subject_template, args.getString(ARG_APPLICATION_NAME));
            } else {
                emailSubject = getString(R.string.feedback);
            }
            final String emailText;
            if (args.containsKey(ARG_CONTACT_EMAIL_TEXT)) {
                emailText = args.getString(ARG_CONTACT_EMAIL_TEXT);
            } else {
                emailText = getString(R.string.i_love_your_app);
            }

            provideFeedback.setVisibility(View.VISIBLE);
            applyIconTint(args, provideFeedback, R.drawable.ic_email);
            provideFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", args.getString(ARG_CONTACT_EMAIL_ADDRESS), null))
                            .putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                            .putExtra(Intent.EXTRA_TEXT, emailText)
                            .putExtra(Intent.EXTRA_EMAIL, args.getString(ARG_CONTACT_EMAIL_ADDRESS));
                    startActivity(Intent.createChooser(emailIntent, view.getContext().getString(R.string.send_email)));
                }
            });
        }

        if (args.containsKey(ARG_GITHUB_PROJECT)) {
            openSourceTitle.setVisibility(View.VISIBLE);
            applyTextArgs(args, openSourceTitle);

            forkOnGithub.setVisibility(View.VISIBLE);
            applyIconTint(args, forkOnGithub, R.drawable.ic_github_circle);
            forkOnGithub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW)
                            .setData(Uri.parse("https://github.com/" + args.getString(ARG_GITHUB_PROJECT))));
                }
            });
        }
    }

    private void applyTextArgs(Bundle args, TextView supportTitle) {
        if (getContext() != null) {
            if (args.getInt(ARG_HEADER_TEXT_APPEARANCE) != 0) {
                supportTitle.setTextAppearance(getContext(), args.getInt(ARG_HEADER_TEXT_APPEARANCE));
            }
            if (args.getInt(ARG_HEADER_TEXT_COLOR) != 0) {
                supportTitle.setTextColor(getContext().getResources().getColor(args.getInt(ARG_HEADER_TEXT_COLOR)));
            }
        }
    }

    private void applyIconTint(Bundle args, TextView textView, @DrawableRes int drawableRes) {
        if (args.getInt(ARG_ICON_TINT) != 0) {
            Drawable drawable = getResources().getDrawable(drawableRes);
            Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
            if (getContext() != null) {
                DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(getContext(), args.getInt(ARG_ICON_TINT)));
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(wrappedDrawable, null, null, null);
        }
    }

    /**
     * Builder to create {@link SocialFragment} instances.
     */
    public static final class Builder {
        private final Bundle args = new Bundle();

        /**
         * Set the application id used for links to the Play Store and for
         * recommendations to friends.
         *
         * @param appId The app id
         * @return The builder
         */
        public Builder setApplicationId(final String appId) {
            args.putString(ARG_APPLICATION_ID, appId);
            return this;
        }

        /**
         * Set the application name used in recommendation message titles and
         * elsewhere.
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

        public Builder setGithubProject(final String githubProject) {
            args.putString(ARG_GITHUB_PROJECT, githubProject);
            return this;
        }

        public Builder setIconTint(final @ColorRes int iconTintResId) {
            args.putInt(ARG_ICON_TINT, iconTintResId);
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

        public Builder setHeaderTextAppearance(final @StyleRes int appearanceResId) {
            args.putInt(ARG_HEADER_TEXT_APPEARANCE, appearanceResId);
            return this;
        }

        public Builder setHeaderTextColor(final @ColorRes int colorResId) {
            args.putInt(ARG_HEADER_TEXT_COLOR, colorResId);
            return this;
        }

        public SocialFragment build() {
            if (!args.containsKey(ARG_APPLICATION_ID)) {
                throw new IllegalStateException("Application ID is mandatory!");
            }

            SocialFragment fragment = new SocialFragment();
            fragment.setArguments(args);
            return fragment;
        }
    }
}