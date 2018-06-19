package com.udacity.gradle.builditbigger;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by lgpc on 2018-06-18.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void checkApiResponse(){
        onView(withId(R.id.bt_telljoke)).perform(click());
        onView(withId(R.id.tv_joke)).check(matches(withText("This is a Joke!!")));
    }

    @After
    public void unregisterIdlingResource(){
        if(mIdlingResource != null){
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
