package ir.amin.personallibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private View parent_view;
    private RecyclerView recyclerView;
    private AdapterBookList adapterBookList;
    private DataBaseHelper dataBaseHelper;
    private ArrayList<Library> books;
    private FloatingActionButton actionButton;
    private androidx.appcompat.widget.SearchView searchView;
    private RadioButton showall, tarikhi, mazhabi, romanirani, romankharegi, sher, adabi, amozesh, elmi, pezeshki, english, moafaghiat, etelaat, varzesh, dastankotah, adabvarosom, zendeginame, sayer, lend, newest, booksearch, authorsearch, translatorsearch;
    private MaterialButton btnfilter;
    private TextView show_all, show_all_cases, show_lend;
    private ImageButton open_drawer, close_drawer, refresh, info;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial_component();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        dataBaseHelper = new DataBaseHelper(this);
        adapterBookList = new AdapterBookList(this, dataBaseHelper.getAllNote());
        recyclerView.setAdapter(adapterBookList);
        onClickForAll();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Information.class));
            }
        });

        btn_filter();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                books = dataBaseHelper.searchBook(newText);
                adapterBookList = new AdapterBookList(getApplicationContext(), books);
                recyclerView.setAdapter(adapterBookList);
                onClickForAll();
                return false;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 1 && actionButton.getVisibility() == View.VISIBLE) {
                    showOut(actionButton);
                } else if (dy < 0 && actionButton.getVisibility() != View.VISIBLE) {
                    showIn(actionButton);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        show_all.setText(String.valueOf(dataBaseHelper.getNumberOfBook()));
        show_all_cases.setText(String.valueOf(dataBaseHelper.getAll2()));
        show_lend.setText(String.valueOf(dataBaseHelper.getNumberOfLend()));

        open_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        close_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                dataBaseHelper = new DataBaseHelper(getApplicationContext());
                adapterBookList = new AdapterBookList(getApplicationContext(), dataBaseHelper.getAllNote());
                recyclerView.setAdapter(adapterBookList);
                Snackbar.make(parent_view, "کتابخانه بارگذاری مجدد شد", Snackbar.LENGTH_SHORT).show();
                onClickForAll();
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(MainActivity.this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.about);
                d.setCancelable(true);
                ImageButton imageButton = d.findViewById(R.id.close_dialog2);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.cancel();
                    }
                });
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.show();
            }
        });

    }

    public static void showOut(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(1f);
        v.setTranslationY(0);
        v.animate()
                .setDuration(200)
                .translationY(v.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                }).alpha(0f)
                .start();
    }

    public static void showIn(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(0f);
        v.setTranslationY(v.getHeight());
        v.animate()
                .setDuration(200)
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(1f)
                .start();
    }

    public void onClickForAll() {
        adapterBookList.setOnItemClickListener(new AdapterBookList.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final Library obj, int position) {
                final Dialog d = new Dialog(MainActivity.this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_show);
                d.setCancelable(true);
                ImageButton imageButton = d.findViewById(R.id.close_dialog);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.cancel();
                    }
                });
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView bookName = d.findViewById(R.id.dialog_bookName);
                TextView author = d.findViewById(R.id.dialog_author);
                TextView translator = d.findViewById(R.id.dialog_translator);
                TextView topic = d.findViewById(R.id.dialog_topic);
                TextView description = d.findViewById(R.id.dialog_description);
                TextView cases = d.findViewById(R.id.dialog_cases);
                TextView lent = d.findViewById(R.id.dialog_yes_no);
                TextView lentHow = d.findViewById(R.id.person_lent);

                bookName.setText(obj.getBookName());
                author.setText(obj.getAuthor());
                translator.setText(obj.getTranslator());
                topic.setText(obj.getTopic());
                description.setText(obj.getDescription());
                cases.setText(String.valueOf(obj.getCases()));
                if (obj.getLendCheck() == 1) {
                    lent.setText("بله");
                } else {
                    lent.setText("خیر");
                }
                lentHow.setText(obj.getLendName());
                MaterialButton btn_edit = d.findViewById(R.id.dialog_edit_btn);
                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), Information.class);
                        intent.putExtra("isEdit", true);
                        intent.putExtra("noteId", obj.getId());
                        startActivity(intent);
                    }
                });

                d.show();
            }
        });

    }

    public void initial_component() {
        romanirani = findViewById(R.id.romanIrani);
        romankharegi = findViewById(R.id.romanKharegi);
        dastankotah = findViewById(R.id.dastanKhotah);
        mazhabi = findViewById(R.id.mazhabi);
        tarikhi = findViewById(R.id.tarikhi);
        sher = findViewById(R.id.sheer);
        adabi = findViewById(R.id.adabi);
        amozesh = findViewById(R.id.amozeshi);
        elmi = findViewById(R.id.elmi);
        english = findViewById(R.id.zabanenglishi);
        moafaghiat = findViewById(R.id.ravanshenasi);
        etelaat = findViewById(R.id.etelaatOmomi);
        pezeshki = findViewById(R.id.pezeshki);
        zendeginame = findViewById(R.id.zendeginame);
        varzesh = findViewById(R.id.varzeshi);
        adabvarosom = findViewById(R.id.adabVaRosom);
        sayer = findViewById(R.id.sayer);
        lend = findViewById(R.id.amanatDade);
        newest = findViewById(R.id.jadidtarinha);
        booksearch = findViewById(R.id.jostjobarKetab);
        authorsearch = findViewById(R.id.jostjobarNevisandeh);
        translatorsearch = findViewById(R.id.jostjobarmotarjem);
        recyclerView = findViewById(R.id.recyclerView);
        actionButton = findViewById(R.id.add_new_book_btn);
        searchView = findViewById(R.id.search);
        btnfilter = findViewById(R.id.filter_btn);
        show_all = findViewById(R.id.all_book_show);
        show_all_cases = findViewById(R.id.all_book_show_with_cases);
        show_lend = findViewById(R.id.lend_show);
        open_drawer = findViewById(R.id.open_drawer);
        close_drawer = findViewById(R.id.btn_mainActivity_closeDrawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        refresh = findViewById(R.id.refresh);
        parent_view = findViewById(android.R.id.content);
        info = findViewById(R.id.info);

    }

    public void btn_filter() {
        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (romanirani.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("رمان ایرانی");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (romankharegi.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("رمان خارجی");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (dastankotah.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("داستان کوتاه");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (mazhabi.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("مذهبی");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (tarikhi.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("تاریخی");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (sher.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("شعر");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (adabi.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("ادبی");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (amozesh.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("آموزشی");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (elmi.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("علمی");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (english.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("زبان انگلیسی");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (moafaghiat.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("روانشناسی و موفقیت");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (etelaat.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("اطلاعات عمومی");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (pezeshki.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("پزشکی");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (zendeginame.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("زندگی نامه");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (varzesh.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("ورزشی");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (adabvarosom.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("آداب و رسوم");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (sayer.isChecked()) {
                    books = dataBaseHelper.getRomanKharegi("سایر");
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (lend.isChecked()) {
                    books = dataBaseHelper.getLended();
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (newest.isChecked()) {
                    books = dataBaseHelper.getNew();
                    adapterBookList = new AdapterBookList(getApplicationContext(), books);
                    recyclerView.setAdapter(adapterBookList);
                    onClickForAll();
                }
                if (translatorsearch.isChecked()) {
                    searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            books = dataBaseHelper.dependToSearch(dataBaseHelper.LIB_TRANSLATOR, s);
                            adapterBookList = new AdapterBookList(getApplicationContext(), books);
                            recyclerView.setAdapter(adapterBookList);
                            onClickForAll();
                            return false;
                        }
                    });
                }
                if (authorsearch.isChecked()) {
                    searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            books = dataBaseHelper.dependToSearch(dataBaseHelper.LIB_AUTHOR, s);
                            adapterBookList = new AdapterBookList(getApplicationContext(), books);
                            recyclerView.setAdapter(adapterBookList);
                            onClickForAll();
                            return false;
                        }
                    });
                }
                if (booksearch.isChecked()) {
                    searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            books = dataBaseHelper.dependToSearch(dataBaseHelper.LIB_BOOK_NAME, s);
                            adapterBookList = new AdapterBookList(getApplicationContext(), books);
                            recyclerView.setAdapter(adapterBookList);
                            onClickForAll();
                            return false;
                        }
                    });
                }
                drawerLayout.closeDrawers();

            }
        });


    }

}
