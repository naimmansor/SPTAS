package com.sptas.sptasv2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sptas.sptasv2.Common.Common;
import com.sptas.sptasv2.Interface.ItemClickListener;
import com.sptas.sptasv2.Lecturer.ChapterBank;
import com.sptas.sptasv2.Model.Category;
import com.sptas.sptasv2.Student.ChapterDetail;
import com.sptas.sptasv2.Student.Start;
import com.sptas.sptasv2.ViewHolder.CategoryViewHolder;
import com.squareup.picasso.Picasso;


public class CategoryFragment extends Fragment {

    View myFragment;

    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference categories;

    public static CategoryFragment newInstance() {
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;
    }

    //Press ctrl + O

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Category");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.student_fragment_category, container, false);

        listCategory = myFragment.findViewById(R.id.listCategory);
        listCategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listCategory.setLayoutManager(layoutManager);

        loadCategories();

        return myFragment;
    }

    private void loadCategories() {
        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(
                Category.class,
                R.layout.student_category_layout,
                CategoryViewHolder.class,
                categories
        ) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {
                viewHolder.category_name.setText(model.getName());
                Picasso.with(getActivity())
                        .load(model.getImage())
                        .into(viewHolder.category_image);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(getActivity(), String.format("%d|%s", adapter.getRef(position).getKey(), model.getName()), Toast.LENGTH_SHORT).show();

                        if (Common.currentUser.getUserType().equals("Lecturer")) {
                            Intent startChapter = new Intent(getActivity(), ChapterBank.class);
                            startActivity(startChapter);
                        } else if (Common.currentUser.getUserType().equals("Student")) {

                            if (model.getName().equals("Data Structure")) {
                                Intent startChapter = new Intent(getActivity(), ChapterDetail.class);
                                Common.categoryId = adapter.getRef(position).getKey();
                                Common.categoryName = model.getName();
                                startActivity(startChapter);
                            } else if (model.getName().equals("Software Engineering")) {
                                Intent startChapter = new Intent(getActivity(), Start.class);
                                Common.categoryId = adapter.getRef(position).getKey();
                                Common.chapterId = model.getChapterId();
                                Common.categoryName = model.getName();
                                startActivity(startChapter);
                            }
                        }
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);
    }
}
