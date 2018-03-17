package com.sptas.sptasv2.Lecturer;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.sptas.sptasv2.Model.Test;
import com.sptas.sptasv2.R;

import java.util.List;

/**
 * Created by Na'im Mansor on 16-Mar-18.
 */

public class TestList extends ArrayAdapter<Test> {

    DatabaseReference databaseReference;
    EditText edtName;
    private Activity context;
    private List<Test> tests;

    public TestList(@NonNull Activity context, List<Test> tests, DatabaseReference databaseReference, EditText edtName) {
        super(context, R.layout.lecturer_question_bank_layout, tests);
        this.context = context;
        this.tests = tests;
        this.databaseReference = databaseReference;
        this.edtName = edtName;
    }

    public View getView(int pos, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.lecturer_question_bank_layout, null, true);

        TextView txtName = (TextView) listViewItem.findViewById(R.id.txtName);
        Button btnDelete = (Button) listViewItem.findViewById(R.id.btnDelete);
        Button btnUpdate = (Button) listViewItem.findViewById(R.id.btnUpdate);

        final Test test = tests.get(pos);
        txtName.setText(test.getName());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child(test.getId()).removeValue();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtName.setText(test.getName());
                QuestionBank.userId = test.getId();
            }
        });

        return listViewItem;
    }
}
