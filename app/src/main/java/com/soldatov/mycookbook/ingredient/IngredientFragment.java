package com.soldatov.mycookbook.ingredient;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soldatov.mycookbook.R;
import com.soldatov.mycookbook.repo.database.IngredientListEntity;
import com.soldatov.mycookbook.repo.database.IngredientListUserEntity;
import com.soldatov.mycookbook.utils.OnCloseFragmentClickListener;
import com.soldatov.mycookbook.utils.ViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IngredientFragment extends Fragment {

    @BindView(R.id.viewIngredientList)
    RecyclerView viewIngredientList;
    @BindView(R.id.search)
    SearchView searchIngredient;
    @BindView(R.id.btnAddIngredients)
    Button btnAddIngredients;
    @BindView(R.id.ingredientToolbar)
    Toolbar ingredientToolbar;

    private Unbinder unbinder;
    private IngredientFragmentViewModel viewModel;
    private IngredientsAdapter adapter;
    private OnCloseFragmentClickListener onCloseFragmentClickListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCloseFragmentClickListener) {
            onCloseFragmentClickListener = (OnCloseFragmentClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (onCloseFragmentClickListener != null){
            onCloseFragmentClickListener = null;
        }
    }

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

        searchIngredient.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchIngredient.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter = (IngredientsAdapter) viewIngredientList.getAdapter();
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        btnAddIngredients.setOnClickListener(v -> {
            adapter = (IngredientsAdapter) viewIngredientList.getAdapter();
            List<IngredientListEntity> checkedIngredients = adapter != null ? adapter.getCheckedIngredients() : null;
            ArrayList<IngredientListUserEntity> addUserIngredients = new ArrayList<>();
            if (checkedIngredients != null) {
                for (IngredientListEntity ingredient: checkedIngredients){
                    IngredientListUserEntity newIngredient = new IngredientListUserEntity(ingredient.getId(), ingredient.getName(), ingredient.getImageUrl());
                    addUserIngredients.add(newIngredient);
                }
                viewModel.addUserIngredients(addUserIngredients);
            }
            onCloseFragmentClickListener.onCloseFragmentClick();
        });

        ingredientToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        ingredientToolbar.setNavigationOnClickListener(v -> onCloseFragmentClickListener.onCloseFragmentClick());

        viewModel.fetchIngredients();
        showIngredientList(viewModel.getLiveData().getValue());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void showIngredientList(List<IngredientListEntity> ingredientListEntities) {
        adapter = new IngredientsAdapter(ingredientListEntities);
        viewIngredientList.setAdapter(adapter);
        viewIngredientList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> implements Filterable {

        private List<IngredientListEntity> ingredientList;
        private List<IngredientListEntity> ingredientListFull;
        private ArrayList<IngredientListEntity> checkedIngredients = new ArrayList<>();

        public IngredientsAdapter(List<IngredientListEntity> ingredientList) {
            this.ingredientList = ingredientList;
            if (ingredientList != null) {
                this.ingredientListFull = new ArrayList<>(ingredientList);
            }
        }

        public List<IngredientListEntity> getCheckedIngredients() {
            return checkedIngredients;
        }

        @NonNull
        @Override
        public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient_list, parent, false);
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

        @Override
        public Filter getFilter() {
            return listFilter;
        }

        private Filter listFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<IngredientListEntity> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(ingredientListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (IngredientListEntity ingredient : ingredientListFull) {
                        if (ingredient.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(ingredient);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (ingredientList != null) {
                    ingredientList.clear();
                    ingredientList.addAll((List) results.values);
                    notifyDataSetChanged();
                }
            }
        };

        public class IngredientViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.ingredientImage)
            ImageView ingredientImage;
            @BindView(R.id.ingredientName)
            TextView ingredientName;
            @BindView(R.id.checkIngredient)
            CheckBox checkIngredient;

            public IngredientViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bindData(IngredientListEntity ingredientListEntity) {
                Picasso.get().load(ingredientListEntity.getImageUrl()).into(ingredientImage);
                String name = ingredientListEntity.getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                ingredientName.setText(name);
                checkIngredient.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        checkedIngredients.add(ingredientListEntity);
                    } else {
                        checkedIngredients.remove(ingredientListEntity);
                    }
                });
                itemView.setOnClickListener(v -> checkIngredient.setChecked(!checkIngredient.isChecked()));
            }
        }
    }
}
