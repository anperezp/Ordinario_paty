package com.example.alanbarrera.ordinario_paty;

import com.example.alanbarrera.ordinario_paty.logic.SaleDetails;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnitTests
{

    private static final String DETAILS = "ALL_DETAILS";
    private static final int ITEM_COUNT = 7;
    private static final boolean SELECTED = true;

    private Random random = new Random();

    @Mock
    private ActivitySaleDetail activitySaleDetail;

    @Mock
    private SaleAdapter saleAdapter;

    @Mock
    private SaleAdapterListenerActivity saleAdapterListenerActivity;

    @org.junit.Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void canGetDetails()
    {
        when(activitySaleDetail.GetAllDetails(any(ArrayList.class))).thenReturn(DETAILS);
        assertThat(activitySaleDetail.GetAllDetails(new ArrayList<SaleDetails>()), is(DETAILS));
    }

    @Test
    public void canGetItemCount()
    {
        when(saleAdapter.getItemCount()).thenReturn(ITEM_COUNT);
        assertThat(saleAdapter.getItemCount(), is(ITEM_COUNT));
    }

    @Test
    public void canItemsBeSelected()
    {
        when(saleAdapterListenerActivity.IsSelected(anyInt())).thenReturn(SELECTED);
        assertThat(saleAdapterListenerActivity.IsSelected(random.nextInt()), is(SELECTED));
    }
}
