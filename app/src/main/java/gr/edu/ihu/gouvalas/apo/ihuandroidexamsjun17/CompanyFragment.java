package gr.edu.ihu.gouvalas.apo.ihuandroidexamsjun17;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ApoGouv on 26/6/2017.
 */

public class CompanyFragment extends Fragment {

    CompanyAdapter companyAdapter;

    public CompanyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchCompanies();
    }

    private void fetchCompanies(){
        FetchCompaniesTask fetchCompaniesTask = new FetchCompaniesTask(companyAdapter);
        fetchCompaniesTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_company, container, false);
        companyAdapter = new CompanyAdapter(getActivity(),new ArrayList<Company>());
        ListView companyListView = (ListView)rootView.findViewById(R.id.lv_companies);
        companyListView.setAdapter(companyAdapter);

        return rootView;
    }

}
