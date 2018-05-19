package com.example.alanbarrera.ordinario_paty;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class SaleAdapterListenerActivity extends AppCompatActivity implements IRecyclerviewClickListener
    {
        protected boolean isMultipleSelectionActivaded = false;
        protected ArrayList<Integer> selectedItemsIndexes = new ArrayList();
        protected HashMap<Integer, View> selectedViews = new HashMap<>();

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

            // Create your application here
        }

        
        //Implemented methods
        public boolean IsSelected(int position)
        {
            if (selectedItemsIndexes.contains(position))
                return true;
            return false;
        }

        public void OnClick(View view, int position)
        {
            if (isMultipleSelectionActivaded)
            {
                if (selectedItemsIndexes.contains(position))
                {
                    view.setBackgroundColor(Color.WHITE);
                    selectedItemsIndexes.remove(position);
                    selectedViews.remove(position);

                    if (selectedItemsIndexes.size() == 0)
                    {
                        isMultipleSelectionActivaded = false;
                        ChangeUI();
                    }
                }
                else
                {
                    view.setBackgroundColor(Color.GREEN);
                    selectedItemsIndexes.add(position);
                    selectedViews.put(position, view);
                }
            }
            else
            {
                OnSingleSelectionClick(position);
            }
        }

        public void OnLongClick(View view, int position)
        {
            if (!isMultipleSelectionActivaded)
            {
                isMultipleSelectionActivaded = true;
                OnClick(view, position);
                ChangeUI();
            }
            
        }

        protected void DeleteSelectedItems()
        {
            for (int index : selectedItemsIndexes)
                selectedViews.get(index).setBackgroundColor(Color.WHITE);

            //Delete data
            selectedViews.clear();
            selectedItemsIndexes.clear();
            isMultipleSelectionActivaded = false;
        }

        public abstract void OnSingleSelectionClick();

        public abstract void ChangeUI();
    }
