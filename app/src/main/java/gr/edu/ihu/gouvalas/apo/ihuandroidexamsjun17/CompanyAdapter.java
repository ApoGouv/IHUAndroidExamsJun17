package gr.edu.ihu.gouvalas.apo.ihuandroidexamsjun17;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ApoGouv on 26/6/2017.
 */

public class CompanyAdapter extends ArrayAdapter<Company> {

    private ArrayList<Company> companies;
    private Context context;

    public CompanyAdapter(@NonNull Context context, @NonNull ArrayList<Company> companies) {
        super(context, 0, companies);
        this.companies = companies;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        Company c = companies.get(position);

        if(rowView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)rowView.getTag();
        }

        viewHolder.companyDetails.setText(c.getLegalName() + " - " + c.getAltName() + " - " + c.getAddress());


        return  rowView;
    }

    static class ViewHolder {
        public final TextView companyDetails;

        public ViewHolder(View view){
            companyDetails = (TextView)view.findViewById(R.id.li_company_details);
        }
    }

}