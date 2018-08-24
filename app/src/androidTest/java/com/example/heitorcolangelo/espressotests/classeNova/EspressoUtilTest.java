package com.example.heitorcolangelo.espressotests.classeNova;
import android.app.Activity;
import android.os.Build;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import com.example.heitorcolangelo.espressotests.common.ScreenRobot;
import org.hamcrest.Matcher;
import org.junit.Before;
import java.util.Collection;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

public class EspressoUtilTest extends ScreenRobot<EspressoUtilTest> {

    private CountingIdlingResource mIdlingResource;
    private Activity currentActivity;

    @Before
    public void grantPhonePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.READ_EXTERNAL_STORAGE");

            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.WRITE_EXTERNAL_STORAGE");
        }
    }

    public void swipeLeft(int tutorial_view_pager) {
        ViewInteraction viewPager = onView(allOf(withId(tutorial_view_pager), isDisplayed()));
        viewPager.perform(ViewActions.swipeLeft());
    }

    public void assertBanner(int idBanner, String message) throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(idBanner)).check(matches(isDisplayed()));
        onView(withText(message)).check(matches(isDisplayed()));
    }

    public void assertBanner(int idBanner, int resourceIdMessage) throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(idBanner)).check(matches(isDisplayed()));
        onView(withText(resourceIdMessage)).check(matches(isDisplayed()));
    }

    public boolean viewWithTextExists(String text) {
        try {
            onView(withText(containsString(text))).check(matches(isDisplayed()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void viweWithTextDoesNotExists(String text){
        onView(withText(containsString(text))).check(doesNotExist());
    }

    public boolean checkViewDisplayed(int view, boolean isDisplayed){
        try {
            if(isDisplayed) {
                onView(withId(view)).check(matches(isDisplayed()));
            }else{
                onView(withId(view)).check(matches(not(isDisplayed())));
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public void checkViewDisplayed(String viewText, boolean isDisplayed){
            if(isDisplayed) {
                onView(withText(viewText)).check(matches(isDisplayed()));
            }else{
                onView(withText(viewText)).check(matches(not(isDisplayed())));
            }
    }

    public void checkViewWithText(int viewId, String text){
        onView(withId(viewId)).check(matches(withText(text)));
    }

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                AppCompatSeekBar seekBar = (AppCompatSeekBar) view;
                seekBar.setProgress(progress);
            }

            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(AppCompatSeekBar.class);
            }
        };
    }

    public boolean viewWithIdExists(int id) {
        try {
            onView(withId(id)).check(matches(isDisplayed()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Activity getActivityInstance() {
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity = (Activity) resumedActivities.iterator().next();
                }
            }
        });

        return currentActivity;
    }
}

