package com.soldatov.mycookbook.ingredient_user;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.soldatov.mycookbook.R;
import com.soldatov.mycookbook.repo.database.IngredientListEntity;
import com.soldatov.mycookbook.repo.database.IngredientListUserEntity;
import com.soldatov.mycookbook.utils.ViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class IngredientUserFragment extends Fragment {

    @BindView(R.id.viewIngredientUserList)
    RecyclerView ingredientUserList;
    @BindView(R.id.viewEmptyUserList)
    TextView viewEmptyUserList;
    @BindView(R.id.addNewIngredient)
    ImageView addNewIngredient;
    @BindView(R.id.searchRecipes)
    Button searchRecipes;

    private Unbinder unbinder;
    private IngredientUserFragmentViewModel viewModel;
    private OnAddBtnClickListener onAddBtnClickListener;
    private OnSearchRecipesClickListener onSearchRecipesClickListener;
    private IngredientsUserAdapter adapter;
    private IngredientListUserEntity deletedIngredient;
    private int deletedPosition;

    public interface OnAddBtnClickListener {
        void onAddBtnClick();
    }
    public interface OnSearchRecipesClickListener {
        void onSearchRecipesClick(List<IngredientListUserEntity> ingredients);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAddBtnClickListener) {
            onAddBtnClickListener = (OnAddBtnClickListener) context;
        }
        if (context instanceof OnAddBtnClickListener) {
            onSearchRecipesClickListener = (OnSearchRecipesClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (onAddBtnClickListener != null) {
            onAddBtnClickListener = null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory viewModelFactory = new ViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(IngredientUserFragmentViewModel.class);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel.getLiveData().observe(
                getViewLifecycleOwner(),
                ingredientListUserEntities -> showIngredientUserList(ingredientListUserEntities)
        );
        viewModel.getNewIngredientId().observe(getViewLifecycleOwner(), aLong -> addDeletedIngredient(aLong));
        return inflater.inflate(R.layout.fragment_ingredient_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        addNewIngredient.setOnClickListener(v -> {
            if (onAddBtnClickListener != null) {
                onAddBtnClickListener.onAddBtnClick();
            }
        });
        searchRecipes.setOnClickListener(v -> {
            if ((onSearchRecipesClickListener != null)) {
                adapter = (IngredientsUserAdapter) ingredientUserList.getAdapter();
                if (adapter != null) {
                    List<IngredientListUserEntity> ingredientListUserEntities = adapter.getCheckedIngredients();
                    if (ingredientListUserEntities.size() != 0) {
                        onSearchRecipesClickListener.onSearchRecipesClick(ingredientListUserEntities);
                    } else {
                        Toast.makeText(getContext(), "Select at least one item", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        viewModel.fetchIngredients();
        showIngredientUserList(viewModel.getLiveData().getValue());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void showIngredientUserList(List<IngredientListUserEntity> ingredientListUserEntity) {
        checkEmptyList(ingredientListUserEntity);
        adapter = new IngredientsUserAdapter(ingredientListUserEntity);
        ingredientUserList.setAdapter(adapter);
        ingredientUserList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(ingredientUserList);
    }

    private void addDeletedIngredient(long id) {
        if (deletedIngredient != null){
            deletedIngredient.setId(id);
            adapter = (IngredientsUserAdapter) ingredientUserList.getAdapter();
            if (adapter != null) {
                adapter.addIngredientByPosition(deletedPosition, deletedIngredient);
                checkEmptyList(adapter.getAllIngredientsUser());
            }
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            adapter = (IngredientsUserAdapter) ingredientUserList.getAdapter();
            if (adapter != null) {
                viewModel.deleteIngredient(adapter.getIngredientByPosition(position).getId());
                deletedIngredient = adapter.removeIngredientByPosition(position);
                deletedPosition = position;
                adapter.notifyItemRemoved(position);
                String name = deletedIngredient.getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                Snackbar.make(ingredientUserList, name, Snackbar.LENGTH_LONG)
                        .setAction("Undo", v -> viewModel.addUserIngredient(deletedIngredient)).show();
                checkEmptyList(adapter.getAllIngredientsUser());
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed))
                    .addActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();
        }
    };

    private void checkEmptyList(List<IngredientListUserEntity> ingredientListUserEntity) {
        if (ingredientListUserEntity == null || ingredientListUserEntity.size() == 0) {
            viewEmptyUserList.setVisibility(View.VISIBLE);
        } else {
            viewEmptyUserList.setVisibility(View.INVISIBLE);
        }
    }

    public class IngredientsUserAdapter extends RecyclerView.Adapter<IngredientsUserAdapter.IngredientUserViewHolder> {

        private List<IngredientListUserEntity> ingredientUserList;
        private ArrayList<IngredientListUserEntity> checkedIngredients = new ArrayList<>();

        public IngredientsUserAdapter(List<IngredientListUserEntity> ingredientUserList) {
            this.ingredientUserList = ingredientUserList;
        }

        @NonNull
        @Override
        public IngredientUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient_list, parent, false);
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

        public List<IngredientListUserEntity> getCheckedIngredients() {
            return checkedIngredients;
        }

        public List<IngredientListUserEntity> getAllIngredientsUser(){
            return ingredientUserList;
        }

        public IngredientListUserEntity getIngredientByPosition(int position) {
            return ingredientUserList.get(position);
        }

        public void addIngredientByPosition(int position, IngredientListUserEntity ingredientListEntity){
            this.ingredientUserList.add(position, ingredientListEntity);
            notifyItemInserted(position);
        }

        public IngredientListUserEntity removeIngredientByPosition(int position) {
            return this.ingredientUserList.remove(position);
        }

        public class IngredientUserViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.ingredientImage)
            ImageView ingredientImage;
            @BindView(R.id.ingredientName)
            TextView ingredientName;
            @BindView(R.id.checkIngredient)
            CheckBox checkIngredient;

            public IngredientUserViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bindData(IngredientListUserEntity ingredientListUserEntity) {
                Picasso.get().load(ingredientListUserEntity.getImageUrl()).into(ingredientImage);
                String name = ingredientListUserEntity.getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                ingredientName.setText(name);
                checkIngredient.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        checkedIngredients.add(ingredientListUserEntity);
                    } else {
                        checkedIngredients.remove(ingredientListUserEntity);
                    }
                });
                itemView.setOnClickListener(v -> checkIngredient.setChecked(!checkIngredient.isChecked()));
            }
        }
    }
}
