/*
* HuskyList App
* Authors: Vladimir Smirnov and Shelema Bekele
*/
package tcss450.uw.edu.mynewapp;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import tcss450.uw.edu.mynewapp.model.ItemContent;
/**
 * The ItemDetailFragment is a fragment that represents
 * an item add.
 *
 * @author Shelema Bekele
 * @author Vladimir Smirnov
 * @version 1.0
 */
public class ItemDetailFragment extends Fragment {
    /** This variable is a TextView that holds the item ID. */
    private TextView mSellerUserNameTextView;
    /** This variable is a TextView that holds the item title. */
    public TextView mItemTitleTextView;
    /** This variable is a TextView that holds the item price. */
    private TextView mItemPriceTextView;
    /** This variable is a TextView that holds the item condition. */
    private TextView mItemConditionTextView;
    /** This variable is a TextView that holds the item description. */
    private TextView mItemDescriptionTextView;
    /** This variable is a TextView that holds the seller location. */
    private TextView mItemSellerLocationTextView;
    /** This variable is a TextView that holds the seller contact information. */
    private TextView mItemSellerContactTextView;
    /** This variable is a String that holds the given email. */
    private String mEmail;
    /** This variable is a String that holds the given password. */
    private String mTitle;
    /** This constant is a String that represents the item selected. */
    public static String ADS_ITEM_SELECTED = "adsItemSelected";

    private int mID;
    private String Prefix;

    private String mCategory;


    /**
     * This is the ItemDetailFragment constructor.
     */
    public ItemDetailFragment() {
        // Required empty public constructor

    }


    /**
     * This method is called when the fragment is created and is used
     * to create the view.
     *
     * @param inflater is the given layout inflater.
     * @param container is the given view group container.
     * @param savedInstanceState is a bundle that holds the saved state.
     * @return is the inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(mTitle);
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_item_detail, container, false);
        //mSellerUserNameTextView = (TextView) view.findViewById(R.id.add_Seller_userName);
        mItemTitleTextView = (TextView) view.findViewById(R.id.item_title);
        mItemPriceTextView = (TextView) view.findViewById(R.id.item_price);
        mItemConditionTextView = (TextView) view.findViewById(R.id.item_condition);
        mItemDescriptionTextView = (TextView) view.findViewById(R.id.item_Description);
        mItemSellerLocationTextView = (TextView) view.findViewById(R.id.item_seller_location);
        mItemSellerContactTextView = (TextView) view.findViewById(R.id.item_seller_contact);


        Button contact_seller = (Button) view.findViewById(R.id.contact_seller_button);
        contact_seller.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is called when the button is clicked.
             *
             * @param view is the given view.
             */
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + mEmail));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, mTitle);
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });

        Button edit = (Button) view.findViewById(R.id.edit_button);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
//                mListener.update(mItemTitleTextView.getText().toString(), mItemPriceTextView.getText().toString(), mItemConditionTextView.getText().toString(),
//                        mItemDescriptionTextView.getText().toString(), mItemSellerLocationTextView.getText().toString(), mItemSellerContactTextView.getText().toString());
                Intent intent = new Intent(getActivity(), UpdateAddsActivity.class);
                intent.putExtra("Title", mItemTitleTextView.getText().toString());
                intent.putExtra("Price", mItemPriceTextView.getText().toString());
                intent.putExtra("Condition", mItemConditionTextView.getText().toString());
                intent.putExtra("Description", mItemDescriptionTextView.getText().toString());
                intent.putExtra("Location", mItemSellerLocationTextView.getText().toString());
                intent.putExtra("Contact", mItemSellerContactTextView.getText().toString());
                intent.putExtra("Category", mCategory);
                intent.putExtra("Email", mEmail);
                intent.putExtra("ID", mID);
                intent.putExtra("Prefix", Prefix);
                startActivity(intent);

            }
        });

        Button delete = (Button) view.findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
//                DeleteTask task = new DeleteTask();
//                String URL = "http://cssgate.insttech.washington.edu/~sdendaa/deleteHusky.php?cmd=" + mCategory + "&Item_id=" + mID;
//                //     http://cssgate.insttech.washington.edu/~sdendaa/updateHusky.php?cmd=books&Seller_userName=v@gmail.com&Item_title=database&Item_price=kfjdjskfkvmd&Item_condition=kckkdjdjfkv&Item_descriptions=kfjdkfkgkg&Seller_location=fjdjfkfkgkkf&Seller_contact=jfjdjdjfjfjgjjf&Item_category=books&Item_id=8
//                task.execute(URL);
//                Intent intent = new Intent(getActivity(), BookActivity.class);
//                startActivity(intent);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Alert Dialog");
                alertDialogBuilder.setMessage("Are you sure, Do you want to delete this " + mItemTitleTextView.getText().toString()+ "?");

                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        DeleteTask task = new DeleteTask();
                        String URL = "http://cssgate.insttech.washington.edu/~sdendaa/deleteHusky.php?cmd=" + mCategory + "&Item_id=" + mID;
                        task.execute(URL);
                        //finish();
                        Intent intent = new Intent(getActivity(), BookActivity.class);
                        startActivity(intent);
//                       getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.reminder_container, new ReminderListFragment())
//                                .commit();
                    }
                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                });

                //Create and display an alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return view;
    }

    private class DeleteTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s;
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    System.out.println(e);

                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        private void deleteReminderProcess(String result){
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Reminder successfully deleted!"
                            , Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to delete: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getActivity().getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }

    /**
     * This method is used to update the view.
     *
     * @param content is the given BookContent.
     */
    public void updateView(ItemContent content) {
        if (content != null) {
            // mSellerUserNameTextView.setText("Seller USerName: "+content.getSellerUserName());
            mItemTitleTextView.setText("Item Tilte: "+content.getItemTitle());
            mItemPriceTextView.setText(("Item Price: "+content.getItemPrice()));
            mItemConditionTextView.setText("Item condition: "+content.getmItemCondtion());
            mItemDescriptionTextView.setText("Item Description: "+content.getItemDescription());
            mItemSellerLocationTextView.setText("Seller Location: "+content.getSellerLocation());
            mItemSellerContactTextView.setText("Seller Contact: "+content.getSellerContact());
            // mIemImageView.setImageBitmap(content.getItemImage());
            mEmail = content.getSellerContact();
            mTitle = content.getItemTitle();
            getActivity().setTitle(mTitle);
        }
    }

    /**
     * This method is used when the fragment starts.
     */
    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateView((ItemContent) args.getSerializable(ADS_ITEM_SELECTED));

            Bundle bundle = this.getArguments();
            mID = bundle.getInt("ID");
            mCategory = bundle.getString("Category");
            Prefix = bundle.getString("Prefx");


        }
    }
}
