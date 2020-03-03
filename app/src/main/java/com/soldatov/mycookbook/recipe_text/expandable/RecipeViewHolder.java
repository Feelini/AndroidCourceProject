package com.soldatov.mycookbook.recipe_text.expandable;

import android.content.Context;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.soldatov.mycookbook.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class RecipeViewHolder extends GroupViewHolder {

    @BindView(R.id.groupTitle)
    public TextView groupTitle;
    @BindView(R.id.groupArrow)
    public ImageView groupArrow;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setGroupTitle(String title, int itemCount, Context context){
        groupTitle.setText(title);
        if (itemCount == 0){
            groupTitle.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        groupArrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        groupArrow.setAnimation(rotate);
    }
}
