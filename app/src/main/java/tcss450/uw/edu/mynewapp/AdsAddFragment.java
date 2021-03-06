/*
* HuskyList App
* Authors: Vladimir Smirnov and Shelema Bekele
*/
package tcss450.uw.edu.mynewapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

/**
 * The AdsAddFragment is responsible for creating new ads.
 *
 * @author Shelema Bekele
 * @author Vladimir Smirnov
 * @version 1.0
 */
public class AdsAddFragment extends Fragment {
    /** This variable holds the book add listener. */
    private ItemAddListener mListener;
    /** This variable holds the EditText for the item ID. */
    private EditText mSellerUserName;
//    /** This variable holds the EditText for the item ID. */
//    private EditText mItemIdEditText;
    /** This variable holds the TextView for the item title. */
    private TextView mItemTitleEditText;
    /** This variable holds the TextView for the item price. */
    private TextView mItemPriceEditText;
    /** This variable holds the TextView for the item condition. */
    private TextView mItemConditionEditText;
    /** This variable holds the TextView for the item description. */
    private TextView mItemDescriptionEditText;
    /** This variable holds the TextView for the seller location. */
    private TextView mItemSellerLocationEditText;
    /** This variable holds the TextView for the seller contact information. */
    private TextView mItemSellerContactEditText;
    //private ImageView  mItemImage;

   //public BookActivity activity;
   private static String CURRENT_URL
           = "http://cssgate.insttech.washington.edu/~sdendaa/AddBooks.php?";
    private final static String HOUSEHOLD_URL
            = "http://cssgate.insttech.washington.edu/~sdendaa/AddHouseHold.php?";
    private final static String CELLPHONE_URL
            = "http://cssgate.insttech.washington.edu/~sdendaa/AddCellPhone.php?";
    private final static String COMPUTER_URL
            = "http://cssgate.insttech.washington.edu/~sdendaa/AddComputer.php?";
    private final static String VEHICLE_URL
            = "http://cssgate.insttech.washington.edu/~sdendaa/AddVehicle.php?";
    private final static String VIDEOGAME_URL
            = "http://cssgate.insttech.washington.edu/~sdendaa/AddVideoGame.php?";

    private String mCategory;

    public AdsAddFragment() {
        // Required empty public constructor
        mCategory = "books";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ads_add, container, false);
       // mSellerUserName = (EditText) v.findViewById(R.id.add_Seller_userName);
       // mItemIdEditText = (EditText) v.findViewById(R.id.add_item_id);
        mItemTitleEditText = (EditText) v.findViewById(R.id.add_item_title);
        mItemPriceEditText = (EditText) v.findViewById(R.id.add_item_price);
        mItemConditionEditText = (EditText) v.findViewById(R.id.add_item_condition);
        mItemDescriptionEditText = (EditText) v.findViewById(R.id.add_item_Description);
        mItemSellerLocationEditText = (EditText) v.findViewById(R.id.add_item_seller_location);
        mItemSellerContactEditText = (EditText) v.findViewById(R.id.add_item_seller_contact);
       // mItemImage = (ImageView)v.findViewById(R.id.image_Display);



        final String[] items = new String[]{"Books", "Vehicles", "Computers", "Cellphones", "Video Gaming", "Household Items"};
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                int theID = (int) id;
                switch (items[theID]) {
                    case "Household Items":
                        CURRENT_URL = HOUSEHOLD_URL;
                        mCategory = "houseHoldItems";
                        break;
                    case "Cellphones":
                        CURRENT_URL = CELLPHONE_URL;
                        mCategory = "cellPhones";
                        break;
                    case "Vehicles":
                        CURRENT_URL = VEHICLE_URL;
                        mCategory =  "vehicles";
                        break;
                    case "Video Gaming":
                        CURRENT_URL = VIDEOGAME_URL;
                        mCategory = "videoGames";
                        break;
                    case "Computers":
                        CURRENT_URL = COMPUTER_URL;
                        mCategory = "computers";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getActivity().setTitle("Create New Ads");
        Button addAdseButton = (Button) v.findViewById(R.id.add_ads_button);
        addAdseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildCourseURL(v);
                mListener.addItems(url);

            }
        });

        //Up load button
        Button upload_btn = (Button) v.findViewById(R.id.Image_Button);
        upload_btn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // To open up a gallery browser
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);
            }});

        return v;
    }

    public interface ItemAddListener {
        void addItems(String url);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemAddListener) {
            mListener = (ItemAddListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AdsAddListener");
        }
    }
    private String buildCourseURL(View v) {
        StringBuilder sb = new StringBuilder(CURRENT_URL);
        try {
            SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("tcss450.uw.edu.mynewapp.PREFS", Context.MODE_PRIVATE);

//            String result = mItemPriceEditText.getText().toString() + mItemConditionEditText.getText().toString() +
//                    mItemDescriptionEditText.getText().toString() + mItemSellerLocationEditText.getText().toString() + mItemSellerContactEditText.getText().toString();
//            StringBuilder StringBuilder = new StringBuilder();
//            StringBuilder.append("" + Math.abs(result.hashCode() % 1000000));
//            String ItemID = StringBuilder.toString();
//            sb.append("Item_id=");
//            sb.append(ItemID);

            String ID = mSharedPreferences.getString("ID", "");
            String SellerUsername = ID;
            sb.append("Seller_userName=");
            sb.append(SellerUsername);

            String ItemTitle = mItemTitleEditText.getText().toString();
            sb.append("&Item_title=");
            sb.append(URLEncoder.encode(ItemTitle, "UTF-8"));

            String ItemPrice = mItemPriceEditText.getText().toString();
            sb.append("&Item_price=");
            sb.append(URLEncoder.encode(ItemPrice, "UTF-8"));

            String ItemCondition = mItemConditionEditText.getText().toString();
            sb.append("&Item_condition=");
            sb.append(URLEncoder.encode(ItemCondition, "UTF-8"));

            String ItemDescription = mItemDescriptionEditText.getText().toString();
            sb.append("&Item_descriptions=");
            sb.append(URLEncoder.encode(ItemDescription, "UTF-8"));

            String SellerLocation = mItemSellerLocationEditText.getText().toString();
            sb.append("&Seller_location=");
            sb.append(URLEncoder.encode(SellerLocation, "UTF-8"));

            String SellerContact = mItemSellerContactEditText.getText().toString();
            sb.append("&Seller_contact=");
            sb.append(URLEncoder.encode(SellerContact, "UTF-8"));

            String Category = mCategory;
            sb.append("&Item_category=");
            sb.append(URLEncoder.encode(Category, "UTF-8"));




            Log.i("AdsAddFragment", sb.toString());
        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }


}
