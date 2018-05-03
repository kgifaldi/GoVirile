package com.example.kylegifaldi.atry;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kylegifaldi on 4/23/18.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {



        private List<Ingredient> ingredientList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, genre;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                genre = (TextView) view.findViewById(R.id.genre);
            }
        }


        public IngredientAdapter(List<Ingredient> ingredientList) {
            this.ingredientList = ingredientList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredient_list_row, parent, false);

            return new MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Ingredient ing = ingredientList.get(position);
            holder.title.setText(ing.getTitle());
            holder.genre.setText(ing.getGenre());
        }

        @Override
        public int getItemCount() {
            return ingredientList.size();
        }
}
