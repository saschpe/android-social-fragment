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

package com.example.saschpe.socialfragment.app;

import android.os.Bundle;

import com.example.saschpe.socialfragment.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import saschpe.android.socialfragment.app.SocialFragment;

public final class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up social fragment
        SocialFragment fragment = new SocialFragment.Builder()
                // Mandatory
                .setApplicationId("saschpe.alphaplus")
                // Optional
                .setApplicationName("Alpha+ Player")
                .setContactEmailAddress("saschpe@example.com")
                .setFacebookGroup("466079123741258")
                .setGithubProject("saschpe/PlanningPoker")
                .setGooglePlusGroup("116602691405798233571")
                .setTwitterProfile("saschpe")
                // Visual customization
                .setHeaderTextColor(R.color.accent)
                .setIconTint(android.R.color.white)
                .build();

        // Attach it
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, fragment)
                .commit();
    }
}
