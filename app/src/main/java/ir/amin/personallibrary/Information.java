package ir.amin.personallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class Information extends AppCompatActivity implements View.OnClickListener {
    private AutoCompleteTextView topic, cases1;
    private MaterialButton btnYes, btnok, btn_delete, btn_edit;
    private TextInputEditText etnewnote, borrow, author, translator, description;
    private boolean isEdit = false;
    private int editid;
    private DataBaseHelper db;
    private View parent_view;
    private MaterialCheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        db = new DataBaseHelper(this);
        parent_view = findViewById(android.R.id.content);
        btnok = findViewById(R.id.material_unelevated_button);
        btn_delete = findViewById(R.id.material_unelevated_button3);
        btn_edit = findViewById(R.id.material_unelevated_button4);
        btnYes = findViewById(R.id.material_unelevated_button2);
        checkBox = findViewById(R.id.appCompatCheckBox2);
        borrow = findViewById(R.id.whose);
        etnewnote = findViewById(R.id.bookName);
        author = findViewById(R.id.author);
        translator = findViewById(R.id.translator);
        topic = findViewById(R.id.spinner);
        cases1 = findViewById(R.id.spinner2);
        description = findViewById(R.id.description);

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.spinner);
        String[] subject = new String[]{"رمان ایرانی", "رمان خارجی", "داستان کوتاه", "مذهبی", "تاریخی", "شعر", "ادبی", "آموزشی", "علمی", "زبان انگلیسی", "روانشناسی و موفقیت", "اطلاعات عمومی", "پزشکی", "زندگی نامه", "ورزشی", "آداب و رسوم", "سایر"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdownmenu, subject);
        autoCompleteTextView.setAdapter(adapter);

        AutoCompleteTextView autoCompleteTextView2 = findViewById(R.id.spinner2);
        String[] cases = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdownmenu, cases);
        autoCompleteTextView2.setAdapter(adapter2);

        if (getIntent().hasExtra("isEdit")) {

            if (getIntent().getExtras().getBoolean("isEdit") == true) {
                isEdit = true;
                editid = getIntent().getExtras().getInt("noteId");
            }
        }
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.material_unelevated_button2).setVisibility(View.GONE);
                findViewById(R.id.material_unelevated_button3).setVisibility(View.VISIBLE);
                findViewById(R.id.material_unelevated_button4).setVisibility(View.VISIBLE);
                AutoCompleteTextView autoCompleteTextView = findViewById(R.id.spinner);
                String[] subject = new String[]{"رمان ایرانی", "رمان خارجی", "داستان کوتاه", "مذهبی", "تاریخی", "شعر", "ادبی", "آموزشی", "علمی", "زبان انگلیسی", "روانشناسی و موفقیت", "اطلاعات عمومی", "پزشکی", "زندگی نامه", "ورزشی", "آداب و رسوم", "سایر"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdownmenu, subject);
                autoCompleteTextView.setAdapter(adapter);

                AutoCompleteTextView autoCompleteTextView2 = findViewById(R.id.spinner2);
                String[] cases = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdownmenu, cases);
                autoCompleteTextView2.setAdapter(adapter2);
                etnewnote.setEnabled(true);
                author.setEnabled(true);
                translator.setEnabled(true);
                topic.setEnabled(true);
                cases1.setEnabled(true);
                description.setEnabled(true);
                borrow.setEnabled(true);
                checkBox.setEnabled(true);
            }
        });

        if (isEdit) {
            etnewnote.setText(db.getBook(editid).getBookName());
            author.setText(db.getBook(editid).getAuthor());
            translator.setText(db.getBook(editid).getTranslator());
            topic.setText(db.getBook(editid).getTopic());
            cases1.setText(String.valueOf(db.getBook(editid).getCases()));
            description.setText(db.getBook(editid).getDescription());
            borrow.setText(db.getBook(editid).getLendName());
            if ((db.getBook(editid).getLendCheck()) == 1) {
                checkBox.setChecked(true);
            }
            findViewById(R.id.material_unelevated_button2).setVisibility(View.VISIBLE);
            findViewById(R.id.material_unelevated_button3).setVisibility(View.GONE);
            findViewById(R.id.material_unelevated_button4).setVisibility(View.GONE);
            btnok.setVisibility(View.GONE);

            etnewnote.setEnabled(false);
            author.setEnabled(false);
            translator.setEnabled(false);
            topic.setEnabled(false);
            cases1.setEnabled(false);
            description.setEnabled(false);
            borrow.setEnabled(false);
            checkBox.setEnabled(false);
            autoCompleteTextView2.setAdapter(adapter2);
        } else {
            findViewById(R.id.material_unelevated_button3).setVisibility(View.GONE);
            findViewById(R.id.material_unelevated_button4).setVisibility(View.GONE);
            btnok.setVisibility(View.VISIBLE);
        }
        btnok.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    //وقتی روی برگشت کلیک شد این صفحه را ببند
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //محتویات دکمه ثبت یادداشت جدید
            case R.id.material_unelevated_button: {
                try {
                    String str = etnewnote.getText().toString();
                    String Authors = author.getText().toString();

                    String Translators;
                    if (translator.equals(null)) {
                        Translators = " ";
                    } else {
                        Translators = translator.getText().toString();
                    }

                    String Topics = topic.getText().toString();
                    int Casess = Integer.parseInt(cases1.getText().toString());

                    String Descriptions;
                    if (description.equals(null)) {
                        Descriptions = " ";
                    } else {
                        Descriptions = description.getText().toString();
                    }

                    int b;
                    if (checkBox.isChecked()) {
                        b = 1;
                    } else {
                        b = 0;
                    }

                    String LendNames;
                    if (borrow.equals(null)) {
                        LendNames = " ";
                    } else {
                        LendNames = borrow.getText().toString();
                    }
                    Library book = new Library();
                    book.setBookName(str);
                    book.setAuthor(Authors);
                    book.setTranslator(Translators);
                    book.setTopic(Topics);
                    book.setCases(Casess);
                    book.setDescription(Descriptions);
                    book.setLendCheck(b);
                    book.setLendName(LendNames);
                    db.insertNote(book);
                    Snackbar.make(parent_view, "کتاب با موفقیت ثبت شد", BaseTransientBottomBar.LENGTH_LONG).show();
                    finish();
                    break;
                } catch (Exception e) {
                    Snackbar.make(parent_view, "همه ی موارد را پر کنید", BaseTransientBottomBar.LENGTH_LONG).show();
                }
            }
            //محتویات دکمه ویرایش
            case R.id.material_unelevated_button4: {

                if (isEdit) {
                    try {
                        Library n = new Library();
                        n.setId(editid);
                        n.setBookName(etnewnote.getText().toString());
                        n.setAuthor(author.getText().toString());

                        if (translator.equals(null)) {
                            n.setTranslator(" ");
                        } else {
                            n.setTranslator(translator.getText().toString());
                        }

                        n.setTopic(topic.getText().toString());
                        n.setCases(Integer.parseInt(cases1.getText().toString()));

                        if (description.equals(null)) {
                            n.setDescription(" ");
                        } else {
                            n.setDescription(description.getText().toString());
                        }

                        if (borrow.equals(null)) {
                            n.setLendName(" ");
                        } else {
                            n.setLendName(borrow.getText().toString());
                        }

                        int b;
                        if (checkBox.isChecked()) {
                            b = 1;
                        } else {
                            b = 0;
                        }
                        n.setLendCheck(b);

                        db.editNote(n);
                        Snackbar.make(parent_view, "کتاب با موفقیت ویرایش شد", BaseTransientBottomBar.LENGTH_LONG).show();
                        finish();

                        break;
                    } catch (Exception e) {
                        Snackbar.make(parent_view, "موارد ستاره دار باید پر شوند", BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }
            }
            //محتویات دکمه حذف
            case R.id.material_unelevated_button3: {
                if (isEdit) {
                    db.deleteNote(editid);
                    Snackbar.make(parent_view, "کتاب با موفقیت حذف شد", BaseTransientBottomBar.LENGTH_LONG).show();
                    finish();
                }
                break;
            }
        }
    }
}
