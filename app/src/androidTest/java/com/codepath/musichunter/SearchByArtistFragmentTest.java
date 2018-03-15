package com.codepath.musichunter;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.EditText;

import com.codepath.musichunter.searchbyartist.SearchByArtistFragment;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by uchit on 15/03/2018.
 */

public class SearchByArtistFragmentTest {

    @Rule
    public FragmentTestRule<SearchByArtistFragment> mFragmentTestRule = new FragmentTestRule<>(SearchByArtistFragment.class);

    @Before
    public void setUp() throws Exception {
        mFragmentTestRule.launchActivity(null);
    }

    @Test
    public void fragment_can_be_instantiated() {

        // Launch the activity to make the fragment visible

        // Then use Espresso to test the Fragment
        onView(withId(R.id.pager)).check(matches(isDisplayed()));
    }

    //[methodName]With[Input]Then[ExpectedResult]()

    //R.id.tv_ArtistBiography
 /*   @Test
    public void checkartistBiography_isNotNull() {
       // onView(withId(R.id.tv_ArtistBiography)).perform(typeText("user@email.com"), closeSoftKeyboard());
       // onView(withId(R.id.tv_password)).perform(typeText("123456"), closeSoftKeyboard());
       // onView(withId(R.id.btn_login)).perform(click());
         onView(withText(R.id.tv_ArtistLabel).perform(typeText("Warner Bros, Records"), closeSoftKeyboard())
                .check(matches(isDisplayed())));
    }*/

   /* @Test
    public void emailIsEmpty() {
        onView(withId(R.id.tv_ArtistLabel)).perform(clearText());
     //   onView(withId(R.id.btn_login)).perform(click());
        onView(withId(R.id.tv_ArtistLabel)).check(matches(withError("")));
    }
*/

    private String getString(@StringRes int resourceId) {
        return mFragmentTestRule.getActivity().getString(resourceId);
    }

    private static Matcher<View> withError(final String expected) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if (item instanceof EditText) {
                    return ((EditText)item).getError().toString().equals(expected);
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Not found error message" + expected + ", find it!");
            }
        };
    }


}
