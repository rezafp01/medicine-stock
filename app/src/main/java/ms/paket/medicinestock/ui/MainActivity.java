package ms.paket.medicinestock.ui;

import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


import ms.paket.medicinestock.R;
import ms.paket.medicinestock.adapter.ApotekAdapter;
import ms.paket.medicinestock.adapter.GridObatAdapter;
import ms.paket.medicinestock.model.Apotek;
import ms.paket.medicinestock.model.Obat;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvObat;
    private RecyclerView rvApotek;
    public ArrayList<Obat> listObat = new ArrayList<>();
    public ArrayList<Apotek> listApotek = new ArrayList<>();
    private GridObatAdapter gridObatAdapter;
    private DatabaseReference database;
    private ProgressDialog loading;
    SearchView searchView;

    SwipeRefreshLayout swipeRefreshLayout;
    private TypedArray dataPhoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Medicine Stok");
        }



        database = FirebaseDatabase.getInstance().getReference();

        rvObat = findViewById(R.id.rv_obat);
        rvObat.setHasFixedSize(true);
        rvApotek = findViewById(R.id.rv_apotek);
        rvApotek.setHasFixedSize(true);
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if(!TextUtils.isEmpty(s)){
                    searchData(s);
                } else{
                    showData();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(!TextUtils.isEmpty(s)){
                    searchData(s);
                } else{
                    showData();
                }

                return false;
            }
        });

        loading = ProgressDialog.show(MainActivity.this,
                null,
                "please wait ....",
                true,
                false);

        listApotek.clear();
        listApotek.addAll(getListData());

        showData();
        showApotek();



    }

    public void showData(){
        database.child("Obat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listObat = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                    Obat obat = noteDataSnapshot.getValue(Obat.class);
                    obat.setKey(noteDataSnapshot.getKey());
                    listObat.add(obat);
                }

                gridObatAdapter = new GridObatAdapter(listObat,MainActivity.this);
                rvObat.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                rvObat.setAdapter(gridObatAdapter);
                loading.dismiss();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
                loading.dismiss();
            }
        });

    }

    public void searchData(final String str) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Obat");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listObat.clear();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                    Obat obat = (noteDataSnapshot.getValue(Obat.class));
                    obat.setKey(noteDataSnapshot.getKey());

                    if(obat.getNamaObat().toLowerCase().contains(str.toLowerCase())) {
                        listObat.add(obat);
                    }

                }
                gridObatAdapter = new GridObatAdapter(listObat,MainActivity.this);
                rvObat.setAdapter(gridObatAdapter);
                loading.dismiss();

            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });


    }

    private void showApotek(){
        rvApotek.setLayoutManager(new GridLayoutManager(MainActivity.this,4));
        ApotekAdapter adapter = new ApotekAdapter(listApotek);
        rvApotek.setAdapter(adapter);
    }

    private ArrayList<Apotek> getListData() {
        String[] dataCategory = getResources().getStringArray(R.array.category_name);
        dataPhoto = getResources().obtainTypedArray(R.array.category_img);

        ArrayList<Apotek> listData = new ArrayList<>();
        for (int i = 0; i < dataCategory.length; i++) {
            Apotek apotek = new Apotek();
            apotek.setName(dataCategory[i]);
            apotek.setImg(dataPhoto.getResourceId(i, -1));

            listData.add(apotek);

        }

        return listData;

    }





}
