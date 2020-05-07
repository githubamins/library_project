package ir.amin.personallibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterBookList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Library> books = new ArrayList<>();

    public AdapterBookList(Context context, List<Library> books) {
        this.context = context;
        this.books = books;
    }
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Library obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View view = LayoutInflater.from(context).inflate(R.layout.list_books, parent, false);
        vh = new ListHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ListHolder) {
            ListHolder listHolder = (ListHolder) holder;
            Library library = books.get(position);

            listHolder.bookName.setText(library.getBookName());
            listHolder.author.setText(library.getAuthor());
            listHolder.translator.setText(library.getTranslator());
            listHolder.translator.setText(library.getTranslator());
            listHolder.topic.setText(library.getTopic());
            listHolder.cases.setText(String.valueOf(library.getCases()));
            listHolder.lay_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null)
                    {
                        mOnItemClickListener.onItemClick(view,books.get(position),position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        private TextView bookName, author, translator, topic, cases;
        private View lay_parent;
        private ImageButton imageButton;

        public ListHolder(@NonNull View i) {
            super(i);
            bookName = i.findViewById(R.id.tv_bookName);
            author = i.findViewById(R.id.tv_author);
            translator = i.findViewById(R.id.tv_translator);
            topic = i.findViewById(R.id.tv_topic);
            cases = i.findViewById(R.id.tv_cases);
            lay_parent = i.findViewById(R.id.parent_layout);

        }
    }
}
