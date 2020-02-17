package com.soldatov.mycookbook.ingredient_user;

import android.content.Context;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soldatov.mycookbook.R;
import com.soldatov.mycookbook.repo.database.IngredientListUserEntity;
import com.soldatov.mycookbook.utils.ViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IngredientUserFragment extends Fragment {

    @BindView(R.id.viewIngredientUserList)
    RecyclerView ingredientUserList;

    @BindView(R.id.viewEmptyUserList)
    TextView viewEmptyUserList;

    @BindView(R.id.addNewIngredient)
    FloatingActionButton addNewIngredient;

    private Unbinder unbinder;
    private IngredientUserFragmentViewModel viewModel;
    private OnAddBtnClickListener onAddBtnClickListener;

    public interface OnAddBtnClickListener{
        void onAddBtnClick();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAddBtnClickListener){
            onAddBtnClickListener = (OnAddBtnClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (onAddBtnClickListener != null){
            onAddBtnClickListener = null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory viewModelFactory = new ViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(IngredientUserFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel.getLiveData().observe(
                getViewLifecycleOwner(),
                ingredientListUserEntities -> showIngredientUserList(ingredientListUserEntities)
        );
        return inflater.inflate(R.layout.fragment_ingredient_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        addNewIngredient.setOnClickListener(v -> {
            if (onAddBtnClickListener != null){
                onAddBtnClickListener.onAddBtnClick();
            }
        });
        viewModel.fetchIngredients();
        showIngredientUserList(viewModel.getLiveData().getValue());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null){
            unbinder.unbind();
        }
    }

    private void showIngredientUserList(List<IngredientListUserEntity> ingredientListUserEntity){
        if (ingredientListUserEntity == null || ingredientListUserEntity.size() == 0){
            viewEmptyUserList.setVisibility(View.VISIBLE);
        } else {
            viewEmptyUserList.setVisibility(View.INVISIBLE);
        }
        IngredientsUserAdapter adapter = new IngredientsUserAdapter(ingredientListUserEntity);
        ingredientUserList.setAdapter(adapter);
    }

    public class IngredientsUserAdapter extends RecyclerView.Adapter<IngredientsUserAdapter.IngredientUserViewHolder>{

        private List<IngredientListUserEntity> ingredientUserList;

        public IngredientsUserAdapter(List<IngredientListUserEntity> ingredientUserList) {
            this.ingredientUserList = ingredientUserList;
        }

        @NonNull
        @Override
        public IngredientUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient_user_list, parent, false);
            return new IngredientUserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull IngredientUserViewHolder holder, int position) {
            holder.bindData(ingredientUserList.get(position));
        }

        @Override
        public int getItemCount() {
            return ingredientUserList != null ? ingredientUserList.size() : 0;
        }

        public class IngredientUserViewHolder extends RecyclerView.ViewHolder{

            @BindView(R.id.ingredientImage)
            ImageView ingredientImage;
            @BindView(R.id.ingredientName)
            TextView ingredientName;

            public IngredientUserViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bindData(IngredientListUserEntity ingredientListUserEntity){
                Picasso.get().load(ingredientListUserEntity.getImageUrl()).into(ingredientImage);
                ingredientName.setText(ingredientListUserEntity.getName());
            }
        }
    }
}
