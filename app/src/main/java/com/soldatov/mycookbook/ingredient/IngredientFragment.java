package com.soldatov.mycookbook.ingredient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.soldatov.mycookbook.R;
import com.soldatov.mycookbook.repo.database.IngredientListEntity;
import com.soldatov.mycookbook.utils.ViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IngredientFragment extends Fragment {

    @BindView(R.id.viewIngredientList)
    RecyclerView ingredientList;

    private Unbinder unbinder;
    private IngredientFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory viewModelFactory = new ViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(IngredientFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel.getLiveData().observe(
                getViewLifecycleOwner(),
                ingredientListEntities -> showIngredientList(ingredientListEntities)
        );
        return inflater.inflate(R.layout.fragment_ingredient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        viewModel.fetchIngredients();
        showIngredientList(viewModel.getLiveData().getValue());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null){
            unbinder.unbind();
        }
    }

    private void showIngredientList(List<IngredientListEntity> ingredientListEntities){
        IngredientsAdapter adapter = new IngredientsAdapter(ingredientListEntities);
        ingredientList.setAdapter(adapter);
    }

    public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>{

        private List<IngredientListEntity> ingredientList;

        public IngredientsAdapter(List<IngredientListEntity> ingredientList) {
            this.ingredientList = ingredientList;
        }

        @NonNull
        @Override
        public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient_user_list, parent, false);
            return new IngredientViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
            holder.bindData(ingredientList.get(position));
        }

        @Override
        public int getItemCount() {
            return ingredientList != null ? ingredientList.size() : 0;
        }

        public class IngredientViewHolder extends RecyclerView.ViewHolder{

            @BindView(R.id.ingredientImage)
            ImageView ingredientImage;
            @BindView(R.id.ingredientName)
            TextView ingredientName;

            public IngredientViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bindData(IngredientListEntity ingredientListEntity){
                Picasso.get().load(ingredientListEntity.getImageUrl()).into(ingredientImage);
                ingredientName.setText(ingredientListEntity.getName());
            }
        }
    }
}
