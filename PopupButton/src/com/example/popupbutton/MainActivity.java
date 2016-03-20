package com.example.popupbutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

	private PopupButton popupButton;
	private LayoutInflater inflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		popupButton = (PopupButton) findViewById(R.id.btn);
		inflater = LayoutInflater.from(this);
		Log.d("dsd", "---->");
		 View view = inflater.inflate(R.layout.popup,null);
	        ListView lv = (ListView) view.findViewById(R.id.lv);
	        final String[] arr = {"item01","item02","item03","item04","item05","item06","item07","item08","item09","item10"};
	        final PopupAdapter adapter = new PopupAdapter(this,R.layout.popup_item,arr,R.drawable.normal,R.drawable.press);
	        lv.setAdapter(adapter);
	        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                adapter.setPressPostion(position);
	                adapter.notifyDataSetChanged();
	                popupButton.setText(arr[position]);
	                popupButton.hidePopup();
	            }
	        });
	        popupButton.setPopupView(view);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
