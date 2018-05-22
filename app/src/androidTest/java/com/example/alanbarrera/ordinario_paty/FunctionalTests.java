package com.example.alanbarrera.ordinario_paty;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.alanbarrera.ordinario_paty.logic.Constants;
import com.example.alanbarrera.ordinario_paty.logic.DATA;
import com.example.alanbarrera.ordinario_paty.logic.ESaleStatus;
import com.example.alanbarrera.ordinario_paty.logic.SaleRestaurant;
import com.example.alanbarrera.ordinario_paty.logic.SingleOrderViewModel;

import java.util.UUID;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FunctionalTests {
    @Rule
    public ActivityTestRule<ActivityDeliverOrders> deliverOrdersRule = new ActivityTestRule<>(
            ActivityDeliverOrders.class, true, true);

    @Rule
    public ActivityTestRule<ActivityUnassignedOrders> unassignedOrdersRule = new ActivityTestRule<>(
            ActivityUnassignedOrders.class, true, true);

    @Rule
    public ActivityTestRule<ActivitySaleDetail> saleDetailRule = new ActivityTestRule<>(
            ActivitySaleDetail.class, true, false);


    @Before
    public void PrepareData() {
        DATA.Prepare();
    }

    @Test
    public void canDeliverOrder() {
        for (int i = 0; i < 5; i++) {
            DATA.SaleRestaurants.get(i).DeliveryManId = Constants.GUID_DELIVERYMAN_ALAN;
            DATA.SaleRestaurants.get(i).ESaleStatus = ESaleStatus.INTRANSIT;
        }

        Intent intent = new Intent();
        deliverOrdersRule.launchActivity(intent);
        onView(withId(R.id.mark_as_delivered)).perform(click());

        for (int i = 0; i < 5; i++) {
            assertThat(DATA.SaleRestaurants.get(i).ESaleStatus, is(ESaleStatus.DELIVERED));
        }
    }

    @Test
    public void canAssignOrder() {
        UUID assignedSaleId = UUID.fromString("91494320-cbdd-4089-8ebe-6f0561c8d9bb");
        Intent intent = new Intent();
        unassignedOrdersRule.launchActivity(intent);
        onView(withId(R.id.unassigned_orders_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
        onView(withId(R.id.assign_orders)).perform(click());

        for (SaleRestaurant sale : DATA.SaleRestaurants) {
            if (sale.SaleRestaurantId.equals(assignedSaleId)) {
                assertThat(sale.DeliveryManId, is(Constants.GUID_DELIVERYMAN_ALAN));
                assertThat(sale.ESaleStatus, is(ESaleStatus.CONFIRMED));
                break;
            }
        }
    }

    @Test
    public void isCallButtonDisabled() {
        SingleOrderViewModel order = new SingleOrderViewModel(DATA.SaleRestaurants.get(7), DATA.DinerAddresses.get(7));
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_DELIVERY_ORDER, Utils.objectToString(order));
        saleDetailRule.launchActivity(intent);

        onView(withId(R.id.buttonContactar)).check(matches(not(isEnabled())));
    }
}