package com.example.alanbarrera.ordinario_paty;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntegrationTests
{
    private UiDevice mUiDevice;
    private static final String GIVEN_NAME = "Alan";
    private static final String FAMILY_NAME = "Barrera";
    private static final String DISPLAY_NAME = "Alan Barrera";
    private static final String EMAIL = "alan.alexis64@gmail.com";
    private static final String ID = "105249752357637039205";

    @Rule
    public ActivityTestRule<MainActivity> loginRule = new ActivityTestRule<>(
            MainActivity.class, true, true);

    @Before
    public void PrepareData() {
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        loginRule.getActivity().logOut(null);
    }

    @Test
    public void canSignIn()
    {
        onView(withId(R.id.sign_in_button)).perform(click());
        try {
            UiObject profileButton = mUiDevice.findObject(new UiSelector().text(EMAIL));
            profileButton.click();
            Thread.sleep(3000);
        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
        GoogleSignInAccount account = loginRule.getActivity().account;

        assertThat(account.getGivenName(), is(GIVEN_NAME));
        assertThat(account.getFamilyName(), is(FAMILY_NAME));
        assertThat(account.getDisplayName(), is(DISPLAY_NAME));
        assertThat(account.getEmail(), is(EMAIL));
        assertThat(account.getId(), is(ID));
    }
}