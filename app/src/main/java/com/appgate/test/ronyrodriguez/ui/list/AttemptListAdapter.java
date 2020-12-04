package com.appgate.test.ronyrodriguez.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.appgate.test.ronyrodriguez.R;
import com.appgate.test.ronyrodriguez.datasource.local.Attempt;

import java.util.List;

public class AttemptListAdapter extends RecyclerView.Adapter<AttemptListAdapter.WordViewHolder> {

    private final LayoutInflater mInflater;
    private List<Attempt> attemptList; // Cached copy of words

    AttemptListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (attemptList != null) {
            Attempt current = attemptList.get(position);
            holder.date.setText(current.getDate());
            holder.isvalid.setText(current.getIsValid());
        } else {
            holder.date.setText("");
            holder.isvalid.setText("");
        }
    }

    void setAttempts(List<Attempt> attemptList) {
        this.attemptList = attemptList;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (attemptList != null)
            return attemptList.size();
        else return 0;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView date;
        private final TextView isvalid;

        private WordViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            isvalid = itemView.findViewById(R.id.isvalid);
        }
    }
}