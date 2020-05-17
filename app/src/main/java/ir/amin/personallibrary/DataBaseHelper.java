package ir.amin.personallibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase mDataBase;
    private Context context;
    public static int db_version = 1;
    public static String db_name = "my_db2";
    private static String db_path = "";
    private boolean mNeedUpdate = false;

    public static String LIB_TABLE_NAME = " name ";
    public static String LIB_ID = "id";
    public static String LIB_BOOK_NAME = "bookname";
    public static String LIB_AUTHOR = "author";
    public static String LIB_TRANSLATOR = "translator";
    public static String LIB_TOPIC = "topic";
    public static String LIB_CASES = "cases";
    public static String LIB_DESCRIPTION = "description";
    public static String LIB_LEND_CHECK = "lendcheck";
    public static String LIB_LEND_NAME = "lendname";


    public DataBaseHelper(Context context) {
        super(context, db_name, null, db_version);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            db_path = context.getApplicationInfo().dataDir + "/databases/";
        else
            db_path = "/data/data/" + context.getPackageName() + "/databases/";
        this.context = context;
        copyDataBase();
        this.getReadableDatabase();
    }

    private boolean checkDataBase() {
        File dbFile = new File(db_path + db_name);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = context.getAssets().open(db_name);
        OutputStream mOutput = new FileOutputStream(db_path + db_name);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i > i1)
            mNeedUpdate = true;
    }

    public void insertNote(Library book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LIB_BOOK_NAME, book.getBookName());
        values.put(LIB_AUTHOR, book.getAuthor());
        values.put(LIB_TRANSLATOR, book.getTranslator());
        values.put(LIB_TOPIC, book.getTopic());
        values.put(LIB_CASES, book.getCases());
        values.put(LIB_DESCRIPTION, book.getDescription());
        values.put(LIB_LEND_CHECK, book.getLendCheck());
        values.put(LIB_LEND_NAME, book.getLendName());
        db.insert(LIB_TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Library> getAllNote() {
        ArrayList<Library> books = new ArrayList<>();
        String query = "Select * from " + LIB_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Library book = new Library();
            book.setId(cursor.getInt(cursor.getColumnIndex(LIB_ID)));
            book.setBookName(cursor.getString(cursor.getColumnIndex(LIB_BOOK_NAME)));
            book.setAuthor(cursor.getString(cursor.getColumnIndex(LIB_AUTHOR)));
            book.setTranslator(cursor.getString(cursor.getColumnIndex(LIB_TRANSLATOR)));
            book.setTopic(cursor.getString(cursor.getColumnIndex(LIB_TOPIC)));
            book.setCases(cursor.getInt(cursor.getColumnIndex(LIB_CASES)));
            book.setDescription(cursor.getString(cursor.getColumnIndex(LIB_DESCRIPTION)));
            book.setLendCheck(cursor.getInt(cursor.getColumnIndex(LIB_LEND_CHECK)));
            book.setLendName(cursor.getString(cursor.getColumnIndex(LIB_LEND_NAME)));
            books.add(book);
            cursor.moveToNext();
        }
        return books;
    }

    public boolean deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LIB_TABLE_NAME, LIB_ID + " = ?", new String[]{String.valueOf(id)});
        return true;
    }

    public boolean editBook(Library book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(LIB_BOOK_NAME, book.getBookName());
        value.put(LIB_AUTHOR, book.getAuthor());
        value.put(LIB_TRANSLATOR, book.getTranslator());
        value.put(LIB_TOPIC, book.getTopic());
        value.put(LIB_CASES, book.getCases());
        value.put(LIB_DESCRIPTION, book.getDescription());
        value.put(LIB_LEND_CHECK, book.getLendCheck());
        value.put(LIB_LEND_NAME, book.getLendName());
        db.update(LIB_TABLE_NAME, value, LIB_ID + " = ?", new String[]{String.valueOf(book.getId())});
        db.close();
        return true;
    }

    public Library getBook(int id) {
        Library book = new Library();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(LIB_TABLE_NAME, new String[]{LIB_ID, LIB_BOOK_NAME, LIB_AUTHOR, LIB_TRANSLATOR,
                LIB_TOPIC, LIB_CASES, LIB_DESCRIPTION, LIB_LEND_CHECK, LIB_LEND_NAME}, LIB_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            book.setId(id);
            book.setBookName(cursor.getString(cursor.getColumnIndex(LIB_BOOK_NAME)));
            book.setAuthor(cursor.getString(cursor.getColumnIndex(LIB_AUTHOR)));
            book.setTranslator(cursor.getString(cursor.getColumnIndex(LIB_TRANSLATOR)));
            book.setTopic(cursor.getString(cursor.getColumnIndex(LIB_TOPIC)));
            book.setCases(cursor.getInt(cursor.getColumnIndex(LIB_CASES)));
            book.setDescription(cursor.getString(cursor.getColumnIndex(LIB_DESCRIPTION)));
            book.setLendCheck(cursor.getInt(cursor.getColumnIndex(LIB_LEND_CHECK)));
            book.setLendName(cursor.getString(cursor.getColumnIndex(LIB_LEND_NAME)));
        }
        cursor.close();
        db.close();
        return book;

    }
    public ArrayList<Library> searchBook(String s) {
        ArrayList<Library> books = new ArrayList<>();
        String query = "Select * from " +LIB_TABLE_NAME+ "WHERE "+ LIB_BOOK_NAME +" LIKE " + "'%" + s + "%'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( query, null );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Library book = new Library();
            book.setId( cursor.getInt( cursor.getColumnIndex( LIB_ID ) ) );
            book.setBookName( cursor.getString( cursor.getColumnIndex(LIB_BOOK_NAME ) ) );
            book.setAuthor(cursor.getString(cursor.getColumnIndex(LIB_AUTHOR)));
            book.setTranslator(cursor.getString(cursor.getColumnIndex(LIB_TRANSLATOR)));
            book.setTopic(cursor.getString(cursor.getColumnIndex(LIB_TOPIC)));
            book.setCases(cursor.getInt(cursor.getColumnIndex(LIB_CASES)));
            book.setDescription(cursor.getString(cursor.getColumnIndex(LIB_DESCRIPTION)));
            book.setLendCheck(cursor.getInt(cursor.getColumnIndex(LIB_LEND_CHECK)));
            book.setLendName(cursor.getString(cursor.getColumnIndex(LIB_LEND_NAME)));
            books.add(book);
            cursor.moveToNext();
        }return books;
    }

    public ArrayList<Library> getFilter(String s) {
        ArrayList<Library> books = new ArrayList<>();
        String query = "Select * from " + LIB_TABLE_NAME+ "WHERE "+ LIB_TOPIC +" = " + "'"+s+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( query, null );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Library book = new Library();
            book.setId( cursor.getInt( cursor.getColumnIndex( LIB_ID ) ) );
            book.setBookName( cursor.getString( cursor.getColumnIndex( LIB_BOOK_NAME ) ) );
            book.setAuthor(cursor.getString(cursor.getColumnIndex(LIB_AUTHOR)));
            book.setTranslator(cursor.getString(cursor.getColumnIndex(LIB_TRANSLATOR)));
            book.setTopic(cursor.getString(cursor.getColumnIndex(LIB_TOPIC)));
            book.setCases(cursor.getInt(cursor.getColumnIndex(LIB_CASES)));
            book.setDescription(cursor.getString(cursor.getColumnIndex(LIB_DESCRIPTION)));
            book.setLendCheck(cursor.getInt(cursor.getColumnIndex(LIB_LEND_CHECK)));
            book.setLendName(cursor.getString(cursor.getColumnIndex(LIB_LEND_NAME)));
            books.add(book);
            cursor.moveToNext();
        }return books;
    }

    public ArrayList<Library> getLended() {
        ArrayList<Library> books = new ArrayList<>();
        String query = "Select * from " + LIB_TABLE_NAME+ "WHERE "+ LIB_LEND_CHECK +" = " + 1 ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( query, null );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Library book = new Library();
            book.setId( cursor.getInt( cursor.getColumnIndex( LIB_ID ) ) );
            book.setBookName( cursor.getString( cursor.getColumnIndex( LIB_BOOK_NAME ) ) );
            book.setAuthor(cursor.getString(cursor.getColumnIndex(LIB_AUTHOR)));
            book.setTranslator(cursor.getString(cursor.getColumnIndex(LIB_TRANSLATOR)));
            book.setTopic(cursor.getString(cursor.getColumnIndex(LIB_TOPIC)));
            book.setCases(cursor.getInt(cursor.getColumnIndex(LIB_CASES)));
            book.setDescription(cursor.getString(cursor.getColumnIndex(LIB_DESCRIPTION)));
            book.setLendCheck(cursor.getInt(cursor.getColumnIndex(LIB_LEND_CHECK)));
            book.setLendName(cursor.getString(cursor.getColumnIndex(LIB_LEND_NAME)));
            books.add(book);
            cursor.moveToNext();
        }return books;
    }

    public ArrayList<Library> getNew() {
        ArrayList<Library> books = new ArrayList<>();
        String query = "Select * from " +LIB_TABLE_NAME+ " ORDER BY "+ LIB_ID + " DESC "  ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( query, null );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Library book = new Library();
            book.setId( cursor.getInt( cursor.getColumnIndex( LIB_ID ) ) );
            book.setBookName( cursor.getString( cursor.getColumnIndex(LIB_BOOK_NAME ) ) );
            book.setAuthor(cursor.getString(cursor.getColumnIndex(LIB_AUTHOR)));
            book.setTranslator(cursor.getString(cursor.getColumnIndex(LIB_TRANSLATOR)));
            book.setTopic(cursor.getString(cursor.getColumnIndex(LIB_TOPIC)));
            book.setCases(cursor.getInt(cursor.getColumnIndex(LIB_CASES)));
            book.setDescription(cursor.getString(cursor.getColumnIndex(LIB_DESCRIPTION)));
            book.setLendCheck(cursor.getInt(cursor.getColumnIndex(LIB_LEND_CHECK)));
            book.setLendName(cursor.getString(cursor.getColumnIndex(LIB_LEND_NAME)));
            books.add(book);
            cursor.moveToNext();
        }return books;
    }

    public ArrayList<Library> dependToSearch(String m, String s) {
        ArrayList<Library> books = new ArrayList<>();
        String query = "Select * from " + LIB_TABLE_NAME+ "WHERE "+ m +" LIKE " + "'%" + s + "%'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( query, null );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Library book = new Library();
            book.setId( cursor.getInt( cursor.getColumnIndex( LIB_ID ) ) );
            book.setBookName( cursor.getString( cursor.getColumnIndex( LIB_BOOK_NAME ) ) );
            book.setAuthor(cursor.getString(cursor.getColumnIndex(LIB_AUTHOR)));
            book.setTranslator(cursor.getString(cursor.getColumnIndex(LIB_TRANSLATOR)));
            book.setTopic(cursor.getString(cursor.getColumnIndex(LIB_TOPIC)));
            book.setCases(cursor.getInt(cursor.getColumnIndex(LIB_CASES)));
            book.setDescription(cursor.getString(cursor.getColumnIndex(LIB_DESCRIPTION)));
            book.setLendCheck(cursor.getInt(cursor.getColumnIndex(LIB_LEND_CHECK)));
            book.setLendName(cursor.getString(cursor.getColumnIndex(LIB_LEND_NAME)));
            books.add(book);
            cursor.moveToNext();
        }return books;
    }

    public int getNumberOfBook () {
        String query = "Select * from " + LIB_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        return count;
    }

    public int getAll2() {
        String query = "Select SUM( "+LIB_CASES+" ) as Total from " + LIB_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(cursor.getColumnIndex("Total"));
            return count;
        }
        return -1;
    }

    public int getNumberOfLend () {
        String query = "Select * from " +LIB_TABLE_NAME + " where " + LIB_LEND_CHECK + " = " + 1 ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        return count;
    }
}
