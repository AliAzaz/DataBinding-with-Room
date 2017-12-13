package com.example.ali.roomapplication.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ali.roomapplication.R;
import com.example.ali.roomapplication.core.DatabaseHelper;
import com.example.ali.roomapplication.persistence.core.dbClass;
import com.example.ali.roomapplication.persistence.model.DirectoryModel;
import com.example.ali.roomapplication.databinding.ActivityLstviewBinding;
import com.example.ali.roomapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    ActivityMainBinding binding;
    dbClass db;
    String email;
    Boolean flagRecord = true;

    DirectoryModel selectData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setCallback(this);
        binding.setData(null);

//        Get roomDB
        DatabaseHelper database = new DatabaseHelper(getApplicationContext());
        db = database.ReturnDB();

        new GetAllData(this).execute();

    }

    private void clearFields() {
        binding.txtName.setText(null);
        binding.txtEmail.setText(null);
        binding.txtContact.setText(null);
    }

    //    Save button click
    public void SaveDT() {
        if (formValidation()) {
            try {
                if (flagRecord) {
                    if(!new CrudOperation(this, "save").execute().get()){
                        binding.txtEmail.setError("Already Exist");
                        Toast.makeText(this, "Email already exist!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    new CrudOperation(this, "update").execute();
                }
            } catch (Exception ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //    Clear button click
    public void ClearDT() {

        clearFields();

        binding.btnDelete.setVisibility(View.GONE);
        binding.btnClear.setVisibility(View.GONE);
    }

    //    Delete button click
    public void DeleteDT() {
        new CrudOperation(this, "delete").execute();

        binding.btnClear.setVisibility(View.GONE);
        binding.btnDelete.setVisibility(View.GONE);
    }

    public Boolean formValidation() {

        if (binding.txtName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "ERROR(empty): Name required", Toast.LENGTH_SHORT).show();
            binding.txtName.setError("This data is Required! ");    // Set Error on last radio button
            binding.txtName.requestFocus();
            Log.i(TAG, "txtName: This data is Required!");
            return false;
        } else {
            binding.txtName.setError(null);
        }

        if (binding.txtEmail.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "ERROR(empty): Email required", Toast.LENGTH_SHORT).show();
            binding.txtEmail.setError("This data is Required! ");    // Set Error on last radio button
            binding.txtEmail.requestFocus();
            Log.i(TAG, "txtEmail: This data is Required!");
            return false;
        } else {
            binding.txtEmail.setError(null);
        }

        if (binding.txtContact.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(empty): Contact No required", Toast.LENGTH_SHORT).show();
            binding.txtContact.setError("This data is Required! ");    // Set Error on last radio button
            binding.txtContact.requestFocus();
            Log.i(TAG, "txtContact: This data is Required!");
            return false;
        } else {
            binding.txtContact.setError(null);
        }
        return true;
    }

    private class GetAllData extends AsyncTask<Void, Void, Void> {

        ArrayList<DirectoryModel> detail;
        Context mContext;

        public GetAllData(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {
            detail = new ArrayList<>();

            for (DirectoryModel model : db.daoAccess().getAllData()) {
                detail.add(new DirectoryModel(model.getName().toUpperCase(), model.getEmail().toLowerCase(), model.getContactNo()));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //To after addition operation here.

            binding.lstNames.setAdapter(new ContactAdapter(detail));
        }
    }

    private class CrudOperation extends AsyncTask<Void, Boolean, Boolean> {

        Context mContext;
        String operation = "";

        public CrudOperation(Context mContext, String operation) {
            this.mContext = mContext;
            this.operation = operation;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            if (operation.equals("save")) {
                if (!db.daoAccess().checkContactExist(binding.txtEmail.getText().toString())) {
                    db.daoAccess().insertNewDirectory(new DirectoryModel(binding.txtName.getText().toString()
                            , binding.txtEmail.getText().toString(),
                            binding.txtContact.getText().toString()));
                }else {
                    return false;
                }
            } else if (operation.equals("getspecificdata")) {
                selectData = new DirectoryModel(db.daoAccess().getSpecificData(email));
                binding.setData(selectData);
            } else if (operation.equals("update")) {
                db.daoAccess().updateNewDirectory(new DirectoryModel(selectData.get_id(), binding.txtName.getText().toString()
                        , binding.txtEmail.getText().toString(),
                        binding.txtContact.getText().toString()));
            } else if (operation.equals("delete")) {
                db.daoAccess().deleteDirectory(new DirectoryModel(selectData.get_id(), binding.txtName.getText().toString()
                        , binding.txtEmail.getText().toString(),
                        binding.txtContact.getText().toString()));
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            //To after addition operation here.

            if (operation.equals("save") || operation.equals("update") || operation.equals("delete")) {
                if (aVoid) {
                    clearFields();
                    flagRecord = true;
                    new GetAllData(mContext).execute();
                }
            } else if (operation.equals("getspecificdata")) {
                flagRecord = false;
            }
        }
    }

    public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
        private ArrayList<DirectoryModel> list;

        public ContactAdapter(ArrayList<DirectoryModel> list) {
            this.list = list;
        }

        @Override
        public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View statusContainer = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lstview, parent, false);
            return new ContactAdapter.ViewHolder(statusContainer);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int pos) {
            final DirectoryModel status = list.get(pos);
            holder.bindUser(status);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, list.get(pos).getName(), Toast.LENGTH_SHORT).show();

                    email = status.getEmail();

                    new CrudOperation(MainActivity.this, "getspecificdata").execute();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ActivityLstviewBinding binding;

            public ViewHolder(View itemView) {
                super(itemView);
                binding = DataBindingUtil.bind(itemView);
            }

            public void bindUser(DirectoryModel contact) {
                binding.name.setText(contact.getName());
                binding.email.setText(contact.getEmail());
                binding.contact.setText(contact.getContactNo());
            }
        }
    }

}
