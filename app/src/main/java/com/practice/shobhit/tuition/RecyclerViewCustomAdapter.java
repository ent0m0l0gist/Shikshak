package com.practice.shobhit.tuition;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewCustomAdapter
        extends RecyclerView.Adapter<RecyclerViewCustomAdapter.ViewHolder>{


    private List<Subject> mDataset;
    private Context callerContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView subjectNameTextView;
        public TextView fromClassTextView;
        public TextView toClassTextView;
        public TextView deleteSubjectButton;


        public ViewHolder(View vRoot) {
            super(vRoot);

            subjectNameTextView = (TextView)vRoot.findViewById(R.id.list_element_subject_name);
            fromClassTextView = (TextView)vRoot.findViewById(R.id.list_element_from_class);
            toClassTextView = (TextView)vRoot.findViewById(R.id.list_element_to_class);
            deleteSubjectButton = (TextView) vRoot.findViewById(R.id.button_delete_subject);

            deleteSubjectButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.equals(deleteSubjectButton)){
                removeAt(getPosition());
            }
        }
    }

    private void removeAt(int position){
        Subject removedSubject = mDataset.get(position);
        RemoveSubjectAsyncTask asyncTask = new RemoveSubjectAsyncTask(callerContext);
        String[] subjectData = {removedSubject.subjName, removedSubject.fromClass, removedSubject.toClass, new TeacherStore(callerContext).getLoggedInTeacher().email};
        asyncTask.execute(subjectData);

        mDataset.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
        notifyItemRangeChanged(position, mDataset.size());
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewCustomAdapter(List<Subject> myDataset, Context context) {
        mDataset = myDataset;
        this.callerContext=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_sub_fragment_recycler_element, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.subjectNameTextView.setText(mDataset.get(position).subjName);
        holder.fromClassTextView.setText(mDataset.get(position).fromClass);
        holder.toClassTextView.setText(mDataset.get(position).toClass);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}