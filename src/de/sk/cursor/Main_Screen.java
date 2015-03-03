package de.sk.cursor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main_Screen extends Activity {
    Button add_btn;
    ListView Contact_listview;
    ArrayList<Contact> contact_data = new ArrayList<Contact>();
    Contact_Adapter cAdapter;
    DatabaseHandler db;
    String Toast_msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	try {
	    Contact_listview = (ListView) findViewById(R.id.list);
	    Contact_listview.setItemsCanFocus(false);
	    add_btn = (Button) findViewById(R.id.add_btn);

	    Set_Referash_Data();

	} catch (Exception e) {
	    // TODO: handle exception
	    Log.e("some error", "" + e);
	}
	
	
	add_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent add_user = new Intent(Main_Screen.this,
			Add_Update_User.class);
		add_user.putExtra("called", "add");
		add_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(add_user);
		finish();
	    }
	});



    }

   

    public void Set_Referash_Data() {
	contact_data.clear();
	db = new DatabaseHandler(this);
	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();

	for (int i = 0; i < contact_array_from_db.size(); i++) {

	    int tidno = contact_array_from_db.get(i).getID();
	    String name = contact_array_from_db.get(i).getName();
	    String dec = contact_array_from_db.get(i).getDEC();
	    String extra = contact_array_from_db.get(i).getExtra();
	    Contact cnt = new Contact();
	    cnt.setID(tidno);
	    cnt.setName(name);
	    cnt.setExtra(extra);
	    cnt.setDEC(dec);

	    contact_data.add(cnt);
	}
	db.close();
	cAdapter = new Contact_Adapter(Main_Screen.this, R.layout.listview_row,
		contact_data);
	Contact_listview.setAdapter(cAdapter);
	cAdapter.notifyDataSetChanged();
    }

    public void Show_Toast(String msg) {
	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	Set_Referash_Data();

    }

    public class Contact_Adapter extends ArrayAdapter<Contact> {
	Activity activity;
	int layoutResourceId;
	Contact user;
	ArrayList<Contact> data = new ArrayList<Contact>();

	public Contact_Adapter(Activity act, int layoutResourceId,
		ArrayList<Contact> data) {
	    super(act, layoutResourceId, data);
	    this.layoutResourceId = layoutResourceId;
	    this.activity = act;
	    this.data = data;
	    notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View row = convertView;
	    UserHolder holder = null;

	    if (row == null) {
		LayoutInflater inflater = LayoutInflater.from(activity);

		row = inflater.inflate(layoutResourceId, parent, false);
		holder = new UserHolder();
		holder.name = (TextView) row.findViewById(R.id.user_name_txt);
		holder.extra = (TextView) row.findViewById(R.id.user_extra_txt);
		holder.dec = (TextView) row.findViewById(R.id.user_dec_txt);
		holder.edit = (Button) row.findViewById(R.id.btn_update);
		holder.delete = (Button) row.findViewById(R.id.btn_delete);
		row.setTag(holder);
	    } else {
		holder = (UserHolder) row.getTag();
	    }
	    user = data.get(position);
	    holder.extra.setTag(user.getID());
	    holder.delete.setTag(user.getID());
	    holder.name.setText(user.getName());
	    holder.extra.setText(user.getExtra());
	    holder.dec.setText(user.getDEC());

	    holder.edit.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    Log.i("Edit Button Clicked", "**********");

		    Intent update_user = new Intent(activity,
			    Add_Update_User.class);
		    update_user.putExtra("called", "update");
		    update_user.putExtra("USER_ID", v.getTag().toString());
		    activity.startActivity(update_user);

		}
	    });
	    holder.delete.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(final View v) {
		    // TODO Auto-generated method stub

		    // show a message while loader is loading

		    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
		    adb.setTitle("Löschen?");
		    adb.setMessage("Wirklich Löschen ? ");
		    final int user_id = Integer.parseInt(v.getTag().toString());
		    adb.setNegativeButton("Abbrechen", null);
		    adb.setPositiveButton("Ok",
			    new AlertDialog.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,
					int which) {
				    // MyDataObject.remove(positionToRemove);
				    DatabaseHandler dBHandler = new DatabaseHandler(
					    activity.getApplicationContext());
				    dBHandler.Delete_Contact(user_id);
				    Main_Screen.this.onResume();

				}
			    });
		    adb.show();
		}

	    });
	    return row;

	}

	class UserHolder {
	    TextView name;
	    TextView extra;
	    TextView dec;
	    Button edit;
	    Button delete;
	}

    }
 //   @Override
 //   public boolean onCreateOptionsMenu(Menu menu) {
 //       // Inflate the menu items for use in the action bar
 //       MenuInflater inflater = getMenuInflater();
 //       inflater.inflate(R.menu.main, menu);
 //       return super.onCreateOptionsMenu(menu);
 //   }
 //   @Override
 //   public boolean onOptionsItemSelected(MenuItem item) {
 //    // Handle item selection
 //  switch (item.getItemId()) {
 //    case R.id.item1: setContentView(R.layout.euro);
 //    return true;
 //   default: return super.onOptionsItemSelected(item);
 //   }
 //}
}
