package gr.edu.ihu.gouvalas.apo.ihuandroidexamsjun17;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ApoGouv on 26/6/2017.
 */

public class FetchCompaniesTask extends AsyncTask<String,Void,ArrayList<Company>> {

    private static final String TAG = "FetchCompaniesTask";

    private CompanyAdapter companyAdapter;
    // http://dev.savecash.gr:3000/Merchant/index.json?$orderby=id%20desc
    // or https://www.yummywallet.com/Merchant/index.json?$orderby=id%20desc
    public static final String BASE_DOMAIN = "http://dev.savecash.gr:3000";

    public FetchCompaniesTask(CompanyAdapter companyAdapter){
        this.companyAdapter = companyAdapter;
    }

    private ArrayList<Company> getCompaniesFromJson(String companyJsonStr) throws JSONException {

        ArrayList<Company> companies = new ArrayList<>();

        try {
            JSONArray companiesArray = new JSONArray(companyJsonStr);

            for (int i = 0; i < companiesArray.length(); i++) {
                JSONObject companyObj = companiesArray.getJSONObject(i);

                String comId = companyObj.getString("id");
                String comName = companyObj.getString("legalName");

                JSONObject comCategoryObj = companyObj.getJSONObject("merchantCategory");
                String comAltName = comCategoryObj.getString("alternateName");

                JSONObject addressObj = companyObj.getJSONObject("contactPoint");
                String comAddress = addressObj.getString("streetAddress");

                companies.add(new Company(comId, comName, comAltName, comAddress));
            }

            Log.d(TAG, "getCompaniesFromJson: " + companies.size() + " companies inserted");

            return companies;
        } catch (JSONException e){
            Log.e(TAG, "getCompaniesFromJson: " + e.getMessage(), e );
            e.printStackTrace();
            return companies;
        }

    }// End getCompaniesFromJson()

    @Override
    protected ArrayList<Company> doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String companyJsonStr = null;

        try {
            final String COMPANIES_URL =
                    "/Merchant/index.json?$orderby=id%20desc";

            Uri builtUri = Uri.parse(BASE_DOMAIN+COMPANIES_URL);

            URL url = new URL(builtUri.toString());

            // Create the request to Yummy Wallet server, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            companyJsonStr = buffer.toString();
            return  getCompaniesFromJson(companyJsonStr);
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Company> companies) {
        if (companies != null && companies.size() > 0) {
            this.companyAdapter.clear();
            for (Company c : companies) {
                this.companyAdapter.add(c);
            }
        }
    }

}