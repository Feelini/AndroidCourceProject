package com.soldatov.mycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.soldatov.mycookbook.ingredient.IngredientFragment;
import com.soldatov.mycookbook.ingredient_user.IngredientUserFragment;

public class MainActivity extends AppCompatActivity implements IngredientUserFragment.OnAddBtnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addIngredientUserFragment();
    }

    private void addIngredientFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new IngredientFragment(), IngredientFragment.class.getName())
                .addToBackStack(null)
                .commit();
    }

    private void addIngredientUserFragment(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new IngredientUserFragment(), IngredientUserFragment.class.getName())
                .commit();
    }

    @Override
    public void onAddBtnClick() {
        addIngredientFragment();
    }
}
