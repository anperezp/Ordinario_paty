package com.example.alanbarrera.ordinario_paty;

import android.view.View;

public interface IRecyclerviewClickListener
{
    void OnLongClick(View view, int position);
    void OnClick(View view, int position);
    void OnSingleSelectionClick(int position);
    boolean IsSelected(int position); //Comprueba si algun item está seleccionado.
    void ChangeUI();
}