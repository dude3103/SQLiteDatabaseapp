package de.sk.cursor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class Add_Update_User extends Activity {
    EditText add_name, add_dec, add_extra;
    Button add_save_btn, add_view_all, update_btn, update_view_all;
    LinearLayout add_view, update_view;
    String valid_dec = null, valid_extra = null, valid_name = null,
	    Toast_msg = null, valid_user_id = "";
    int USER_ID;

    DatabaseHandler dbHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.add_update_screen);

	// set screen
	Set_Add_Update_Screen();

	// set visibility of view as per calling activity
	String called_from = getIntent().getStringExtra("called");

	if (called_from.equalsIgnoreCase("add")) {
	    add_view.setVisibility(View.VISIBLE);
	    update_view.setVisibility(View.GONE);
	} else {

	    update_view.setVisibility(View.VISIBLE);
	    add_view.setVisibility(View.GONE);
	    USER_ID = Integer.parseInt(getIntent().getStringExtra("USER_ID"));

	    Contact c = dbHandler.Get_Contact(USER_ID);

	    add_name.setText(c.getName());
	    add_dec.setText(c.getDEC());
	    add_extra.setText(c.getExtra());
	    // dbHandler.close();
	}
	add_dec.addTextChangedListener(new TextWatcher() {

	    @Override
	    public void onTextChanged(CharSequence s, int start, int before,
		    int count) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count,
		    int after) {
		// TODO Auto-generated method stub

	    }

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}

	   
		
	    }
	);
	

	add_extra.addTextChangedListener(new TextWatcher() {

	    @Override
	    public void onTextChanged(CharSequence s, int start, int before,
		    int count) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count,
		    int after) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	    }
	});

	add_name.addTextChangedListener(new TextWatcher() {

	    @Override
	    public void onTextChanged(CharSequence s, int start, int before,
		    int count) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count,
		    int after) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void afterTextChanged(Editable s) {

            double doubleValue;
            doubleValue = 0;
            if (s != null) {
                try {
                    doubleValue = Double.parseDouble(s.toString().replace(',', '.'));
                } catch (NumberFormatException e) {
                    //Error
                }
            }
	    }
	});




	add_save_btn.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // check the value state is null or not
            valid_name = add_name.getText().toString();
            valid_dec = add_dec.getText().toString();
            valid_extra = add_extra.getText().toString();

            dbHandler.Add_Contact(new Contact(valid_name,
                    valid_dec, valid_extra));
            Toast_msg = "Eintrag gespeichert";
            Show_Toast(Toast_msg);
            Reset_Text();


        }
    });

	update_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub

		valid_name = add_name.getText().toString();
		valid_dec = add_dec.getText().toString();
		valid_extra = add_extra.getText().toString();

		// check the value state is null or not
		
		    dbHandler.Update_Contact(new Contact(USER_ID, valid_name,
			    valid_dec, valid_extra));
		    dbHandler.close();
		    Toast_msg = "Update Erfolgreich";
		    Show_Toast(Toast_msg);
		    Reset_Text();
		

	    }
	});
	update_view_all.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent view_user = new Intent(Add_Update_User.this,
			Main_Screen.class);
		view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(view_user);
		finish();
	    }
	});

	add_view_all.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent view_user = new Intent(Add_Update_User.this,
			Main_Screen.class);
		view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(view_user);
		finish();
	    }
	});

    }

    public void Set_Add_Update_Screen() {

	add_name = (EditText) findViewById(R.id.add_name);
	add_dec = (EditText) findViewById(R.id.add_dec);
	add_extra = (EditText) findViewById(R.id.add_extra);

	add_save_btn = (Button) findViewById(R.id.add_save_btn);
	update_btn = (Button) findViewById(R.id.update_btn);
	add_view_all = (Button) findViewById(R.id.add_view_all);
	update_view_all = (Button) findViewById(R.id.update_view_all);

	add_view = (LinearLayout) findViewById(R.id.add_view);
	update_view = (LinearLayout) findViewById(R.id.update_view);

	add_view.setVisibility(View.GONE);
	update_view.setVisibility(View.GONE);

    }

    public void Is_Valid_Sign_Number_Validation(int MinLen, int MaxLen,
    	    EditText edt) throws NumberFormatException {
    	if (edt.getText().toString().length() <= 0) {
    	    edt.setError("Number Only");
    	    valid_dec = null;
    	}
    	{
    	    valid_dec = edt.getText().toString();
    	}

    	}

    public void Show_Toast(String msg) {
	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void Reset_Text() {

	add_name.getText().clear();
	add_dec.getText().clear();
	add_extra.getText().clear();



    }
    public class ServicesViewActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // etc...
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }


        }
    }


