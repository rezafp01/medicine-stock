package ms.paket.medicinestock.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import ms.paket.medicinestock.R;
import ms.paket.medicinestock.adapter.FireBaseViewHolder;
import ms.paket.medicinestock.adapter.GridObatAdapter;
import ms.paket.medicinestock.model.Obat;

public class ApotekActivity extends AppCompatActivity {

    private RecyclerView rvobat;
    private ArrayList<Obat> list = new ArrayList<>();
    private FirebaseRecyclerOptions<Obat> options;
    private FirebaseRecyclerAdapter<Obat, FireBaseViewHolder> adapter;
    private DatabaseReference databaseReference;
    private TextView tvNoData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apotek);

        rvobat = findViewById(R.id.rv_obat);
        tvNoData = findViewById(R.id.tv_noData);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Obat");
        databaseReference.keepSynced(true);

        String ap = getIntent().getStringExtra("apotek");
        String search = getIntent().getStringExtra("namaObat");

        if(ap != null){
            setActionBarTitle("Apotek "+ap);
            showApotek(ap);
        }else{
            setActionBarTitle("'"+search+"'");
            searchData(search);
        }
    }

    private void setActionBarTitle(String title){
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);
        }


}

    private void searchData(String str) {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Obat");
        mRef.orderByChild("namaObat")
                .startAt(str)
                .endAt(str+"\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            tvNoData.setText(null);
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                list.add(ds.getValue(Obat.class));
                            }
                            GridObatAdapter gridObatAdapter = new GridObatAdapter(list,ApotekActivity.this);
                            rvobat.setLayoutManager(new GridLayoutManager(ApotekActivity.this, 2));
                            rvobat.setAdapter(gridObatAdapter);
                        }else{
                            list.clear();
                            GridObatAdapter gridObatAdapter = new GridObatAdapter(list,ApotekActivity.this);
                            rvobat.setLayoutManager(new GridLayoutManager(ApotekActivity.this, 2));
                            rvobat.setAdapter(gridObatAdapter);
                            String msg = getResources().getString(R.string.no_Obat_found);
                            tvNoData.setText(msg);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void showApotek(String str) {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Obat");
        mRef.orderByChild("apotek")
                .startAt(str)
                .endAt(str + "\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Obat> myList = new ArrayList<>();
                        if (dataSnapshot.exists()) {
                            tvNoData.setText(null);
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                myList.add(ds.getValue(Obat.class));
                            }
                            GridObatAdapter gridObatAdapter = new GridObatAdapter(myList , ApotekActivity.this);
                            rvobat.setLayoutManager(new GridLayoutManager(ApotekActivity.this, 2));
                            rvobat.setAdapter(gridObatAdapter);
                        }else{
                            String msg = getResources().getString(R.string.no_Obat_found);
                            tvNoData.setText(msg);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint("Nama Obat");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchData(query);
                    setActionBarTitle("'"+query+"'");
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    //searchData(newText);
                    return false;
                }
            });
        } else {
            //showData();
        }


        return true;
    }



}
