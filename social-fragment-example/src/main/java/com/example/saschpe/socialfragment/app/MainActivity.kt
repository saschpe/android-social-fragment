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

package com.example.saschpe.socialfragment.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.saschpe.socialfragment.R
import kotlinx.android.synthetic.main.activity_main.*
import saschpe.android.socialfragment.app.SocialFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Set up social fragment
        val fragment = SocialFragment.Builder()
            // Mandatory
            .setApplicationId("saschpe.alphaplus")
            // Optional
            .setApplicationName("Alpha+ Player")
            .setContactEmailAddress("saschpe@example.com")
            .setFacebookGroup("466079123741258")
            .setGithubProject("saschpe/android-social-fragment")
            .setGitlabProject("saschpe")
            .setTwitterProfile("saschpe")
            // Visual customization
            .setHeaderTextAppearance(R.style.TextAppearance_MaterialComponents_Headline6)
            .setHeaderTextColor(R.color.accent)
            .setIconTint(android.R.color.white)
            .build()

        // Attach it
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .commit()
    }
}
