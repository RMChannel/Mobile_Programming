package com.example.dbstudent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

class CustomExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Studente> studenti;

    public CustomExpandableAdapter(Context context, List<Studente> studenti) {
        this.context = context;
        this.studenti = studenti;
    }

    @Override
    public int getGroupCount() { return studenti.size(); }

    @Override
    public int getChildrenCount(int groupPosition) { return 1; } // Mostriamo 1 riga di dettagli

    @Override
    public Object getGroup(int groupPosition) { return studenti.get(groupPosition); }

    @Override
    public Object getChild(int groupPosition, int childPosition) { return studenti.get(groupPosition); }

    @Override
    public long getGroupId(int groupPosition) { return groupPosition; }

    @Override
    public long getChildId(int groupPosition, int childPosition) { return childPosition; }

    @Override
    public boolean hasStableIds() { return true; }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Studente s = (Studente) getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        }
        TextView tv = convertView.findViewById(android.R.id.text1);
        tv.setText(s.getNome() + " " + s.getCognome());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Studente s = (Studente) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView tv = convertView.findViewById(android.R.id.text1);
        tv.setPadding(80, 16, 16, 16);
        tv.setText("Matricola: " + s.getMatricola());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }
}